package com.imooc.exception;

import lombok.Data;

/**
 * @author zhang.suxing
 * @date 2020/6/26 14:43
 **/
@Data
public class UserNotExitException extends RuntimeException {
    private String id;

    public UserNotExitException(String id) {
        super("user not exit");
        this.id = id;
    }


}
