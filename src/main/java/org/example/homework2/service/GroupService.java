package org.example.homework2.service;

import org.example.homework2.database.dao.GroupDao;
import org.example.homework2.dto.request.*;
import org.example.homework2.dto.response.GroupResponse;
import org.example.homework2.dto.response.MessageResponse;
import org.example.homework2.exception.DeleteException;
import org.example.homework2.exception.NoAddException;
import org.example.homework2.exception.NoUpdateException;
import org.example.homework2.exception.NotFoundException;
import org.example.homework2.mapper.GroupMapper;
import org.example.homework2.database.model.Group;

import java.sql.SQLException;
import java.util.List;

public class GroupService {
    private static final String MS_FAILED_ADD = "Failed to add group", MS_SUCCESS_ADD = "Group adding is successful",
            MS_FAILED_UPDATE = "Failed to update group", MS_SUCCESS_UPDATE = "Group updating is successful",
            MS_FAILED_DELETE = "Failed to delete group", MS_SUCCESS_DELETE = "Group deleting is successful",
            MS_NOT_FOUND_LIST = "No groups found", MS_NOT_FOUND = "Group not found",
            MS_FAILED_ADD_TEACHING = "Failed to add teaching", MS_SUCCESS_ADD_TEACHING = "Teaching adding is successful";

    public MessageResponse addGroup(GroupAddRequest groupAddRequest) throws NoAddException {
        try {
            Group group = GroupMapper.INSTANCE.groupAddRequestToGroup(groupAddRequest);
            if (GroupDao.add(group) <= 0) throw new NoAddException(MS_FAILED_ADD);
            return new MessageResponse(MS_SUCCESS_ADD);
        } catch (SQLException e) {
            throw new NoAddException(MS_FAILED_ADD);
        }
    }

    public MessageResponse updateGroup(GroupUpdateRequest groupUpdateRequest) throws NoUpdateException {
        try {
            Group group = GroupMapper.INSTANCE.groupUpdateRequestToGroup(groupUpdateRequest);
            if (GroupDao.update(group) <= 0) throw new NoUpdateException(MS_FAILED_UPDATE);
            return new MessageResponse(MS_SUCCESS_UPDATE);
        } catch (SQLException e) {
            throw new NoUpdateException(MS_SUCCESS_UPDATE);
        }
    }

    public MessageResponse addTeaching(TeachingRequest teachingRequest) throws NoAddException {
        try {
            if (GroupDao.addTeaching(teachingRequest.getGroupId(), teachingRequest.getTeacherId()) <= 0)
                throw new NoAddException(MS_FAILED_ADD_TEACHING);
            return new MessageResponse(MS_SUCCESS_ADD_TEACHING);
        } catch (SQLException e) {
            throw new NoAddException(MS_FAILED_ADD_TEACHING);
        }
    }

    public MessageResponse deleteGroup(GroupRequest groupRequest) throws DeleteException {
        try {
            if (GroupDao.delete(groupRequest.getGroupId()) <= 0) throw new DeleteException(MS_FAILED_DELETE);
            return new MessageResponse(MS_SUCCESS_DELETE);
        } catch (SQLException e) {
            throw new DeleteException(MS_FAILED_DELETE);
        }
    }

    public List<GroupResponse> findGroupsAll() throws NotFoundException {
        try {
            List<Group> groups = GroupDao.findAll();
            return getGroupResponses(groups);
        } catch (SQLException e) {
            throw new NotFoundException(MS_NOT_FOUND_LIST);
        }
    }

    public List<GroupResponse> findGroupsByKey(SearchRequest searchRequest) throws NotFoundException {
        try {
            List<Group> groups = GroupDao.findByKey(searchRequest.getKey());
            return getGroupResponses(groups);
        } catch (SQLException e) {
            throw new NotFoundException(MS_NOT_FOUND_LIST);
        }
    }

    public GroupResponse findGroupById(GroupRequest groupRequest) throws NotFoundException {
        try {
            Group group = GroupDao.findById(groupRequest.getGroupId());
            return getGroupResponse(group);
        } catch (SQLException e) {
            throw new NotFoundException(MS_NOT_FOUND_LIST);
        }
    }

    private GroupResponse getGroupResponse(Group group) throws NotFoundException {
        if (group == null) throw new NotFoundException(MS_NOT_FOUND);
        return GroupMapper.INSTANCE.toGroupResponse(group);
    }

    private List<GroupResponse> getGroupResponses(List<Group> groups) throws NotFoundException {
        if (groups.isEmpty()) throw new NotFoundException(MS_NOT_FOUND_LIST);
        return GroupMapper.INSTANCE.toGroupResponses(groups);
    }
}
