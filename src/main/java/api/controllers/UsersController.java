package api.controllers;

import business.concretes.UserManager;
import business.dto.requests.UserLoginRequest;
import business.dto.requests.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserManager userManager;

    @PostMapping("/register")
    public String register(@RequestBody UserRegisterRequest request) {
        return userManager.register(request);
    }

    @PostMapping("/login")
public String login(@RequestBody UserLoginRequest request) {
    return userManager.login(request);
}
}