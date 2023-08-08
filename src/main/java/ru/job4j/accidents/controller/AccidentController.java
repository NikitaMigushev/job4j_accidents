package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
@AllArgsConstructor
@RequestMapping("/accident")
public class AccidentController {
    private final AccidentService accidentService;

    @GetMapping("/create")
    public String viewCreateAccident(Model model) {
        model.addAttribute("lastId", accidentService.getLastId());

        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident) {
        accidentService.save(accident);
        return "redirect:/index";
    }

    @GetMapping("/edit")
    public String viewEditAccident(Model model, @RequestParam("id") int accidentId) {
        var accident = accidentService.findById(accidentId).get();
        model.addAttribute("accident", accident);
        return "accident/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Accident accident) {
        accidentService.update(accident);
        return "redirect:/index";
    }


}
