package susussg.pengreenlive.util.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import susussg.pengreenlive.util.DTO.ForbiddenWordDTO;

@Mapper
public interface ForbiddenWordMapper {

    List<ForbiddenWordDTO> selectDefaultForbiddenWordList();

    List<ForbiddenWordDTO> selectIndividualForbiddenWordList(int broadcastSeq);
}
