package org.example.homework2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.homework2.dto.request.*;
import org.example.homework2.dto.response.DisciplineResponse;
import org.example.homework2.exception.NoAddException;
import org.example.homework2.exception.NoUpdateException;
import org.example.homework2.exception.NotFoundException;
import org.example.homework2.exception.ValidationErrorsException;
import org.example.homework2.exception.service.HandlerExceptionService;
import org.example.homework2.service.DisciplineService;
import org.example.homework2.service.JsonService;
import org.example.homework2.validation.service.ValidationService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "DisciplineServlet", urlPatterns = {"/discipline/all", "/discipline/add",
        "/discipline/update", "/discipline/search"})
public class DisciplineServlet extends HttpServlet {
    private final String[] paths = {"/discipline/all", "/discipline/add",
            "/discipline/update", "/discipline/search"};
    private transient DisciplineService disciplineService;

    @Override
    public void init() {
        disciplineService = new DisciplineService();
        ValidationService.getValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getServletPath();
        if (path.equals(paths[0])) {
            findAllDisciplines(resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getServletPath();
        if (path.equals(paths[1])) {
            addDiscipline(req, resp);
        } else if (path.equals(paths[2])) {
            updateDiscipline(req, resp);
        } else if (path.equals(paths[3])) {
            searchDiscipline(req, resp);
        }
    }

    private void searchDiscipline(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            SearchRequest searchRequest = JsonService.getObject(req.getReader(), SearchRequest.class);
            ValidationService.validation(searchRequest);
            String json = JsonService.getJson(disciplineService.findDisciplineKey(searchRequest));
            Response.makeResponse(resp, json);
        } catch (NotFoundException e) {
            HandlerExceptionService.handlerNotFoundException(e.getMessage(), resp);
        } catch (JsonProcessingException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void updateDiscipline(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            DisciplineUpdateRequest disciplineUpdateRequest = JsonService.getObject(req.getReader(), DisciplineUpdateRequest.class);
            ValidationService.validation(disciplineUpdateRequest);
            String json = JsonService.getJson(disciplineService.updateDiscipline(disciplineUpdateRequest));
            Response.makeResponse(resp, json);
        } catch (JsonProcessingException | NoUpdateException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void addDiscipline(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            DisciplineAddRequest disciplineAddRequest = JsonService.getObject(req.getReader(), DisciplineAddRequest.class);
            ValidationService.validation(disciplineAddRequest);
            String json = JsonService.getJson(disciplineService.addDiscipline(disciplineAddRequest));
            Response.makeResponse(resp, json);
        } catch (JsonProcessingException | NoAddException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void findAllDisciplines(HttpServletResponse resp) throws IOException {
        try {
            List<DisciplineResponse> disciplineResponses =disciplineService.findDisciplineAll();
            String json = JsonService.getJson(disciplineResponses);
            Response.makeResponse(resp, json);
        } catch (NotFoundException e) {
            HandlerExceptionService.handlerNotFoundException(e.getMessage(), resp);
        } catch (JsonProcessingException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        }
    }
}
