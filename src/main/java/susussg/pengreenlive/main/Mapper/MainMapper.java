package susussg.pengreenlive.main.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import susussg.pengreenlive.main.DTO.MainCarouselDTO;

@Mapper
public interface MainMapper {
    List<MainCarouselDTO> selectMainCarousels();
}
