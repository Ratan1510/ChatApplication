package com.chat.app.controller;

import com.chat.app.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final Set<String> users = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/join")
    @SendTo("/topic/users")
    public String join(@RequestParam String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (users.contains(username)) {
            throw new IllegalArgumentException("Username already taken");
        }
        users.add(username);
        messagingTemplate.convertAndSend("/topic/users", username + " has joined the chat.");
        return username;
    }

    @MessageMapping("/leave")
    @SendTo("/topic/users")
    public String leave(@RequestParam String username) {
        users.remove(username);
        messagingTemplate.convertAndSend("/topic/users", username + " has left the chat.");
        return username;
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        if (message == null || message.getContent() == null || message.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        return message;
    }

    @MessageMapping("/typing")
    @SendTo("/topic/typing")
    public String typingNotification(String username) {
        return username;
    }

    @GetMapping("/chatapp")
    public String chat() {
        return "index"; // Changed to match existing template
    }
}
