package validator;

import com.imooc.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.util.annotation.ConstraintAnnotationDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author zhang.suxing
 * @date 2020/6/25 23:38
 * <p>
 * spring 会自动将MyConstraintValidator declare 成 spring 的 bean？
 **/
@Slf4j
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {
    public static final String UUU_III = "'sd";

    @Autowired
    private HelloService helloService;

    @Override
    public void initialize(MyConstraint constraintAnnotation) {
        log.info("++++++MyConstraintValidator initialize");
    }

    /**
     * 可以使用任何校验逻辑来检验你的注解 只需要自己实现即可
     *
     * @param value                      注解字段接收到的值
     * @param constraintValidatorContext 通过注解对象拿到的注解里的属性
     * @return
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        log.info("++++++MyConstraintValidator isValid param--->" + value);
        helloService.greeting("jeremy");
        ConstraintAnnotationDescriptor annotationDescriptor = ((ConstraintDescriptorImpl) ((ConstraintValidatorContextImpl) constraintValidatorContext).getConstraintDescriptor()).getAnnotationDescriptor();
        String message = annotationDescriptor.getMessage();
        log.info("++++++MyConstraintValidator isValid message--->" + message);
        return false;
    }
}
