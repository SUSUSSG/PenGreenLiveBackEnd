package susussg.pengreenlive.gpt.dto;

import lombok.Data;

@Data
public class ChatRequest {
    private String message;

    public ChatRequest() {
    }

    public ChatRequest(String message) {
        this.message = message;
    }

}
