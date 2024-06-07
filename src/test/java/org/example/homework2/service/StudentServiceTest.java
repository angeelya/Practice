package org.example.homework2.service;

import org.example.homework2.database.dao.StudentDao;
import org.example.homework2.database.model.Group;
import org.example.homework2.database.model.Student;
import org.example.homework2.dto.request.*;
import org.example.homework2.dto.response.MessageResponse;
import org.example.homework2.dto.response.StudentResponse;
import org.example.homework2.exception.DeleteException;
import org.example.homework2.exception.NoAddException;
import org.example.homework2.exception.NoUpdateException;
import org.example.homework2.exception.NotFoundException;
import org.example.homework2.mapper.StudentMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class StudentServiceTest {
    private static StudentService studentService;

    @BeforeAll
    public static void setUp() {
        studentService = new StudentService();
    }

    @Test
    void shouldFindAllStudents() throws NotFoundException {
        List<Student> students = List.of(new Student(1L, "Angela","Len",new Group(1L,"M-12")));
        List<StudentResponse> studentResponses = StudentMapper.INSTANCE.studentsToStudentResponses(students);
        try (MockedStatic<StudentDao> mocked = Mockito.mockStatic(StudentDao.class)) {
            mocked.when(StudentDao::findAll).thenReturn(students);
            List<StudentResponse> actual=studentService.findAllStudents();
            assertEquals(1,actual.size());
            assertEquals(studentResponses.get(0).getId(), actual.get(0).getId());
        }
    }
    @Test
    void shouldFindAllStudentsThrowsException() {
        try (MockedStatic<StudentDao> mocked = Mockito.mockStatic(StudentDao.class)) {
            mocked.when(StudentDao::findAll).thenReturn(List.of());
           assertThrows(NotFoundException.class,()->studentService.findAllStudents());
        }
    }
    @Test
    void shouldFindStudentByGroup() throws NotFoundException {
        List<Student> students = List.of(new Student(1L, "Angela","Len",new Group(1L,"M-12")));
        List<StudentResponse> studentResponses = StudentMapper.INSTANCE.studentsToStudentResponses(students);
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setGroupId(1L);
        try (MockedStatic<StudentDao> mocked = Mockito.mockStatic(StudentDao.class)) {
            mocked.when(()->StudentDao.findByGroupId(any(Long.class))).thenReturn(students);
            List<StudentResponse> actual=studentService.findStudentByGroup(groupRequest);
            assertEquals(1,actual.size());
            assertEquals(studentResponses.get(0).getId(), actual.get(0).getId());
        }
    }
    @Test
    void shouldFindStudentByGroupThrowsException() {
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setGroupId(1L);
        try (MockedStatic<StudentDao> mocked = Mockito.mockStatic(StudentDao.class)) {
            mocked.when(()->StudentDao.findByGroupId(any(Long.class))).thenReturn(List.of());
            assertThrows(NotFoundException.class,()->studentService.findStudentByGroup(groupRequest));
        }
    }
    @Test
    void shouldFindStudentById() throws NotFoundException {
        Student student = new Student(1L, "Angela","Len",new Group(1L,"M-12"));
        StudentResponse studentResponse = StudentMapper.INSTANCE.studentToStudentResponse(student);
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setStudentId(1L);
        try (MockedStatic<StudentDao> mocked = Mockito.mockStatic(StudentDao.class)) {
            mocked.when(()->StudentDao.findById(any(Long.class))).thenReturn(student);
            StudentResponse actual=studentService.findStudentById(studentRequest);
            assertEquals(studentResponse.getId(), actual.getId());
        }
    }
    @Test
    void shouldFindStudentByIdThrowsException()  {
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setStudentId(1L);
        try (MockedStatic<StudentDao> mocked = Mockito.mockStatic(StudentDao.class)) {
            mocked.when(()->StudentDao.findById(any(Long.class))).thenReturn(null);
        assertThrows(NotFoundException.class,()->studentService.findStudentById(studentRequest));
        }
    }

    @Test
    void shouldFindStudentsByKey() throws NotFoundException {
        List<Student> students = List.of(new Student(1L, "Angela","Len",new Group(1L,"M-12")));
        List<StudentResponse> studentResponses = StudentMapper.INSTANCE.studentsToStudentResponses(students);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setKey("Angelina");
        try (MockedStatic<StudentDao> mocked = Mockito.mockStatic(StudentDao.class)) {
            mocked.when(()->StudentDao.findByKey(any(String.class))).thenReturn(students);
            List<StudentResponse> actual=studentService.findStudentsByKey(searchRequest);
            assertEquals(1,actual.size());
            assertEquals(studentResponses.get(0).getId(), actual.get(0).getId());
        }
    }
    @Test
    void shouldFindStudentsByKeyThrowsException() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setKey("Angelina");
        try (MockedStatic<StudentDao> mocked = Mockito.mockStatic(StudentDao.class)) {
            mocked.when(()->StudentDao.findByKey(any(String.class))).thenReturn(List.of());
            assertThrows(NotFoundException.class,()->studentService.findStudentsByKey(searchRequest));
        }
    }

    @Test
    void shouldAddStudent() throws NoAddException {
        StudentAddRequest studentAddRequest = new StudentAddRequest();
        studentAddRequest.setGroupId(1L);
        studentAddRequest.setName("Angela");
        studentAddRequest.setLastName("Len");
        try (MockedStatic<StudentDao> mocked = Mockito.mockStatic(StudentDao.class)) {
            mocked.when(()->StudentDao.add(any(Student.class))).thenReturn(1);
            MessageResponse actual=studentService.addStudent(studentAddRequest);
            String excepted="Student adding is successful";
            assertEquals(excepted,actual.getMessage());
        }
    }
    @Test
    void shouldAddStudentThrowsException()  {
        StudentAddRequest studentAddRequest = new StudentAddRequest();
        studentAddRequest.setGroupId(1L);
        studentAddRequest.setName("Angela");
        studentAddRequest.setLastName("Len");
        try (MockedStatic<StudentDao> mocked = Mockito.mockStatic(StudentDao.class)) {
            mocked.when(()->StudentDao.add(any(Student.class))).thenReturn(0);
            assertThrows(NoAddException.class,()->studentService.addStudent(studentAddRequest));
        }
    }

    @Test
    void shouldUpdateStudent() throws NoUpdateException {
        StudentUpdateRequest studentUpdateRequest = new StudentUpdateRequest();
        studentUpdateRequest.setId(1L);
        studentUpdateRequest.setGroupId(1L);
        studentUpdateRequest.setName("Angela");
        studentUpdateRequest.setLastName("Len");
        try (MockedStatic<StudentDao> mocked = Mockito.mockStatic(StudentDao.class)) {
            mocked.when(()->StudentDao.update(any(Student.class))).thenReturn(1);
            MessageResponse actual=studentService.updateStudent(studentUpdateRequest);
            String excepted="Student updating is successful";
            assertEquals(excepted,actual.getMessage());
        }
    }
    @Test
    void shouldUpdateStudentThrowsException() {
        StudentUpdateRequest studentUpdateRequest = new StudentUpdateRequest();
        studentUpdateRequest.setId(1L);
        studentUpdateRequest.setGroupId(1L);
        studentUpdateRequest.setName("Angela");
        studentUpdateRequest.setLastName("Len");
        try (MockedStatic<StudentDao> mocked = Mockito.mockStatic(StudentDao.class)) {
            mocked.when(()->StudentDao.update(any(Student.class))).thenReturn(0);
            assertThrows(NoUpdateException.class,()->studentService.updateStudent(studentUpdateRequest));
        }
    }
    @Test
    void shouldDeleteStudent() throws DeleteException {
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setStudentId(1L);
        try (MockedStatic<StudentDao> mocked = Mockito.mockStatic(StudentDao.class)) {
            mocked.when(()->StudentDao.delete(any(Long.class))).thenReturn(1);
            MessageResponse actual=studentService.deleteStudent(studentRequest);
            String excepted="Student deleting is successful";
            assertEquals(excepted,actual.getMessage());
        }
    }
    @Test
    void shouldDeleteStudentThrowsException() {
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setStudentId(1L);
        try (MockedStatic<StudentDao> mocked = Mockito.mockStatic(StudentDao.class)) {
            mocked.when(()->StudentDao.delete(any(Long.class))).thenReturn(0);
            assertThrows(DeleteException.class,()->studentService.deleteStudent(studentRequest));
        }
    }
}