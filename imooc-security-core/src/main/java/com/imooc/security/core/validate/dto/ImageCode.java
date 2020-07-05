package com.imooc.security.core.validate.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhang.suxing
 * @date 2020/7/5 9:46
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageCode implements Serializable {

    /**
     * 图片二维码
     */
    private BufferedImage image;
    /**
     * 验证码
     */
    private String code;
    /**
     * 过期时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime expireTime;

    public ImageCode(BufferedImage image, String code, int expire) {
        this.image = image;
        this.code = code;
        //当前时间 + 有效时间
        this.expireTime = LocalDateTime.now().plusSeconds(expire);
    }


    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        ImageIO.write(image, "JPEG", out);

    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        ImageIO.read(in);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
