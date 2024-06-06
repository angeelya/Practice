package org.example.homework2.database.dao;

import org.example.homework2.database.Conversion;
import org.example.homework2.database.model.Discipline;
import org.example.homework2.database.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DisciplineDao {
    private DisciplineDao() {
    }

    private static final String sqlFindAll = "select d.id as discipline_id, d.name AS discipline_name, " +
            "t.id AS teacher_id, t.name AS teacher_name, t.last_name AS teacher_last_name " +
            "from discipline d left join teacher t on d.id = t.discipline_id";

    public static Integer add(Discipline discipline) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement("insert into discipline (name) value(?)");
        statement.setString(1, discipline.getDisciplineName());
        return Query.executeQuery(statement);
    }

    public static Integer update(Discipline discipline) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement("update discipline set name=? where id=?");
        statement.setString(1, discipline.getDisciplineName());
        statement.setLong(2, discipline.getId());
        return Query.executeQuery(statement);
    }

    public static List<Discipline> findAll() throws SQLException {
        ResultSet rs = Query.getData(sqlFindAll);
        List<Discipline> disciplines = Conversion.makeDisciplineList(rs);
        Query.closeConnection();
        return disciplines;
    }

    public static Discipline findByName(String name) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement(sqlFindAll + " where d.name=?");
        statement.setString(1, name);
        ResultSet rs = Query.getResultSet(statement);
        Discipline discipline = Conversion.getDiscipline(rs);
        Query.closeConnection();
        return discipline;
    }

    public static List<Discipline> findByKey(String key) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement(sqlFindAll + " where d.name like ?");
        statement.setString(1, "%"+key + "%");
        ResultSet rs = Query.getResultSet(statement);
        List<Discipline> disciplines = Conversion.makeDisciplineList(rs);
        Query.closeConnection();
        return disciplines;
    }
}
