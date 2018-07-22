package com.killjl.guanli.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.killjl.guanli.Service.TokenService;
import com.killjl.guanli.guanliUtil.GuanliUtil;
import com.killjl.guanli.model.HostHolder;
import com.killjl.guanli.model.Token;

@Controller

public class UserController {
	@Autowired
	com.killjl.guanli.Service.UserService userService;
	
	@Autowired
	com.killjl.guanli.Service.QiniuService qiniuService;
	
	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	TokenService tokenService;
	
	@RequestMapping(path= {"/upload"},method= {RequestMethod.POST})
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file) throws IOException{
		//String filepath=stdMsgService.uploadPic(file);
		String filepath=qiniuService.upload(file);
		try {
			if(filepath==null)
				return GuanliUtil.getJSONString(1, "上传图片失败");
			if(hostHolder!=null) {
				int userid=hostHolder.getUser().getUserid();
				userService.updatePic(userid, filepath);
			}
			else
				return GuanliUtil.getJSONString(1,"请先登陆");
			return GuanliUtil.getJSONString(0, filepath);
			
		} catch (Exception e) {
			return GuanliUtil.getJSONString(1,"上传失败");
		}
		
	}
	
	@RequestMapping(path= {"/load"},method= {RequestMethod.GET})
	@ResponseBody
	public void load(@RequestParam("name") String name,
			HttpServletResponse response) throws IOException{
		response.setContentType("image/jpeg");
		StreamUtils.copy(new FileInputStream
				(new File(GuanliUtil.IMAGE_DIR+name)),response.getOutputStream());
	}
	
	@RequestMapping(path= {"/doChangeName"},method= {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String doChangeName(@RequestParam("name") String name,HttpServletRequest httpServletRequest) {
		try {
			String token=null;
		     if(httpServletRequest.getCookies()!=null) {
		        for(Cookie cookie:httpServletRequest.getCookies()) {
		        	if(cookie.getName().equals("token")) {
		        		token=cookie.getValue();
		       			break;
		       		}
		       	}
		     }  
		        Token tokener=tokenService.findToken(token);
		        int userid =tokener.getUserid();
			Map<String,Object> map=userService.updateName(userid, name);
			return GuanliUtil.getJSONString(0, map);			
		} catch (Exception e) {
			return GuanliUtil.getJSONString(1, "更改异常");
		}
	}
	
	@RequestMapping(path= {"/doChangePwd"},method= {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String doChangePwd(@RequestParam("password") String password,HttpServletRequest httpServletRequest, 
			 @RequestParam("newpassword") String newPassword) {
		try {
			 String token=null;
		     if(httpServletRequest.getCookies()!=null) {
		        for(Cookie cookie:httpServletRequest.getCookies()) {
		        	if(cookie.getName().equals("token")) {
		        		token=cookie.getValue();
		       			break;
		       		}
		       	}
		     }  
		    Token tokener=tokenService.findToken(token);
		    int userid =tokener.getUserid();
		     
			Map<String,Object> map=userService.updatePwd(userid, password, newPassword);
			
			return GuanliUtil.getJSONString(0, map);			
		} catch (Exception e) {
			return GuanliUtil.getJSONString(1, "更改异常");
		}
	}
	
}
