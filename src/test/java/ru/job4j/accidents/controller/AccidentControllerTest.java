package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class AccidentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccidentService accidentService;


    @Test
    @WithMockUser
    public void shouldReturnCreatePage() throws Exception {
        this.mockMvc.perform(get("/accident/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/create"));
    }

    @Test
    @WithMockUser
    public void shouldReturnEditPage() throws Exception {
        int accidentId = 1;
        Accident mockAccident = new Accident();
        mockAccident.setId(accidentId);
        when(accidentService.findById(accidentId)).thenReturn(Optional.of(mockAccident));
        this.mockMvc.perform(get("/accident/edit")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/edit"));
    }

    @Test
    @WithMockUser
    public void saveAccidentTest() throws Exception {
        Accident accident = new Accident("TestName", "TestDescription", "TestAddress");
        String[] ruleIds = {"1", "2"};
        this.mockMvc.perform(post("/accident/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("accident", accident)
                        .param("rIds", "1", "2")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> accidentCaptor = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<String[]> ruleIdsCaptor = ArgumentCaptor.forClass(String[].class);
        verify(accidentService).save(accidentCaptor.capture(), ruleIdsCaptor.capture());
    }

    @Test
    @WithMockUser
    public void editAccidentTest() throws Exception {
        Accident testAccident = new Accident();
        testAccident.setId(1);
        when(accidentService.findById(1)).thenReturn(Optional.of(testAccident));
        this.mockMvc.perform(get("/accident/edit")
                        .param("id", "1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("accident", "types", "rules"))
                .andExpect(model().attribute("accident", testAccident));
        verify(accidentService).findById(1);
    }
}