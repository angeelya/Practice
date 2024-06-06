package org.example.homework2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.homework2.dto.request.*;
import org.example.homework2.exception.*;
import org.example.homework2.exception.service.HandlerExceptionService;
import org.example.homework2.service.JsonService;
import org.example.homework2.service.TeacherService;
import org.example.homework2.validation.service.ValidationService;

import java.io.IOException;

@WebServlet(name = "TeacherServlet", urlPatterns = {"/teacher/add", "/teacher/update",
        "/teacher/delete", "/teacher/all", "/teacher/get/data", "/teacher/get/by/discipline", "/teacher/search"})
public class TeacherServlet extends HttpServlet {
    private transient TeacherService teacherService;
    private final String[] paths = {"/teacher/all", "/teacher/add",
            "/teacher/update", "/teacher/delete", "/teacher/get/data", "/teacher/get/by/discipline", "/teacher/search"};

    @Override
    public void init() throws ServletException {
        super.init();
        teacherService = new TeacherService();
        ValidationService.getValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getServletPath();
        if (path.equals(paths[0])) {
            findAllTeachers(resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getServletPath();
        if (path.equals(paths[1])) {
            addTeacher(req, resp);
        } else if (path.equals(paths[2])) {
            updateTeacher(req, resp);
        } else if (path.equals(paths[3])) {
            deleteTeacher(req, resp);
        } else if (path.equals(paths[4])) {
            findTeacherById(req, resp);
        } else if (path.equals(paths[5])) {
            findTeacherByDiscipline(req, resp);
        } else if (path.equals(paths[6])) {
            searchTeacher(req, resp);
        }
    }

    private void searchTeacher(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            SearchRequest searchRequest = JsonService.getObject(req.getReader(), SearchRequest.class);
            ValidationService.validation(searchRequest);
            String json = JsonService.getJson(teacherService.findTeachersByKey(searchRequest));
            Response.makeResponse(resp, json);
        } catch (NotFoundException e) {
            HandlerExceptionService.handlerNotFoundException(e.getMessage(), resp);
        } catch (JsonProcessingException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void findTeacherByDiscipline(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            DisciplineRequest disciplineRequest = JsonService.getObject(req.getReader(), DisciplineRequest.class);
            ValidationService.validation(disciplineRequest);
            String json = JsonService.getJson(teacherService.findTeachersByDisciplineId(disciplineRequest));
            Response.makeResponse(resp, json);
        } catch (NotFoundException e) {
            HandlerExceptionService.handlerNotFoundException(e.getMessage(), resp);
        } catch (JsonProcessingException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void findTeacherById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            TeacherRequest teacherRequest = JsonService.getObject(req.getReader(), TeacherRequest.class);
            ValidationService.validation(teacherRequest);
            String json = JsonService.getJson(teacherService.findTeacherById(teacherRequest));
            Response.makeResponse(resp, json);
        } catch (NotFoundException e) {
            HandlerExceptionService.handlerNotFoundException(e.getMessage(), resp);
        } catch (JsonProcessingException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void deleteTeacher(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            TeacherRequest teacherRequest = JsonService.getObject(req.getReader(), TeacherRequest.class);
            ValidationService.validation(teacherRequest);
            String json = JsonService.getJson(teacherService.deleteTeacher(teacherRequest));
            Response.makeResponse(resp, json);
        } catch (JsonProcessingException | DeleteException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void updateTeacher(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            TeacherUpdateRequest teacherUpdateRequest = JsonService.getObject(req.getReader(), TeacherUpdateRequest.class);
            ValidationService.validation(teacherUpdateRequest);
            String json = JsonService.getJson(teacherService.updateTeacher(teacherUpdateRequest));
            Response.makeResponse(resp, json);
        } catch (JsonProcessingException | NoUpdateException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void addTeacher(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            TeacherAddRequest teacherAddRequest = JsonService.getObject(req.getReader(), TeacherAddRequest.class);
            ValidationService.validation(teacherAddRequest);
            String json = JsonService.getJson(teacherService.addTeacher(teacherAddRequest));
            Response.makeResponse(resp, json);
        } catch (JsonProcessingException | NoAddException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void findAllTeachers(HttpServletResponse resp) throws IOException {
        try {
            String json = JsonService.getJson(teacherService.findAllTeachers());
            Response.makeResponse(resp, json);
        } catch (NotFoundException e) {
            HandlerExceptionService.handlerNotFoundException(e.getMessage(), resp);
        } catch (JsonProcessingException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        }
    }
}
