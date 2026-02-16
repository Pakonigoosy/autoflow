package com.kargin.autoflow.servlet;

import com.kargin.autoflow.entity.Transmission;
import com.kargin.autoflow.exception.ComponentInUseException;
import com.kargin.autoflow.exception.ComponentNotFoundException;
import com.kargin.autoflow.exception.DuplicateException;
import com.kargin.autoflow.service.TransmissionService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/transmissions/form")
public class TransmissionFormServlet extends HttpServlet {

    @EJB
    private TransmissionService transmissionService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        Transmission transmission;
        if (idParam != null && !idParam.isEmpty()) {
            transmission = transmissionService.findById(Long.parseLong(idParam));
            if (transmission == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Трансмиссия не найдена");
                return;
            }
        } else {
            transmission = new Transmission();
        }
        request.setAttribute("transmission", transmission);
        request.getRequestDispatcher("/WEB-INF/jsp/transmission-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idParam = request.getParameter("id");
        Transmission transmission = new Transmission();
        if (idParam != null && !idParam.isEmpty()) {
            transmission.setId(Long.parseLong(idParam));
        }
        transmission.setType(request.getParameter("type"));
        transmission.setSerialNumber(request.getParameter("serialNumber"));

        try {
            if (transmission.getId() == null) {
                transmissionService.create(transmission);
            } else {
                transmissionService.update(transmission);
            }
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
            response.sendRedirect("../transmissions" + (cleanedQueryString.isEmpty() ? "" : "?" + cleanedQueryString));
        } catch (ComponentNotFoundException | DuplicateException e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        } catch (ComponentInUseException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("transmission", transmission);
            request.getRequestDispatcher("/WEB-INF/jsp/transmission-form.jsp").forward(request, response);
        }
    }
}
