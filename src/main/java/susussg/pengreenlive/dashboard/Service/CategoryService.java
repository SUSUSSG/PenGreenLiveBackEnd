package susussg.pengreenlive.dashboard.Service;

import java.util.List;
import susussg.pengreenlive.dashboard.DTO.BroadcastCategoryDTO;
import susussg.pengreenlive.dashboard.DTO.CategoryDTO;

public interface CategoryService {

  List<CategoryDTO> getAllCategoryList();

  List<BroadcastCategoryDTO> getAllBroadcastCategoryList();

}
