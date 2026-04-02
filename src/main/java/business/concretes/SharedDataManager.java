package business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import business.abstracts.ActionLogService;
import business.abstracts.SharedDataService;
import business.dto.requests.CreateSharedDataRequest;
import business.dto.requests.UpdateSharedDataRequest;
import business.dto.responses.SharedDataResponse;
import dataAccess.abstracts.SharedDataRepository;
import dataAccess.abstracts.UserRepository;
import entities.concretes.SharedData;
import entities.concretes.User;
import entities.enums.ActionType;
import entities.enums.DataStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SharedDataManager implements SharedDataService {

    private final SharedDataRepository sharedDataRepository;
    private final UserRepository userRepository;
    private final ActionLogService actionLogService;

    @Override
    public void add(CreateSharedDataRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        SharedData sharedData = new SharedData();
        sharedData.setTitle(request.getTitle());
        sharedData.setContent(request.getContent());
        sharedData.setOwner(user);
        sharedData.setStatus(DataStatus.ACTIVE);
        sharedData.setVersion(1);

        SharedData savedData = sharedDataRepository.save(sharedData);

        actionLogService.log(ActionType.CREATE,
                "Yeni veri paylaşıldı: " + savedData.getTitle(),
                savedData.getId());
    }

    @Override
    public List<SharedDataResponse> getAll(Pageable pageable) { // Pageable parametresi eklendi
        return sharedDataRepository.findAll(pageable).stream().map(data -> {
            SharedDataResponse response = new SharedDataResponse();
            response.setId(data.getId());
            response.setTitle(data.getTitle());
            response.setContent(data.getContent());
            response.setVersion(data.getVersion());
            response.setCreatedAt(data.getCreatedAt());

            if (data.getOwner() != null) {
                response.setOwnerName(data.getOwner().getFirstName() + " " + data.getOwner().getLastName());
            }
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(UpdateSharedDataRequest request) {
        SharedData oldData = sharedDataRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Veri bulunamadı!"));

        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!oldData.getOwner().getEmail().equals(currentEmail)) {
            throw new RuntimeException("Bu veriyi güncelleme yetkiniz yok!");
        }

        oldData.setStatus(DataStatus.ARCHIVED);
        sharedDataRepository.save(oldData);

        SharedData newData = new SharedData();
        newData.setTitle(request.getNewTitle());
        newData.setContent(request.getNewContent());
        newData.setOwner(oldData.getOwner());
        newData.setStatus(DataStatus.ACTIVE);
        newData.setVersion(oldData.getVersion() + 1);

        SharedData savedNewData = sharedDataRepository.save(newData);

        actionLogService.log(ActionType.UPDATE,
                "Veri versiyonu yükseltildi. Eski ID: " + oldData.getId() + " -> Yeni ID: " + savedNewData.getId(),
                savedNewData.getId());
    }
}