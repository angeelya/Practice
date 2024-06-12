package org.example.homework2.database.dao;

import org.example.homework2.database.DataSource;
import org.example.homework2.database.model.Group;
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

class GroupDaoTest {
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
        Group group = new Group();
        group.setGroupName("GH-45");
        int result =GroupDao.add(group);
        assertTrue(result>0);
        assertEquals(group.getGroupName(),GroupDao.findByName("GH-45").getGroupName());
    }
    @Test
    void shouldAddThrowsException() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        Group group = new Group();
        group.setGroupName("GH-48");
        int result =GroupDao.add(group);
        assertTrue(result>0);
        assertThrows(SQLIntegrityConstraintViolationException.class,()->GroupDao.add(group));
    }


    @Test
    void shouldDelete() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        Long  id =1L;
        int result = GroupDao.delete(id);
        assertTrue(result>0);
        assertNull(GroupDao.findById(id));
    }

    @Test
    void shouldUpdate() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        Group group = new Group();
        group.setId(2L);
        group.setGroupName("M-14");
        int result = GroupDao.update(group);
        assertTrue(result>0);
        assertEquals(group.getGroupName(),GroupDao.findByName("M-14").getGroupName());
    }

    @Test
    void shouldFindAll() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        List<Group> groups=GroupDao.findAll();
        assertEquals(3,groups.size());
    }

    @Test
    void shouldFindByKey() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        String key="-";
        List<Group> groups=GroupDao.findByKey(key);
        assertEquals(3,groups.size());
    }

    @Test
    void shouldFindById() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        Long id =1L;
        Group group = GroupDao.findById(1L);
        assertEquals(id,group.getId());
    }

    @Test
    void addTeaching() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        Long groupId=1L,teachingId=2L;
        int result = GroupDao.addTeaching(groupId,teachingId);
        assertTrue(result>0);
    }
    @Test
    void addTeachingTrowsException() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        Long groupId=100L,teachingId=200L;
        assertThrows(SQLIntegrityConstraintViolationException.class,()->GroupDao.addTeaching(groupId,teachingId));

    }

    @Test
    void findByName() throws SQLException {
        mocked.when(DataSource::getConnection).thenReturn(mysql.createConnection(""));
        String name ="M-12";
        Group group = GroupDao.findByName(name);
        assertEquals(name,group.getGroupName());
    }
}