package api.controllers;

import business.abstracts.SharedDataService;
import business.dto.requests.CreateSharedDataRequest;
import business.dto.requests.UpdateSharedDataRequest;
import business.dto.responses.SharedDataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class SharedDataController {

    private final SharedDataService sharedDataService;

    @PostMapping("/add")
    public String add( @Valid @RequestBody CreateSharedDataRequest request) {
        sharedDataService.add(request);
        return "Veri başarıyla paylaşıldı!";
    }

    @GetMapping("/all")
public List<SharedDataResponse> getAll() {
    return sharedDataService.getAll();
}

    @PutMapping("/update")
public String update(@RequestBody UpdateSharedDataRequest request) {
    sharedDataService.update(request);
    return "Veri güncellendi (Yeni versiyon oluşturuldu, eski kayıt arşivlendi).";
}
}