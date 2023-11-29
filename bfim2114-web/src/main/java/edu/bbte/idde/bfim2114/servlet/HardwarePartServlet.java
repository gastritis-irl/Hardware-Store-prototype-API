package edu.bbte.idde.bfim2114.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import edu.bbte.idde.bfim2114.backend.model.HardwarePart;
import edu.bbte.idde.bfim2114.backend.service.HardwareService;
import edu.bbte.idde.bfim2114.backend.service.ServiceFactory;
import edu.bbte.idde.bfim2114.backend.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@Slf4j
@WebServlet("/api/hardwareparts")
@RequiredArgsConstructor
public class HardwarePartServlet extends HttpServlet {

    private final HardwareService hardwareService = ServiceFactory.getInstance().getHardwareService();
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        log.info("GET /api/hardwareparts");

        if (idParam == null) {
            Collection<HardwarePart> parts = hardwareService.findAll();
            String json = gson.toJson(parts);
            out.print(json);
            log.info("Returned list of HardwareParts");

        } else {
            try {
                Long id = Long.parseLong(idParam);
                HardwarePart part = hardwareService.findById(id);
                if (part == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{}");
                    log.warn("HardwarePart with id: {} not found", id);
                } else {
                    String json = gson.toJson(part);
                    out.print(json);
                    log.info("Returned HardwarePart with id: {}", id);
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Invalid ID format\"}");
                log.error("Invalid ID format received: {}", idParam);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        log.info("POST /api/hardwareparts");

        try {
            HardwarePart part = gson.fromJson(request.getReader(), HardwarePart.class);
            if (hardwareService.isValid(part)) {
                HardwarePart createdPart = hardwareService.create(part);
                response.setStatus(HttpServletResponse.SC_CREATED);
                String json = gson.toJson(createdPart);
                out.print(json);
                log.info("Created HardwarePart with id: {}", createdPart.getId());
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Invalid HardwarePart data\"}");
                log.error("Invalid HardwarePart data provided");
            }
        } catch (JsonIOException | JsonSyntaxException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"Invalid JSON format: " + e.getMessage() + "\"}");
            log.error("Invalid JSON format received", e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        log.info("PUT /api/hardwareparts");

        if (idParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"ID parameter is required\"}");
            log.error("ID parameter is required");
        } else {
            try {
                Long id = Long.parseLong(idParam);
                HardwarePart part = hardwareService.findById(id);
                if (part == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\":\"HardwarePart not found\"}");
                    log.warn("HardwarePart with id: {} not found", id);
                } else {
                    try {
                        HardwarePart updatedPart = gson.fromJson(request.getReader(), HardwarePart.class);
                        updatedPart.setId(id);
                        if (hardwareService.isValid(updatedPart)) {
                            hardwareService.update(updatedPart);
                            response.setStatus(HttpServletResponse.SC_OK);
                            String json = gson.toJson(updatedPart);
                            out.print(json);
                            log.info("Updated HardwarePart with id: {}", id);
                        } else {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print("{\"error\":\"Invalid HardwarePart data\"}");
                            log.error("Invalid HardwarePart data provided");
                        }
                    } catch (JsonIOException | JsonSyntaxException e) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("{\"error\":\"Invalid JSON format: " + e.getMessage() + "\"}");
                        log.error("Invalid JSON format received", e);
                    }
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Invalid ID format\"}");
                log.error("Invalid ID format received: {}", idParam);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        log.info("DELETE /api/hardwareparts");

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"ID parameter is required\"}");
            log.error("ID parameter is required");

        } else {
            try {
                Long id = Long.parseLong(idParam);
                hardwareService.delete(id);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Invalid ID format\"}");
                log.error("Invalid ID format received: {}", idParam);
            }
        }
    }
}
