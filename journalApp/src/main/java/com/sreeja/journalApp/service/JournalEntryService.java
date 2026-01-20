package com.sreeja.journalApp.service;

import com.sreeja.journalApp.entity.JournalEntry;
import com.sreeja.journalApp.repository.JournalEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marks this class as a service component for Spring to manage
public class JournalEntryService {

    private final JournalEntryRepository repository;

    // Constructor injection: Spring will automatically provide the repository
    public JournalEntryService(JournalEntryRepository repository) {
        this.repository = repository;
    }

    // Get all journal entries
    public List<JournalEntry> getAll() {
        return repository.findAll();
    }

    // Get a single entry by ID (String for MongoDB)
    public Optional<JournalEntry> getById(String id) {
        return repository.findById(id);
    }

    // Create a new journal entry
    public JournalEntry create(JournalEntry entry) {
        // MongoDB will auto-generate ObjectId if id is null
        entry.setId(null);
        return repository.save(entry);
    }

    // Update an existing entry
    public Optional<JournalEntry> update(String id, JournalEntry updatedEntry) {
        return repository.findById(id).map(existingEntry -> {
            existingEntry.setTitle(updatedEntry.getTitle());
            existingEntry.setContent(updatedEntry.getContent());
            return repository.save(existingEntry);
        });
    }

    // Delete an entry by ID
    public boolean delete(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
