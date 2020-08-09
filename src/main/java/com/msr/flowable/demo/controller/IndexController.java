package com.msr.flowable.demo.controller;

import com.msr.flowable.demo.common.SessionConst;
import com.msr.flowable.demo.common.dto.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


/**
 * @author MaiShuRen
 * @version v1.0
 * @date 2020/8/8 17:37
 */

@Controller
public class IndexController {


    @RequestMapping("/")
    public String index0() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login/action.json")
    @ResponseBody
    public Result<String> loginAction(HttpSession session, String username) {
        session.setAttribute(SessionConst.SESSION_USERNAME_KEY, username);
        return Result.success("");
    }

    @RequestMapping("/login/out")
    public String loginOut(HttpSession session) {
        session.setAttribute(SessionConst.SESSION_USERNAME_KEY, null);
        return "login";
    }

}

