package business.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateSharedDataRequest {
    
    @NotBlank(message = "Başlık boş olamaz")
    @Size(min = 3, max = 100, message = "Başlık en az 3, en fazla 100 karakter olmalıdır")
    private String title;

    @NotBlank(message = "İçerik boş olamaz")
    private String content;
}