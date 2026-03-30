package business.abstracts;

import business.dto.requests.UserLoginRequest;
import business.dto.requests.UserRegisterRequest;

public interface UserService {
    String register(UserRegisterRequest request);

    String login(UserLoginRequest request);
}