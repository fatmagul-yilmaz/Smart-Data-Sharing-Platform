package business.concretes;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import business.abstracts.ActionLogService;
import dataAccess.abstracts.ActionLogRepository;
import dataAccess.abstracts.UserRepository;
import entities.concretes.ActionLog;
import entities.enums.ActionType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActionLogManager implements ActionLogService {

    private final ActionLogRepository actionLogRepository;
    private final UserRepository userRepository;

    @Override
    public void log(ActionType actionType, String detail, Long targetDataId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        ActionLog log = new ActionLog();
        log.setActionType(actionType);
        log.setActionDetail(detail);
        log.setTargetDataId(targetDataId);
        log.setCreatedAt(LocalDateTime.now());
        
        // İşlemi yapan kullanıcıyı bağla
        userRepository.findByEmail(email).ifPresent(log::setUser);

        actionLogRepository.save(log);
    }
}