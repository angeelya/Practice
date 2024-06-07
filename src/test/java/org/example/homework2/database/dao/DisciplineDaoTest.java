package org.example.homework2.database.dao;

import org.example.homework2.database.DataSource;
import org.example.homework2.database.model.Discipline;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testcontainers.containers.MySQLContainer;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DisciplineDaoTest {
   private static MockedStatic<DataSource> mocked;

    public static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("university")
            .withUsername("root")
            .withInitScript("init.sql")
            .withPassword("mysql");

    @BeforeAll
    public  static void setUp() {
        mysql.start();
        mocked = Mockito.mockStatic(DataSource.class);
    }

    @Test
    void shouldAdd() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        Discipline discipline = new Discipline();
        discipline.setDisciplineName("German");
        int result = DisciplineDao.add(discipline);
        assertTrue(result > 0);
        assertEquals(discipline.getDisciplineName(), DisciplineDao.findByName("German").getDisciplineName());
    }
    @Test
    void shouldAddThrowsException() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        Discipline discipline = new Discipline();
        discipline.setDisciplineName("French");
        DisciplineDao.add(discipline);
        assertThrows(SQLIntegrityConstraintViolationException.class,()->DisciplineDao.add(discipline));
    }

    @Test
    void shouldUpdate() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        Discipline discipline = new Discipline();
        discipline.setId(2L);
        discipline.setDisciplineName("Latin");
        int result = DisciplineDao.update(discipline);
        assertEquals(discipline.getId(),DisciplineDao.findByName("Latin").getId());
        assertTrue(result>0);
    }
    @Test
    void shouldUpdateThrowsException() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        Discipline discipline = new Discipline();
        discipline.setId(2L);
        discipline.setDisciplineName("Latin");
        DisciplineDao.update(discipline);
        assertThrows(SQLIntegrityConstraintViolationException.class,()->DisciplineDao.add(discipline));
    }
    @Test
    void shouldFindAll() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        List<Discipline> disciplines=DisciplineDao.findAll();
        assertEquals(8,disciplines.size());
    }

    @Test
    void findByName() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        String name ="History";
        Discipline discipline = DisciplineDao.findByName(name);
        assertEquals(name,discipline.getDisciplineName());
    }

    @Test
    void findByKey() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        String key ="Language";
        List<Discipline> disciplines = DisciplineDao.findByKey(key);
        assertEquals(2,disciplines.size());
        assertTrue(disciplines.get(0).getDisciplineName().contains(key));
    }
}