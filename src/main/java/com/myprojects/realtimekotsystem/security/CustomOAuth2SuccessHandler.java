package com.myprojects.realtimekotsystem.security;

import com.myprojects.realtimekotsystem.entity.User;
import com.myprojects.realtimekotsystem.repository.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepo usersRepository;
    private final JwtUtils jwtUtils;

    public CustomOAuth2SuccessHandler(UserRepo usersRepository, JwtUtils jwtUtils) {
        this.usersRepository = usersRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        // 1. Lazy registration logic in Postgres
        User user = usersRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setUsername(oAuth2User.getAttribute("name"));
                    newUser.setRole("ROLE_ADMIN"); // Default role
                    return usersRepository.save(newUser);
                });

        // 2. Generate the stateless token with database metadata
        Long restaurantId = (user.getRestaurant() != null) ? user.getRestaurant().getId() : null;
        String token = jwtUtils.generateToken(user.getEmail(), user.getRole(), restaurantId);

        // 3. Build Redirect URL targeting the React local server
        String targetUrl = "http://localhost:5173/oauth2/redirect?token=" + token;

        if (restaurantId == null) {
            targetUrl += "&setup=true"; // Signals React to display onboarding panel
        }

        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
