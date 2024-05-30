package susussg.pengreenlive.dashboard.Controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.dashboard.DTO.BroadcastCategoryDTO;
import susussg.pengreenlive.dashboard.DTO.CategoryDTO;
import susussg.pengreenlive.dashboard.Service.CategoryService;


@Log4j2
@RestController
@RequestMapping("/api")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @Operation(summary = "상품 카테고리 조회", description = "상품 카테고리를 조회합니다.")
  @GetMapping("/category")
  public List<CategoryDTO> getAllCategories() {
    return categoryService.getAllCategoryList();
  }

  @Operation(summary = "방송 카테고리 조회", description = "방송 카테고리를 조회합니다.")
  @GetMapping("/broadcastcategory")
  public List<BroadcastCategoryDTO> getAllBroadcastCategories() {
    return categoryService.getAllBroadcastCategoryList();
  }

}
