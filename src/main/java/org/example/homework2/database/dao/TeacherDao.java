package org.example.homework2.database.dao;

import org.example.homework2.database.Conversion;
import org.example.homework2.database.Query;
import org.example.homework2.database.model.Teacher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TeacherDao {
    private TeacherDao() {
    }

    private static final String sqlFindAll = "select d.id as discipline_id, d.name as discipline_name, " +
            "t.id as teacher_id, t.name as teacher_name, t.last_name as teacher_last_name, " +
            "gu.id as group_id, gu.name as group_name from teacher t " +
            "join discipline d ON d.id = t.discipline_id " +
            "left join teaching tg on t.id= tg.teacher_id " +
            "left join group_university gu on gu.id=tg.group_id";

    public static Integer add(Teacher teacher) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement("insert into teacher(name, last_name, " +
                "discipline_id) value(?,?,?)");
        statement.setString(1, teacher.getName());
        statement.setString(2, teacher.getLastName());
        statement.setLong(3, teacher.getDiscipline().getId());
        return Query.executeQuery(statement);
    }

    public static Integer update(Teacher teacher) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement("update teacher set name=?, last_name=?, discipline_id=? where id=?");
        statement.setString(1, teacher.getName());
        statement.setString(2, teacher.getLastName());
        statement.setLong(3, teacher.getDiscipline().getId());
        statement.setLong(4, teacher.getId());
        return Query.executeQuery(statement);
    }

    public static Integer delete(Long id) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement("delete from teacher where id=?");
        statement.setLong(1, id);
        return Query.executeQuery(statement);
    }

    public static List<Teacher> findAll() throws SQLException {
        ResultSet rs = Query.getData(sqlFindAll);
        List<Teacher> teachers = Conversion.makeTeacherList(rs);
        Query.closeConnection();
        return teachers;
    }


    public static Teacher findById(Long id) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement(sqlFindAll + " where t.id=?");
        statement.setLong(1,id);
        ResultSet rs = Query.getResultSet(statement);
        Teacher teacher = Conversion.getTeacher(rs);
        Query.closeConnection();
        return teacher;
    }

    public static List<Teacher> findByDisciplineId(Long disciplineId) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement(sqlFindAll + " where d.id=?");
        statement.setLong(1,disciplineId);
        ResultSet rs = Query.getResultSet(statement);
        List<Teacher> teachers = Conversion.makeTeacherList(rs);
        Query.closeConnection();
        return teachers;
    }

    public static List<Teacher> findByKey(String key) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement(sqlFindAll + " where t.name like ? or t.last_name like ? or d.name like ?");
        statement.setString(1, "%"+key + "%");
        statement.setString(2, "%"+key + "%");
        statement.setString(3, "%"+key + "%");
        ResultSet rs = Query.getResultSet(statement);
        List<Teacher> teachers = Conversion.makeTeacherList(rs);
        Query.closeConnection();
        return teachers;
    }
}
