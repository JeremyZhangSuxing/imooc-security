package com.imooc.security.core.validate.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author zhang.suxing
 * @date 2020/7/5 9:49
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidateCode {

    /**
     * 验证码
     */
    private String code;
    /**
     * 过期时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime expireTime;

    public ValidateCode(String code, int expire) {
        this.code = code;
        //当前时间 + 有效时间
        this.expireTime = LocalDateTime.now().plusSeconds(expire);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
