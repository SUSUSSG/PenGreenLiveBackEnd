package susussg.pengreenlive.test.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.test.DTO.TestDTO;
import susussg.pengreenlive.test.Mapper.TestMapper;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

  @Autowired
  private final TestMapper testMapper;

  @Override
  public TestDTO selectforbiddenword() {
    return  testMapper.selectforbiddenword();
  }
}
