package com.killjl.guanli.Controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.killjl.guanli.Service.UserService;
import com.killjl.guanli.guanliUtil.GuanliUtil;

@Controller

public class LoginController {
	@Autowired
	UserService userService;
	
	
	@RequestMapping(path= {"/reg"},method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String reg(Model model,@RequestParam("userid") int userid,
			@RequestParam("password") String password, HttpServletResponse response) {
		try {
			Map<String,Object> map=userService.reg(userid, password);
			
			if(map.containsKey("token")) {
				Cookie cookie=new Cookie("token",map.get("token").toString());
				cookie.setPath("/");
				response.addCookie(cookie);
				return GuanliUtil.getJSONString(0, "注册成功");
			}
			else
				return GuanliUtil.getJSONString(1, map);
		} catch (Exception e) {
			return GuanliUtil.getJSONString(1, "注册异常");
		}
		
	}

	@RequestMapping(path= {"/login"},method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String login(Model model,@RequestParam("userid") int userid,
			@RequestParam("password") String password,HttpServletResponse response) {
		try {
			Map<String,Object> map=userService.logIn(userid, password);
			if(map.containsKey("token")) {
				Cookie cookie=new Cookie("token",map.get("token").toString());
				cookie.setPath("/");
				response.addCookie(cookie);
				return GuanliUtil.getJSONString(0, "登陆成功");
			}
			else
				return GuanliUtil.getJSONString(1, map);
		} catch (Exception e) {
			return GuanliUtil.getJSONString(1, "登陆异常");
		}
		
	}
	
	@RequestMapping(path = {"/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("token") String token) {
        userService.logOut(token);
        return "redirect:/";
    }
}
