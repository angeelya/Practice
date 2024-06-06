package org.example.homework2.exception.service;

import jakarta.servlet.http.HttpServletResponse;
import org.example.homework2.dto.response.MessageResponse;
import org.example.homework2.service.JsonService;

import java.io.IOException;

public class HandlerExceptionService {
    private HandlerExceptionService() {
    }

    public static void handlerNotFoundException(String message, HttpServletResponse resp) throws IOException {
        getResponse(resp, message);
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        resp.getWriter().flush();
    }

    public static void handlerServerException(String message, HttpServletResponse resp) throws IOException {
        getResponse(resp, message);
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.getWriter().flush();
    }
    public  static void handlerBadRequestException(String message, HttpServletResponse resp) throws IOException {
        getResponse(resp, message);
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter().flush();
    }

    private static void getResponse(HttpServletResponse resp, String message) throws IOException {
        resp.setContentType("application/json");
        String json = JsonService.getJson(new MessageResponse(message));
        resp.getWriter().print(json);
    }
}
