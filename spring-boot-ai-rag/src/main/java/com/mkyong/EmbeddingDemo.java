package com.mkyong;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EmbeddingDemo implements CommandLineRunner {

    private final EmbeddingModel embeddingModel;

    public EmbeddingDemo(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @Override
    public void run(String... args) {

        // Turn one sentence into numbers
        float[] numbers = embeddingModel.embed("Java 25 in Action");

        // Which model answered, and how many numbers came back
        System.out.println("Model  : " + embeddingModel.getClass().getSimpleName());
        System.out.println("Length : " + numbers.length);
    }
}
