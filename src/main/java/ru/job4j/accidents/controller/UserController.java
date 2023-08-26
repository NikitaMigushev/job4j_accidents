
package ru.job4j.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.job4j.accidents.model.AccidentUser;
import ru.job4j.accidents.repository.SpringDataUserAuthorityRepository;
import ru.job4j.accidents.service.AccidentUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    private final AccidentUserService userService;

    private final SpringDataUserAuthorityRepository userAuthorityRepository;

    public UserController(AccidentUserService userService, SpringDataUserAuthorityRepository userAuthorityRepository) {
        this.userService = userService;
        this.userAuthorityRepository = userAuthorityRepository;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }

    @GetMapping("/register")
    public ModelAndView getRegistrationPage() {
        ModelAndView modelAndView = new ModelAndView("users/register");
        return modelAndView;
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute AccidentUser user, HttpServletRequest request) {
        var savedAccidentUser = userService.save(user);
        System.out.println("check here");
        if (savedAccidentUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с такой почтой уже существует");
            user.setUsername("Гость");
            return "users/register";
        }
        var session = request.getSession();
        session.setAttribute("user", savedAccidentUser.get());

        return "redirect:/users/login";
    }
}