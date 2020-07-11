package com.imooc.security.core.validate.processor.impl;

import com.imooc.security.core.exception.ValidateException;
import com.imooc.security.core.validate.enums.ValidateCodeType;
import com.imooc.security.core.validate.processor.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhang.suxing
 * @date 2020/7/11 14:02
 **/
@Component
public class ValidateProcessorHolder {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessorMap;

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType validateCodeType) {
        return findValidateCodeProcessor(validateCodeType.getParamNameOnValidate());
    }

    private ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String processorKey = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        if (!validateCodeProcessorMap.containsKey(processorKey)) {
            throw new ValidateException("验证码处理器" + processorKey + "不存在");
        }
        return validateCodeProcessorMap.get(processorKey);
    }


}
