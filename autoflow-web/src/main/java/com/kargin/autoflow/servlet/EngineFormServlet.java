package com.kargin.autoflow.servlet;

import com.kargin.autoflow.entity.Engine;
import com.kargin.autoflow.exception.ComponentInUseException;
import com.kargin.autoflow.exception.ComponentNotFoundException;
import com.kargin.autoflow.exception.DuplicateException;
import com.kargin.autoflow.service.EngineService;
import com.kargin.autoflow.util.ServletUtils;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/engines/form")
public class EngineFormServlet extends HttpServlet {

    @EJB
    private EngineService engineService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        Engine engine;
        if (idParam != null && !idParam.isEmpty()) {
            engine = engineService.findById(Long.parseLong(idParam));
            if (engine == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Двигатель не найден");
                return;
            }
        } else {
            engine = new Engine();
        }
        request.setAttribute("engine", engine);
        request.getRequestDispatcher("/WEB-INF/jsp/engine-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idParam = request.getParameter("id");
        Engine engine = new Engine();
        if (idParam != null && !idParam.isEmpty()) {
            engine.setId(Long.parseLong(idParam));
        }
        engine.setType(request.getParameter("type"));
        engine.setVolume(BigDecimal.valueOf(Double.parseDouble(request.getParameter("volume"))));
        engine.setPowerKw(BigDecimal.valueOf(Double.parseDouble(request.getParameter("power"))));
        engine.setSerialNumber(request.getParameter("serialNumber"));

        try {
            if (engine.getId() == null) {
                engineService.create(engine);
            } else {
                engineService.update(engine);
            }
            String queryString = ServletUtils.buildListQueryString(request);
            response.sendRedirect("../engines" + (queryString.isEmpty() ? "" : "?" + queryString));
        } catch (ComponentNotFoundException | DuplicateException e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        } catch (ComponentInUseException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("engine", engine);
            request.getRequestDispatcher("/WEB-INF/jsp/engine-form.jsp").forward(request, response);
        }
    }
}
