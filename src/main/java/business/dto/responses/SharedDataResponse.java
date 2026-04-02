package business.dto.responses;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SharedDataResponse {
    private Long id;
    private String title;
    private String content;
    private String ownerName;
    private LocalDateTime createdAt;
    private Integer version;
}