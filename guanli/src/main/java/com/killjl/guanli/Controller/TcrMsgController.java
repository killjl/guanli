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
import com.killjl.guanli.Service.TokenService;
import com.killjl.guanli.guanliUtil.GuanliUtil;
import com.killjl.guanli.model.Tcrmsg;
import com.killjl.guanli.model.Token;


@Controller

public class TcrMsgController {
	@Autowired
	com.killjl.guanli.Service.TcrMsgService tcrMsgService;
	
	@Autowired
	TokenService tokenService;
	
	@RequestMapping(path= {"/tcrmsg/add/"},method= {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String addTcrmsg(@RequestParam("name") String name,@RequestParam("sex") String sex,
			@RequestParam("college") String college,@RequestParam("phone") int phone,HttpServletRequest httpServletRequest) {
		try {
			Tcrmsg tcrMsg=new Tcrmsg();
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
				tcrMsg.setName(name);
				tcrMsg.setSex(sex);
				tcrMsg.setCollege(college);
				tcrMsg.setPhone(phone);
				tcrMsg.setUserid(userid);
				tcrMsgService.addTcrMsg(tcrMsg);
				return GuanliUtil.getJSONString(0,"添加成功");
			}
			else
				return GuanliUtil.getJSONString(1,"请先登陆");	
		} catch (Exception e) {
			return GuanliUtil.getJSONString(1,"添加失败");
		}
	}

	@RequestMapping(path= {"/tcrmsg/change/"},method= {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String updateTcrmsg(@RequestParam("name") String name,@RequestParam("sex") String sex,
			@RequestParam("college") String college,@RequestParam("phone") int phone,HttpServletRequest httpServletRequest) {
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
		        
				tcrMsgService.updateTcrMsg(userid, name, sex, college, phone);
				return GuanliUtil.getJSONString(0,"更新成功");
			}
			else 
				return GuanliUtil.getJSONString(1,"请先登陆");
		} catch (Exception e) {
			return GuanliUtil.getJSONString(1,"更新失败");
		}
	}
	
	@RequestMapping(path= {"/tcrmsg/find/"},method= {RequestMethod.POST})
	public String findStdmsg(@RequestParam("userid") int userid,@RequestParam("name") String name,
			@RequestParam("sex") String sex,@RequestParam("phone") int phone,Model model,@RequestParam("page") int page,
			@RequestParam("college") String college,@RequestParam("limit") int limit,HttpServletRequest httpServletRequest) {
			PageHelper.startPage(page, limit);
			List<Tcrmsg> list=tcrMsgService.getTcrMsg(userid, name, sex, phone, college);
			PageInfo<Tcrmsg> info=new PageInfo<Tcrmsg>(list);
			long sum=info.getTotal();
			int now=info.getPageNum();
			int pagesum=info.getPages();
			
			model.addAttribute("pagesum", pagesum);
			model.addAttribute("list", list);
			model.addAttribute("sum", sum);
			model.addAttribute("now", now);
			model.addAttribute("college", college);
			model.addAttribute("userid", userid);
			model.addAttribute("name", name);
			model.addAttribute("sex", sex);
			model.addAttribute("phone",phone);
			
			return "TcrMsgPage";
	}
	
}
