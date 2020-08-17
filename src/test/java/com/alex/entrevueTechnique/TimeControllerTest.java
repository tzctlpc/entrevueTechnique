package com.alex.entrevueTechnique;

import org.joda.time.MutableDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TimeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void currentTimeShouldReturnTime() throws Exception {
        String adresse = "/time/current";
        String dateAttendue = (new MutableDateTime()).toString().substring(0, 18);

        ResultActions resultat = this.mockMvc.perform(get(adresse)).andDo(print());

        resultat.andExpect(status().isOk()).andExpect(jsonPath("$").value(containsString(dateAttendue)));
    }

}

