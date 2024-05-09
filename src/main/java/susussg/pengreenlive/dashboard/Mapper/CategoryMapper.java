package susussg.pengreenlive.dashboard.Mapper;


import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import susussg.pengreenlive.dashboard.DTO.BroadcastCategoryDTO;
import susussg.pengreenlive.dashboard.DTO.CategoryDTO;

@Mapper
public interface CategoryMapper {

  List<CategoryDTO> selectCategoryList();

  List<BroadcastCategoryDTO> selectBroadcastCategoryList();

}
