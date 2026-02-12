package com.kargin.autoflow.servlet;

import com.kargin.autoflow.dto.PaginationParams;
import com.kargin.autoflow.entity.CarBody;
import com.kargin.autoflow.exception.ComponentInUseException;
import com.kargin.autoflow.exception.ComponentNotFoundException;
import com.kargin.autoflow.exception.DuplicateVinException;
import com.kargin.autoflow.service.CarBodyService;
import com.kargin.autoflow.util.PaginationHelper;
import com.kargin.autoflow.util.PaginationParamsFactory;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/carbodies")
public class CarBodyServlet extends HttpServlet {

    @EJB
    private CarBodyService carBodyService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PaginationParams params = PaginationParamsFactory.fromRequest(request);
        PaginationHelper<CarBody> result = carBodyService.findAll(params);

        request.setAttribute("result", result);
        request.setAttribute("params", params);

        request.getRequestDispatcher("/WEB-INF/jsp/carbodies.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
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
        CarBody carBody = new CarBody();
        carBody.setType(request.getParameter("type"));
        carBody.setColor(request.getParameter("color"));
        carBody.setDoorCount(Integer.parseInt(request.getParameter("doorCount")));
        carBody.setVin(request.getParameter("vin"));
        try {
            carBodyService.create(carBody);
            response.sendRedirect("carbodies");
        } catch (DuplicateVinException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("carBody", carBody);
            doGet(request, response);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String id = request.getParameter("id");
        if (id == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID required");
            return;
        }
        try {
            carBodyService.delete(Long.parseLong(id));
        } catch (ComponentNotFoundException | ComponentInUseException e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
        response.sendRedirect("carbodies");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        CarBody carBody = new CarBody();
        carBody.setId(Long.parseLong(request.getParameter("id")));
        carBody.setType(request.getParameter("type"));
        carBody.setColor(request.getParameter("color"));
        carBody.setDoorCount(Integer.parseInt(request.getParameter("doorCount")));
        carBody.setVin(request.getParameter("vin"));
        try {
            carBodyService.update(carBody);
        } catch (ComponentNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Car body not found");
        } catch (ComponentInUseException e) {
            response.sendError(HttpServletResponse.SC_CONFLICT, "Car body in use");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
        response.sendRedirect("carbodies");
    }
}
