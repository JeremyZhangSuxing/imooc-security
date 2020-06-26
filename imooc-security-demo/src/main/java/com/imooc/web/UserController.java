package com.imooc.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.dto.User;
import com.imooc.dto.UserCondition;
import com.imooc.exception.UserNotExitException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zhang.suxing
 * @date 2020/6/25 14:21
 **/
@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {

    @GetMapping
    @JsonView(User.UserSimpleView.class)
    public List<User> query(UserCondition userCondition, Pageable pageable) {
        log.info("++++++test query+++++++++" + userCondition.toString());
        log.info("++++++test query+++++++++" + ReflectionToStringBuilder.toString(pageable, ToStringStyle.MULTI_LINE_STYLE));
        List<User> users = Arrays.asList(new User("1", "jeremy", "123456", new Date()),
                new User("2", userCondition.getUserName(), "123456", new Date()),
                new User());
        return users;

    }

    /**
     * 正则表达式限制了用户id必须为数字
     */
    @GetMapping(value = "/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getUserInfo(@PathVariable(name = "id") String id) {
        log.info("++++++test getUserInfo+++++++++" + id);
//        return new User("1", "jeremy", "123456", new Date());
        throw new UserNotExitException(id);
    }


    @PostMapping
    @JsonView(User.UserSimpleView.class)
    public User createUserInfo(@Valid @RequestBody User user) {
//        if (errors.hasErrors()) {
//            errors.getAllErrors().forEach(error -> {
//                FieldError fieldError = (FieldError) error;
//                log.info("++++++test createUserInfo+++++++++print error[" + fieldError.getField() + "---" + fieldError.getDefaultMessage() + "]");
//            });
//        }
        user.setId("1");
        log.info("++++++test createUserInfo+++++++++" + user.toString());
        return user;
    }

    @PutMapping(value = "/{id:\\d+}")
    @JsonView(User.UserSimpleView.class)
    public User updateUserInfo(@Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(v -> {
                FieldError fieldError = (FieldError) v;
                log.info("++++++test createUserInfo+++++++++print error[" + fieldError.getField() + "---" + fieldError.getDefaultMessage() + "]");
            });
        }
        user.setId("1");
        log.info("++++++test updateUserInfo+++++++++" + user.toString());
        return user;
    }

    @DeleteMapping(value = "/{id:\\d+}")
    public String deleteUser(@PathVariable String id) {
        log.info("++++++start deleteUser+++++++++" + id);
        return id;
    }
}
