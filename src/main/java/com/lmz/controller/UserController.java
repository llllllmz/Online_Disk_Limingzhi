package com.lmz.controller;

import com.lmz.service.UserService;
import com.lmz.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/loginPage")
    public String loginPage(){
        return "layout/login";
    }
    @RequestMapping("/registerPage")
    public String registerPage(){
        return "layout/register";
    }


    @RequestMapping("/login")
    public String login(String username, String password, ModelMap map, HttpSession session){

        User user = userService.login(username, password);
        if(user!=null && user.getStat()==1){
            session.setAttribute("user_login",user);
            return "redirect:/index";
        }else if(user!=null && user.getStat()==0){
            map.put("msg","账号已被禁用");
            return "layout/login";
        }
        map.put("msg","用户名或者密码错误");
        return "layout/login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user_login");
        return "layout/login";
    }

    @RequestMapping("/userlists")
    public String userlists(HttpSession session,ModelMap map){
        User user = (User)session.getAttribute("user_login");
        if(user.getPermission()==0){
            return "permission";
        }
        List<User> userList = userService.userlist();
        map.put("userList",userList);
        return "userlist";
    }

    @RequestMapping("/register")
    public String register(String username, String password, ModelMap map){

        try{
            if(userService.register(username, password)>0) {
                return "layout/login";
            }else{
                map.put("msg","注册失败！！！");
                return "layout/register";
            }
        }catch (Exception e){
            map.put("msg","注册失败！！！");
            return "layout/register";
        }
    }

    @RequestMapping("/chmod")
    public String chmod(String username,String stat){
        userService.chmod(username,stat);
        return "redirect:userlists";
    }
}
