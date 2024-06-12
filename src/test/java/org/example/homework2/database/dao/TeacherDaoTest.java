package org.example.homework2.database.dao;

import org.example.homework2.database.DataSource;
import org.example.homework2.database.model.Discipline;
import org.example.homework2.database.model.Teacher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.MySQLContainer;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TeacherDaoTest {

    public static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("university")
            .withUsername("root")
            .withInitScript("init.sql")
            .withPassword("mysql");

    @BeforeAll
    public static void setUp() {
        mysql.start();
    }

    @Test
    void shouldAdd() throws SQLException {
        try (MockedStatic<DataSource> mocked = Mockito.mockStatic(DataSource.class)) {
            mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
            Teacher teacher = new Teacher();
            teacher.setName("Angelina");
            teacher.setLastName("Ivanova");
            teacher.setDiscipline(new Discipline(2L, "Physics"));
            int result = TeacherDao.add(teacher);
            assertTrue(result > 0);
            assertEquals(teacher.getName(), TeacherDao.findByKey(teacher.getName()).get(0).getName());
        }
    }

    @Test
    void shouldUpdate() throws SQLException {
        try (MockedStatic<DataSource> mocked = Mockito.mockStatic(DataSource.class)) {
            mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
            Teacher teacher = new Teacher();
            teacher.setId(3L);
            teacher.setName("Angelina");
            teacher.setLastName("Ivanova");
            teacher.setDiscipline(new Discipline(2L, "Physics"));
            int result = TeacherDao.update(teacher);
            assertTrue(result > 0);
            Teacher actual = TeacherDao.findById(3L);
            assertEquals(teacher.getName(), actual.getName());
            assertEquals(teacher.getLastName(), actual.getLastName());
            assertEquals(teacher.getId(), actual.getId());
            assertEquals(teacher.getDiscipline().getId(), actual.getDiscipline().getId());
        }
    }

    @Test
    void shouldDelete() throws SQLException {
        try (MockedStatic<DataSource> mocked = Mockito.mockStatic(DataSource.class)) {
            mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
            Long id = 1L;
            int result = TeacherDao.delete(id);
            assertTrue(result > 0);
            assertNull(TeacherDao.findById(id));
        }
    }

    @Test
    void shouldFindAll() throws SQLException {
        try (MockedStatic<DataSource> mocked = Mockito.mockStatic(DataSource.class)) {
            mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
            List<Teacher> teachers = TeacherDao.findAll();
            assertEquals(8, teachers.size());
        }
    }

    @Test
    void shouldFindById() throws SQLException {
        try (MockedStatic<DataSource> mocked = Mockito.mockStatic(DataSource.class)) {
            mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
            Long id = 1L;
            Teacher teacher = TeacherDao.findById(id);
            assertEquals(id, teacher.getId());
        }
    }

    @Test
    void shouldFindByDisciplineId() throws SQLException {
        try (MockedStatic<DataSource> mocked = Mockito.mockStatic(DataSource.class)) {
            mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
            Long disciplineId = 1L;
            List<Teacher> teachers = TeacherDao.findByDisciplineId(disciplineId);
            assertEquals(1, teachers.size());
            assertEquals(disciplineId, teachers.get(0).getDiscipline().getId());
        }
    }

    @Test
    void shouldFindByKey() throws SQLException {
        try (MockedStatic<DataSource> mocked = Mockito.mockStatic(DataSource.class)) {
            mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
            String key = "Anna";
            List<Teacher> teachers = TeacherDao.findByKey(key);
            assertEquals(1, teachers.size());
            assertTrue(teachers.get(0).getName().contains(key));
        }
    }
}