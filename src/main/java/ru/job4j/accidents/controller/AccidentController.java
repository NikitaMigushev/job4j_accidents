package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/accident")
public class AccidentController {
    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;

    private final RuleService ruleService;

    @GetMapping("/create")
    public String viewCreateAccident(Model model, HttpSession session) {
        model.addAttribute("accident", new Accident());
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req, HttpSession session) {
        var selectedAccidentType = accidentTypeService.findById(accident.getType().getId()).get();
        accident.setType(selectedAccidentType);
        Set<Rule> selectedRules = ruleService.findByIds(req.getParameterValues("rIds"));
        accident.setRules(selectedRules);
        accidentService.save(accident);
        return "redirect:/index";
    }

    @GetMapping("/edit")
    public String viewEditAccident(Model model, @RequestParam("id") int accidentId, HttpSession session) {
        var accident = accidentService.findById(accidentId).get();
        model.addAttribute("accident", accident);
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "accident/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Accident accident, HttpServletRequest req, HttpSession session) {
        var selectedAccidentType = accidentTypeService.findById(accident.getType().getId()).get();
        accident.setType(selectedAccidentType);
        var selectedRules = ruleService.findByIds(req.getParameterValues("rIds"));
        accident.setRules(selectedRules);
        accidentService.update(accident);
        return "redirect:/index";
    }
}
