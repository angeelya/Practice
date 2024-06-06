package org.example.homework2.service;

import org.example.homework2.database.dao.StudentDao;
import org.example.homework2.dto.request.*;
import org.example.homework2.dto.response.MessageResponse;
import org.example.homework2.dto.response.StudentResponse;
import org.example.homework2.exception.DeleteException;
import org.example.homework2.exception.NoAddException;
import org.example.homework2.exception.NoUpdateException;
import org.example.homework2.exception.NotFoundException;
import org.example.homework2.mapper.StudentMapper;
import org.example.homework2.database.model.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private static final String
            MS_NOT_FOUND_LIST = "No students found", MS_NOT_FOUND = "Student not found",
            MS_FAILED_ADD = "Failed to add student", MS_FAILED_DELETE = "Failed to delete student",
            MS_FAILED_UPDATE = "Failed to update student", MS_SUCCESS_ADD = "Student adding is successful",
            MS_SUCCESS_UPDATE = "Student updating is successful", MS_SUCCESS_DELETE = "Student deleting is successful";

    public List<StudentResponse> findAllStudents() throws NotFoundException {
        try {
            List<Student> students = StudentDao.findAll();
            return getStudentResponses(students);
        } catch (SQLException e) {
            throw new NotFoundException(MS_NOT_FOUND_LIST);
        }
    }

    public List<StudentResponse> findStudentByGroup(GroupRequest groupRequest) throws NotFoundException {
        try {
            List<Student> students = StudentDao.findByGroupId(groupRequest.getGroupId());
            return getStudentResponses(students);
        } catch (SQLException e) {
            throw new NotFoundException(MS_NOT_FOUND_LIST);
        }
    }

    public StudentResponse findStudentById(StudentRequest studentRequest) throws NotFoundException {
        try {
            Student student = StudentDao.findById(studentRequest.getStudentId());
            return getStudentResponse(student);
        } catch (SQLException e) {
            throw new NotFoundException(MS_NOT_FOUND);
        }
    }

    public List<StudentResponse> findStudentsByKey(SearchRequest searchRequest) throws NotFoundException {
        try {
            List<Student> students = StudentDao.findByKey(searchRequest.getKey());
            return getStudentResponses(students);
        } catch (SQLException e) {
            throw new NotFoundException(MS_NOT_FOUND_LIST);
        }
    }

    public MessageResponse addStudent(StudentAddRequest studentAddRequest) throws NoAddException {
        try {
            Student student = StudentMapper.INSTANCE.studentAddRequestToStudent(studentAddRequest);
            if (StudentDao.add(student) <= 0) throw new NoAddException(MS_FAILED_ADD);
            return new MessageResponse(MS_SUCCESS_ADD);
        } catch (SQLException e) {
            throw new NoAddException(MS_FAILED_ADD);
        }
    }

    public MessageResponse updateStudent(StudentUpdateRequest studentUpdateRequest) throws NoUpdateException {
        try {
            Student student = StudentMapper.INSTANCE.studentUpdateRequestToStudent(studentUpdateRequest);
            if (StudentDao.update(student) <= 0) throw new NoUpdateException(MS_FAILED_UPDATE);
            return new MessageResponse(MS_SUCCESS_UPDATE);
        } catch (SQLException e) {
            throw new NoUpdateException(MS_FAILED_UPDATE);
        }
    }

    public MessageResponse deleteStudent(StudentRequest studentRequest) throws DeleteException {
        try {
            if (StudentDao.delete(studentRequest.getStudentId()) <= 0) throw new DeleteException(MS_FAILED_DELETE);
            return new MessageResponse(MS_SUCCESS_DELETE);
        } catch (SQLException e) {
            throw new DeleteException(MS_FAILED_DELETE);
        }
    }

    private StudentResponse getStudentResponse(Student student) throws NotFoundException {
        if (student == null) throw new NotFoundException(MS_NOT_FOUND);
        return StudentMapper.INSTANCE.studentToStudentResponse(student);
    }

    private List<StudentResponse> getStudentResponses(List<Student> students) throws NotFoundException {
        if (students.isEmpty()) throw new NotFoundException(MS_NOT_FOUND_LIST);
        return StudentMapper.INSTANCE.studentsToStudentResponses(students);
    }
}
