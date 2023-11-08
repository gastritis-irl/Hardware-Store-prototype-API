package edu.bbte.idde.bfim2114.webapp;

import edu.bbte.idde.bfim2114.backend.HardwareCrudOperations;
import edu.bbte.idde.bfim2114.backend.model.HardwarePart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/hardware-list")
public class HardwareListServlet extends HttpServlet {

    private final HardwareCrudOperations hardwareCrudOperations = new HardwareCrudOperations();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<HardwarePart> parts = hardwareCrudOperations.readAll();
        request.setAttribute("parts", parts);
        request.getRequestDispatcher("/hardware-list.jsp").forward(request, response);
    }
}



