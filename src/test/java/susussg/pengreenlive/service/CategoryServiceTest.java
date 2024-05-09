package susussg.pengreenlive.service;

import java.util.List;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import susussg.pengreenlive.dashboard.DTO.CategoryDTO;
import susussg.pengreenlive.dashboard.Service.CategoryService;


@SpringBootTest
@Log4j2
public class CategoryServiceTest {


  @Autowired
  private final CategoryService categoryService;

  @Autowired
  public CategoryServiceTest(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @Test
  public void getAllCategoryTest(){
    List<CategoryDTO> categoryDTOList = categoryService.getAllCategoryList();
    log.info(categoryDTOList);
  }


}
