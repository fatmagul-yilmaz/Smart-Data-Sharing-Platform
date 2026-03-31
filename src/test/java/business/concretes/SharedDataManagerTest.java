package business.concretes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import business.dto.requests.CreateSharedDataRequest;
import dataAccess.abstracts.SharedDataRepository;
import dataAccess.abstracts.UserRepository;
import entities.concretes.User;
import entities.enums.ActionType;

@ExtendWith(MockitoExtension.class)
public class SharedDataManagerTest {

    @Mock
    private SharedDataRepository sharedDataRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ActionLogManager actionLogService;

    @InjectMocks
    private SharedDataManager sharedDataManager;

    @Test
    public void whenAddCalledWithValidRequest_itShouldSaveDataAndLog() {
        CreateSharedDataRequest request = new CreateSharedDataRequest();
        request.setTitle("Test Başlığı");
        request.setContent("Test İçeriği");

        User mockUser = new User();
        mockUser.setEmail("test@test.com");

        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        when(auth.getName()).thenReturn("test@test.com");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(mockUser));
        when(sharedDataRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        sharedDataManager.add(request);

        verify(sharedDataRepository, times(1)).save(any()); // Kaydetme çağrıldı mı?
        verify(actionLogService, times(1)).log(eq(ActionType.CREATE), anyString(), any()); // Log atıldı mı?
    }
}