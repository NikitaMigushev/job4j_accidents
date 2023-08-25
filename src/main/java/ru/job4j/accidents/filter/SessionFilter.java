package ru.job4j.accidents.filter;

//@Component
//@Order(2)
//public class SessionFilter extends HttpFilter {
//
//    /**
//     *
//     */
//    @Override
//    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        var session = request.getSession();
//        addUserToSession(session, request);
//        chain.doFilter(request, response);
//    }
//
//    private void addUserToSession(HttpSession session, HttpServletRequest request) {
//        var user = (AccidentUser) session.getAttribute("user");
//        if (user == null) {
//            user = new AccidentUser();
//            user.setUsername("Гость");
//        }
//        request.setAttribute("user", user);
//    }
//}
