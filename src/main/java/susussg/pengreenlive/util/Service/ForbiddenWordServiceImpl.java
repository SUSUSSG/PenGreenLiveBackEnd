package susussg.pengreenlive.util.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import susussg.pengreenlive.util.DTO.ForbiddenWordDTO;
import susussg.pengreenlive.util.Mapper.ForbiddenWordMapper;

@Service
public class ForbiddenWordServiceImpl implements ForbiddenWordService {

    @Autowired
    ForbiddenWordMapper forbiddenWordMapper;

    @Override
    public List<ForbiddenWordDTO> getDefaultForbiddenWordList() {
        return forbiddenWordMapper.selectDefaultForbiddenWordList();
    }

    @Override
    public List<ForbiddenWordDTO> getIndividualForbiddenWordList(int broadcastSeq) {
        return forbiddenWordMapper.selectIndividualForbiddenWordList(broadcastSeq);
    }

    @Override
    public void addForbiddenWord(long broadcastSeq, String forbiddenWord) {
        forbiddenWordMapper.insertForbiddenWord(broadcastSeq, forbiddenWord);
    }
}
