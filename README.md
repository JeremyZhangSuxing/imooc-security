# security
1.package相关  
  如果需要package 成可运行的jar 请在有main() 函数的的module pom.xml 指定 打包的插件为 【org.springframework.boot】并指定<finalName>
2.尽量避免使用自动版本控制工具易造成冲突
3.security 的相关配置在 cloud 2.xx以上版本已经不允许使用 请通过配置bean的方式来完成security配置
4.正则表达式 限定 @PathValue 类型
5.@JsonView的用法
6.自定义@Valid 注解 减少业务代码  bean不需要declare为 组件，spring会自动注入？？
7.request 从不透明和服务端发出时  反生error获取到的response不相同---》  原因:BasicErrorController 入参不同的不同实现逻辑
  1)browser----》model and view
  2)app（client）---》 json response 
8.[spring boot中 restFul api 错误处理机制 自定义异常处理机制] 自定义exception 构建[ExceptionAdvise] 拦截异常给前端顶定制化返回信息和相关字段
9.使用bean 注入的方式将第三方的 filter组件加入到你的project中去
  filter: problems [1]只能从http request/response 获取一部分信息  because filter是 j2ee 规范中定义的
  interceptor：三个步骤完成一次请求的调用，自定义一个interceptor 观测执行流程且 抛出的exception 不可以吃掉
  aop [@Around]的  
 1)pom配置
       <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-devtools</artifactId>
                <scope>runtime</scope>
                <optional>true</optional>
       </dependency>
       
 2)compiler [abcd]配置
 3)Crtl+Shilt+Alt+/  registry的两个配置 [whenRunning] [...edit]
 4)restart
11.过滤器链  一般的Filter 可配置可修改 但是 ExceptionTranslationFilter(catch 后面的filter抛出来的异常，如果是没有login会根据filter配置引导用户完成login) FilterSecurityInterceptor  不可修改，且顺序不可变更
任何一个filter成功完成用户login，会在请求上做一个标记，用户已经认证成功

FilterSecurityInterceptor 依赖BrowserSecurityConfig的配置

12.同一用户login will generate different password ,spring的security 会每次用不同的盐序列生成不同的加密密码 提高防护性
13.前后台之间 传递日期类型参数，直接使用时间戳更方便
14.自定义校验注解
 
      
       
  



  
  
  