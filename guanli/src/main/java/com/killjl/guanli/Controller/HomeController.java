package com.killjl.guanli.Controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.killjl.guanli.Service.ClassService;
import com.killjl.guanli.Service.PointService;
import com.killjl.guanli.Service.StdMsgService;
import com.killjl.guanli.Service.TcrMsgService;
import com.killjl.guanli.Service.TokenService;
import com.killjl.guanli.Service.UserService;
import com.killjl.guanli.guanliUtil.GuanliUtil;
import com.killjl.guanli.model.Class;
import com.killjl.guanli.model.Point;
import com.killjl.guanli.model.Stdmsg;
import com.killjl.guanli.model.Tcrmsg;
import com.killjl.guanli.model.Token;
import com.killjl.guanli.model.User;

@Controller

public class HomeController {
	@Autowired
	UserService userService;
	
	@Autowired
	PointService pointService;
	
	@Autowired
	StdMsgService stdMsgService;
	
	@Autowired
	TcrMsgService tcrMsgService;
	
	@Autowired
	ClassService classService;
	
	@Autowired
	TokenService tokenService;
	
	@RequestMapping(path= {"/index","/"},method= {RequestMethod.GET,RequestMethod.POST})
	public String homePage(Model model) {
		
		return "index";
	}
	
