package com.kargin.autoflow.util;

import com.kargin.autoflow.dto.PaginationParams;
import javax.servlet.http.HttpServletRequest;

public class PaginationParamsFactory {
    public static PaginationParams fromRequest(HttpServletRequest request) {
        int page = extractInt(request, "page", 1);
        int pageSize = extractInt(request, "pageSize", 10);
        String search = request.getParameter("search");
        String sortBy = request.getParameter("sortBy");
        String sortOrder = request.getParameter("sortOrder");

        return new PaginationParams(page, pageSize, search, sortBy, sortOrder);
    }

    private static int extractInt(HttpServletRequest request,
                                  String paramName, int defaultValue) {
        String param = request.getParameter(paramName);
        if (param == null || param.trim().isEmpty()) return defaultValue;
        try {
            return Integer.parseInt(param.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}