package com.killjl.guanli.Interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import com.killjl.guanli.Service.TokenService;
import com.killjl.guanli.model.Token;

public class StdPassportInterceptor implements HandlerInterceptor{
	@Autowired
	TokenService tokenService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		 String token=null;
	     if(request.getCookies()!=null) {
	        for(Cookie cookie:request.getCookies()) {
	        	if(cookie.getName().equals("token")) {
	        		token=cookie.getValue();
	       			break;
	       		}
	       	}
	        
	        Token tokener=tokenService.findToken(token);
	        System.out.print(tokener);
	        int userid =tokener.getUserid();
	        
	        if(userid!=2014052504) {
	        	System.out.print("yes");
	        	if(userid<=999999999) {
	        		System.out.print("no");
	        		response.sendRedirect("/loginpage");
		     		return false; 
	        	}
	        }
	     }
	      return true;
	}
}
