package com.killjl.guanli.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.killjl.guanli.DAO.TokenDao;
import com.killjl.guanli.DAO.UserDao;
import com.killjl.guanli.guanliUtil.GuanliUtil;
import com.killjl.guanli.model.HostHolder;
import com.killjl.guanli.model.Token;
import com.killjl.guanli.model.User;

@Service

public class UserService {
	@Autowired
	UserDao userDao;
	
	@Autowired
	TokenDao tokenDao;
	
	@Autowired
	HostHolder hostHolder;
	
	public User getUser(int userid) {
		return userDao.selectByUserId(userid);
	}
	
	public Map<String, Object> updateName(int userid ,String name){
		Map<String, Object> map=new HashMap<>();
		if(StringUtils.isEmpty(name)) {
			map.put("msg","名字不能为空");
			return map;
		}
		
		userDao.updateName(userid, name);
		map.put("msg", "设置成功");
		
		return map;
	}
	
	public Map<String, Object> updatePwd(int userid,String password,String newPassword) {
		Map<String, Object> map=new HashMap<>();
		
		if(StringUtils.isEmpty(password)) {
			map.put("msg","旧密码不能为空");
			return map;
		}
		
		if(StringUtils.isEmpty(newPassword)) {
			map.put("msg","新密码不能为空");
			return map;
		}
		
		User user=userDao.selectByUserId(userid);
		
		if(!GuanliUtil.MD5(password+user.getSalt()).equals(user.getPassword())) {
			map.put("msg", "密码错误");
			return map;
		}
		
		userDao.updatePassword(userid,GuanliUtil.MD5(password+user.getSalt()));
		map.put("msg", "设置成功");
		
		return map;
	}
	
	public Map<String, Object> reg(int userid,String password){
		Map<String, Object> map=new HashMap<>();
		
		if(StringUtils.isEmpty(password)) {
			map.put("msg","密码不能为空");
			return map;
		}
		User user=userDao.selectByUserId(userid);
		if(user!=null) {
			map.put("msg", "该学号已经被注册");
			return map;
		}
		
		user =new User();
		user.setName("");
		user.setUserid(userid);
		user.setSalt(UUID.randomUUID().toString().substring(0, 5));
		user.setPassword(GuanliUtil.MD5(password+user.getSalt()));
		user.setPic("");
		userDao.addUser(user);
		
		String token=addToken(userid);
		map.put("token", token);
		return map;
	}

	public Map<String, Object> logIn(int userid,String password){

		Map<String, Object> map=new HashMap<>();
		
		if(StringUtils.isEmpty(password)) {
			map.put("msg","密码不能为空");
			return map;
		}
		
		User user=userDao.selectByUserId(userid);
		
		if(user==null) {
			map.put("msg", "用户不存在");
			return map;
		}
		
		if(!GuanliUtil.MD5(password+user.getSalt()).equals(user.getPassword())) {
			map.put("msg", "密码错误");
			return map;
		}
		
		
		String token=addToken(userid);
		map.put("token", token);
		return map;
	}
	
	public void logOut(String token){
		tokenDao.updateStatus(token, 1);
	}
	
	public String addToken(int userid) {
		 Token token = new Token();
	     token.setUserid(userid);
	     Date date = new Date();
	     date.setTime(date.getTime() + 1000*3600*24);
	     token.setDelay(date);
	     token.setStatus(0);
	     token.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
	     tokenDao.addToken(token);
	     
	     User user=userDao.selectByUserId(userid);
	     hostHolder.setUser(user);
	     
	     return token.getToken();
	}
	
	public void updatePic(int userid,String pic) {
		userDao.updatePic(userid, pic);
	}
}
