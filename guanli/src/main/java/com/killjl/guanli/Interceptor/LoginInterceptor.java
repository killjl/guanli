package com.killjl.guanli.Interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.killjl.guanli.model.Token;

public class LoginInterceptor implements HandlerInterceptor{
	@Autowired
	private com.killjl.guanli.DAO.TokenDao tokenDao;
	
	@Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String token=null;
        if(httpServletRequest.getCookies()!=null) {
        	for(Cookie cookie:httpServletRequest.getCookies()) {
        		if(cookie.getName().equals("token")) {
        			token=cookie.getValue();
        			break;
        		}
        	}		
        }
        
        if(token!=null) {
        	Token logintoken=tokenDao.selectByToken(token);
        	int status=logintoken.getStatus();
        	if(status!=2)
        		return true;
        }
        return false;
    }
	
	@Override
	    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
	    //if(modelAndView!=null&&hostHolder.getUser()!=null)
	    	//modelAndView.addObject("user", hostHolder.getUser());
	}

	@Override
	    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
	    //hostHolder.clear();
	}

}
