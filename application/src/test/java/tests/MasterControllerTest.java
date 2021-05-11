package tests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import config.MvcTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = MvcTestConfig.class)
public class MasterControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @BeforeEach
    private void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void getAllMastersTest() throws Exception {
        mvc.perform(get("/masters"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(JsonTestData.GET_ALL_MASTERS_JSON));
    }

    @Test
    public void getMasterByIdTest() throws Exception {
        mvc.perform(get("/masters/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(JsonTestData.GET_MASTER_BY_ID_JSON));

        mvc.perform(get("/masters/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addNewMasterTest() throws Exception {
        mvc.perform(post("/masters")
                        .content(JsonTestData.ADD_NEW_MASTER_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mvc.perform(post("/masters")
                        .content(JsonTestData.ADD_NEW_MASTER_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateMasterTest() throws Exception {
        mvc.perform(put("/masters")
                        .content(JsonTestData.UPDATE_MASTER_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteMasterTest() throws Exception {
        mvc.perform(delete("/masters/Ivan"))
                .andExpect(status().isOk());

        mvc.perform(delete("/masters/Simon"))
                .andExpect(status().isNotModified());
    }
}
