package edu.bbte.idde.bfim2114.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import edu.bbte.idde.bfim2114.backend.model.HardwarePart;
import edu.bbte.idde.bfim2114.backend.service.HardwareService;
import edu.bbte.idde.bfim2114.backend.service.ServiceException;
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
            try {
                Collection<HardwarePart> parts = hardwareService.findAll();
                String json = gson.toJson(parts);
                out.print(json);
                log.info("Returned list of HardwareParts");
            } catch (ServiceException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                log.error("Error while finding HardwarePart", e);
                throw new IOException("Error while finding HardwarePart", e);
            }
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
            } catch (ServiceException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                log.error("Error while finding HardwarePart", e);
                throw new IOException("Error while finding HardwarePart", e);
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
        } catch (ServiceException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("Error while creating HardwarePart", e);
            throw new IOException("Error while creating HardwarePart", e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        response.setContentType("application/json");
        log.info("PUT /api/hardwareparts");

        if (idParam == null) {
            respondWithBadRequest(response, "ID parameter is required");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            updateHardwarePart(id, request, response);
        } catch (NumberFormatException e) {
            respondWithBadRequest(response, "Invalid ID format");
            log.error("Invalid ID format received: {}", idParam);
        } catch (ServiceException e) {
            handleErrorUpdatingHardwarePart(response, e);
        }
    }

    private void updateHardwarePart(Long id, HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {
        HardwarePart part = hardwareService.findById(id);
        if (part == null) {
            respondWithNotFound(response);
            return;
        }

        try {
            HardwarePart updatedPart = gson.fromJson(request.getReader(), HardwarePart.class);
            processHardwarePartUpdate(id, updatedPart, response);
        } catch (JsonIOException | JsonSyntaxException e) {
            respondWithBadRequest(response, "Invalid JSON format: " + e.getMessage());
        }
    }

    private void processHardwarePartUpdate(Long id, HardwarePart updatedPart,
                                           HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        updatedPart.setId(id);
        if (hardwareService.isValid(updatedPart)) {
            hardwareService.update(updatedPart);
            response.setStatus(HttpServletResponse.SC_OK);
            String json = gson.toJson(updatedPart);
            out.print(json);
            log.info("Updated HardwarePart with id: {}", id);
        } else {
            respondWithBadRequest(response, "Invalid HardwarePart data");
        }
    }

    private void respondWithBadRequest(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        PrintWriter out = response.getWriter();
        out.print("{\"error\":\"" + message + "\"}");
        log.error(message);
    }

    private void respondWithNotFound(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        PrintWriter out = response.getWriter();
        out.print("{\"error\":\"" + "HardwarePart not found" + "\"}");
        log.warn("HardwarePart not found");
    }

    private void handleErrorUpdatingHardwarePart(HttpServletResponse response, ServiceException e) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        log.error("Error while updating HardwarePart", e);
        throw new IOException("Error while updating HardwarePart", e);
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
            } catch (ServiceException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                log.error("Error while deleting HardwarePart", e);
                throw new IOException("Error while deleting HardwarePart", e);
            }
        }
    }
}
