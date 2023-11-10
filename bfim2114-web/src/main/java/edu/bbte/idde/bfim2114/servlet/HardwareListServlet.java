package edu.bbte.idde.bfim2114.servlet;

import edu.bbte.idde.bfim2114.backend.HardwareCrudOperations;
import edu.bbte.idde.bfim2114.backend.model.HardwarePart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/hardware-list")
public class HardwareListServlet extends HttpServlet {

    private final HardwareCrudOperations hardwareCrudOperations = HardwareCrudOperations.getInstance();

    private static final Logger LOGGER = LoggerFactory.getLogger(HardwarePartServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("GET /hardware-list");
        List<HardwarePart> parts = hardwareCrudOperations.readAll();
        LOGGER.info(parts.toString());
        request.setAttribute("parts", parts);
        request.getRequestDispatcher("/hardware-list.jsp").forward(request, response);
    }
}
