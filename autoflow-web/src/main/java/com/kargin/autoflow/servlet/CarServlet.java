package com.kargin.autoflow.servlet;

import com.kargin.autoflow.dto.PaginationParams;
import com.kargin.autoflow.entity.Car;
import com.kargin.autoflow.service.CarService;
import com.kargin.autoflow.util.PaginationHelper;
import com.kargin.autoflow.util.PaginationParamsFactory;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars")
public class CarServlet extends HttpServlet {

    @EJB
    private CarService carService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("disassemble".equals(action)) {
            String id = request.getParameter("id");
            if (id != null) {
                try {
                    carService.disassembleCar(Long.parseLong(id));
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            response.sendRedirect("cars");
            return;
        }

        PaginationParams params = PaginationParamsFactory.fromRequest(request);

        PaginationHelper<Car> result = carService.findAll(params);
        request.setAttribute("result", result);
        request.setAttribute("params", params);
        request.setAttribute("queryString", request.getQueryString());

        request.getRequestDispatcher("/WEB-INF/jsp/cars.jsp").forward(request, response);
    }

}
