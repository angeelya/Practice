package org.example.homework2.database.dao;

import org.example.homework2.database.Conversion;
import org.example.homework2.database.Query;
import org.example.homework2.database.model.Student;

import java.sql.*;
import java.util.List;

public class StudentDao {
    private StudentDao() {
    }

    private static final String sqlFindAll = "select s.id, s.name, s.last_name, gr.id as group_id, gr.name " +
            "as group_name from student s join group_university " +
            "gr on s.group_id=gr.id";


    public static List<Student> findAll() throws SQLException {
        ResultSet rs = Query.getData(sqlFindAll);
        List<Student> students = Conversion.makeStudentList(rs);
        Query.closeConnection();
        return students;
    }


    public static List<Student> findByGroupId(Long groupId) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement(sqlFindAll + " where gr.id=?");
        statement.setLong(1, groupId);
        ResultSet rs = Query.getResultSet(statement);
        List<Student> students = Conversion.makeStudentList(rs);
        Query.closeConnection();
        return students;
    }

    public static Student findById(Long id) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement(sqlFindAll + " where s.id=?");
        statement.setLong(1, id);
        ResultSet rs = Query.getResultSet(statement);
        Student student = Conversion.getStudent(rs);
        Query.closeConnection();
        return student;
    }

    public static List<Student> findByKey(String key) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement(sqlFindAll + " where s.name like ? or s.last_name like ?");
        statement.setString(1, "%"+key + "%");
        statement.setString(2, "%"+key + "%");
        ResultSet rs = Query.getResultSet(statement);
        List<Student> students = Conversion.makeStudentList(rs);
        Query.closeConnection();
        return students;
    }

    public static Integer add(Student student) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement("insert into student(name,last_name,group_id) value(?,?,?)");
        statement.setString(1, student.getName());
        statement.setString(2, student.getLastName());
        statement.setLong(3, student.getGroup().getId());
        return Query.executeQuery(statement);
    }

    public static Integer update(Student student) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement("update student set name =?, last_name=?, group_id=? where id =?");
        statement.setString(1, student.getName());
        statement.setString(2, student.getLastName());
        statement.setLong(3, student.getGroup().getId());
        statement.setLong(4, student.getId());
        return Query.executeQuery(statement);
    }

    public static Integer delete(Long id) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement("delete from student where id=?");
        statement.setLong(1, id);
        return Query.executeQuery(statement);
    }
}
