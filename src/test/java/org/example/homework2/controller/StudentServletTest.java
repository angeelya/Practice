package org.example.homework2.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.homework2.dto.request.*;
import org.example.homework2.dto.response.MessageResponse;
import org.example.homework2.dto.response.StudentResponse;
import org.example.homework2.exception.DeleteException;
import org.example.homework2.exception.NoAddException;
import org.example.homework2.exception.NoUpdateException;
import org.example.homework2.exception.NotFoundException;
import org.example.homework2.service.JsonService;
import org.example.homework2.service.StudentService;
import org.example.homework2.validation.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
@ExtendWith(MockitoExtension.class)

class StudentServletTest {
    @Mock
    private static StudentService mockService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private static StudentServlet studentServlet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDoGet() throws IOException, NotFoundException {
        when(request.getServletPath()).thenReturn("/student/all");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        List<StudentResponse> studentResponses = List.of(new StudentResponse(1L, "Angela","Len","M-12"));
        when(mockService.findAllStudents()).thenReturn(studentResponses);
        studentServlet.doGet(request, response);
        verify(mockService, times(1)).findAllStudents();
        verify(request, times(1)).getServletPath();
        verify(response, times(1)).getWriter();
    }

    @Test
    void shouldDoPostAdd() throws IOException, NoAddException {
        StudentAddRequest studentAddRequest = new StudentAddRequest();
        studentAddRequest.setGroupId(1L);
        studentAddRequest.setName("Angela");
        studentAddRequest.setLastName("Len");
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/student/add");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"name\":\"Angel\",\"lastName\":\"Len\",\"groupId\":1}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.addStudent(studentAddRequest)).thenReturn(new MessageResponse("Successful"));
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(StudentAddRequest.class))).thenReturn(studentAddRequest);
            mocked.when(() -> JsonService.getJson(any(Object.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                studentServlet.doPost(request, response);
            }
        }
        verify( mockService, times(1)).addStudent(studentAddRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }
    @Test
    void shouldDoPostUpdate() throws IOException, NoUpdateException {
        StudentUpdateRequest studentUpdateRequest = new StudentUpdateRequest();
        studentUpdateRequest.setId(1L);
        studentUpdateRequest.setGroupId(1L);
        studentUpdateRequest.setName("Angela");
        studentUpdateRequest.setLastName("Len");
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/student/update");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"name\":\"Angel\",\"lastName\":\"Len\",\"groupId\":1,\"id\":1}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.updateStudent(studentUpdateRequest)).thenReturn(new MessageResponse("Successful"));
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(StudentUpdateRequest.class))).thenReturn(studentUpdateRequest);
            mocked.when(() -> JsonService.getJson(any(Object.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                studentServlet.doPost(request, response);
            }
        }
        verify(mockService,times(1)).updateStudent(studentUpdateRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }
    @Test
    void shouldDoPostDelete() throws IOException, DeleteException {
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setStudentId(1L);
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/student/delete");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"studentId\":1}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.deleteStudent(studentRequest)).thenReturn(new MessageResponse("Successful"));
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(StudentRequest.class))).thenReturn(studentRequest);
            mocked.when(() -> JsonService.getJson(any(Object.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                studentServlet.doPost(request, response);
            }
        }
        verify(mockService,times(1)).deleteStudent(studentRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }
    @Test
    void shouldDoPostFindStudentById() throws IOException, NotFoundException {
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setStudentId(1L);
        StudentResponse studentResponse = new StudentResponse(1L, "Angela","Len","M-12");
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/student/get/data");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"studentId\":1}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.findStudentById(studentRequest)).thenReturn(studentResponse);
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(StudentRequest.class))).thenReturn(studentRequest);
            mocked.when(() -> JsonService.getJson(any(Object.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                studentServlet.doPost(request, response);
            }
        }
        verify(mockService,times(1)).findStudentById(studentRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }
    @Test
    void shouldDoPostFindStudentsByGroupId() throws IOException, NotFoundException {
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setGroupId(1L);
        List<StudentResponse> studentResponses = List.of(new StudentResponse(1L, "Angela","Len","M-12"));
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/student/get/by/group");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"groupId\":1}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.findStudentByGroup(groupRequest)).thenReturn(studentResponses);
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(GroupRequest.class))).thenReturn(groupRequest);
            mocked.when(() -> JsonService.getJson(eq(List.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                studentServlet.doPost(request, response);
            }
        }
        verify(mockService,times(1)).findStudentByGroup(groupRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }
    @Test
    void shouldDoPostSearchStudent() throws IOException, NotFoundException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setKey("Angela");
        List<StudentResponse> studentResponses = List.of(new StudentResponse(1L, "Angela","Len","M-12"));
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/student/search");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"groupId\":1}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.findStudentsByKey(searchRequest)).thenReturn(studentResponses);
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(SearchRequest.class))).thenReturn(searchRequest);
            mocked.when(() -> JsonService.getJson(eq(List.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                studentServlet.doPost(request, response);
            }
        }
        verify(mockService,times(1)).findStudentsByKey(searchRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }
}