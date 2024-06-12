package org.example.homework2.service;

import org.example.homework2.database.dao.StudentDao;
import org.example.homework2.database.dao.TeacherDao;
import org.example.homework2.database.model.Discipline;
import org.example.homework2.database.model.Group;
import org.example.homework2.database.model.Student;
import org.example.homework2.database.model.Teacher;
import org.example.homework2.dto.request.*;
import org.example.homework2.dto.response.MessageResponse;
import org.example.homework2.dto.response.StudentResponse;
import org.example.homework2.dto.response.TeacherResponse;
import org.example.homework2.exception.DeleteException;
import org.example.homework2.exception.NoAddException;
import org.example.homework2.exception.NoUpdateException;
import org.example.homework2.exception.NotFoundException;
import org.example.homework2.mapper.StudentMapper;
import org.example.homework2.mapper.TeacherMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    private static TeacherService teacherService;

    @BeforeAll
    public static void setUp() {
        teacherService = new TeacherService();
    }

    @Test
    void shouldAddTeacher() throws NoAddException {
        TeacherAddRequest teacherAddRequest = new TeacherAddRequest();
        teacherAddRequest.setDisciplineId(1L);
        teacherAddRequest.setName("Angela");
        teacherAddRequest.setLastName("Len");
        try (MockedStatic<TeacherDao> mocked = Mockito.mockStatic(TeacherDao.class)) {
            mocked.when(()->TeacherDao.add(any(Teacher.class))).thenReturn(1);
            MessageResponse actual=teacherService.addTeacher(teacherAddRequest);
            String excepted="Teacher adding is successful";
            assertEquals(excepted,actual.getMessage());
        }
    }
    @Test
    void shouldAddTeacherThrowsException() {
        TeacherAddRequest teacherAddRequest = new TeacherAddRequest();
        teacherAddRequest.setDisciplineId(1L);
        teacherAddRequest.setName("Angela");
        teacherAddRequest.setLastName("Len");
        try (MockedStatic<TeacherDao> mocked = Mockito.mockStatic(TeacherDao.class)) {
            mocked.when(()->TeacherDao.add(any(Teacher.class))).thenReturn(0);
           assertThrows(NoAddException.class,()->teacherService.addTeacher(teacherAddRequest));
        }
    }
    @Test
    void  shouldUpdateTeacher() throws NoUpdateException {
        TeacherUpdateRequest teacherUpdateRequest = new TeacherUpdateRequest();
        teacherUpdateRequest.setId(1L);
        teacherUpdateRequest.setDisciplineId(1L);
        teacherUpdateRequest.setName("Angela");
        teacherUpdateRequest.setLastName("Len");
        try (MockedStatic<TeacherDao> mocked = Mockito.mockStatic(TeacherDao.class)) {
            mocked.when(()->TeacherDao.update(any(Teacher.class))).thenReturn(1);
            MessageResponse actual=teacherService.updateTeacher(teacherUpdateRequest);
            String excepted="Teacher updating is successful";
            assertEquals(excepted,actual.getMessage());
        }
    }
    @Test
    void  shouldUpdateTeacherThrowsException() {
        TeacherUpdateRequest teacherUpdateRequest = new TeacherUpdateRequest();
        teacherUpdateRequest.setId(1L);
        teacherUpdateRequest.setDisciplineId(1L);
        teacherUpdateRequest.setName("Angela");
        teacherUpdateRequest.setLastName("Len");
        try (MockedStatic<TeacherDao> mocked = Mockito.mockStatic(TeacherDao.class)) {
            mocked.when(()->TeacherDao.update(any(Teacher.class))).thenReturn(0);
            assertThrows(NoUpdateException.class,()->teacherService.updateTeacher(teacherUpdateRequest));
        }
    }

    @Test
    void shouldDeleteTeacher() throws DeleteException {
        TeacherRequest teacherRequest = new TeacherRequest();
        teacherRequest.setTeacherId(1L);
        try (MockedStatic<TeacherDao> mocked = Mockito.mockStatic(TeacherDao.class)) {
            mocked.when(()->TeacherDao.delete(any(Long.class))).thenReturn(1);
            MessageResponse actual=teacherService.deleteTeacher(teacherRequest);
            String excepted="Teacher deleting is successful";
            assertEquals(excepted,actual.getMessage());
        }
    }
    @Test
    void shouldDeleteTeacherThrowsException() {
        TeacherRequest teacherRequest = new TeacherRequest();
        teacherRequest.setTeacherId(1L);
        try (MockedStatic<TeacherDao> mocked = Mockito.mockStatic(TeacherDao.class)) {
            mocked.when(()->TeacherDao.delete(any(Long.class))).thenReturn(0);
            assertThrows(DeleteException.class,()->teacherService.deleteTeacher(teacherRequest));
        }
    }

    @Test
    void  shouldFindAllTeachers() throws NotFoundException {
        List<Teacher> teachers = List.of(new Teacher(1L, "Angela","Len",new Discipline(1L,"Math")));
        List<TeacherResponse> teacherResponses = TeacherMapper.INSTANCE.toTeacherResponses(teachers);
        try (MockedStatic<TeacherDao> mocked = Mockito.mockStatic(TeacherDao.class)) {
            mocked.when(TeacherDao::findAll).thenReturn(teachers);
            List<TeacherResponse> actual=teacherService.findAllTeachers();
            assertEquals(1,actual.size());
            assertEquals(teacherResponses.get(0).getId(), actual.get(0).getId());
        }
    }
    @Test
    void  shouldFindAllTeachersThrowsException() {
        try (MockedStatic<TeacherDao> mocked = Mockito.mockStatic(TeacherDao.class)) {
            mocked.when(TeacherDao::findAll).thenReturn(List.of());
           assertThrows(NotFoundException.class,()->teacherService.findAllTeachers());
        }
    }

    @Test
    void  shouldFindTeachersByDisciplineId() throws NotFoundException {
        List<Teacher> teachers = List.of(new Teacher(1L, "Angela","Len",new Discipline(1L,"Math")));
        List<TeacherResponse> teacherResponses = TeacherMapper.INSTANCE.toTeacherResponses(teachers);
        DisciplineRequest disciplineRequest = new DisciplineRequest();
        disciplineRequest.setDisciplineId(1L);
        try (MockedStatic<TeacherDao> mocked = Mockito.mockStatic(TeacherDao.class)) {
            mocked.when(()->TeacherDao.findByDisciplineId(any(Long.class))).thenReturn(teachers);
            List<TeacherResponse> actual=teacherService.findTeachersByDisciplineId(disciplineRequest);
            assertEquals(1,actual.size());
            assertEquals(teacherResponses.get(0).getId(), actual.get(0).getId());
        }
    }
    @Test
    void  shouldFindTeachersByDisciplineIdThrowsException() {
        DisciplineRequest disciplineRequest = new DisciplineRequest();
        disciplineRequest.setDisciplineId(1L);
        try (MockedStatic<TeacherDao> mocked = Mockito.mockStatic(TeacherDao.class)) {
            mocked.when(()->TeacherDao.findByDisciplineId(any(Long.class))).thenReturn(List.of());
            assertThrows(NotFoundException.class,()->teacherService.findTeachersByDisciplineId(disciplineRequest));
        }
    }

    @Test
    void  shouldFindTeachersByKey() throws NotFoundException {
        List<Teacher> teachers = List.of(new Teacher(1L, "Angela","Len",new Discipline(1L,"Math")));
        List<TeacherResponse> teacherResponses = TeacherMapper.INSTANCE.toTeacherResponses(teachers);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setKey("Math");
        try (MockedStatic<TeacherDao> mocked = Mockito.mockStatic(TeacherDao.class)) {
            mocked.when(()->TeacherDao.findByKey(any(String.class))).thenReturn(teachers);
            List<TeacherResponse> actual=teacherService.findTeachersByKey(searchRequest);
            assertEquals(1,actual.size());
            assertEquals(teacherResponses.get(0).getId(), actual.get(0).getId());
        }
    }

    @Test
    void  shouldFindTeachersByKeyThrowsException() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setKey("Math");
        try (MockedStatic<TeacherDao> mocked = Mockito.mockStatic(TeacherDao.class)) {
            mocked.when(()->TeacherDao.findByKey(any(String.class))).thenReturn(List.of());
            assertThrows(NotFoundException.class,()->teacherService.findTeachersByKey(searchRequest));
        }
    }

    @Test
    void  shouldFindTeacherById() throws NotFoundException {
        Teacher teacher =new Teacher(1L, "Angela","Len",new Discipline(1L,"Math"));
        TeacherResponse teacherResponse = TeacherMapper.INSTANCE.toTeacherResponse(teacher);
        TeacherRequest teacherRequest = new TeacherRequest();
        teacherRequest.setTeacherId(1L);
        try (MockedStatic<TeacherDao> mocked = Mockito.mockStatic(TeacherDao.class)) {
            mocked.when(()->TeacherDao.findById(any(Long.class))).thenReturn(teacher);
            TeacherResponse actual=teacherService.findTeacherById(teacherRequest);
            assertEquals(teacherResponse.getId(), actual.getId());
        }
    }
    @Test
    void  shouldFindTeacherByIdThrowsException() {
        TeacherRequest teacherRequest = new TeacherRequest();
        teacherRequest.setTeacherId(1L);
        try (MockedStatic<TeacherDao> mocked = Mockito.mockStatic(TeacherDao.class)) {
            mocked.when(()->TeacherDao.findById(any(Long.class))).thenReturn(null);
            assertThrows(NotFoundException.class,()->teacherService.findTeacherById(teacherRequest));
        }
    }
}