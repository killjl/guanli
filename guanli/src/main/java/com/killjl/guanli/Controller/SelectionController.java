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
import com.killjl.guanli.Service.ScoreService;
import com.killjl.guanli.Service.SelectionService;
import com.killjl.guanli.Service.StdMsgService;
import com.killjl.guanli.Service.TokenService;
import com.killjl.guanli.guanliUtil.GuanliUtil;
import com.killjl.guanli.model.Class;
import com.killjl.guanli.model.Selection;
import com.killjl.guanli.model.Token;

@Controller
public class SelectionController {

	@Autowired
	TokenService tokenService;
	
	@Autowired
	StdMsgService stdMsgService;
	
	@Autowired
	SelectionService selectionService;
	
	@Autowired
	ScoreService scoreService;
	
	@Autowired
	ClassService classService;
	
	@RequestMapping(path= {"/stdmsg/classselect/"},method= {RequestMethod.POST})
	public String chooseMode(@RequestParam("selection") int selection,@RequestParam("limit") int limit,
			@RequestParam("page") int page,Model model,HttpServletRequest httpServletRequest) {
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
			
			    if(selection==0)   
			    	return "ClassSelection";
			    else if(selection==1) {
			    	PageHelper.startPage(page, limit);
			    	List<Class> list=selectionService.findSeletionByStd(userid);
			    	PageInfo<Class> info=new PageInfo<Class>(list);
					long sum=info.getTotal();
					int now=info.getPageNum();
					int pagesum=info.getPages();
					
					model.addAttribute("pagesum", pagesum);
					model.addAttribute("list", list);
					model.addAttribute("sum", sum);
					model.addAttribute("now", now);
					
			    	return "HaveChoosePage";
			    }
			    else if(selection==2) {
			    	PageHelper.startPage(page, limit);
			    	List<Class> list=selectionService.findClassBest(userid, "2018-2019上", "2019-2020上");
			    	PageInfo<Class> info=new PageInfo<Class>(list);
					long sum=info.getTotal();
					int now=info.getPageNum();
					int pagesum=info.getPages();
					int havepoint=stdMsgService.getStdMsgById(userid).getSelectpoint();
					
					model.addAttribute("havepoint", havepoint);
					model.addAttribute("pagesum", pagesum);
					model.addAttribute("list", list);
					model.addAttribute("sum", sum);
					model.addAttribute("now", now);
			    	return "AutoChoose";
			    }
			    else if(selection==3) {
			    	return "Choosecondition";
			    }
			}
			return "ClassSelection";
	}
	
	@RequestMapping(path= {"/class/delete/"},method= {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String deleteClass(@RequestParam("classid") String classid,HttpServletRequest httpServletRequest) {
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
		        selectionService.deleteSelection(userid, classid);
		        int havepoint=stdMsgService.getStdMsgById(userid).getSelectpoint();
		        int classpoint=classService.findClassById(classid).getPoint();
		        havepoint=havepoint+classpoint;
		        stdMsgService.updateSelectPoint(userid, havepoint);
				return GuanliUtil.getJSONString(0,"退课成功");
			}
			else
				return GuanliUtil.getJSONString(1,"请先登陆");
		} catch (Exception e) {
			return GuanliUtil.getJSONString(1,"退课失败");
		}
	}
	
	@RequestMapping(path= {"/selection/add/"},method= {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String addSelection(@RequestParam("classid") String classid,HttpServletRequest httpServletRequest) {
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
		        int point=classService.findClassById(classid).getPoint();
		        int havepoint=stdMsgService.getStdMsgById(userid).getSelectpoint();
		        String year=classService.findClassById(classid).getYear();
		        String time=classService.findClassById(classid).getTime();
		        String testtime=classService.findClassById(classid).getTesttime();
		        if(havepoint<point)
		        	return GuanliUtil.getJSONString(1,"可选学分不足");
		        else if(!year.equals("2018-2019上"))
		        	return GuanliUtil.getJSONString(1,"非本学年课程");
		        else {
		        	
		        	List<Class> selectlist=selectionService.findSeletionByStd(userid);
		        	boolean ok=false;
		        	for(Class c:selectlist) {
		        		if(c.getTime().equals(time))
		        			ok=true;
		        		else if(c.getTesttime().equals(testtime))
		        			ok=true;
		        		else
		        			continue;
		        	}
		        	
			        Selection haveselection=selectionService.findSelectionByIdAndClassId(userid, classid);
			        if(haveselection!=null)
			        	return GuanliUtil.getJSONString(1,"已选课");
			        else if(ok==true)
			        	return GuanliUtil.getJSONString(1,"时间冲突");
			        else {
				        Selection selection=new Selection();
				        selection.setClassid(classid);
				        selection.setStatus(1);
				        selection.setStdid(userid);
				        selectionService.addSelection(selection);
				        havepoint=havepoint-point;
				        stdMsgService.updateSelectPoint(userid, havepoint);
						return GuanliUtil.getJSONString(0,"选课成功");
			        }
		        }
			}
			else
				return GuanliUtil.getJSONString(1,"请先登陆");
		} catch (Exception e) {
			return GuanliUtil.getJSONString(1,"选课失败");
		}
	}
	
	@RequestMapping(path= {"/selection/find"},method= {RequestMethod.POST})
	public String findClass(@RequestParam("classid") String classid,@RequestParam("classname") String classname,
			@RequestParam("department") String department,@RequestParam("point") int point,
			@RequestParam("tcrname") String tcrname,@RequestParam("time") String time,@RequestParam("page") int page,
			@RequestParam("place") String place,@RequestParam("year") String year,@RequestParam("limit") int limit,
			@RequestParam("major") String major,@RequestParam("testtime") String testtime,
			Model model,HttpServletRequest httpServletRequest) {
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
			
			PageHelper.startPage(page, limit);    
			List<Class> list=classService.findClass(classid, classname, department, point, tcrname, time, place, year, major, testtime, 1);
			PageInfo<Class> info=new PageInfo<Class>(list);
			long sum=info.getTotal();
			int now=info.getPageNum();
			int pagesum=info.getPages();
			int havepoint=stdMsgService.getStdMsgById(userid).getSelectpoint();
			
			model.addAttribute("havepoint", havepoint);
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
			}
			return "SelectionPage";
	}
}
