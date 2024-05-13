package susussg.pengreenlive.main.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainCarouselDTO {
    private int broadcastSeq;
    private String broadcastTitle;
    private byte[] broadcastImage;
    private String broadcastSummary;
    private List<MainCarouselProductDTO> products;
}
