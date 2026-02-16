package com.kargin.autoflow.util;

import javax.servlet.http.HttpServletRequest;

public final class ServletUtils {

    private ServletUtils() {
    }

    /**
     * Собирает query string для редиректа на список.
     * Возвращает строку без ведущего "?" или пустую строку.
     */
    public static String buildListQueryString(HttpServletRequest request) {
        StringBuilder queryString = new StringBuilder();
        appendParam(queryString, "page", request.getParameter("page"));
        appendParam(queryString, "search", request.getParameter("search"));
        appendParam(queryString, "sortBy", request.getParameter("sortBy"));
        appendParam(queryString, "sortOrder", request.getParameter("sortOrder"));
        if (!queryString.isEmpty()) {
            queryString.setLength(queryString.length() - 1);
        }
        return queryString.toString();
    }

    private static void appendParam(StringBuilder sb, String name, String value) {
        if (value != null && !value.isEmpty()) {
            sb.append(name).append("=").append(value).append("&");
        }
    }
}
