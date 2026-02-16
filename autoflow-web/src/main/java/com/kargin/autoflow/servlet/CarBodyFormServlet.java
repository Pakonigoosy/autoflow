package com.kargin.autoflow.servlet;

import com.kargin.autoflow.entity.CarBody;
import com.kargin.autoflow.exception.ComponentInUseException;
import com.kargin.autoflow.exception.ComponentNotFoundException;
import com.kargin.autoflow.exception.DuplicateException;
import com.kargin.autoflow.service.CarBodyService;

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
            StringBuilder queryString = new StringBuilder();
            String page = request.getParameter("page");
            String search = request.getParameter("search");
            String sortBy = request.getParameter("sortBy");
            String sortOrder = request.getParameter("sortOrder");

            if (page != null && !page.isEmpty()) queryString.append("page=").append(page).append("&");
            if (search != null && !search.isEmpty()) queryString.append("search=").append(search).append("&");
            if (sortBy != null && !sortBy.isEmpty()) queryString.append("sortBy=").append(sortBy).append("&");
            if (sortOrder != null && !sortOrder.isEmpty())
                queryString.append("sortOrder=").append(sortOrder).append("&");

            String cleanedQueryString = !queryString.isEmpty()
                    ? queryString.substring(0, queryString.length() - 1)
                    : "";

            response.sendRedirect("../carbodies" +
                    (cleanedQueryString.isEmpty() ? "" : "?" + cleanedQueryString));
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
