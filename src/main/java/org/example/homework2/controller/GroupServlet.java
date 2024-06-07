package org.example.homework2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.homework2.dto.request.*;
import org.example.homework2.exception.*;
import org.example.homework2.exception.service.HandlerExceptionService;
import org.example.homework2.service.GroupService;
import org.example.homework2.service.JsonService;
import org.example.homework2.validation.service.ValidationService;

import java.io.IOException;

@WebServlet(name = "GroupServlet", urlPatterns = {"/group/all", "/group/add",
        "/group/update", "/group/delete", "/group/get/data", "/group/search", "/group/teaching"})
public class GroupServlet extends HttpServlet {
    private final String[] paths = {"/group/all", "/group/add",
            "/group/update", "/group/delete", "/group/get/data", "/group/search", "/group/teaching"};
    private transient GroupService groupService;

    @Override
    public void init() {
        groupService = new GroupService();
        ValidationService.getValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getServletPath();
        if (path.equals(paths[0])) {
            findGroupAll(resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getServletPath();
        if (path.equals(paths[1])) {
            addGroup(req, resp);
        } else if (path.equals(paths[2])) {
            updateGroup(req, resp);
        } else if (path.equals(paths[3])) {
            deleteGroup(req, resp);
        } else if (path.equals(paths[4])) {
            findGroupById(req, resp);
        } else if (path.equals(paths[5])) {
            searchGroup(req, resp);
        } else if (path.equals(paths[6])) {
            addTeaching(req, resp);
        }
    }

    private void addTeaching(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            TeachingRequest teachingRequest = JsonService.getObject(req.getReader(), TeachingRequest.class);
            ValidationService.validation(teachingRequest);
            String json = JsonService.getJson(groupService.addTeaching(teachingRequest));
            Response.makeResponse(resp, json);
        } catch (JsonProcessingException | NoAddException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void searchGroup(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            SearchRequest searchRequest = JsonService.getObject(req.getReader(), SearchRequest.class);
            ValidationService.validation(searchRequest);
            String json = JsonService.getJson(groupService.findGroupsByKey(searchRequest));
            Response.makeResponse(resp, json);
        } catch (NotFoundException e) {
            HandlerExceptionService.handlerNotFoundException(e.getMessage(), resp);
        } catch (JsonProcessingException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void findGroupById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            GroupRequest groupRequest = JsonService.getObject(req.getReader(), GroupRequest.class);
            ValidationService.validation(groupRequest);
            String json = JsonService.getJson(groupService.findGroupById(groupRequest));
            Response.makeResponse(resp, json);
        } catch (NotFoundException e) {
            HandlerExceptionService.handlerNotFoundException(e.getMessage(), resp);
        } catch (JsonProcessingException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void deleteGroup(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            GroupRequest groupRequest = JsonService.getObject(req.getReader(), GroupRequest.class);
            ValidationService.validation(groupRequest);
            String json = JsonService.getJson(groupService.deleteGroup(groupRequest));
            Response.makeResponse(resp, json);
        } catch (JsonProcessingException | DeleteException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void updateGroup(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            GroupUpdateRequest groupUpdateRequest = JsonService.getObject(req.getReader(), GroupUpdateRequest.class);
            ValidationService.validation(groupUpdateRequest);
            String json = JsonService.getJson(groupService.updateGroup(groupUpdateRequest));
            Response.makeResponse(resp, json);
        } catch (JsonProcessingException | NoUpdateException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void addGroup(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            GroupAddRequest groupAddRequest = JsonService.getObject(req.getReader(), GroupAddRequest.class);
            ValidationService.validation(groupAddRequest);
            String json = JsonService.getJson(groupService.addGroup(groupAddRequest));
            Response.makeResponse(resp, json);
        } catch (JsonProcessingException | NoAddException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        } catch (ValidationErrorsException e) {
            HandlerExceptionService.handlerBadRequestException(e.getMessage(), resp);
        }
    }

    private void findGroupAll(HttpServletResponse resp) throws IOException {
        try {
            String json = JsonService.getJson(groupService.findGroupsAll());
            Response.makeResponse(resp, json);
        } catch (NotFoundException e) {
            HandlerExceptionService.handlerNotFoundException(e.getMessage(), resp);
        } catch (JsonProcessingException e) {
            HandlerExceptionService.handlerServerException(e.getMessage(), resp);
        }
    }
}
