package com.gumihoy.dasgp.admin.web;

import com.gumihoy.dasgp.admin.common.APIResult;
import com.gumihoy.dasgp.admin.dao.UserDAO;
import com.gumihoy.dasgp.admin.web.vo.UserChangeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @author kongtong.ouyang on 2018/10/9.
 */
@RestController
@RequestMapping
public class UserApi {

    private static final Logger log = LoggerFactory.getLogger(UserApi.class);

    @Autowired(required = false)
    private UserDAO userDAO;

    @PostMapping(value = "/login")
    public APIResult<String> login(String email,
                                   String password) {
        try {
            userDAO.getByEmail(email);
        } catch (Exception e) {
            log.error("afaaaa123 {}", email, e);
        }

        return APIResult.succ();
    }

    @PostMapping(value = "/change")
    public APIResult<String> change(@RequestBody UserChangeVO userChangeVO) {

        userDAO.updatePasswordByEmail(userChangeVO.getEmail(), userChangeVO.getPassword());

        return APIResult.succ();
    }

    public static void main(String[] args) {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println(runtimeMXBean.getName());
        System.out.println(Thread.currentThread().getId());
        System.out.println(System.currentTimeMillis());
        System.out.println(Integer.parseInt("0001", 16));
    }
}
