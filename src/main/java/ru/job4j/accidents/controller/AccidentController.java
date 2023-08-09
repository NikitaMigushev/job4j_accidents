package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/accident")
public class AccidentController {
    private final AccidentService accidentService;

    @GetMapping("/create")
    public String viewCreateAccident(Model model, HttpSession session) {
        List<AccidentType> types = new ArrayList<>();
        types.add(new AccidentType(1, "Две машины"));
        types.add(new AccidentType(2, "Машина и человек"));
        types.add(new AccidentType(3, "Машина и велосипед"));
        model.addAttribute("types", types);
        List<Rule> rules = List.of(

                new Rule(2, "Статья. 2"),
                new Rule(3, "Статья. 3")
        );
        model.addAttribute("rules", rules);
        session.setAttribute("rules", rules);
        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req, HttpSession session) {
        String[] ids = req.getParameterValues("rIds");
        List<Rule> rules = (List<Rule>) session.getAttribute("rules");
        var selectedRules = getSelectedRules(ids, rules);
        accident.setRules(selectedRules);
        accidentService.save(accident);
        return "redirect:/index";
    }

    @GetMapping("/edit")
    public String viewEditAccident(Model model, @RequestParam("id") int accidentId, HttpSession session) {
        var accident = accidentService.findById(accidentId).get();
        List<AccidentType> types = new ArrayList<>();
        types.add(new AccidentType(1, "Две машины"));
        types.add(new AccidentType(2, "Машина и человек"));
        types.add(new AccidentType(3, "Машина и велосипед"));
        model.addAttribute("accident", accident);
        model.addAttribute("types", types);
        List<Rule> rules = List.of(
                new Rule(2, "Статья. 2"),
                new Rule(3, "Статья. 3")
        );
        session.setAttribute("rules", rules);
        model.addAttribute("rules", rules);
        return "accident/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Accident accident, HttpServletRequest req, HttpSession session) {
        String[] ids = req.getParameterValues("rIds");
        List<Rule> rules = (List<Rule>) session.getAttribute("rules");
        var selectedRules = getSelectedRules(ids, rules);
        accident.setRules(selectedRules);
        accidentService.update(accident);
        return "redirect:/index";
    }

    private Set<Rule> getSelectedRules(String[] ids, List<Rule> rules) {
        Set<Rule> selectedRules = new HashSet<>();
        for (String id : ids) {
            for (Rule rule : rules) {
                if (id.equals(String.valueOf(rule.getId()))) {
                    selectedRules.add(rule);
                    break;
                }
            }
        }
        return selectedRules;
    }
}
