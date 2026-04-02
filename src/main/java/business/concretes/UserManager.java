package business.concretes;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import business.dto.requests.UserLoginRequest;
import business.dto.requests.UserRegisterRequest;
import dataAccess.abstracts.UserRepository;
import entities.concretes.User;
import entities.enums.Role;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserManager {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String register(UserRegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Hata: Bu email adresi zaten sistemde kayıtlı!";
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return "Kullanıcı başarıyla kaydedildi.";
    }

    public String login(UserLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        return jwtService.generateToken(request.getEmail());
    }
}