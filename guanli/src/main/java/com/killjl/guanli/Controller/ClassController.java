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
import com.killjl.guanli.Service.ClassService;
import com.killjl.guanli.Service.SelectionService;
import com.killjl.guanli.Service.TokenService;
import com.killjl.guanli.Service.TcrMsgService;
import com.killjl.guanli.guanliUtil.GuanliUtil;
import com.killjl.guanli.model.Class;
import com.killjl.guanli.model.Tcrmsg;
import com.killjl.guanli.model.Token;

@Controller

public class ClassController {
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	SelectionService selectionService;
	
	@Autowired
	ClassService classService;
	
	@Autowired
	TcrMsgService tcrMsgService;
	
	@RequestMapping(path= {"/class/add/"},method= {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String addClass(@RequestParam("classname") String classname,@RequestParam("department") String department,
			@RequestParam("point") int point,@RequestParam("time") String time,@RequestParam("place") String place,
			@RequestParam("year") String year,@RequestParam("major") String major,@RequestParam("testtime") String testtime,
			@RequestParam("classid") String classid,HttpServletRequest httpServletRequest) {
		try {
			Class clazz=new Class();
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
		        String tcrname=tcrMsgService.getTcrMsgById(userid).getName();
		        
		        clazz.setClassid(classid);
				clazz.setClassname(classname);
				clazz.setDepartment(department);
				clazz.setMajor(major);
				clazz.setPlace(place);
				clazz.setPoint(point);
				clazz.setTesttime(testtime);
				clazz.setPrefercount(0);
				clazz.setTcrname(tcrname);
				clazz.setTime(time);
				clazz.setYear(year);
				clazz.setStatus(1);
				classService.addClass(clazz);
				
				return GuanliUtil.getJSONString(0,"添加成功");
			}
			else
				return GuanliUtil.getJSONString(1,"请先登陆");
			
		} catch (Exception e) {
			return GuanliUtil.getJSONString(1,"添加失败");
		}
	}
	

	@RequestMapping(path= {"/tcrmsg/classchange"},method= {RequestMethod.GET,RequestMethod.POST})
	public String changeClassPageV(Model model,@RequestParam("classname") String classname,
			HttpServletRequest httpServletRequest) {
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
	        Tcrmsg tcrMsg=tcrMsgService.getTcrMsgById(userid);
	        String tcrname=tcrMsg.getName();
	        Class clazz=classService.findClassByName(classname, tcrname, 1);
	        
	        model.addAttribute("tcrmsg", tcrMsg);
	        model.addAttribute("clazz" , clazz);
	     }
		
		return "Classchange";	
	}

	
	@RequestMapping(path= {"/class/change/"},method= {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String changeClass(@RequestParam("department") String department,@RequestParam("major") String major,
			@RequestParam("point") int point,@RequestParam("time") String time,@RequestParam("place") String place,
			@RequestParam("year") String year,@RequestParam("testtime") String testtime,
			@RequestParam("classid") String classid,HttpServletRequest httpServletRequest) {
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
		        String tcrname=tcrMsgService.getTcrMsgById(userid).getName();
		        classService.updateClass(classid, department, point, tcrname, time, place, year, major, testtime);
		        return GuanliUtil.getJSONString(0,"更改成功");
				}
				else
					return GuanliUtil.getJSONString(1,"请先登陆");
				
			} catch (Exception e) {
				return GuanliUtil.getJSONString(1,"更改失败");
			}
		}

	@RequestMapping(path= {"/class/find"},method= {RequestMethod.POST})
	public String findClass(@RequestParam("classid") String classid,@RequestParam("classname") String classname,
			@RequestParam("department") String department,@RequestParam("point") int point,@RequestParam("limit") int limit,
			@RequestParam("tcrname") String tcrname,@RequestParam("time") String time,
			@RequestParam("place") String place,@RequestParam("year") String year,@RequestParam("page") int page,
			@RequestParam("major") String major,@RequestParam("testtime") String testtime,@RequestParam("status") int status,
			Model model,HttpServletRequest httpServletRequest) {
			PageHelper.startPage(page, limit);   
			List<Class> list=classService.findClass(classid, classname, department, point, tcrname, time, place, year, major, testtime, status);
			PageInfo<Class> info=new PageInfo<Class>(list);
			long sum=info.getTotal();
			int now=info.getPageNum();
			int pagesum=info.getPages();
			
			model.addAttribute("pagesum", pagesum);
			model.addAttribute("list", list);
			model.addAttribute("sum", sum);
			model.addAttribute("now", now);
			model.addAttribute("classid", classid);
			model.addAttribute("classname", classname);
			model.addAttribute("department", department);
			model.addAttribute("major", major);
			model.addAttribute("place", place);
			model.addAttribute("point", point);
			model.addAttribute("testtime", testtime);
			model.addAttribute("tcrname", tcrname);
			model.addAttribute("time", time);
			model.addAttribute("year", year);
			model.addAttribute("status", status);
			
			return "ClassPage";
	}

}
