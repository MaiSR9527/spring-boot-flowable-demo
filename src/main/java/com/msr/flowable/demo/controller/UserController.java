package com.msr.flowable.demo.controller;

import com.google.common.collect.Maps;
import com.msr.flowable.demo.common.SessionConst;
import com.msr.flowable.demo.common.dto.Result;
import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author MaiShuRen
 * @version v1.0
 * @date 2020/8/8 17:42
 */

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    IdentityService identityService;

    @RequestMapping(value = "/login.json")
    @ResponseBody
    public Result<String> login(HttpSession session, String username) {
        session.setAttribute(SessionConst.SESSION_USERNAME_KEY, username);
        return Result.success("success");
    }

    @RequestMapping(value = "/addGroup.json")
    @ResponseBody
    public Result<String> addGroup(String groupId) {
        Group group1 = identityService.newGroup(groupId);
        group1.setName("for test");
        identityService.saveGroup(group1);
        return Result.success("success");
    }

    @RequestMapping(value = "/add.json")
    @ResponseBody
    public Result<String> add(String userId, String groupId) {
        User user1 = identityService.newUser(userId);
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("user1@126.com");
        user1.setPassword("pwd");
        identityService.saveUser(user1);
        Group group1 = identityService.createGroupQuery().groupId(groupId).singleResult();
        identityService.createMembership(user1.getId(), group1.getId());

        return Result.success("success");
    }

    @RequestMapping(value = "/show.json")
    @ResponseBody
    public Result<Map<String, Object>> show(HttpSession session, String userId, String groupId) {

        Map<String, Object> maps = Maps.newHashMap();

        List<User> userList = identityService.createUserQuery().list();

        List<Group> groupList = identityService.createGroupQuery().list();

        List<User> group1UserList = identityService.createUserQuery().memberOfGroup("group1").list();

        Group elGroup = identityService.createGroupQuery().groupMember("el").singleResult();

        maps.put("userList", userList);
        maps.put("groupList", groupList);
        maps.put("group1UserList", group1UserList);
        maps.put("elGroup", elGroup.getId());

        return Result.success(maps);
    }


}

