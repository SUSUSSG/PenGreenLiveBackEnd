package susussg.pengreenlive.util.DTO;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ahocorasick.trie.PayloadEmit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BanwordValidationResultDTO {
    private String originalText;
    private Collection<PayloadEmit<BanwordDTO>> banwordsFound;

    public void BanwordValidationResult(String originalText, Collection<PayloadEmit<BanwordDTO>> banwordsFound) {
        this.originalText = originalText;
        this.banwordsFound = banwordsFound;
    }

}
