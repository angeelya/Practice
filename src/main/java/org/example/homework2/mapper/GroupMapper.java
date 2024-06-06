package org.example.homework2.mapper;

import org.example.homework2.dto.request.GroupAddRequest;
import org.example.homework2.dto.request.GroupUpdateRequest;
import org.example.homework2.dto.response.GroupResponse;
import org.example.homework2.dto.response.StudentResponse;
import org.example.homework2.database.model.Group;
import org.example.homework2.database.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GroupMapper {
    GroupMapper INSTANCE= Mappers.getMapper(GroupMapper.class);

    @Mapping(target = "groupName", source = "name")
    Group groupAddRequestToGroup(GroupAddRequest groupAddRequest);
    @Mapping(target = "groupName", source = "name")
    Group groupUpdateRequestToGroup(GroupUpdateRequest groupUpdateRequest);

    @Mapping(target = "name", source = "groupName")
    @Mapping(target = "studentResponses", source = "students",qualifiedByName = "studentResponses")
    GroupResponse toGroupResponse(Group group);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "groupName", source = "group.groupName")
    StudentResponse toStudentResponse(Student student);
    @Named("studentResponses")
    default List<StudentResponse> studentResponses(List<Student> students) {
        return students != null ? students.stream().map(this::toStudentResponse).toList() : null;
    }
    List<GroupResponse> toGroupResponses(List<Group> groups);

}
