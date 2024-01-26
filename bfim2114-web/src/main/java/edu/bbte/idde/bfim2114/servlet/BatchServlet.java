package edu.bbte.idde.bfim2114.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import edu.bbte.idde.bfim2114.backend.model.HardwarePart;
import edu.bbte.idde.bfim2114.backend.service.HardwareService;
import edu.bbte.idde.bfim2114.backend.service.ServiceException;
import edu.bbte.idde.bfim2114.backend.service.ServiceFactory;
import edu.bbte.idde.bfim2114.backend.util.Batch;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@WebServlet("/api/hardwarepart/batch")
public class BatchServlet extends HttpServlet {

    private final HardwareService hardwareService = ServiceFactory.getInstance().getHardwareService();

    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        log.info("POST /api/hardwarepart/batch");
        try {
            Batch parts = gson.fromJson(request.getReader(), Batch.class);
            for (HardwarePart part : parts.batch) {
                if (!hardwareService.isValid(part)) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\":\"Invalid HardwarePart data\"}");
                    log.error("Invalid HardwarePart data provided");
                }
            }
            for (HardwarePart part : parts.batch) {
                hardwareService.create(part);
            }
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (JsonIOException | JsonSyntaxException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"Invalid JSON format: " + e.getMessage() + "\"}");
            log.error("Invalid JSON format received", e);
        } catch (ServiceException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("Error while creating HardwareParts", e);
            throw new IOException("Error while creating HardwareParts", e);
        }
    }
}
