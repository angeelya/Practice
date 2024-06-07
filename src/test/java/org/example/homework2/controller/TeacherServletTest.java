package org.example.homework2.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.homework2.dto.request.*;
import org.example.homework2.dto.response.MessageResponse;
import org.example.homework2.dto.response.TeacherResponse;
import org.example.homework2.exception.DeleteException;
import org.example.homework2.exception.NoAddException;
import org.example.homework2.exception.NoUpdateException;
import org.example.homework2.exception.NotFoundException;
import org.example.homework2.service.JsonService;
import org.example.homework2.service.TeacherService;
import org.example.homework2.validation.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.*;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class TeacherServletTest {
    @Mock
    private static TeacherService mockService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private static TeacherServlet teacherServlet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void shouldDoGet() throws IOException, NotFoundException {
        when(request.getServletPath()).thenReturn("/teacher/all");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        List<TeacherResponse> teacherResponses = List.of(new TeacherResponse(1L, "Angela","Len","Math"));
        when(mockService.findAllTeachers()).thenReturn(teacherResponses);
        teacherServlet.doGet(request, response);
        verify(mockService, times(1)).findAllTeachers();
        verify(request, times(1)).getServletPath();
        verify(response, times(1)).getWriter();
    }

    @Test
    void shouldDoPostAdd() throws IOException, NoAddException {
        TeacherAddRequest teacherAddRequest = new TeacherAddRequest();
        teacherAddRequest.setDisciplineId(1L);
        teacherAddRequest.setName("Angela");
        teacherAddRequest.setLastName("Len");
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/teacher/add");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"name\":\"Angel\",\"lastName\":\"Len\",\"disciplineId\":1}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.addTeacher(teacherAddRequest)).thenReturn(new MessageResponse("Successful"));
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(TeacherAddRequest.class))).thenReturn(teacherAddRequest);
            mocked.when(() -> JsonService.getJson(any(Object.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                teacherServlet.doPost(request, response);
            }
        }
        verify(mockService,times(1)).addTeacher(teacherAddRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }
    @Test
    void shouldDoPostUpdate() throws IOException, NoUpdateException {
        TeacherUpdateRequest teacherUpdateRequest = new TeacherUpdateRequest();
        teacherUpdateRequest.setId(1L);
        teacherUpdateRequest.setDisciplineId(1L);
        teacherUpdateRequest.setName("Angela");
        teacherUpdateRequest.setLastName("Len");
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/teacher/update");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"name\":\"Angel\",\"lastName\":\"Len\",\"disciplineId\":1,\"id\":1}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.updateTeacher(teacherUpdateRequest)).thenReturn(new MessageResponse("Successful"));
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(TeacherUpdateRequest.class))).thenReturn(teacherUpdateRequest);
            mocked.when(() -> JsonService.getJson(any(Object.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                teacherServlet.doPost(request, response);
            }
        }
        verify(mockService,times(1)).updateTeacher(teacherUpdateRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }
    @Test
    void shouldDoPostDelete() throws IOException, DeleteException {
        TeacherRequest teacherRequest = new TeacherRequest();
        teacherRequest.setTeacherId(1L);
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/teacher/delete");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"teacherId\":1}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.deleteTeacher(teacherRequest)).thenReturn(new MessageResponse("Successful"));
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(TeacherRequest.class))).thenReturn(teacherRequest);
            mocked.when(() -> JsonService.getJson(any(Object.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                teacherServlet.doPost(request, response);
            }
        }
        verify(mockService,times(1)).deleteTeacher(teacherRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }
    @Test
    void shouldDoPostFindTeacherById() throws IOException, NotFoundException {
        TeacherRequest teacherRequest = new TeacherRequest();
        teacherRequest.setTeacherId(1L);
        TeacherResponse teacherResponse =new TeacherResponse(1L, "Angela","Len","Math");
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/teacher/get/data");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"teacherId\":1}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.findTeacherById(teacherRequest)).thenReturn(teacherResponse);
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(TeacherRequest.class))).thenReturn(teacherRequest);
            mocked.when(() -> JsonService.getJson(any(Object.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                teacherServlet.doPost(request, response);
            }
        }
        verify(mockService,times(1)).findTeacherById(teacherRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }
    @Test
    void shouldDoPostFindTeacherByDiscipline() throws IOException, NotFoundException {
        DisciplineRequest disciplineRequest = new DisciplineRequest();
        disciplineRequest.setDisciplineId(1L);
        List<TeacherResponse> teacherResponses =List.of(new TeacherResponse(1L, "Angela","Len","Math"));
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/teacher/get/by/discipline");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"disciplineId\":1}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.findTeachersByDisciplineId(disciplineRequest)).thenReturn(teacherResponses);
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(DisciplineRequest.class))).thenReturn(disciplineRequest);
            mocked.when(() -> JsonService.getJson(eq(List.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                teacherServlet.doPost(request, response);
            }
        }
        verify(mockService,times(1)).findTeachersByDisciplineId(disciplineRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }
    @Test
    void shouldDoPostSearchTeacher() throws IOException, NotFoundException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setKey("Angela");
        List<TeacherResponse> teacherResponses =List.of(new TeacherResponse(1L, "Angela","Len","Math"));
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/teacher/search");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"key\":\"Angela\"}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.findTeachersByKey(searchRequest)).thenReturn(teacherResponses);
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(SearchRequest.class))).thenReturn(searchRequest);
            mocked.when(() -> JsonService.getJson(eq(List.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                teacherServlet.doPost(request, response);
            }
        }
        verify(mockService,times(1)).findTeachersByKey(searchRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }
}