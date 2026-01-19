package com.prodapt.license_tracker.auth.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.prodapt.license_tracker.user.entity.User;
import com.prodapt.license_tracker.user.repository.UserRepository;

@Component
public class LoggedInUserUtil {

    private final UserRepository userRepository;

    public LoggedInUserUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Integer getCurrentUserId() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String email = authentication.getName();

        return userRepository
                .findByEmail(email)
                .map(User::getUserId)
                .orElse(null);
    }
    
    public String getCurrentUserRole() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "UNKNOWN";
        }

        return authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .orElse("UNKNOWN");
    }
}
