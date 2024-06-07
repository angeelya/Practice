package org.example.homework2.service;

import org.example.homework2.database.dao.DisciplineDao;
import org.example.homework2.database.model.Discipline;
import org.example.homework2.dto.request.DisciplineAddRequest;
import org.example.homework2.dto.request.DisciplineUpdateRequest;
import org.example.homework2.dto.request.SearchRequest;
import org.example.homework2.dto.response.DisciplineResponse;
import org.example.homework2.dto.response.MessageResponse;
import org.example.homework2.exception.NoAddException;
import org.example.homework2.exception.NoUpdateException;
import org.example.homework2.exception.NotFoundException;
import org.example.homework2.mapper.DisciplineMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class DisciplineServiceTest {
    private static DisciplineService disciplineService;

    @BeforeAll
    public static void setUp() {
        disciplineService = new DisciplineService();
    }


    @Test
    void shouldAddDiscipline() throws NoAddException {
        DisciplineAddRequest disciplineAddRequest = new DisciplineAddRequest();
        disciplineAddRequest.setDiscipline("Math");
        try (MockedStatic<DisciplineDao> mocked = Mockito.mockStatic(DisciplineDao.class)) {
            mocked.when(() -> DisciplineDao.add(any(Discipline.class))).thenReturn(1);
            MessageResponse messageResponse = disciplineService.addDiscipline(disciplineAddRequest);
            String excepted = "Discipline adding is successful";
            assertEquals(excepted, messageResponse.getMessage());
        }
    }

    @Test
    void shouldAddDisciplineThrowsException()  {
        DisciplineAddRequest disciplineAddRequest = new DisciplineAddRequest();
        disciplineAddRequest.setDiscipline("Math");
        try (MockedStatic<DisciplineDao> mocked = Mockito.mockStatic(DisciplineDao.class)) {
            mocked.when(() -> DisciplineDao.add(any(Discipline.class))).thenReturn(0);
            assertThrows(NoAddException.class, () -> disciplineService.addDiscipline(disciplineAddRequest));
        }
    }

    @Test
    void shouldUpdateDiscipline() throws NoUpdateException {
        DisciplineUpdateRequest disciplineUpdateRequest = new DisciplineUpdateRequest();
        disciplineUpdateRequest.setId(1L);
        disciplineUpdateRequest.setDisciplineName("Math");
        try (MockedStatic<DisciplineDao> mocked = Mockito.mockStatic(DisciplineDao.class)) {
            mocked.when(() -> DisciplineDao.update(any(Discipline.class))).thenReturn(1);
            MessageResponse messageResponse = disciplineService.updateDiscipline(disciplineUpdateRequest);
            String excepted = "Discipline updating is successful";
            assertEquals(excepted, messageResponse.getMessage());
        }
    }

    @Test
    void shouldUpdateDisciplineThrowsException() {
        DisciplineUpdateRequest disciplineUpdateRequest = new DisciplineUpdateRequest();
        disciplineUpdateRequest.setId(1L);
        disciplineUpdateRequest.setDisciplineName("Math");
        try (MockedStatic<DisciplineDao> mocked = Mockito.mockStatic(DisciplineDao.class)) {
            mocked.when(() -> DisciplineDao.update(any(Discipline.class))).thenReturn(0);
            assertThrows(NoUpdateException.class, () -> disciplineService.updateDiscipline(disciplineUpdateRequest));
        }
    }

    @Test
    void shouldFindDisciplineAll() throws NotFoundException {
        List<Discipline> disciplines = List.of(new Discipline(1L, "Math"));
        List<DisciplineResponse> disciplineResponses = DisciplineMapper.INSTANCE.toDisciplineResponses(disciplines);
        try (MockedStatic<DisciplineDao> mocked = Mockito.mockStatic(DisciplineDao.class)) {
            mocked.when(DisciplineDao::findAll).thenReturn(disciplines);
            List<DisciplineResponse> actual=disciplineService.findDisciplineAll();
            assertEquals(1,actual.size());
            assertEquals(disciplineResponses.get(0).getId(), actual.get(0).getId());
        }
    }
    @Test
    void shouldFindDisciplineAllThrowsException() {
        try (MockedStatic<DisciplineDao> mocked = Mockito.mockStatic(DisciplineDao.class)) {
            mocked.when(DisciplineDao::findAll).thenReturn(List.of());
           assertThrows(NotFoundException.class,()->disciplineService.findDisciplineAll());
        }
    }

    @Test
    void shouldFindDisciplineKey() throws NotFoundException {
        List<Discipline> disciplines = List.of(new Discipline(1L, "Math"));
        List<DisciplineResponse> disciplineResponses = DisciplineMapper.INSTANCE.toDisciplineResponses(disciplines);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setKey("Math");
        try (MockedStatic<DisciplineDao> mocked = Mockito.mockStatic(DisciplineDao.class)) {
            mocked.when(() -> DisciplineDao.findByKey(any(String.class))).thenReturn(disciplines);
            List<DisciplineResponse> actual=disciplineService.findDisciplineKey(searchRequest);
            assertEquals(1,actual.size());
            assertEquals(disciplineResponses.get(0).getId(), actual.get(0).getId());
        }
    }
    @Test
    void shouldFindDisciplineKeyThrowsException()  {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setKey("Math");
        try (MockedStatic<DisciplineDao> mocked = Mockito.mockStatic(DisciplineDao.class)) {
            mocked.when(() -> DisciplineDao.findByKey(any(String.class))).thenReturn(List.of());
           assertThrows(NotFoundException.class,()->disciplineService.findDisciplineKey(searchRequest));
        }
    }
}