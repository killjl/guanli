package com.killjl.guanli.Controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.killjl.guanli.Service.PointService;
import com.killjl.guanli.Service.StdMsgService;
import com.killjl.guanli.Service.TokenService;
import com.killjl.guanli.guanliUtil.GuanliUtil;
import com.killjl.guanli.model.Point;
import com.killjl.guanli.model.Stdmsg;
import com.killjl.guanli.model.Token;

@Controller

public class StdMsgController {
	@Autowired
	StdMsgService stdMsgService;
	
	@Autowired
	PointService pointService;
	
	@Autowired
	TokenService tokenService;
	
	@RequestMapping(path= {"/stdmsg/add/"},method= {RequestMethod.POST})
	@ResponseBody
	public String addStdmsg(@RequestParam("name") String name,@RequestParam("sex") String sex,
			@RequestParam("grade") String grade,@RequestParam("home") String home,
			@RequestParam("major") String major,@RequestParam("phone") int phone,
			@RequestParam("college") String college,HttpServletRequest httpServletRequest) {
		try {
			Stdmsg stdMsg=new Stdmsg();
			String token=null;
		     if(httpServletRequest.getCookies()!=null) {
		        for(Cookie cookie:httpServletRequest.getCookies()) {
		        	if(cookie.getName().equals("token")) {
		        		token=cookie.getValue();
		       			break;
		       		}
		       	}
		        
		        Token tokener=tokenService.findToken(token);
		        int userid=tokener.getUserid();
		     
				stdMsg.setGrade(grade);
				stdMsg.setCollege(college);
				stdMsg.setHome(home);
				stdMsg.setMajor(major);
				stdMsg.setName(name);
				stdMsg.setPhone(phone);
				stdMsg.setSex(sex);
				stdMsg.setUserid(userid);
				stdMsg.setPoint(0);
				stdMsg.setSelectpoint(22);
				stdMsgService.addStdMsg(stdMsg);
				
				Point point=new Point();
				point.setArt(0);
				point.setEconomy(0);
				point.setInnovation(0);
				point.setLanguage(0);
				point.setLiterature(0);
				point.setMajor(0);
				point.setMinor(0);
				point.setSport(0);
				point.setStdid(userid);
				point.setSum(0);
				pointService.addPoint(point);
				
				return GuanliUtil.getJSONString(0,"添加成功");
			}
			else
				return GuanliUtil.getJSONString(1,"请先登陆");
			
		} catch (Exception e) {
			return GuanliUtil.getJSONString(1,"添加失败");
		}
	}

	@RequestMapping(path= {"/stdmsg/change/"},method= {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String updateStdmsg(@RequestParam("name") String name,@RequestParam("sex") String sex,
			@RequestParam("grade") String grade,@RequestParam("home") String home,@RequestParam("college") String college,
			@RequestParam("major") String major,@RequestParam("phone") int phone,HttpServletRequest httpServletRequest) {
		try {
			String token=null;
		     if(httpServletRequest.getCookies()!=null) {
		        for(Cookie cookie:httpServletRequest.getCookies()) {
		        	if(cookie.getName().equals("token")) {
		        		token=cookie.getValue();
		       			break;
		       		}
		       	}
		        
		        Token tokener=tokenService.findToken(token);
		        int userid=tokener.getUserid();
		        
				stdMsgService.updateMsg(userid, college, name, grade, home, major, sex, phone);
				
				return GuanliUtil.getJSONString(0,"更新成功");
			}
			else 
				return GuanliUtil.getJSONString(1,"请先登陆");
		} catch (Exception e) {
			return GuanliUtil.getJSONString(1,"更新失败");
		}
	}
	
	@RequestMapping(path= {"/stdmsg/find/"},method= {RequestMethod.POST})
	public String findStdmsg(@RequestParam("userid") int userid,@RequestParam("name") String name,@RequestParam("sex") String sex,
			@RequestParam("grade") String grade,@RequestParam("home") String home,
			@RequestParam("major") String major,@RequestParam("phone") int phone,
			@RequestParam("college") String college,@RequestParam("page") int page,@RequestParam("limit") int limit,Model model,HttpServletRequest httpServletRequest) {
			PageHelper.startPage(page, limit);   
			List<Stdmsg> list=stdMsgService.getStdMsg(userid, name, grade, home, major, sex, phone, college);
			PageInfo<Stdmsg> info=new PageInfo<Stdmsg>(list);
			long sum=info.getTotal();
			int now=info.getPageNum();
			int pagesum=info.getPages();
			
			model.addAttribute("pagesum", pagesum);
			model.addAttribute("sum", sum);
			model.addAttribute("now", now);
			
			Stdmsg stdmsg=new Stdmsg();
			stdmsg.setCollege(college);
			stdmsg.setGrade(grade);
			stdmsg.setHome(home);
			stdmsg.setMajor(major);
			stdmsg.setName(name);
			stdmsg.setPhone(phone);
			stdmsg.setSex(sex);
			stdmsg.setUserid(userid);
			
			model.addAttribute("stdmsg", stdmsg);
			model.addAttribute("list", list);
			
			return "StdMsgPage";
	}
	
}
