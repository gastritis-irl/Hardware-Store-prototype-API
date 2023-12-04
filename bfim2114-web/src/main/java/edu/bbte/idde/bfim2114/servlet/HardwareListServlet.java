package edu.bbte.idde.bfim2114.servlet;

import edu.bbte.idde.bfim2114.backend.model.HardwarePart;
import edu.bbte.idde.bfim2114.backend.service.HardwareService;
import edu.bbte.idde.bfim2114.backend.service.ServiceException;
import edu.bbte.idde.bfim2114.backend.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/hardware-list")
public class HardwareListServlet extends HttpServlet {

    private final HardwareService hardwareService = ServiceFactory.getInstance().getHardwareService();

    private static final Logger LOGGER = LoggerFactory.getLogger(HardwarePartServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("GET /hardware-list");
        try {
            Collection<HardwarePart> parts = hardwareService.findAll();
            LOGGER.info(parts.toString());
            request.setAttribute("parts", parts);
            request.getRequestDispatcher("/hardware-list.jsp").forward(request, response);
        } catch (ServiceException e) {
            LOGGER.error("Error while finding HardwarePart", e);
            throw new ServletException("Error while finding HardwarePart", e);
        }

    }
}
