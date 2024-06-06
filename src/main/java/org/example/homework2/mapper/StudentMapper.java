package org.example.homework2.mapper;

import org.example.homework2.dto.request.StudentAddRequest;
import org.example.homework2.dto.response.StudentResponse;
import org.example.homework2.dto.request.StudentUpdateRequest;
import org.example.homework2.database.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE= Mappers.getMapper(StudentMapper.class);
    @Mapping(target = "groupName", source = "group.groupName")
    StudentResponse studentToStudentResponse(Student student);
    List<StudentResponse> studentsToStudentResponses(List<Student> students);
    @Mapping(target = "group.id", source = "groupId")
    Student studentAddRequestToStudent(StudentAddRequest studentAddRequest);
    @Mapping(target = "group.id", source = "groupId")
    Student studentUpdateRequestToStudent(StudentUpdateRequest studentUpdateRequest);
}
