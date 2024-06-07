package org.example.homework2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.homework2.dto.request.*;
import org.example.homework2.exception.*;
import org.example.homework2.exception.service.HandlerExceptionService;
import org.example.homework2.service.JsonService;
import org.example.homework2.service.StudentService;
import org.example.homework2.validation.service.ValidationService;

import java.io.IOException;


@WebServlet(name = "StudentServlet", urlPatterns = {"/student/add", "/student/get/data",
        "/student/update", "/student/delete", "/student/all", "/student/get/by/group", "/student/search"})
public class StudentServlet extends HttpServlet {
    private final String[] paths = {"/student/all", "/student/add", "/student/update",
            "/student/get/data", "/student/delete", "/student/get/by/group", "/student/search"};
    private transient StudentService studentService;

    @Override
    public void init() {
        studentService = new StudentService();
        ValidationService.getValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getServletPath();
        if (path.equals(paths[0])) {
            findStudentsAll(resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getServletPath();
        if (path.equals(paths[1])) {
            addStudent(req, resp);
        } else if (path.equals(paths[2])) {
            updateStudent(req, resp);
        } else if (path.equals(paths[3])) {
            findStudentById(req, resp);
        } else if (path.equals(paths[4])) {
            deleteStudent(req, resp);
        } else if (path.equals(paths[5])) {
            findStudentsByGroupId(req, resp);
        } else if (path.equals(paths[6])) {
            searchStudent(req, resp);
        }
    }

    private void searchStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            SearchRequest searchRequest = JsonService.getObject(req.getReader(), SearchRequest.class);
            ValidationService.validation(searchRequest);
            String json = JsonService.getJson(studentService.findStudentsByKey(searchRequest));
            Response.makeResponse(resp, json);
        } catch (NotFoundException e) {
            HandlerExceptionService.handlerNotFoundException(e.getMessage(), resp);
        } catch (JsonProcessingException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void findStudentsByGroupId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            GroupRequest groupRequest = JsonService.getObject(req.getReader(), GroupRequest.class);
            ValidationService.validation(groupRequest);
            String json = JsonService.getJson(studentService.findStudentByGroup(groupRequest));
            Response.makeResponse(resp, json);
        } catch (NotFoundException e) {
            HandlerExceptionService.handlerNotFoundException(e.getMessage(), resp);
        } catch (JsonProcessingException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            StudentRequest studentRequest = JsonService.getObject(req.getReader(), StudentRequest.class);
            ValidationService.validation(studentRequest);
            String json = JsonService.getJson(studentService.deleteStudent(studentRequest));
            Response.makeResponse(resp, json);
        } catch (JsonProcessingException | DeleteException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void findStudentById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            StudentRequest studentRequest = JsonService.getObject(req.getReader(), StudentRequest.class);
            ValidationService.validation(studentRequest);
            String json = JsonService.getJson(studentService.findStudentById(studentRequest));
            Response.makeResponse(resp, json);
        } catch (NotFoundException e) {
            HandlerExceptionService.handlerNotFoundException(e.getMessage(), resp);
        } catch (JsonProcessingException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            StudentUpdateRequest studentUpdateRequest = JsonService.getObject(req.getReader(), StudentUpdateRequest.class);
            ValidationService.validation(studentUpdateRequest);
            String json = JsonService.getJson(studentService.updateStudent(studentUpdateRequest));
            Response.makeResponse(resp, json);
        } catch (JsonProcessingException | NoUpdateException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void addStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            StudentAddRequest studentAddRequest = JsonService.getObject(req.getReader(), StudentAddRequest.class);
            ValidationService.validation(studentAddRequest);
            String json = JsonService.getJson(studentService.addStudent(studentAddRequest));
            Response.makeResponse(resp, json);
        } catch (JsonProcessingException | NoAddException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void findStudentsAll(HttpServletResponse resp) throws IOException {
        try {
            String json = JsonService.getJson(studentService.findAllStudents());
            Response.makeResponse(resp, json);
        } catch (NotFoundException e) {
            HandlerExceptionService.handlerNotFoundException(e.getMessage(), resp);
        } catch (JsonProcessingException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        }
    }
}
