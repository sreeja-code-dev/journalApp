package com.sreeja.journalApp.controller;

import com.sreeja.journalApp.entity.JournalEntry;
import com.sreeja.journalApp.repository.JournalEntryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/entries")   // ✅ match frontend calls
public class JournalEntryController {

    @Autowired
    private JournalEntryRepository repository;

    // GET all entries
    @GetMapping
    public List<JournalEntry> getAll() {
        return repository.findAll();
    }

    // POST new entry
    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry entry) {
        return repository.save(entry);
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJournalEntryById(@PathVariable String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // UPDATE by ID
    @PutMapping("/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable String id,
                                                               @RequestBody JournalEntry entry) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        entry.setId(id);
        return ResponseEntity.ok(repository.save(entry));
    }

    // Reflect endpoint (Flask integration)
    @PostMapping("/reflect")
    public ResponseEntity<String> reflect(@RequestBody JournalEntry entry) {
        RestTemplate restTemplate = new RestTemplate();
        String flaskUrl = "http://localhost:5000/embed";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<JournalEntry> request = new HttpEntity<>(entry, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(flaskUrl, request, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error communicating with Flask: " + e.getMessage());
        }
    }
}
