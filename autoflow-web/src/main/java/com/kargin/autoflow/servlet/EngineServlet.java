package com.kargin.autoflow.servlet;

import com.kargin.autoflow.dto.PaginationParams;
import com.kargin.autoflow.entity.Engine;
import com.kargin.autoflow.exception.ComponentInUseException;
import com.kargin.autoflow.exception.ComponentNotFoundException;
import com.kargin.autoflow.service.EngineService;
import com.kargin.autoflow.util.PaginationHelper;
import com.kargin.autoflow.util.PaginationParamsFactory;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/engines")
public class EngineServlet extends HttpServlet {

    @EJB
    private EngineService engineService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PaginationParams params = PaginationParamsFactory.fromRequest(request);

        PaginationHelper<Engine> result = engineService.findAll(params);
        request.setAttribute("result", result);
        request.setAttribute("params", params);

        request.getRequestDispatcher("/WEB-INF/jsp/engines.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        String method = request.getParameter("_method");
        if (method != null) {
            if ("DELETE".equalsIgnoreCase(method)) {
                doDelete(request, response);
                return;
            }
            if ("PUT".equalsIgnoreCase(method)) {
                doPut(request, response);
                return;
            }
        }
        Engine engine = new Engine();
        engine.setType(request.getParameter("type"));
        engine.setVolume(BigDecimal.valueOf(Double.parseDouble(request.getParameter("volume"))));
        engine.setPowerKw(BigDecimal.valueOf(Double.parseDouble(request.getParameter("power"))));
        engine.setSerialNumber(request.getParameter("serialNumber"));
        engineService.create(engine);

        response.sendRedirect("engines");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String id = request.getParameter("id");
        if (id == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID required");
            return;
        }
        try {
            engineService.delete(Long.parseLong(id));
        } catch (ComponentNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Engine not found");
        } catch (ComponentInUseException e) {
            response.sendError(HttpServletResponse.SC_CONFLICT, "Engine in use");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
        response.sendRedirect("engines");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Engine engine = new Engine();
        engine.setId(Long.parseLong(request.getParameter("id")));
        engine.setType(request.getParameter("type"));
        engine.setVolume(BigDecimal.valueOf(Double.parseDouble(request.getParameter("volume"))));
        engine.setPowerKw(BigDecimal.valueOf(Double.parseDouble(request.getParameter("power"))));
        engine.setSerialNumber(request.getParameter("serialNumber"));
        try {
            engineService.update(engine);
        } catch (ComponentNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Engine not found");
        } catch (ComponentInUseException e) {
            response.sendError(HttpServletResponse.SC_CONFLICT, "Engine in use");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
        response.sendRedirect("engines");
    }
}
