package com.imooc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhang.suxing
 * @date 2020/6/25 16:18
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCondition {
    private String userName;

    private int age;

    private int ageTo;

    private String param;
}
