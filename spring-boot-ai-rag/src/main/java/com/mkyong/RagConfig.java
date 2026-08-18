package com.mkyong;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.List;

@Configuration
public class RagConfig {

    // The numbers land here, next to your pom.xml
    private static final File STORE_FILE = new File("vectorstore.json");

    @Bean
    public SimpleVectorStore vectorStore(EmbeddingModel embeddingModel) {

        SimpleVectorStore store = SimpleVectorStore.builder(embeddingModel).build();

        if (STORE_FILE.exists()) {
            // Reads numbers straight off your disk. No API call. No cost.
            store.load(STORE_FILE);
            System.out.println("Loaded vectors from " + STORE_FILE.getName());
        } else {
            // First run only. This is the one that costs money.
            store.add(notes());
            store.save(STORE_FILE);
            System.out.println("Embedded notes and saved to " + STORE_FILE.getName());
        }

        return store;
    }

    private List<Document> notes() {
        return List.of(
                new Document("Mkyong Book Shop opens at 9am and closes at 6pm, Monday to Friday."),
                new Document("The book 'Java 25 in Action' costs RM 89 and is written by Ali Rahman."),
                new Document("The book 'Spring Boot 4 Recipes' costs RM 120 and has 480 pages."),
                new Document("Members get 15 percent off every book on the first Saturday of the month."),
                new Document("Mkyong Book Shop does not sell e-books, only paper books.")
        );
    }
}
