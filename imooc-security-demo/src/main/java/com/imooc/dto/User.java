package com.imooc.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.imooc.validator.MyConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @author zhang.suxing
 * @date 2020/6/25 14:23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @JsonView(UserSimpleView.class)
    private String id;

    @MyConstraint(message = "测试自定义注解")
    @JsonView(UserSimpleView.class)
    private String userName;

    @JsonView(UserDetailView.class)
    @NotBlank(message = "密码不能为空")
    private String passWord;

    @Past(message = "生日时间必须小于当前时间")
    private Date birthDay;

    public interface UserSimpleView {
    }

    public interface UserDetailView extends UserSimpleView {
    }
}
