package org.example.homework2.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.homework2.dto.request.DisciplineAddRequest;
import org.example.homework2.dto.request.DisciplineUpdateRequest;
import org.example.homework2.dto.request.SearchRequest;
import org.example.homework2.dto.response.DisciplineResponse;
import org.example.homework2.dto.response.MessageResponse;
import org.example.homework2.exception.NoAddException;
import org.example.homework2.exception.NoUpdateException;
import org.example.homework2.exception.NotFoundException;
import org.example.homework2.service.DisciplineService;
import org.example.homework2.service.JsonService;
import org.example.homework2.validation.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.util.List;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class DisciplineServletTest {
    @Mock
    private DisciplineService mockService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private DisciplineServlet disciplineServlet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void doGet() throws IOException, NotFoundException {
        when(request.getServletPath()).thenReturn("/discipline/all");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        List<DisciplineResponse> disciplineResponses = List.of(new DisciplineResponse(1L, "Math"));
        when(mockService.findDisciplineAll()).thenReturn(disciplineResponses);
        disciplineServlet.doGet(request, response);
        verify(mockService, times(1)).findDisciplineAll();
        verify(request, times(1)).getServletPath();
        verify(response, times(1)).getWriter();
    }

    @Test
    void shouldDoPostDisciplineAdd() throws IOException, NoAddException {
        DisciplineAddRequest disciplineAddRequest = new DisciplineAddRequest();
        disciplineAddRequest.setDiscipline("Math");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(request.getServletPath()).thenReturn("/discipline/add");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"disciplineName\":\"Math\"}")));
        when(mockService.addDiscipline(any(DisciplineAddRequest.class))).thenReturn(new MessageResponse("Successful"));
        try (MockedStatic<JsonService> mockedJsonService = Mockito.mockStatic(JsonService.class)) {
            mockedJsonService.when(() -> JsonService.getObject(any(BufferedReader.class), eq(DisciplineAddRequest.class)))
                    .thenReturn(disciplineAddRequest);
            mockedJsonService.when(() -> JsonService.getJson(any(Object.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                disciplineServlet.doPost(request, response);
            }
        }
        verify(mockService,times(1)).addDiscipline(disciplineAddRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }

    @Test
    void shouldDoPostDisciplineUpdate() throws IOException, NoUpdateException {
        DisciplineUpdateRequest disciplineUpdateRequest = new DisciplineUpdateRequest();
        disciplineUpdateRequest.setId(1L);
        disciplineUpdateRequest.setDisciplineName("Math");
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/discipline/update");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"disciplineName\":\"Math\",\"id\":1}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.updateDiscipline(disciplineUpdateRequest)).thenReturn(new MessageResponse("Successful"));
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(DisciplineUpdateRequest.class))).thenReturn(disciplineUpdateRequest);
            mocked.when(() -> JsonService.getJson(any(Object.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                disciplineServlet.doPost(request, response);
            }
        }
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }

    @Test
    void shouldDoPostFindDisciplineByKey() throws IOException, NotFoundException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setKey("Math");
        List<DisciplineResponse> disciplineResponses = List.of(new DisciplineResponse(1L, "Math"));
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/discipline/search");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"key\":\"Math\"}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.findDisciplineKey(searchRequest)).thenReturn(disciplineResponses);
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(SearchRequest.class))).thenReturn(searchRequest);
            mocked.when(() -> JsonService.getJson(eq(List.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                disciplineServlet.doPost(request, response);
            }
        }
        verify(mockService).findDisciplineKey(searchRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }
}