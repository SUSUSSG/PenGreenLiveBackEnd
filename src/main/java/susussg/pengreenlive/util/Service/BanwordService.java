package susussg.pengreenlive.util.Service;

import jakarta.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import org.ahocorasick.trie.PayloadEmit;
import org.ahocorasick.trie.PayloadTrie;
import org.ahocorasick.trie.PayloadTrie.PayloadTrieBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.util.DTO.BanwordDTO;
import susussg.pengreenlive.util.DTO.BanwordValidationResultDTO;
import susussg.pengreenlive.util.DTO.ForbiddenWordDTO;

@Service
public class BanwordService{

    @Autowired
    private ForbiddenWordService forbiddenWordService;

    private PayloadTrie<BanwordDTO> banwordTrie;

    @PostConstruct
    public void init() {
        List<ForbiddenWordDTO> forbiddenWords = forbiddenWordService.getDefaultForbiddenWordList();
        PayloadTrie.PayloadTrieBuilder<BanwordDTO> trieBuilder = PayloadTrie.<BanwordDTO>builder();
        forbiddenWords.forEach(fw -> trieBuilder.addKeyword(fw.getForbiddenword(), new BanwordDTO(fw.getForbiddenword())));
        banwordTrie = trieBuilder.build();
    }

    public BanwordValidationResultDTO validate(String originSentence) {
        Collection<PayloadEmit<BanwordDTO>> banwordResult = banwordTrie.parseText(originSentence);
        return new BanwordValidationResultDTO(originSentence, banwordResult);
    }
}
