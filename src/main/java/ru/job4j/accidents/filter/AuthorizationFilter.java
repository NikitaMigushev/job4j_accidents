package ru.job4j.accidents.filter;

//@Component
//@Order(1)
//public class AuthorizationFilter extends HttpFilter {
//
//    /**
//     *Method checks if user is on registration page or login page, if so - move to SessionFilter
//     * If not, check if user is logged in. If not redirect to login page. If so move to SessionFilter
//     */
//    @Override
//    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        var uri = request.getRequestURI();
//        if (isAlwaysPermitted(uri)) {
//            chain.doFilter(request, response);
//            return;
//        }
//        var userLoggedIn = request.getSession().getAttribute("user") != null;
//        if (!userLoggedIn) {
//            var loginPageUrl = request.getContextPath() + "/users/login";
//            response.sendRedirect(loginPageUrl);
//            return;
//        }
//        chain.doFilter(request, response);
//    }
//
//    private boolean isAlwaysPermitted(String uri) {
//        return uri.startsWith("/users/register") || uri.startsWith("/users/login");
//    }
//}
