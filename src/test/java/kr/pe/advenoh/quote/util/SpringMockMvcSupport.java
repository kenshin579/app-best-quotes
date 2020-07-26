package kr.pe.advenoh.quote.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public abstract class SpringMockMvcSupport extends SpringBootTestSupport {

    @Autowired
    protected MockMvc mvc;
}