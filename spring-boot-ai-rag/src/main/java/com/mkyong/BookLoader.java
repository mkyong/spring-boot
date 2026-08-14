package com.mkyong;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookLoader implements CommandLineRunner {

    private final VectorStore vectorStore;

    public BookLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(String... args) {

        // These are your private facts. No AI model has ever seen them.
        List<Document> notes = List.of(
                new Document("Mkyong Book Shop opens at 9am and closes at 6pm, Monday to Friday."),
                new Document("The book 'Java 25 in Action' costs RM 89 and is written by Ali Rahman."),
                new Document("The book 'Spring Boot 4 Recipes' costs RM 120 and has 480 pages."),
                new Document("Members get 15 percent off every book on the first Saturday of the month."),
                new Document("Mkyong Book Shop does not sell e-books, only paper books.")
        );

        // This one line turns all 5 notes into numbers and saves them
        vectorStore.add(notes);

        System.out.println("Loaded " + notes.size() + " notes into the vector store.");
    }
}
