package kr.pe.advenoh.quote.controller;

import kr.pe.advenoh.quote.util.SpringBootTestSupport;
import kr.pe.advenoh.quote.util.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest extends SpringBootTestSupport {
    @Autowired
    private MockMvc mockMvc;

    private String username;

    @BeforeEach
    void setUp() throws Exception {
        username = TestUtils.generateRandomString(5);
    }

    @Test
    @Transactional
    void createUser_deleteUser() throws Exception {
        //user 생성
        this.mockMvc.perform(post("/api/auth/signup")
                .param("name", "test name")
                .param("email", "test@sdf.com")
                .param("password", "123456")
                .param("username", username))
                .andDo(print())
                .andExpect(status().isCreated());

        //user 삭제
        this.mockMvc.perform(delete("/api/user/{username}", username))
                .andDo(print())
                .andExpect(status().isOk());
    }
}