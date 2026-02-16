package com.kargin.autoflow.servlet;

import com.kargin.autoflow.entity.CarBody;
import com.kargin.autoflow.exception.ComponentInUseException;
import com.kargin.autoflow.exception.ComponentNotFoundException;
import com.kargin.autoflow.exception.DuplicateException;
import com.kargin.autoflow.service.CarBodyService;
import com.kargin.autoflow.util.ServletUtils;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/carbodies/form")
public class CarBodyFormServlet extends HttpServlet {

    @EJB
    private CarBodyService carBodyService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        CarBody carBody;
        if (idParam != null && !idParam.isEmpty()) {
            carBody = carBodyService.findById(Long.parseLong(idParam));
            if (carBody == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Кузов не найден");
                return;
            }
        } else {
            carBody = new CarBody();
        }
        request.setAttribute("carBody", carBody);
        request.getRequestDispatcher("/WEB-INF/jsp/carbody-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idParam = request.getParameter("id");
        CarBody carBody = new CarBody();
        if (idParam != null && !idParam.isEmpty()) {
            carBody.setId(Long.parseLong(idParam));
        }
        carBody.setType(request.getParameter("type"));
        carBody.setColor(request.getParameter("color"));
        carBody.setDoorCount(Integer.parseInt(request.getParameter("doorCount")));
        carBody.setVin(request.getParameter("vin"));

        try {
            if (carBody.getId() == null) {
                carBodyService.create(carBody);
            } else {
                carBodyService.update(carBody);
            }
            String queryString = ServletUtils.buildListQueryString(request);
            response.sendRedirect("../carbodies" + (queryString.isEmpty() ? "" : "?" + queryString));
        } catch (DuplicateException | ComponentInUseException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("carBody", carBody);
            request.getRequestDispatcher("/WEB-INF/jsp/carbody-form.jsp").forward(request, response);
        } catch (ComponentNotFoundException e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }
}
