package com.sreeja.journalApp.repository;

import com.sreeja.journalApp.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {
    // You can add custom query methods here if needed
}
