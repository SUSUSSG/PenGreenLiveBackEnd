package susussg.pengreenlive.util.Service;

import java.util.List;
import susussg.pengreenlive.util.DTO.ForbiddenWordDTO;

public interface ForbiddenWordService {

    List<ForbiddenWordDTO> getDefaultForbiddenWordList();
    List<ForbiddenWordDTO> getIndividualForbiddenWordList(int broadcastSeq);
    void addForbiddenWord(long broadcastSeq, String forbiddenWord);

}
