package susussg.pengreenlive.test.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.test.Service.TestService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class TestController {

  @Autowired
  private TestService testService;

  @GetMapping("test")
  public String testMethod(){
    System.out.println(testService.selectForbiddenword().getForbiddenword());

    return testService.selectForbiddenword().getForbiddenword();
  }


}
