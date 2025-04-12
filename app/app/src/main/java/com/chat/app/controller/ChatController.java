package com.chat.app.controller;

import com.chat.app.model.ChatMessage;
import com.chat.app.service.ActiveUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ActiveUserService activeUserService;

    @PostMapping("/setName")
    public String setName(@RequestParam("name") String name, HttpSession session) {
        if (session.getAttribute("username") != null) {
            return "redirect:/chat";
        }
        session.setAttribute("username", name);
        return "redirect:/chat";
    }

    @GetMapping("/chat")
    public String chatPage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/setName";
        }
        model.addAttribute("username", username);
        return "index";
    }

    @MessageMapping("/join")
    @SendTo("/topic/users")
    public String join(@Payload String username, SimpMessageHeaderAccessor headerAccessor) {
        if (username == null || username.trim().isEmpty()) {
            return "Username cannot be empty";
        }
        if (activeUserService.getActiveUsers().contains(username)) {
            return "Username already taken";
        }
        activeUserService.addUser(username);
        headerAccessor.getSessionAttributes().put("username", username);
        messagingTemplate.convertAndSend("/topic/users", username + " has joined the chat.");
        return username;
    }

    @MessageMapping("/leave")
    @SendTo("/topic/users")
    public String leave(@Payload String username) {
        activeUserService.removeUser(username);
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
        return "index";
    }

    public SimpMessagingTemplate getMessagingTemplate() {
        return messagingTemplate;
    }
}