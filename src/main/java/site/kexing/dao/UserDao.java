package site.kexing.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import site.kexing.pojo.User;

@Repository
@Mapper
public interface UserDao {
    User selectUserByNickName(@Param("nickname") String nickname);
}
