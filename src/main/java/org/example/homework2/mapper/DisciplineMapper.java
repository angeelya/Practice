package org.example.homework2.mapper;

import org.example.homework2.database.model.Discipline;
import org.example.homework2.database.model.Teacher;
import org.example.homework2.dto.request.DisciplineAddRequest;
import org.example.homework2.dto.request.DisciplineUpdateRequest;
import org.example.homework2.dto.response.DisciplineResponse;
import org.example.homework2.dto.response.TeacherForDisciplineResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DisciplineMapper {
    DisciplineMapper INSTANCE = Mappers.getMapper(DisciplineMapper.class);
    @Mapping(target = "disciplineName", source = "discipline")
    Discipline disciplineAddRequestToDiscipline(DisciplineAddRequest disciplineAddRequest);
    @Mapping(target = "disciplineName", source = "disciplineName")
    Discipline disciplineUpdateRequestToDiscipline(DisciplineUpdateRequest disciplineUpdateRequest);
    @Mapping(target = "discipline", source = "disciplineName")
    @Mapping(target = "teacherDisciplineResponses", source = "teachers",qualifiedByName = "teacherForDisciplineResponses")
    DisciplineResponse toDisciplineResponse(Discipline discipline);
    TeacherForDisciplineResponse toTeacherResponse(Teacher teacher);
    @Named("teacherForDisciplineResponses")
    default List<TeacherForDisciplineResponse> teacherForDisciplineResponses(List<Teacher> teachers) {
        return teachers != null ? teachers.stream().map(this::toTeacherResponse).toList() : null;
    }
    List<DisciplineResponse> toDisciplineResponses(List<Discipline> disciplines);
}
