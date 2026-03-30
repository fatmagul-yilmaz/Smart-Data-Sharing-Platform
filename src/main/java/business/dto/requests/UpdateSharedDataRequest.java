package business.dto.requests;

import lombok.Data;

@Data
public class UpdateSharedDataRequest {
    private Long id; // Güncellenmek istenen eski verinin ID'si
    private String newTitle;
    private String newContent;
}