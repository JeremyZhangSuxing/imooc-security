package com.imooc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author zhang.suxing
 * @date 2020/6/25 13:57
 * <p>
 * 坑：springBoot 启动类需要和你的test case 在一个目录下面
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserControllerTest {

    /**
     * mock 的环境不会真正地启动tomcat速度会很快
     */
    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * 伪造的mvc环境
     */
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        log.info("++++++start a test environment++++++++");
        //构建mvc环境
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * test get request
     * github.com/json-path/JsonPath
     */
    @Test
    public void whenQuerySuccess() throws Exception {
        String mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .param("userName", "jeremy")
                .param("age", "10")
                .param("ageTo", "50")
                .param("param", "param")
                .param("size", "10")
                .param("page", "1")
                .param("sort", "age decs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();
        log.info("++++++" + mvcResult);
    }

    /**
     * test get user info
     */
    @Test
    public void whenGetUserInfo() throws Exception {
        String mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("jeremy"))
                .andReturn().getResponse().getContentAsString();
        log.info("++++++" + ReflectionToStringBuilder.toString(mvcResult, ToStringStyle.JSON_STYLE));
    }

    /**
     * test exception
     */
    @Test
    public void whenGetUserInfoFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/xxx")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        log.info("++++++ whenGetUserInfoFail");
    }

    /**
     * test create user
     */
    @Test
    public void whenCreate() throws Exception {
        String requestBody = "{ \"userName\": \"jeremy\", \"id\": \"1\",\"passWord\":null,\"birthDay\":" + new Date().getTime() + "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * test update user
     */
    @Test
    public void whenUpdate() throws Exception {
        Date date = new Date(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        String requestBody = "{ \"userName\": \"jeremy\", \"id\": \"1\",\"passWord\":null,\"birthDay\":" + date.getTime() + "}";
        mockMvc.perform(MockMvcRequestBuilders.put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();
    }


}
