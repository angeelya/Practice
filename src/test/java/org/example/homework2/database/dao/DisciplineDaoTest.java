package org.example.homework2.database.dao;

import org.example.homework2.database.DataSource;
import org.example.homework2.database.model.Discipline;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.MySQLContainer;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DisciplineDaoTest {

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
            Discipline discipline = new Discipline();
            discipline.setDisciplineName("German");
            int result = DisciplineDao.add(discipline);
            assertTrue(result > 0);
            assertEquals(discipline.getDisciplineName(), DisciplineDao.findByName("German").getDisciplineName());
        }
    }

    @Test
    void shouldAddThrowsException() throws SQLException {
        try (MockedStatic<DataSource> mocked = Mockito.mockStatic(DataSource.class)) {
            mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
            Discipline discipline = new Discipline();
            discipline.setDisciplineName("French");
            DisciplineDao.add(discipline);
            assertThrows(SQLIntegrityConstraintViolationException.class, () -> DisciplineDao.add(discipline));
        }
    }

    @Test
    void shouldUpdate() throws SQLException {
        try (MockedStatic<DataSource> mocked = Mockito.mockStatic(DataSource.class)) {
            mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
            Discipline discipline = new Discipline();
            discipline.setId(2L);
            discipline.setDisciplineName("Latin");
            int result = DisciplineDao.update(discipline);
            assertEquals(discipline.getId(), DisciplineDao.findByName("Latin").getId());
            assertTrue(result > 0);
        }
    }

    @Test
    void shouldUpdateThrowsException() throws SQLException {
        try (MockedStatic<DataSource> mocked = Mockito.mockStatic(DataSource.class)) {
            mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
            Discipline discipline = new Discipline();
            discipline.setId(2L);
            discipline.setDisciplineName("Latin");
            DisciplineDao.update(discipline);
            assertThrows(SQLIntegrityConstraintViolationException.class, () -> DisciplineDao.add(discipline));
        }
    }

    @Test
    void shouldFindAll() throws SQLException {
        try (MockedStatic<DataSource> mocked = Mockito.mockStatic(DataSource.class)) {
            mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
            List<Discipline> disciplines = DisciplineDao.findAll();
            assertEquals(10, disciplines.size());
        }
    }

    @Test
    void findByName() throws SQLException {
        try (MockedStatic<DataSource> mocked = Mockito.mockStatic(DataSource.class)) {
            mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
            String name = "History";
            Discipline discipline = DisciplineDao.findByName(name);
            assertEquals(name, discipline.getDisciplineName());
        }
    }

    @Test
    void findByKey() throws SQLException {
        try (MockedStatic<DataSource> mocked = Mockito.mockStatic(DataSource.class)) {
            mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
            String key = "Language";
            List<Discipline> disciplines = DisciplineDao.findByKey(key);
            assertEquals(2, disciplines.size());
            assertTrue(disciplines.get(0).getDisciplineName().contains(key));
        }
    }
}