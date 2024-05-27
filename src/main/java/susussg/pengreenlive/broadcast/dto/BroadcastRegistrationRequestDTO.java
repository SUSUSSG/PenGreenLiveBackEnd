package susussg.pengreenlive.broadcast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BroadcastRegistrationRequestDTO {
    private String broadcastTitle;
    private String broadcastSummary;
    private LocalDateTime broadcastScheduledTime;
    private String categoryCd;
    private List<BroadcastProductDTO> registeredProducts;
    private List<String> notices;
    private List<FaqDTO> qa;
    private List<String> benefits;
    private String image;
}
