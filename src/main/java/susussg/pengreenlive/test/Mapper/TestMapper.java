package susussg.pengreenlive.test.Mapper;

import org.apache.ibatis.annotations.Mapper;
import susussg.pengreenlive.test.DTO.TestDTO;

@Mapper
public interface TestMapper {

   TestDTO selectForbiddenword();

}
