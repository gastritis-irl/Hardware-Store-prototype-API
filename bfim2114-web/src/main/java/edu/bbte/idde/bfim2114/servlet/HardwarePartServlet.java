package edu.bbte.idde.bfim2114.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import edu.bbte.idde.bfim2114.backend.repository.mem.MemHardwareRepository;
import edu.bbte.idde.bfim2114.backend.model.HardwarePart;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/api/hardwareparts")
public class HardwarePartServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(HardwarePartServlet.class);
    private final MemHardwareRepository memHardwareRepository = MemHardwareRepository.getInstance();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        LOGGER.info("GET /api/hardwareparts");

        if (idParam == null) {
            List<HardwarePart> parts = memHardwareRepository.findAll();
            String json = gson.toJson(parts);
            out.print(json);
            LOGGER.info("Returned list of HardwareParts");

        } else {
            try {
                Long id = Long.parseLong(idParam);
                HardwarePart part = memHardwareRepository.findById(id);
                if (part == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{}");
                    LOGGER.warn("HardwarePart with id: {} not found", id);
                } else {
                    String json = gson.toJson(part);
                    out.print(json);
                    LOGGER.info("Returned HardwarePart with id: {}", id);
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Invalid ID format\"}");
                LOGGER.error("Invalid ID format received: {}", idParam);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        LOGGER.info("POST /api/hardwareparts");

        try {
            HardwarePart part = gson.fromJson(request.getReader(), HardwarePart.class);
            if (part.isValid()) {
                HardwarePart createdPart = memHardwareRepository.create(part);
                response.setStatus(HttpServletResponse.SC_CREATED);
                String json = gson.toJson(createdPart);
                out.print(json);
                LOGGER.info("Created HardwarePart with id: {}", createdPart.getId());
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Invalid HardwarePart data\"}");
                LOGGER.error("Invalid HardwarePart data provided");
            }
        } catch (JsonIOException | JsonSyntaxException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"Invalid JSON format: " + e.getMessage() + "\"}");
            LOGGER.error("Invalid JSON format received", e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        LOGGER.info("PUT /api/hardwareparts");

        if (idParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"ID parameter is required\"}");
            LOGGER.error("ID parameter is required");
        } else {
            try {
                Long id = Long.parseLong(idParam);
                HardwarePart part = memHardwareRepository.findById(id);
                if (part == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\":\"HardwarePart not found\"}");
                    LOGGER.warn("HardwarePart with id: {} not found", id);
                } else {
                    try {
                        HardwarePart updatedPart = gson.fromJson(request.getReader(), HardwarePart.class);
                        updatedPart.setId(id);
                        if (updatedPart.isValid()) {
                            memHardwareRepository.update(updatedPart);
                            response.setStatus(HttpServletResponse.SC_OK);
                            String json = gson.toJson(updatedPart);
                            out.print(json);
                            LOGGER.info("Updated HardwarePart with id: {}", id);
                        } else {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print("{\"error\":\"Invalid HardwarePart data\"}");
                            LOGGER.error("Invalid HardwarePart data provided");
                        }
                    } catch (JsonIOException | JsonSyntaxException e) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("{\"error\":\"Invalid JSON format: " + e.getMessage() + "\"}");
                        LOGGER.error("Invalid JSON format received", e);
                    }
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Invalid ID format\"}");
                LOGGER.error("Invalid ID format received: {}", idParam);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        LOGGER.info("DELETE /api/hardwareparts");

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"ID parameter is required\"}");
            LOGGER.error("ID parameter is required");

        } else {
            try {
                Long id = Long.parseLong(idParam);
                memHardwareRepository.deleteById(id);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Invalid ID format\"}");
                LOGGER.error("Invalid ID format received: {}", idParam);
            }
        }
    }
}
