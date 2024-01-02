package ch.martinelli.demo.cqrs.api;

import ch.martinelli.demo.cqrs.TestCqrsApplication;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StopWatch;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestCqrsApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
class CqrsOrderControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CqrsOrderControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createPurchaseOrder() throws Exception {
        var stopWatch = new StopWatch();
        stopWatch.start();

        mockMvc.perform(post("/orders")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                    "customerId":  1
                                }"""))
                .andExpect(status().isCreated());

        stopWatch.stop();
        LOGGER.info("Test took {} ms", stopWatch.getTotalTimeMillis());
    }
}