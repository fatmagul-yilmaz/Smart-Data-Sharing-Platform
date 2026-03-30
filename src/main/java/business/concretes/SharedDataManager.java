package business.concretes;

import business.abstracts.SharedDataService;
import business.abstracts.ActionLogService; // Yeni eklenen
import business.dto.requests.CreateSharedDataRequest;
import business.dto.requests.UpdateSharedDataRequest;
import business.dto.responses.SharedDataResponse;
import dataAccess.abstracts.SharedDataRepository;
import dataAccess.abstracts.UserRepository;
import entities.concretes.SharedData;
import entities.concretes.User;
import entities.enums.ActionType; // Yeni eklenen
import entities.enums.DataStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SharedDataManager implements SharedDataService {

    private final SharedDataRepository sharedDataRepository;
    private final UserRepository userRepository;
    private final ActionLogService actionLogService; // Log servisini enjekte ettik

    @Override
    public void add(CreateSharedDataRequest request) {
        // 1. O an login olan kullanıcının emailini Context'ten al
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // 2. Kullanıcıyı bul
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        // 3. Veriyi oluştur
        SharedData sharedData = new SharedData();
        sharedData.setTitle(request.getTitle());
        sharedData.setContent(request.getContent());
        sharedData.setOwner(user);
        sharedData.setStatus(DataStatus.ACTIVE);
        sharedData.setVersion(1);

        SharedData savedData = sharedDataRepository.save(sharedData);

        // 4. ACTION LOG: Yeni veri ekleme işlemini kaydet
        actionLogService.log(ActionType.CREATE, 
            "Yeni veri paylaşıldı: " + savedData.getTitle(), 
            savedData.getId());
    }

    @Override
    public List<SharedDataResponse> getAll() {
        // Not: Genelde sadece ACTIVE olanları listelemek istersin
        // Ama şimdilik tüm geçmişi görmek için findAll() bırakıyorum
        return sharedDataRepository.findAll().stream().map(data -> {
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
        // 1. Güncellenmek istenen eski veriyi bul
        SharedData oldData = sharedDataRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Veri bulunamadı!"));

        // 2. Yetki Kontrolü
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!oldData.getOwner().getEmail().equals(currentEmail)) {
            throw new RuntimeException("Bu veriyi güncelleme yetkiniz yok!");
        }

        // 3. ESKİ KAYDI ARŞİVLE
        oldData.setStatus(DataStatus.ARCHIVED);
        sharedDataRepository.save(oldData);

        // 4. YENİ KAYIT OLUŞTUR (Yeni Versiyon)
        SharedData newData = new SharedData();
        newData.setTitle(request.getNewTitle());
        newData.setContent(request.getNewContent());
        newData.setOwner(oldData.getOwner());
        newData.setStatus(DataStatus.ACTIVE);
        newData.setVersion(oldData.getVersion() + 1);

        SharedData savedNewData = sharedDataRepository.save(newData);

        // 5. ACTION LOG: Güncelleme işlemini detaylıca kaydet
        actionLogService.log(ActionType.UPDATE, 
            "Veri versiyonu yükseltildi. Eski ID: " + oldData.getId() + " -> Yeni ID: " + savedNewData.getId(), 
            savedNewData.getId());
    }
}