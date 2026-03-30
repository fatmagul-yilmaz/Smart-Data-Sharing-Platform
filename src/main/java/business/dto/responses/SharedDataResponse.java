package business.dto.responses;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SharedDataResponse {
    private Long id;
    private String title;
    private String content;
    private String ownerName; // Sadece sahibinin adını dönelim (E-posta gizli kalsın)
    private LocalDateTime createdAt;
    private Integer version;
}