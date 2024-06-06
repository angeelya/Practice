package org.example.homework2.database;

import org.example.homework2.database.model.Discipline;
import org.example.homework2.database.model.Group;
import org.example.homework2.database.model.Student;
import org.example.homework2.database.model.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Conversion {
    private Conversion() {
    }

    public static List<Discipline> makeDisciplineList(ResultSet rs) throws SQLException {
        List<Discipline> disciplines = new ArrayList<>();
        if (rs == null) return disciplines;
        Discipline currentDiscipline = null;
        List<Teacher> teachers = null;
        while (rs.next()) {
            String disciplineName = rs.getString("discipline_name");
            String teacherName = rs.getString("teacher_name");
            if (currentDiscipline == null || !disciplineName.equals(currentDiscipline.getDisciplineName())) {
                currentDiscipline = makeDiscipline(rs);
                teachers = new ArrayList<>();
                disciplines.add(currentDiscipline);
            }
            if (teacherName != null) {
                teachers.add(makeTeacher(rs));
            }
            currentDiscipline.setTeachers(teachers);
        }

        return disciplines;
    }

    public static List<Group> makeGroupList(ResultSet rs) throws SQLException {
        List<Group> groups = new ArrayList<>();
        if (rs == null) return groups;
        Group currentGroup = null;
        List<Student> students = null;
        List<Teacher> teachers = null;
        while (rs.next()) {
            String groupName = rs.getString("group_name");
            String teacherName = rs.getString("teacher_name");
            String studentName = rs.getString("name");
            if (currentGroup == null || !groupName.equals(currentGroup.getGroupName())) {
                currentGroup = makeGroup(rs);
                students = new ArrayList<>();
                teachers = new ArrayList<>();
                groups.add(currentGroup);
            }
            if (teacherName != null) {
                teachers.add(makeTeacher(rs));
            }
            if (studentName != null) {
                students.add(makeStudent(rs));
            }
            currentGroup.setTeachers(teachers);
            currentGroup.setStudents(students);
        }
        return groups;
    }

    public static List<Student> makeStudentList(ResultSet rs) throws SQLException {
        List<Student> students = new ArrayList<>();
        if (rs == null) return students;
        Student student;
        while (rs.next()) {
            student = makeStudent(rs);
            students.add(student);
        }
        return students;
    }

    public static List<Teacher> makeTeacherList(ResultSet rs) throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        if (rs == null) return teachers;
        Teacher currentTeacher = null;
        while (rs.next()) {
            String groupName = rs.getString("group_name");
            String teacherName =rs.getString("teacher_name");
            if (currentTeacher == null || !teacherName.equals(currentTeacher.getName())) {
                currentTeacher = makeTeacher(rs);
                currentTeacher.setDiscipline(makeDiscipline(rs));
                currentTeacher.setGroups(new ArrayList<>());
                teachers.add(currentTeacher);
            }
            if (groupName != null) {
                currentTeacher.getGroups().add(makeGroup(rs));
            }
        }
        return teachers;
    }

    public static Group getGroup(ResultSet rs) throws SQLException {
        List<Group> groups = makeGroupList(rs);
        if (groups.isEmpty()) return null;
        return groups.get(0);
    }

    public static Discipline getDiscipline(ResultSet rs) throws SQLException {
        List<Discipline> disciplines = makeDisciplineList(rs);
        if (disciplines.isEmpty()) return null;
        return disciplines.get(0);
    }

    public static Teacher getTeacher(ResultSet rs) throws SQLException {
        List<Teacher> teachers = makeTeacherList(rs);
        if (teachers.isEmpty()) return null;
        return teachers.get(0);
    }

    public static Student getStudent(ResultSet rs) throws SQLException {
        List<Student> students = makeStudentList(rs);
        if (students.isEmpty()) return null;
        return students.get(0);
    }


    private static Student makeStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setId(rs.getLong("id"));
        student.setName(rs.getString("name"));
        student.setLastName(rs.getString("last_name"));
        student.setGroup(new Group(rs.getLong("group_id"), rs.getString("group_name")));
        return student;
    }

    private static Discipline makeDiscipline(ResultSet rs) throws SQLException {
        Discipline discipline = new Discipline();
        discipline.setId(rs.getLong("discipline_id"));
        discipline.setDisciplineName(rs.getString("discipline_name"));
        return discipline;
    }

    private static Group makeGroup(ResultSet rs) throws SQLException {
        Group group = new Group();
        group.setId(rs.getLong("group_id"));
        group.setGroupName(rs.getString("group_name"));
        return group;
    }

    public static Teacher makeTeacher(ResultSet rs) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(rs.getLong("teacher_id"));
        teacher.setName(rs.getString("teacher_name"));
        teacher.setLastName(rs.getString("teacher_last_name"));
        return teacher;
    }
}
