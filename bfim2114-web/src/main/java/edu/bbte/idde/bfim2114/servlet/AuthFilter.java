package edu.bbte.idde.bfim2114.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/hardware-list")
public class AuthFilter implements Filter {

    private static final String USERNAME = "admin";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        if (!(req instanceof HttpServletRequest request) || !(res instanceof HttpServletResponse response)) {
            throw new ServletException("Non-HTTP request or response");
        }


        HttpSession session = request.getSession(false);
        if (session != null && USERNAME.equals(session.getAttribute("username"))) {
            chain.doFilter(req, res);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}


