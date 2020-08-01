# security
1.package相关  
  如果需要package 成可运行的jar 请在有main() 函数的的module pom.xml 指定 打包的插件为 【org.springframework.boot】并指定<finalName>  
2.尽量避免使用自动版本控制工具易造成冲突  
3.security 的相关配置在 cloud 2.xx以上版本已经不允许使用 请通过配置bean的方式来完成security配置  
4.正则表达式 限定 @PathValue 类型  
5.@JsonView的用法  
6.自定义@Valid 注解 减少业务代码  bean不需要declare为 组件，spring会自动注入？？  
7.request 从不透明和服务端发出时  反生error获取到的response不相同--->  原因:BasicErrorController 入参不同的不同实现逻辑  
  1)browser---->model and view  
  2)app（client）---> json response  
8.[spring boot中 restFul api 错误处理机制 自定义异常处理机制] 自定义exception 构建[ExceptionAdvise] 拦截异常给前端顶定制化返回信息和相关字段  
9.使用bean 注入的方式将第三方的 filter组件加入到你的project中去  
  filter: problems [1]只能从http request/response 获取一部分信息  because filter是 j2ee 规范中定义的  
  interceptor：三个步骤完成一次请求的调用，自定义一个interceptor 观测执行流程且 抛出的exception 不可以吃掉  
  aop [@Around]的  
 1)pom配置  
       【<dependency>  
                <groupId>org.springframework.boot</groupId>  
               <artifactId>spring-boot-devtools</artifactId>  
                <scope>runtime</scope>  
                <optional>true</optional>  
       </dependency> 】
       
 2)compiler [abcd]配置  
 3)Crtl+Shilt+Alt+/  registry的两个配置 [whenRunning] [...edit]  
 4)restart  
11.过滤器链  一般的Filter 可配置可修改 但是 ExceptionTranslationFilter(catch 后面的filter抛出来的异常，如果是没有login会根据filter配置引导用户完成login) FilterSecurityInterceptor  不可修改，且顺序不可变更  
任何一个filter成功完成用户login，会在请求上做一个标记，用户已经认证成功  

FilterSecurityInterceptor 依赖BrowserSecurityConfig的配置  

12.同一用户login will generate different password ,spring的security 会每次用不同的盐序列生成不同的加密密码 提高防护性  
13.前后台之间 传递日期类型参数，直接使用时间戳更方便  
14.自定义校验注解  
15.local repository push to github , warning using SSH keys  
steps:  
1) choose 【import into version control 】--->【share...】 do not forget config your git information  

16.spring BOOT restFul 默认的异常处理机制

BasicErrorController 默认的错误处理的控制器 处理 /error的请求  对应两个 error 方法 ---> 看请求head里面是否带有 "text/html" 

17.api 的拦截顺序是 controller--->Aspect--->ControllerAdvise--->Interceptor--->Filter

18.remember me 源码流程  AbstractAuthenticationProcessingFilter  rememberMeService  创建token写入db

没有密码的时候 RememberMeAuthenticationFilter 97 是否有身份认证过 从请求的cookie里面拿 token与db的token 做验证 通过token登录


19. 模板方法模式
实现方式：
a)     父类模板类（规定要执行的方法和顺序，只关心方法的定义及顺序，不关心方法实现）
b)     子类实现类（实现a规定要执行的方法，只关心方法实现，不关心调用顺序
 优点：
     1）封装不变部分，扩展可变部分：把认为不变部分的算法封装到父类实现，可变部分则可以通过继承来实现，很容易扩展。
     2）提取公共部分代码，便于维护。
     3）行为由父类控制，由子类实现。
 缺点：
     模板方法模式颠倒了我们平常的设计习惯：抽象类负责声明最抽象、最一般的事物属性和方法，实现类实现具体的事物属性和方法。在复杂的项目中可能会带来代码阅读的难度。
 
      
       
  



  
  
  