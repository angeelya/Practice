package org.example.homework2.database.dao;

import org.example.homework2.database.Conversion;
import org.example.homework2.database.model.Group;
import org.example.homework2.database.Query;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GroupDao {
    private GroupDao() {
    }

    private static final String sqlFindAll = "select  gr.id as group_id, gr.name as group_name, st.id as id," +
            " st.name as name, st.last_name as last_name, t.id as teacher_id, " +
            "t.name as teacher_name, t.last_name as teacher_last_name, d.name as discipline " +
            "from group_university gr left join student st on st.group_id=gr.id " +
            "left join teaching tg on tg.group_id=gr.id left join teacher t on tg.teacher_id=t.id " +
            "left join discipline d on d.id=t.discipline_id";

    public static Integer add(Group group) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement("insert into group_university (name) value(?)");
        statement.setString(1, group.getGroupName());
        return Query.executeQuery(statement);
    }

    public static Integer delete(Long id) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement("delete from group_university where id=?");
        statement.setLong(1, id);
        return Query.executeQuery(statement);
    }

    public static Integer update(Group group) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement("update group_university set name=? where id=?");
        statement.setString(1, group.getGroupName());
        statement.setLong(2, group.getId());
        return Query.executeQuery(statement);
    }

    public static List<Group> findAll() throws SQLException {
        ResultSet rs = Query.getData(sqlFindAll);
        List<Group> groups = Conversion.makeGroupList(rs);
        Query.closeConnection();
        return groups;
    }

    public static List<Group> findByKey(String key) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement(sqlFindAll + " where gr.name like ?");
        statement.setString(1, "%"+key + "%");
        ResultSet rs = Query.getResultSet(statement);
        List<Group> groups = Conversion.makeGroupList(rs);
        Query.closeConnection();
        return groups;
    }

    public static Group findById(Long id) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement(sqlFindAll + " where gr.id=?");
        statement.setLong(1, id);
        ResultSet rs = Query.getResultSet(statement);
        Group group = Conversion.getGroup(rs);
        Query.closeConnection();
        return group;
    }
    public static Integer addTeaching(Long groupId,Long teacherId) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement("insert into teaching (group_id,teacher_id) value(?,?)");
        statement.setLong(1,groupId);
        statement.setLong(2,teacherId);
        return Query.executeQuery(statement);
    }

    public static Group findByName(String name) throws SQLException {
        PreparedStatement statement = Query.getPreparedStatement(sqlFindAll + " where gr.name=?");
        statement.setString(1, name);
        ResultSet rs = Query.getResultSet(statement);
        Group group = Conversion.getGroup(rs);
        Query.closeConnection();
        return group;
    }
}
