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

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestTraditionalApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
class TraditionalOrderControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraditionalOrderControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getOrders() throws Exception {
        var stopWatch = new StopWatch();
        stopWatch.start();

        mockMvc.perform(get("/orders?pageNumber=0&pageSize=500"))
                .andExpect(status().isOk());

        stopWatch.stop();
        LOGGER.info("Test took {} ms", stopWatch.getTotalTimeMillis());
    }

    @Test
    void postOrder() throws Exception {
        var stopWatch = new StopWatch();
        stopWatch.start();

        mockMvc.perform(post("/orders")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                    "orderDate": "2024-01-02T14:49:10",
                                    "customer": {
                                        "id": 2,
                                        "firstName": "Kelci",
                                        "lastName": "Grinnov",
                                        "street": "88889 Lawn Point",
                                        "postalCode": "1000",
                                        "city": "Tangguhang"
                                    },
                                    "items": [
                                        {
                                            "quantity": 1,
                                            "product": {
                                                "id": 1,
                                                "name": "Sobe - Berry Energy",
                                                "price": 24.96
                                            }
                                        }
                                    ]
                                }"""))
                .andExpect(status().isCreated());

        stopWatch.stop();
        LOGGER.info("Test took {} ms", stopWatch.getTotalTimeMillis());
    }
}