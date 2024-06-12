package org.example.homework2.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.homework2.dto.request.*;
import org.example.homework2.dto.response.GroupResponse;
import org.example.homework2.dto.response.MessageResponse;
import org.example.homework2.exception.DeleteException;
import org.example.homework2.exception.NoAddException;
import org.example.homework2.exception.NoUpdateException;
import org.example.homework2.exception.NotFoundException;
import org.example.homework2.service.GroupService;
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
import static org.mockito.Mockito.times;
@ExtendWith(MockitoExtension.class)

class GroupServletTest {
    @Mock
    private static GroupService mockService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private static GroupServlet groupServlet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDoGet() throws IOException, NotFoundException {
        when(request.getServletPath()).thenReturn("/group/all");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        List<GroupResponse> groupResponses = List.of(new GroupResponse(1L, "Math"));
        when(mockService.findGroupsAll()).thenReturn(groupResponses);
        groupServlet.doGet(request, response);
        verify(mockService, times(1)).findGroupsAll();
        verify(request, times(1)).getServletPath();
        verify(response, times(1)).getWriter();
    }

    @Test
    void shouldDoPostAdd() throws IOException, NoAddException {
        GroupAddRequest groupAddRequest = new GroupAddRequest();
        groupAddRequest.setName("M-12");
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/group/add");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"name\":\"M-12\"}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.addGroup(groupAddRequest)).thenReturn(new MessageResponse("Successful"));
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(GroupAddRequest.class))).thenReturn(groupAddRequest);
            mocked.when(() -> JsonService.getJson(any(Object.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                groupServlet.doPost(request, response);
            }
        }
        verify(mockService, times(1)).addGroup(groupAddRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }

    @Test
    void shouldDoPostUpdate() throws IOException, NoUpdateException {
        GroupUpdateRequest groupUpdateRequest = new GroupUpdateRequest();
        groupUpdateRequest.setId(1L);
        groupUpdateRequest.setName("M-12");
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/group/update");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"name\":\"M-12\",\"id\":1}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.updateGroup(groupUpdateRequest)).thenReturn(new MessageResponse("Successful"));
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(GroupUpdateRequest.class))).thenReturn(groupUpdateRequest);
            mocked.when(() -> JsonService.getJson(any(Object.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                groupServlet.doPost(request, response);
            }
        }
        verify(mockService, times(1)).updateGroup(groupUpdateRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }

    @Test
    void shouldDoPostDelete() throws IOException, DeleteException {
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setGroupId(1L);
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/group/delete");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"groupId\":1}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.deleteGroup(groupRequest)).thenReturn(new MessageResponse("Successful"));
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(GroupRequest.class))).thenReturn(groupRequest);
            mocked.when(() -> JsonService.getJson(any(Object.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                groupServlet.doPost(request, response);
            }
        }
        verify(mockService, times(1)).deleteGroup(groupRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }

    @Test
    void shouldDoPostFindGroupById() throws IOException, NotFoundException {
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setGroupId(1L);
        GroupResponse groupResponse = new GroupResponse(1L, "M-1");
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/group/get/data");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"groupId\":1}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.findGroupById(groupRequest)).thenReturn(groupResponse);
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(GroupRequest.class))).thenReturn(groupRequest);
            mocked.when(() -> JsonService.getJson(eq(List.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                groupServlet.doPost(request, response);
            }
        }
        verify(mockService, times(1)).findGroupById(groupRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }

    @Test
    void shouldDoPostSearchGroup() throws IOException, NotFoundException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setKey("M-12");
        List<GroupResponse> groupResponses = List.of(new GroupResponse(1L, "M-1"));
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/group/search");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"key\":\"M-12\"}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.findGroupsByKey(searchRequest)).thenReturn(groupResponses);
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(SearchRequest.class))).thenReturn(searchRequest);
            mocked.when(() -> JsonService.getJson(eq(List.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                groupServlet.doPost(request, response);
            }
        }
        verify(mockService, times(1)).findGroupsByKey(searchRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }

    @Test
    void shouldDoPostAddTeaching() throws IOException, NoAddException {
        TeachingRequest teachingRequest = new TeachingRequest();
        teachingRequest.setGroupId(1L);
        teachingRequest.setTeacherId(2L);
        PrintWriter writer = new PrintWriter("response");
        when(request.getServletPath()).thenReturn("/group/teaching");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"groupId\":1,\"teacherId\":2}")));
        when(response.getWriter()).thenReturn(writer);
        when(mockService.addTeaching(teachingRequest)).thenReturn(new MessageResponse("Successful"));
        try (MockedStatic<JsonService> mocked = Mockito.mockStatic(JsonService.class)) {
            mocked.when(() -> JsonService.getObject(any(BufferedReader.class), eq(TeachingRequest.class))).thenReturn(teachingRequest);
            mocked.when(() -> JsonService.getJson(any(Object.class))).thenReturn("json");
            try (MockedStatic<ValidationService> mockedValid = Mockito.mockStatic(ValidationService.class)) {
                groupServlet.doPost(request, response);
            }
        }
        verify(mockService, times(1)).addTeaching(teachingRequest);
        verify(request, times(1)).getServletPath();
        verify(request, times(1)).getReader();
        verify(response, times(1)).getWriter();
    }
}