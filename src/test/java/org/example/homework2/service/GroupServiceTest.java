package org.example.homework2.service;

import org.example.homework2.database.dao.GroupDao;
import org.example.homework2.database.model.Group;
import org.example.homework2.dto.request.*;
import org.example.homework2.dto.response.GroupResponse;
import org.example.homework2.dto.response.MessageResponse;
import org.example.homework2.exception.DeleteException;
import org.example.homework2.exception.NoAddException;
import org.example.homework2.exception.NoUpdateException;
import org.example.homework2.exception.NotFoundException;
import org.example.homework2.mapper.GroupMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class GroupServiceTest {
    private static GroupService groupService;

    @BeforeAll
    public static void setUp() {
        groupService = new GroupService();
    }

    @Test
    void shouldAddGroup() throws NoAddException {
        GroupAddRequest groupAddRequest = new GroupAddRequest();
        groupAddRequest.setName("M-16");
        try (MockedStatic<GroupDao> mocked = Mockito.mockStatic(GroupDao.class)) {
            mocked.when(() -> GroupDao.add(any(Group.class))).thenReturn(1);
            MessageResponse messageResponse = groupService.addGroup(groupAddRequest);
            String excepted = "Group adding is successful";
            assertEquals(excepted, messageResponse.getMessage());
        }
    }
    @Test
    void shouldAddGroupThrowsException()  {
        GroupAddRequest groupAddRequest = new GroupAddRequest();
        groupAddRequest.setName("M-16");
        try (MockedStatic<GroupDao> mocked = Mockito.mockStatic(GroupDao.class)) {
            mocked.when(() -> GroupDao.add(any(Group.class))).thenReturn(0);
           assertThrows(NoAddException.class,()->  groupService.addGroup(groupAddRequest));
        }
    }
    @Test
    void shouldUpdateGroup() throws NoUpdateException {
        GroupUpdateRequest groupUpdateRequest = new GroupUpdateRequest();
        groupUpdateRequest.setId(1L);
        groupUpdateRequest.setName("M-16");
        try (MockedStatic<GroupDao> mocked = Mockito.mockStatic(GroupDao.class)) {
            mocked.when(() -> GroupDao.update(any(Group.class))).thenReturn(1);
            MessageResponse messageResponse = groupService.updateGroup(groupUpdateRequest);
            String excepted = "Group updating is successful";
            assertEquals(excepted, messageResponse.getMessage());
        }
    }
    @Test
    void shouldUpdateGroupThrows() {
        GroupUpdateRequest groupUpdateRequest = new GroupUpdateRequest();
        groupUpdateRequest.setId(1L);
        groupUpdateRequest.setName("M-16");
        try (MockedStatic<GroupDao> mocked = Mockito.mockStatic(GroupDao.class)) {
            mocked.when(() -> GroupDao.update(any(Group.class))).thenReturn(0);
            assertThrows(NoUpdateException.class,()-> groupService.updateGroup(groupUpdateRequest));
        }
    }

    @Test
    void shouldAddTeaching() throws NoAddException {
        TeachingRequest teachingRequest = new TeachingRequest();
        teachingRequest.setGroupId(1L);
        teachingRequest.setTeacherId(2L);
        try (MockedStatic<GroupDao> mocked = Mockito.mockStatic(GroupDao.class)) {
            mocked.when(() -> GroupDao.addTeaching(teachingRequest.getGroupId(),teachingRequest.getTeacherId())).thenReturn(1);
            MessageResponse messageResponse = groupService.addTeaching(teachingRequest);
            String excepted = "Teaching adding is successful";
            assertEquals(excepted, messageResponse.getMessage());
        }
    }
    @Test
    void shouldAddTeachingTrowsException() {
        TeachingRequest teachingRequest = new TeachingRequest();
        teachingRequest.setGroupId(1L);
        teachingRequest.setTeacherId(2L);
        try (MockedStatic<GroupDao> mocked = Mockito.mockStatic(GroupDao.class)) {
            mocked.when(() -> GroupDao.addTeaching(teachingRequest.getGroupId(),teachingRequest.getTeacherId())).thenReturn(0);
          assertThrows(NoAddException.class,()-> groupService.addTeaching(teachingRequest));

        }
    }

    @Test
    void shouldDeleteGroup() throws DeleteException {
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setGroupId(1L);
        try (MockedStatic<GroupDao> mocked = Mockito.mockStatic(GroupDao.class)) {
            mocked.when(() -> GroupDao.delete(any(Long.class))).thenReturn(1);
            MessageResponse messageResponse = groupService.deleteGroup(groupRequest);
            String excepted = "Group deleting is successful";
            assertEquals(excepted, messageResponse.getMessage());
        }
    }
    @Test
    void shouldDeleteGroupThrowsException() {
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setGroupId(1L);
        try (MockedStatic<GroupDao> mocked = Mockito.mockStatic(GroupDao.class)) {
            mocked.when(() -> GroupDao.delete(any(Long.class))).thenReturn(0);
            assertThrows(DeleteException.class,()-> groupService.deleteGroup(groupRequest));
        }
    }

    @Test
    void shouldFindGroupsAll() throws NotFoundException {
        List<Group> groups = List.of(new Group(1L, "M-1"));
        List<GroupResponse> groupResponses = GroupMapper.INSTANCE.toGroupResponses(groups);
        try (MockedStatic<GroupDao> mocked = Mockito.mockStatic(GroupDao.class)) {
            mocked.when(GroupDao::findAll).thenReturn(groups);
            List<GroupResponse> actual=groupService.findGroupsAll();
            assertEquals(1,actual.size());
            assertEquals(groupResponses.get(0).getId(), actual.get(0).getId());
        }
    }
    @Test
    void shouldFindGroupsAllThrowsException() {
        try (MockedStatic<GroupDao> mocked = Mockito.mockStatic(GroupDao.class)) {
            mocked.when(GroupDao::findAll).thenReturn(List.of());
            assertThrows(NotFoundException.class,()->groupService.findGroupsAll());
        }
    }
    @Test
    void shouldFindGroupsByKey() throws NotFoundException {
        List<Group> groups = List.of(new Group(1L, "M-1"));
        List<GroupResponse> groupResponses = GroupMapper.INSTANCE.toGroupResponses(groups);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setKey("M-1");
        try (MockedStatic<GroupDao> mocked = Mockito.mockStatic(GroupDao.class)) {
            mocked.when(()->GroupDao.findByKey(any(String.class))).thenReturn(groups);
            List<GroupResponse> actual=groupService.findGroupsByKey(searchRequest);
            assertEquals(1,actual.size());
            assertEquals(groupResponses.get(0).getId(), actual.get(0).getId());
        }
    }
    @Test
    void shouldFindGroupsByKeyThrowsException()  {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setKey("M-1");
        try (MockedStatic<GroupDao> mocked = Mockito.mockStatic(GroupDao.class)) {
            mocked.when(()->GroupDao.findByKey(any(String.class))).thenReturn(List.of());
           assertThrows(NotFoundException.class,()->groupService.findGroupsByKey(searchRequest));
        }
    }

    @Test
    void findGroupById() throws NotFoundException {
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setGroupId(1L);
        Group group = new Group(1L,"M-1");
        GroupResponse groupResponse = GroupMapper.INSTANCE.toGroupResponse(group);
        try (MockedStatic<GroupDao> mocked = Mockito.mockStatic(GroupDao.class)) {
            mocked.when(()->GroupDao.findById(any(Long.class))).thenReturn(group);
            GroupResponse actual=groupService.findGroupById(groupRequest);
            assertEquals(groupResponse.getId(), actual.getId());
        }
    }
    @Test
    void findGroupByIdThrowsException() {
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setGroupId(1L);
        try (MockedStatic<GroupDao> mocked = Mockito.mockStatic(GroupDao.class)) {
            mocked.when(()->GroupDao.findById(any(Long.class))).thenReturn(null);
            assertThrows(NotFoundException.class,()->groupService.findGroupById(groupRequest));
        }
    }
}