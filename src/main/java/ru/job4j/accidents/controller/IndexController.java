package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
@AllArgsConstructor
public class IndexController {
    private final AccidentService accidentService;

    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        accidentService.save(new Accident(1, "Test name", "Test text", "test address"));
        accidentService.save(new Accident(2, "Test name 2", "Test text 2", "test address 2"));
        model.addAttribute("accidents", accidentService.findAll());
        model.addAttribute("user", "Petr Arsentev");
        return "index";
    }
}

