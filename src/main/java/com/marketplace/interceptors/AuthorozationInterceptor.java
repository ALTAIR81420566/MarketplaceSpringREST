package com.marketplace.interceptors;

import com.marketplace.model.User;
import com.marketplace.repositories.UserRepo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorozationInterceptor extends HandlerInterceptorAdapter {
    private static final String USER_ATTR = "user";
    private static final String USER_ROLE = "USER";
    private static final String AUTH_ERROR_REDIR = "/authError";
    private static final String AUTH_REDIR = "/";
    private final UserRepo repo;

    public AuthorozationInterceptor(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user = (User) request.getSession().getAttribute(USER_ATTR);
        if (user != null && user.getLogin() != null) {
            if (user.getRole().equals(USER_ROLE)) {
                User respUser = repo.findByLogin(user.getLogin());
                if (respUser == null || !respUser.getPassword().equals(user.getPassword())) {
                    response.sendRedirect(AUTH_ERROR_REDIR);
                } else {
                    request.getSession().setAttribute(USER_ATTR, respUser);
                }
            }
        } else {
            request.getSession().setAttribute(USER_ATTR, new User());
            response.sendRedirect(AUTH_REDIR);
        }

        return true;
    }
}
