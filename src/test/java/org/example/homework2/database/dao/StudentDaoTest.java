package org.example.homework2.database.dao;

import org.example.homework2.database.DataSource;
import org.example.homework2.database.model.Group;
import org.example.homework2.database.model.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testcontainers.containers.MySQLContainer;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDaoTest {
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
    void shouldFindAll() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        List<Student> students  = StudentDao.findAll();
        assertEquals(5,students.size());
    }

    @Test
    void shouldFindByGroupId() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        Long groupId=1L;
        List<Student> students  = StudentDao.findByGroupId(groupId);
        assertEquals(2,students.size());
        assertEquals(groupId,students.get(0).getGroup().getId());
    }

    @Test
    void shouldFindById() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        Long id=1L;
        Student student  = StudentDao.findById(id);
        assertEquals(id,student.getId());
    }

    @Test
    void shouldFindByKey() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        String key="Ivan";
        List<Student> students  = StudentDao.findByKey(key);
        assertEquals(2,students.size());
        assertTrue(students.get(0).getName().contains(key));
    }

    @Test
    void shouldAdd() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        Student student= new Student();
        student.setName("Angelina");
        student.setLastName("Naidenova");
        student.setGroup(new Group(1L,"M-12"));
        int result =StudentDao.add(student);
        assertTrue(result>0);
        assertEquals(student.getName(), StudentDao.findByKey(student.getName()).get(0).getName());
    }

    @Test
    void shouldUpdate() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        Student student= new Student();
        student.setId(1L);
        student.setName("Angelina");
        student.setLastName("Naidenova");
        student.setGroup(new Group(1L,"M-12"));
        int result =StudentDao.update(student);
        assertTrue(result>0);
        Student actual =StudentDao.findById(1L);
        assertEquals(student.getName(), actual.getName());
        assertEquals(student.getLastName(), actual.getLastName());
        assertEquals(student.getGroup().getGroupName(),actual.getGroup().getGroupName());
    }

    @Test
    void shouldDelete() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        Long  id =1L;
        int result = StudentDao.delete(id);
        assertTrue(result>0);
        assertNull(StudentDao.findById(id));
    }
}