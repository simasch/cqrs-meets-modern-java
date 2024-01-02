package ch.martinelli.demo.traditional.api;

import ch.martinelli.demo.traditional.TestTraditionalApplication;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StopWatch;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestTraditionalApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
class TraditionalCustomerControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraditionalCustomerControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCustomers() throws Exception {
        var stopWatch = new StopWatch();
        stopWatch.start();

        mockMvc.perform(get("/customers?pageNumber=0&pageSize=500")).andExpect(status().isOk());

        stopWatch.stop();
        LOGGER.info("Test took {} ms", stopWatch.getTotalTimeMillis());
    }
}