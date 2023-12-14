package edu.bbte.idde.bfim2114.servlet;

import edu.bbte.idde.bfim2114.backend.repository.jdbc.JdbcInit;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";

    @Override
    public void init() throws ServletException {
        super.init();

        String systemProfile = System.getProperty("profile");
        String systemInitDb = System.getProperty("initdb");
        log.info("Initializing application in {} mode", systemProfile);
        log.info("Initializing database: {}", systemInitDb);


        if ("prod".equals(systemProfile)) {
            log.info("Initializing database...");
            JdbcInit jdbcInit = new JdbcInit();
            jdbcInit.init();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            request.getSession().setAttribute("username", username);
            response.sendRedirect(request.getContextPath() + "/hardware-list");
        } else {
            response.sendRedirect(request.getContextPath() + "/login?error=true");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.include(request, response);
    }

}