	@RequestMapping(path= {"/message"},method= {RequestMethod.GET,RequestMethod.POST})
	public String pointPage(Model model,HttpServletRequest httpServletRequest) {
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
	        User user=userService.getUser(userid);
	        Point point=pointService.findPoint(userid);
	        model.addAttribute("point", point);
	        model.addAttribute("standard", GuanliUtil.StandardElectronic);
	        model.addAttribute("user", user);
		}	
		return "point";
	}
	
	@RequestMapping(path= {"/loginpage"},method= {RequestMethod.GET,RequestMethod.POST})
	public String loginPage(Model model) {
		
		return "login";
	}
	
	@RequestMapping(path= {"/regpage"},method= {RequestMethod.GET,RequestMethod.POST})
	public String regPage(Model model) {
		
		return "reg";
	}
	
	@RequestMapping(path= {"/userpage"},method= {RequestMethod.GET,RequestMethod.POST})
	public String userPage(Model model,HttpServletRequest httpServletRequest) {
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
		        if(userid==2014052504)
		        	return "user";
		        else if(userid>=1000000000)
		        	return "stduser";
		        else if(userid>=10000000 && userid<100000000)
		        	return "tcruser";
		     }
		return "loginpage";
	}
	
	@RequestMapping(path= {"user/changeName"},method= {RequestMethod.GET,RequestMethod.POST})
	public String namePage(Model model,HttpServletRequest httpServletRequest) {
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
	        User user=userService.getUser(userid);
	        model.addAttribute("user", user);
	     }
		
		return "changename";
	}
	
	@RequestMapping(path= {"user/changePwd"},method= {RequestMethod.GET,RequestMethod.POST})
	public String pwdPage(Model model,HttpServletRequest httpServletRequest) {
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
	        User user=userService.getUser(userid);
	        model.addAttribute("user", user);
	     }
		
		return "changepwd";	
	}
	
	@RequestMapping(path= {"/stdmsg/addpage"},method= {RequestMethod.GET,RequestMethod.POST})
	public String addStdPage(Model model,HttpServletRequest httpServletRequest) {
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
	        User user=userService.getUser(userid);
	        model.addAttribute("user", user);
	     }
		
		return "addStdMsg";	
	}
	
	@RequestMapping(path= {"/stdmsg/changepage"},method= {RequestMethod.GET,RequestMethod.POST})
	public String changeStdPage(Model model,HttpServletRequest httpServletRequest) {
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
	        Stdmsg stdMsg=stdMsgService.getStdMsgById(userid);
	        model.addAttribute("stdmsg", stdMsg);
	     }
		
		return "changeStdMsg";	
	}
	
	@RequestMapping(path= {"/tcrmsg/addpage"},method= {RequestMethod.GET,RequestMethod.POST})
	public String addTcrPage(Model model,HttpServletRequest httpServletRequest) {
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
	        User user=userService.getUser(userid);
	        model.addAttribute("user", user);
	     }
		
		return "addTcrMsg";	
	}
	
	@RequestMapping(path= {"/tcrmsg/changepage"},method= {RequestMethod.GET,RequestMethod.POST})
	public String changeTcrPage(Model model,HttpServletRequest httpServletRequest) {
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
	        model.addAttribute("tcrmsg", tcrMsg);
	     }
		
		return "changeTcrMsg";	
	}
	
	@RequestMapping(path= {"/tcrmsg/addclass"},method= {RequestMethod.GET,RequestMethod.POST})
	public String addClassPage(Model model,HttpServletRequest httpServletRequest) {
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
	        Tcrmsg tcrmsg=tcrMsgService.getTcrMsgById(userid);
	        model.addAttribute("tcrmsg", tcrmsg);
	     }
		
		return "addClass";	
	}
	
	@RequestMapping(path= {"/tcrmsg/changeclass"},method= {RequestMethod.GET,RequestMethod.POST})
	public String changeClassPage(Model model,HttpServletRequest httpServletRequest) {
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
	        List<Class> list=classService.findClass("", "", "", 0, tcrname, "", "","", "","", 1);
	        
	        model.addAttribute("tcrmsg", tcrMsg);
	        model.addAttribute("list",list);
	     }
		
		return "changeClass";	
	}

	@RequestMapping(path= {"/tcrmsg/addscore"},method= {RequestMethod.GET,RequestMethod.POST})
	public String addScorePage(Model model,HttpServletRequest httpServletRequest) {
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
	        Tcrmsg tcrmsg=tcrMsgService.getTcrMsgById(userid);
	        String tcrname=tcrmsg.getName();
	        List<Class> list=classService.findClass("", "", "", 0, tcrname, "", "", "", "","", 1);
	        
	        model.addAttribute("tcrmsg", tcrmsg);
	        model.addAttribute("list",list);
	     }
		
		return "addScore";	
	}
	
	@RequestMapping(path= {"/tcrmsg/changescore"},method= {RequestMethod.GET,RequestMethod.POST})
	public String changeScorePage(Model model,HttpServletRequest httpServletRequest) {
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
	        Tcrmsg tcrmsg=tcrMsgService.getTcrMsgById(userid);
	        String tcrname=tcrmsg.getName();
	        List<Class> list=classService.findClass("", "", "", 0, tcrname,"", "", "", "","", 1);
	        
	        model.addAttribute("tcrmsg", tcrmsg);
	        model.addAttribute("list",list);
	     }
		
		return "changeScore";	
	}
	
	@RequestMapping(path= {"/user/findStdMsg"},method= {RequestMethod.GET,RequestMethod.POST})
	public String findStdMsgPage(Model model) {
		
		return "stdmsgcondition";	
	}
	
	@RequestMapping(path= {"/user/findTcrMsg"},method= {RequestMethod.GET,RequestMethod.POST})
	public String findTcrMsgPage(Model model) {
		
		return "tcrmsgcondition";	
	}
	
	@RequestMapping(path= {"/user/findClass"},method= {RequestMethod.GET,RequestMethod.POST})
	public String findClassPage(Model model) {
		
		return "classcondition";	
	}

	@RequestMapping(path= {"/stdmsg/chooseClass"},method= {RequestMethod.GET,RequestMethod.POST})
	public String chooseClassPage(Model model,HttpServletRequest httpServletRequest) {
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
	        Stdmsg stdMsg=stdMsgService.getStdMsgById(userid);
	       
	        model.addAttribute("stdmsg", stdMsg);
	     }
		return "ClassSelection";	
	}
}
