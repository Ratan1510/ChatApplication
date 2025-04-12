package com.chat.app.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ActiveUserService {
    private Set<String> activeUsers = new HashSet<>();

    public void addUser(String username) {
        activeUsers.add(username);
    }

    public void removeUser(String username) {
        activeUsers.remove(username);
    }

    public Set<String> getActiveUsers() {
        return activeUsers;
    }
}
