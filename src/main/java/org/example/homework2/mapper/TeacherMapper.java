package org.example.homework2.mapper;

import org.example.homework2.dto.request.TeacherAddRequest;
import org.example.homework2.dto.request.TeacherUpdateRequest;
import org.example.homework2.dto.response.GroupForTeacherResponse;
import org.example.homework2.dto.response.TeacherResponse;
import org.example.homework2.database.model.Group;
import org.example.homework2.database.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TeacherMapper {
    TeacherMapper INSTANCE= Mappers.getMapper(TeacherMapper.class);
    @Mapping(target ="discipline.id" , source ="disciplineId")
    Teacher teacherAddRequestToTeacher(TeacherAddRequest teacherAddRequest);
    @Mapping(target = "discipline.id",source ="disciplineId")
    Teacher teacherUpdateRequestToTeacher(TeacherUpdateRequest teacherUpdateRequest);

    @Mapping(target = "disciplineName", source = "discipline.disciplineName")
    @Mapping(target = "groupForTeacherResponses", source = "groups",qualifiedByName = "groupToGroupForTeacherResponse")
    TeacherResponse toTeacherResponse(Teacher teacher);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "groupName")
    GroupForTeacherResponse toGroupForTeacherResponse(Group group);
    @Named("groupToGroupForTeacherResponse")
    default List<org.example.homework2.dto.response.GroupForTeacherResponse> groupToGroupForTeacherResponse(List<Group> groups) {
        return groups != null ? groups.stream().map(this::toGroupForTeacherResponse).toList() : null;
    }
    List<TeacherResponse> toTeacherResponses(List<Teacher> teachers);
}
