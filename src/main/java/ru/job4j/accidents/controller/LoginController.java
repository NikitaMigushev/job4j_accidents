package ru.job4j.accidents.controller;

//@Controller
//public class LoginController {
//    @GetMapping("/login")
//    public String loginPage(@RequestParam(value = "error", required = false) String error,
//                            @RequestParam(value = "logout", required = false) String logout,
//                            Model model) {
//        String errorMessage = null;
//        if (error != null) {
//            errorMessage = "Username or Password is incorrect !!";
//        }
//        if (logout != null) {
//            errorMessage = "You have been successfully logged out !!";
//        }
//        model.addAttribute("errorMessage", errorMessage);
//        return "login";
//    }
//
//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/login?logout=true";
//    }
//}
