package br.com.quipux.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private Map<String, String> userStore = new HashMap<>();

    public UserService() {
        userStore.put("admin", "admin");
        userStore.put("user", "user");
    }

    public boolean isValidUser(String username, String password) {
        String storedPassword = userStore.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
}
