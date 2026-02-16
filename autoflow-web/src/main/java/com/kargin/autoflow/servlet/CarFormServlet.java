package com.kargin.autoflow.servlet;

import com.kargin.autoflow.service.CarBodyService;
import com.kargin.autoflow.service.CarService;
import com.kargin.autoflow.service.EngineService;
import com.kargin.autoflow.service.TransmissionService;

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
            StringBuilder queryString = new StringBuilder();
            String page = request.getParameter("page");
            String search = request.getParameter("search");
            String sortBy = request.getParameter("sortBy");
            String sortOrder = request.getParameter("sortOrder");
            if (page != null && !page.isEmpty()) queryString.append("page=").append(page).append("&");
            if (search != null && !search.isEmpty()) queryString.append("search=").append(search).append("&");
            if (sortBy != null && !sortBy.isEmpty()) queryString.append("sortBy=").append(sortBy).append("&");
            if (sortOrder != null && !sortOrder.isEmpty()) queryString.append("sortOrder=").append(sortOrder).append("&");
            String cleanedQueryString = queryString.length() > 0 ? queryString.substring(0, queryString.length() - 1) : "";
            response.sendRedirect("../cars" + (cleanedQueryString.isEmpty() ? "" : "?" + cleanedQueryString));
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("availableBodies", carBodyService.findAvailable());
            request.setAttribute("availableEngines", engineService.findAvailable());
            request.setAttribute("availableTransmissions", transmissionService.findAvailable());
            request.getRequestDispatcher("/WEB-INF/jsp/car-form.jsp").forward(request, response);
        }
    }
}
