package business.concretes;

import business.dto.requests.UserLoginRequest;
import business.dto.requests.UserRegisterRequest;
import dataAccess.abstracts.UserRepository;
import entities.concretes.User;
import entities.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManager {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
     * Yeni kullanıcı kaydı oluşturur.
     */
    public String register(UserRegisterRequest request) {
        // 1. Email kontrolü
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Hata: Bu email adresi zaten sistemde kayıtlı!";
        }

        // 2. Yeni kullanıcı entity'sini oluştur ve map'le
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        
        // 3. Şifreyi BCrypt ile şifrele
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // 4. Rol ata
        user.setRole(Role.USER);

        userRepository.save(user);
        return "Kullanıcı başarıyla kaydedildi.";
    }

    /**
     * Kullanıcı girişi yapar ve geçerli bir JWT Token döner.
     */
    public String login(UserLoginRequest request) {
        // 1. Spring Security AuthenticationManager ile kimlik doğrulaması yap
        // Bu adım, email ve şifre doğru değilse otomatik olarak Exception fırlatır.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), 
                        request.getPassword()
                )
        );

        // 2. Kimlik doğrulama başarılıysa JWT Token üret ve dön
        return jwtService.generateToken(request.getEmail());
    }
}