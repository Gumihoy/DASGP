package com.gumihoy.dasgp.admin.dao;

//import org.apache.ibatis.annotations.Mapper;
import com.gumihoy.dasgp.admin.common.User;
import org.springframework.stereotype.Repository;

/**
 * @author kongtong.ouyang on 2018/10/10.
 */
@Repository
//@Mapper
public interface UserDAO {


    User getByEmail(String email);

    void insert(User user);


    int updatePasswordByEmail(String email, String password);


}
