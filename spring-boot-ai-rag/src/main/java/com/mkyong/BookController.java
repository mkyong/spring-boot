package com.mkyong;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final BookAssistant assistant;

    public BookController(BookAssistant assistant) {
        this.assistant = assistant;
    }

    // No notes. The model guesses.
    @GetMapping("/ask-plain")
    public String askPlain(@RequestParam String q) {
        return assistant.askPlain(q);
    }

    // With notes. The model reads first.
    @GetMapping("/ask")
    public String ask(@RequestParam String q) {
        return assistant.askWithRag(q);
    }

    // Shows the answer plus the notes behind it
    @GetMapping("/ask-debug")
    public String askDebug(@RequestParam String q) {
        return assistant.askAndShowNotes(q);
    }

}
