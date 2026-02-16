package com.kargin.autoflow.servlet;

import com.kargin.autoflow.dto.PaginationParams;
import com.kargin.autoflow.entity.Transmission;
import com.kargin.autoflow.exception.ComponentInUseException;
import com.kargin.autoflow.exception.ComponentNotFoundException;
import com.kargin.autoflow.service.TransmissionService;
import com.kargin.autoflow.util.PaginationHelper;
import com.kargin.autoflow.util.PaginationParamsFactory;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/transmissions")
public class TransmissionServlet extends HttpServlet {

    @EJB
    private TransmissionService transmissionService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PaginationParams params = PaginationParamsFactory.fromRequest(request);

        PaginationHelper<Transmission> result = transmissionService.findAll(params);
        request.setAttribute("result", result);
        request.setAttribute("params", params);
        request.setAttribute("queryString", request.getQueryString());

        request.getRequestDispatcher("/WEB-INF/jsp/transmissions.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        String method = request.getParameter("_method");
        if ("DELETE".equalsIgnoreCase(method)) {
            doDelete(request, response);
            return;
        }
        response.sendRedirect("transmissions");
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
            transmissionService.delete(Long.parseLong(id));
        } catch (ComponentNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Transmission not found");
        } catch (ComponentInUseException e) {
            response.sendError(HttpServletResponse.SC_CONFLICT, "Transmission in use");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
        response.sendRedirect("transmissions");
    }

}
