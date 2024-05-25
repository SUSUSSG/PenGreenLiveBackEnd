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

    private static int currentBroadcastSeq = 6;
    @PostConstruct
    public void init() {
        loadDefaultForbiddenWords();
        updateIndividualBanwordTrie();
    }
    private void buildBanwordTrie() {
        List<ForbiddenWordDTO> allForbiddenWords = new ArrayList<>(defaultForbiddenWords);
        List<ForbiddenWordDTO> individualForbiddenWords = forbiddenWordService.getIndividualForbiddenWordList(currentBroadcastSeq); // 예시로 6번 방송.
        allForbiddenWords.addAll(individualForbiddenWords);

        PayloadTrie.PayloadTrieBuilder<BanwordDTO> trieBuilder = PayloadTrie.<BanwordDTO>builder();
        allForbiddenWords.forEach(fw -> trieBuilder.addKeyword(fw.getForbiddenword(), new BanwordDTO(fw.getForbiddenword())));
        banwordTrie = trieBuilder.build();
    }

    private void loadDefaultForbiddenWords() {
        defaultForbiddenWords = forbiddenWordService.getDefaultForbiddenWordList();
        buildBanwordTrie(); // 기본 금칙어 로드 후 트라이 재구성
    }

    @Scheduled(fixedDelay = 3000) // 30,000 milliseconds = 30 seconds
    public void updateIndividualBanwordTrie() {
        buildBanwordTrie(); // 개별 금칙어 업데이트 후 트라이 재구성
    }



    public BanwordValidationResultDTO validate(String originSentence) {
        Collection<PayloadEmit<BanwordDTO>> banwordResult = banwordTrie.parseText(originSentence);
        return new BanwordValidationResultDTO(originSentence, banwordResult);
    }
}
