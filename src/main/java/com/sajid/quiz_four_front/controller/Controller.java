package com.sajid.quiz_four_front.controller;

import com.sajid.quiz_four_front.model.Dashboard;
import com.sajid.quiz_four_front.model.Login;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@org.springframework.stereotype.Controller
public class Controller {

    public final RestClient restClient = RestClient.create();


    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request, Model model) {

        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("my-token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null || token.isEmpty()) {
            return "redirect:/login";
        }

        ResponseEntity<Dashboard> res = restClient.get()
                .uri("http://localhost:5050/api/dashboard")
                .header("Authorization", token)
                .retrieve()
                .toEntity(Dashboard.class);

        Dashboard dashboard = res.getBody();

        model.addAttribute("dashboard", dashboard);
        model.addAttribute("orders", dashboard.getOderList());

        return "dashboard";
    }


    @GetMapping("/login")
    public String login(Model model) {
        Login login = new Login();
        model.addAttribute("login", login);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Login login, HttpServletResponse response) {
        IO.println(login);

        ResponseEntity<Map> res = restClient.post()
                .uri("http://localhost:5050/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(login)
                .retrieve()
                .toEntity(Map.class);
        String token = (String) res.getBody().get("token");

        Cookie cookie = new Cookie("my-token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);

        response.addCookie(cookie);
        IO.println("Login successful");
        return "redirect:/dashboard";
    }
}
