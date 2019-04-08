package top.yunshu.shw.server.controller.teacher;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import top.yunshu.shw.server.ShwServerApplication;
import top.yunshu.shw.server.entity.LoginUser;
import top.yunshu.shw.server.util.JwtUtils;

import java.util.UUID;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShwServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeacherControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private String authorizationValue;

    @Before
    public void setUp() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        LoginUser loginUser = new LoginUser();
        loginUser.setId("1");
        loginUser.setNo("2");
        loginUser.setLoginName("3");
        loginUser.setUserType(LoginUser.TEACHER_USER);
        loginUser.setName("4");
        loginUser.setPhone("5");
        loginUser.setEmail("6");
        loginUser.setMobile("7");
        loginUser.setLoginIp("8");
        loginUser.setRemarks("9");
        loginUser.setRoleId("0");
        loginUser.setDormId("1");
        loginUser.setClazzId("2");
        loginUser.setCompanyId("3");
        loginUser.setOfficeId("4");
        authorizationValue = JwtUtils.buildJwt(loginUser);
    }

    @Test
    public void getTeacherCreateGroups() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                get("/teacher/groups")
                        .header(HttpHeaders.AUTHORIZATION, authorizationValue)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(request().asyncStarted())
                .andExpect(request().asyncResult(instanceOf(ResponseEntity.class)))
                .andReturn();
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void addGroup() throws Exception {
        String newGroupName = UUID.randomUUID().toString();
        mockMvc.perform(
                post("/teacher/group")
                        .header(HttpHeaders.AUTHORIZATION, authorizationValue)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .content("groupName=" + newGroupName)
        )
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void deleteGroup() throws Exception {
        mockMvc.perform(
                delete("/teacher/group/{id}", "bc25f27ab0ac4dc9ba349c57598cec23")
                        .header(HttpHeaders.AUTHORIZATION, authorizationValue)
        )
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
