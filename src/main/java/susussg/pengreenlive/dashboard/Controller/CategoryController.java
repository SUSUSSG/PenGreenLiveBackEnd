package susussg.pengreenlive.dashboard.Controller;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.dashboard.DTO.BroadcastCategoryDTO;
import susussg.pengreenlive.dashboard.DTO.CategoryDTO;
import susussg.pengreenlive.dashboard.Service.CategoryService;


@Log4j2
@RestController
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("/category")
  public List<CategoryDTO> getAllCategories() {
    return categoryService.getAllCategoryList();
  }

  @GetMapping("/broadcastcategory")
  public List<BroadcastCategoryDTO> getAllBroadcastCategories() {
    return categoryService.getAllBroadcastCategoryList();
  }

}
