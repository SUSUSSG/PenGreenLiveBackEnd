package susussg.pengreenlive.user.Mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    String selectUserNmByUUID(String uuid);

}
