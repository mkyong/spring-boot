package com.mkyong;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookAssistant {

    private final ChatClient plainClient;   // no notes
    private final ChatClient ragClient;     // with notes

    public BookAssistant(ChatClient.Builder builder, VectorStore vectorStore) {

        // A normal chat client. It only knows what it learned during training.
        this.plainClient = builder.build();

        // mutate() copies the plain client, then adds the RAG advisor on top
        this.ragClient = this.plainClient.mutate()
                .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build())
                .build();
    }

    // Asks the model directly
    public String askPlain(String question) {
        return this.plainClient.prompt()
                .user(question)
                .call()
                .content();
    }

    // Searches your notes first, then asks the model
    public String askWithRag(String question) {
        return this.ragClient.prompt()
                .user(question)
                .call()
                .content();
    }

    // Same as askWithRag, but it also shows the notes behind the answer
    public String askAndShowNotes(String question) {

        // chatClientResponse() gives you the answer AND the advisor context
        ChatClientResponse response = this.ragClient.prompt()
                .user(question)
                .call()
                .chatClientResponse();

        // The advisor parked the matching notes under this key
        @SuppressWarnings("unchecked")
        List<Document> notesUsed = (List<Document>) response.context()
                .get(QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS);

        StringBuilder out = new StringBuilder();
        out.append("ANSWER:\n");
        out.append(response.chatResponse().getResult().getOutput().getText());
        out.append("\n\nNOTES USED:\n");

        // Print every note the vector store handed over
        for (Document note : notesUsed) {
            out.append("- ").append(note.getText()).append("\n");
        }

        return out.toString();
    }

}
