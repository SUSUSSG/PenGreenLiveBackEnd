package susussg.pengreenlive.util.Service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.ahocorasick.trie.PayloadEmit;
import org.ahocorasick.trie.PayloadTrie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.util.DTO.BanwordDTO;
import susussg.pengreenlive.util.DTO.BanwordValidationResultDTO;
import susussg.pengreenlive.util.DTO.ForbiddenWordDTO;

@Service
public class BanwordService {

    @Autowired
    private ForbiddenWordService forbiddenWordService;

    private PayloadTrie<BanwordDTO> banwordTrie;
    private List<ForbiddenWordDTO> defaultForbiddenWords;

    @PostConstruct
    public void init() {
        loadDefaultForbiddenWords();
    }

    private void buildBanwordTrie(int broadcastSeq) {
        List<ForbiddenWordDTO> allForbiddenWords = new ArrayList<>(defaultForbiddenWords);
        List<ForbiddenWordDTO> individualForbiddenWords = forbiddenWordService.getIndividualForbiddenWordList(broadcastSeq);
        allForbiddenWords.addAll(individualForbiddenWords);

        PayloadTrie.PayloadTrieBuilder<BanwordDTO> trieBuilder = PayloadTrie.<BanwordDTO>builder();
        allForbiddenWords.forEach(fw -> trieBuilder.addKeyword(fw.getForbiddenword(), new BanwordDTO(fw.getForbiddenword())));
        banwordTrie = trieBuilder.build();
    }

    private void loadDefaultForbiddenWords() {
        defaultForbiddenWords = forbiddenWordService.getDefaultForbiddenWordList();
    }

    public void updateIndividualBanwordTrie(int broadcastSeq) {
        buildBanwordTrie(broadcastSeq); // 개별 금칙어 업데이트 후 트라이 재구성
    }

    public BanwordValidationResultDTO validate(String originSentence) {
        Collection<PayloadEmit<BanwordDTO>> banwordResult = banwordTrie.parseText(originSentence);
        return new BanwordValidationResultDTO(originSentence, banwordResult);
    }
}
