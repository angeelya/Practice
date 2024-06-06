package org.example.homework2.service;

import org.example.homework2.database.dao.DisciplineDao;
import org.example.homework2.database.model.Discipline;
import org.example.homework2.dto.request.DisciplineAddRequest;
import org.example.homework2.dto.request.DisciplineUpdateRequest;
import org.example.homework2.dto.request.SearchRequest;
import org.example.homework2.dto.response.DisciplineResponse;
import org.example.homework2.dto.response.MessageResponse;
import org.example.homework2.exception.NoAddException;
import org.example.homework2.exception.NoUpdateException;
import org.example.homework2.exception.NotFoundException;
import org.example.homework2.mapper.DisciplineMapper;

import java.sql.SQLException;
import java.util.List;

public class DisciplineService {
    private static final  String MS_FAILED_ADD = "Failed to add discipline", MS_SUCCESS_ADD = "Discipline adding is successful",
            MS_FAILED_UPDATE = "Failed to update discipline", MS_SUCCESS_UPDATE = "Discipline update is successful",
            MS_NOT_FOUND_LIST = "No discipline found";

    public MessageResponse addDiscipline(DisciplineAddRequest disciplineAddRequest) throws NoAddException {
        try {
            Discipline discipline = DisciplineMapper.INSTANCE.disciplineAddRequestToDiscipline(disciplineAddRequest);
            if (DisciplineDao.add(discipline) <= 0) throw new NoAddException(MS_FAILED_ADD);
            return new MessageResponse(MS_SUCCESS_ADD);
        } catch (SQLException e) {
            throw new NoAddException(MS_FAILED_ADD);
        }
    }

    public MessageResponse updateDiscipline(DisciplineUpdateRequest disciplineUpdateRequest) throws NoUpdateException {
        try {
            Discipline discipline = DisciplineMapper.INSTANCE.disciplineUpdateRequestToDiscipline(disciplineUpdateRequest);
            if (DisciplineDao.update(discipline) <= 0) throw new NoUpdateException(MS_FAILED_ADD);
            return new MessageResponse(MS_SUCCESS_UPDATE);
        } catch (SQLException e) {
            throw new NoUpdateException(MS_FAILED_UPDATE);
        }
    }

    public List<DisciplineResponse> findDisciplineAll() throws NotFoundException {
        try {
            List<Discipline> disciplines = DisciplineDao.findAll();
            return getDisciplineResponses(disciplines);
        } catch (SQLException e) {
            throw new NotFoundException(MS_NOT_FOUND_LIST);
        }
    }
    public List<DisciplineResponse> findDisciplineKey(SearchRequest searchRequest) throws NotFoundException {
        try {
            List<Discipline> disciplines = DisciplineDao.findByKey(searchRequest.getKey());
            return getDisciplineResponses(disciplines);
        } catch (SQLException e) {
            throw new NotFoundException(MS_NOT_FOUND_LIST);
        }
    }

    private List<DisciplineResponse> getDisciplineResponses(List<Discipline> disciplines) throws NotFoundException {
        if (disciplines.isEmpty()) throw new NotFoundException(MS_NOT_FOUND_LIST);
        return DisciplineMapper.INSTANCE.toDisciplineResponses(disciplines);
    }
}
