package org.example.homework2.service;

import org.example.homework2.database.dao.TeacherDao;
import org.example.homework2.dto.request.*;
import org.example.homework2.dto.response.MessageResponse;
import org.example.homework2.dto.response.TeacherResponse;
import org.example.homework2.exception.DeleteException;
import org.example.homework2.exception.NoAddException;
import org.example.homework2.exception.NoUpdateException;
import org.example.homework2.exception.NotFoundException;
import org.example.homework2.mapper.TeacherMapper;
import org.example.homework2.database.model.Teacher;

import java.sql.SQLException;
import java.util.List;

public class TeacherService {
    private static final String MS_FAILED_ADD = "Failed to add teacher", MS_SUCCESS_ADD = "Teacher adding is successful",
            MS_FAILED_UPDATE = "Failed to update teacher", MS_SUCCESS_UPDATE = "Teacher updating is successful",
            MS_FAILED_DELETE = "Failed to delete teacher", MS_SUCCESS_DELETE = "Teacher deleting is successful",
            MS_NOT_FOUND_LIST = "No teachers found", MS_NOT_FOUND = "Teacher not found";

    public MessageResponse addTeacher(TeacherAddRequest teacherAddRequest) throws NoAddException {
        try {
            Teacher teacher = TeacherMapper.INSTANCE.teacherAddRequestToTeacher(teacherAddRequest);
            if (TeacherDao.add(teacher) <= 0) throw new NoAddException(MS_FAILED_ADD);
            return new MessageResponse(MS_SUCCESS_ADD);
        } catch (SQLException e) {
            throw new NoAddException(MS_FAILED_ADD);
        }
    }

    public MessageResponse updateTeacher(TeacherUpdateRequest teacherUpdateRequest) throws NoUpdateException {
        try {
            Teacher teacher = TeacherMapper.INSTANCE.teacherUpdateRequestToTeacher(teacherUpdateRequest);
            if (TeacherDao.update(teacher) <= 0) throw new NoUpdateException(MS_FAILED_UPDATE);
            return new MessageResponse(MS_SUCCESS_UPDATE);
        } catch (SQLException e) {
            throw new NoUpdateException(MS_FAILED_UPDATE);
        }
    }

    public MessageResponse deleteTeacher(TeacherRequest teacherRequest) throws DeleteException {
        try {
            if (TeacherDao.delete(teacherRequest.getTeacherId()) <= 0) throw new DeleteException(MS_FAILED_DELETE);
            return new MessageResponse(MS_SUCCESS_DELETE);
        } catch (SQLException e) {
            throw new DeleteException(MS_SUCCESS_DELETE);
        }
    }

    public List<TeacherResponse> findAllTeachers() throws NotFoundException {
        try {
            List<Teacher> teachers = TeacherDao.findAll();
            return getTeacherResponses(teachers);
        } catch (SQLException e) {
            throw new NotFoundException(MS_NOT_FOUND_LIST);
        }
    }

    public List<TeacherResponse> findTeachersByDisciplineId(DisciplineRequest disciplineRequest) throws NotFoundException {
        try {
            List<Teacher> teachers = TeacherDao.findByDisciplineId(disciplineRequest.getDisciplineId());
            return getTeacherResponses(teachers);
        } catch (SQLException e) {
            throw new NotFoundException(MS_NOT_FOUND_LIST);
        }
    }

    public List<TeacherResponse> findTeachersByKey(SearchRequest searchRequest) throws NotFoundException {
        try {
            List<Teacher> teachers = TeacherDao.findByKey(searchRequest.getKey());
            return getTeacherResponses(teachers);
        } catch (SQLException e) {
            throw new NotFoundException(MS_NOT_FOUND_LIST);
        }
    }

    public TeacherResponse findTeacherById(TeacherRequest teacherRequest) throws NotFoundException {
        try {
            Teacher teacher = TeacherDao.findById(teacherRequest.getTeacherId());
            return getTeacherResponse(teacher);
        } catch (SQLException e) {
            throw new NotFoundException(MS_NOT_FOUND);
        }
    }

    private TeacherResponse getTeacherResponse(Teacher teacher) throws NotFoundException {
        if (teacher == null) throw new NotFoundException(MS_NOT_FOUND);
        return TeacherMapper.INSTANCE.toTeacherResponse(teacher);
    }

    private List<TeacherResponse> getTeacherResponses(List<Teacher> teachers) throws NotFoundException {
        if (teachers.isEmpty()) throw new NotFoundException(MS_NOT_FOUND_LIST);
        return TeacherMapper.INSTANCE.toTeacherResponses(teachers);
    }
}
