package susussg.pengreenlive.dashboard.Service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.dashboard.DTO.BroadcastCategoryDTO;
import susussg.pengreenlive.dashboard.DTO.CategoryDTO;
import susussg.pengreenlive.dashboard.Mapper.CategoryMapper;

@Service
@Log4j2
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private final CategoryMapper categoryMapper;


  @Override
  public List<CategoryDTO> getAllCategoryList() {
    return categoryMapper.selectCategoryList();
  }

  @Override
  public List<BroadcastCategoryDTO> getAllBroadcastCategoryList() {
    return categoryMapper.selectBroadcastCategoryList();
  }
}
