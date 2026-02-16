package com.kargin.autoflow.servlet;

import com.kargin.autoflow.service.CarBodyService;
import com.kargin.autoflow.service.CarService;
import com.kargin.autoflow.service.EngineService;
import com.kargin.autoflow.service.TransmissionService;
import com.kargin.autoflow.util.ServletUtils;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/form")
public class CarFormServlet extends HttpServlet {

    @EJB
    private CarService carService;

    @EJB
    private CarBodyService carBodyService;

    @EJB
    private EngineService engineService;

    @EJB
    private TransmissionService transmissionService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("availableBodies", carBodyService.findAvailable());
        request.setAttribute("availableEngines", engineService.findAvailable());
        request.setAttribute("availableTransmissions", transmissionService.findAvailable());
        request.getRequestDispatcher("/WEB-INF/jsp/car-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        try {
            Long bodyId = Long.parseLong(request.getParameter("bodyId"));
            Long engineId = Long.parseLong(request.getParameter("engineId"));
            Long transmissionId = Long.parseLong(request.getParameter("transmissionId"));
            carService.assembleCar(bodyId, engineId, transmissionId);
            String queryString = ServletUtils.buildListQueryString(request);
            response.sendRedirect("../cars" + (queryString.isEmpty() ? "" : "?" + queryString));
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("availableBodies", carBodyService.findAvailable());
            request.setAttribute("availableEngines", engineService.findAvailable());
            request.setAttribute("availableTransmissions", transmissionService.findAvailable());
            request.getRequestDispatcher("/WEB-INF/jsp/car-form.jsp").forward(request, response);
        }
    }
}
