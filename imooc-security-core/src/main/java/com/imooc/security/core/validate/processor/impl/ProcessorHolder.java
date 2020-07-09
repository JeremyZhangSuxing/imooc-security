package com.imooc.security.core.validate.processor.impl;

import com.imooc.security.core.validate.processor.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhang.suxing
 * @date 2020/7/9 23:11
 **/
@Component
public class ProcessorHolder {
    @Autowired
    private Map<String, ValidateCodeProcessor> codeProcessorMap;

    public ValidateCodeProcessor initialProcessor(String type) {
        return codeProcessorMap.get(type);
    }
}
