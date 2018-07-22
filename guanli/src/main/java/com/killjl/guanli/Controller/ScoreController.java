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
import com.killjl.guanli.Service.PointService;
import com.killjl.guanli.Service.ScoreService;
import com.killjl.guanli.Service.SelectionService;
import com.killjl.guanli.Service.TcrMsgService;
import com.killjl.guanli.Service.TokenService;
import com.killjl.guanli.guanliUtil.GuanliUtil;
import com.killjl.guanli.model.Class;
import com.killjl.guanli.model.Point;
import com.killjl.guanli.model.Score;
import com.killjl.guanli.model.ScoreShow;
import com.killjl.guanli.model.SelectionShow;
import com.killjl.guanli.model.SelectionShowII;
import com.killjl.guanli.model.Token;

@Controller

public class ScoreController {
	@Autowired
	ScoreService scoreService;
	
	@Autowired
	SelectionService selectionService;
	
	@Autowired
	ClassService classService;
	
	@Autowired
	PointService pointService;
	
	@Autowired
	TcrMsgService tcrService;
	
	@Autowired
	TokenService tokenService;
	
	@RequestMapping(path= {"/score/add/"},method= {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String addScore(@RequestParam("classid") String classid,@RequestParam("stdid") int stdid,
			@RequestParam("score") int score,HttpServletRequest httpServletRequest) {
		try {
			Score scorept=new Score();
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
		        Score scoreptt=scoreService.selectScore(stdid, classid);
		        if(scoreptt!=null)
		        	return GuanliUtil.getJSONString(1,"已录入");
		        else {
			        scorept.setClassid(classid);
			        scorept.setScore(score);
			        scorept.setStatus("在录");//
			        scorept.setStdid(stdid);
			        scorept.setTcrid(userid);
			        scoreService.addScore(scorept);
			        
			        selectionService.updateSelectionStatus(stdid, classid, 2);
			        return GuanliUtil.getJSONString(0,"添加成功");
		        }
		     }
				else
					return GuanliUtil.getJSONString(1,"请先登陆");
				
			} catch (Exception e) {
				return GuanliUtil.getJSONString(1,"添加失败");
			}
		}
	
	@RequestMapping(path= {"/score/change/"},method= {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String changeScore(@RequestParam("classid") String classid,@RequestParam("stdid") int stdid,
			@RequestParam("score") int score,HttpServletRequest httpServletRequest) {
		try {
		    if(httpServletRequest.getCookies()!=null) {
		        for(Cookie cookie:httpServletRequest.getCookies()) {
		        	if(cookie.getName().equals("token")) {
		       			break;
		       		}
		       	}
		        
		        scoreService.updateScore(stdid, classid, score);
		        return GuanliUtil.getJSONString(0,"修改成功");
				}
				else
					return GuanliUtil.getJSONString(1,"请先登陆");
				
			} catch (Exception e) {
				return GuanliUtil.getJSONString(1,"修改失败");
			}
		}
	
	@RequestMapping(path= {"/tcrmsg/stdscoreadd/"},method= {RequestMethod.POST,RequestMethod.GET})
	public String addScorePageV(Model model,@RequestParam("classname") String classname,@RequestParam("limit") int limit
			,@RequestParam("page") int page,HttpServletRequest httpServletRequest) {
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
		        String tcrname=tcrService.getTcrMsgById(userid).getName();
		        Class clazz=classService.findClassByName(classname, tcrname, 1);
		        String classid=clazz.getClassid();
		        
		        PageHelper.startPage(page, limit);
		        List<SelectionShow> list =selectionService.findSeletionByClassId(classid,1);
		       
		        PageInfo<SelectionShow> info=new PageInfo<SelectionShow>(list);
				long sum=info.getTotal();
				int now=info.getPageNum();
				int pagesum=info.getPages();
				
				model.addAttribute("pagesum", pagesum);
				model.addAttribute("sum", sum);
				model.addAttribute("now", now);
		        model.addAttribute("classname", classname);
		        model.addAttribute("list", list);
		     }
			
			return "scoreadd";	
		
		}
	
	@RequestMapping(path= {"/tcrmsg/stdscorechange/"},method= {RequestMethod.POST,RequestMethod.GET})
	public String changeScorePageV(Model model,@RequestParam("classname") String classname,@RequestParam("limit") int limit
			,@RequestParam("page") int page,HttpServletRequest httpServletRequest) {
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
		        String tcrname=tcrService.getTcrMsgById(userid).getName();
		        Class clazz=classService.findClassByName(classname, tcrname, 1);
		        String classid=clazz.getClassid();
		        
		        PageHelper.startPage(page, limit);
		        List<SelectionShowII> list =selectionService.findSeletionByClassIdII(classid);
		        
		        PageInfo<SelectionShowII> info=new PageInfo<SelectionShowII>(list);
				long sum=info.getTotal();
				int now=info.getPageNum();
				int pagesum=info.getPages();
				
				model.addAttribute("pagesum", pagesum);
				model.addAttribute("sum", sum);
				model.addAttribute("now", now);
		        model.addAttribute("classname", classname);
		        model.addAttribute("list", list);
		     }
			
			return "scorechange";	
		
		}

	
	@RequestMapping(path= {"/stdmsg/findScore"},method= {RequestMethod.POST,RequestMethod.GET})
	public String findScore(Model model,HttpServletRequest httpServletRequest) {
		
			String token=null;
		    if(httpServletRequest.getCookies()!=null) {
		        for(Cookie cookie:httpServletRequest.getCookies()) {
		        	if(cookie.getName().equals("token")) {
		        		token=cookie.getValue();
		       			break;
		       		}
		       	}
		        
		        Token t=tokenService.findToken(token);
		        int userid=t.getUserid();
		        PageHelper.startPage(1, 10);
		        List<ScoreShow> list=scoreService.selectScoreByStd(userid);
		        
		        PageInfo<ScoreShow> info=new PageInfo<ScoreShow>(list);
				long sum=info.getTotal();
				int now=info.getPageNum();
				int pagesum=info.getPages();
				
				model.addAttribute("pagesum", pagesum);
				model.addAttribute("sum", sum);
				model.addAttribute("now", now);
		        model.addAttribute("list", list);
		      
		    }
		    return "scorePage";  
		}
	
	@RequestMapping(path= {"/stdmsg/findScoreII"},method= {RequestMethod.POST,RequestMethod.GET})
	public String findScore(Model model,@RequestParam("limit") int limit
			,@RequestParam("page") int page,HttpServletRequest httpServletRequest) {
		
			String token=null;
		    if(httpServletRequest.getCookies()!=null) {
		        for(Cookie cookie:httpServletRequest.getCookies()) {
		        	if(cookie.getName().equals("token")) {
		        		token=cookie.getValue();
		       			break;
		       		}
		       	}
		        
		        Token t=tokenService.findToken(token);
		        int userid=t.getUserid();
		        PageHelper.startPage(page, limit);
		        List<ScoreShow> list=scoreService.selectScoreByStd(userid);
		        
		        PageInfo<ScoreShow> info=new PageInfo<ScoreShow>(list);
				long sum=info.getTotal();
				int now=info.getPageNum();
				int pagesum=info.getPages();
				
				model.addAttribute("pagesum", pagesum);
				model.addAttribute("sum", sum);
				model.addAttribute("now", now);
		        model.addAttribute("list", list);
		      
		    }
		    return "scorePage";  
		}

	@RequestMapping(path= {"/score/summit/"},method= {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String summitScore(@RequestParam("classid") String classid,HttpServletRequest httpServletRequest) {
		try {
			//String token=null;
		    if(httpServletRequest.getCookies()!=null) {
		        for(Cookie cookie:httpServletRequest.getCookies()) {
		        	if(cookie.getName().equals("token")) {
		        		//token=cookie.getValue();
		       			break;
		       		}
		       	}
		        
		        String type=classService.findClassById(classid).getDepartment();
		        System.out.print(type);
		        List<SelectionShowII> list =selectionService.findSeletionByClassIdII(classid);
		        for(SelectionShowII std:list) {
		        	int stdid=std.getStdid();
		        	int score=std.getScore();
		        	scoreService.updateStatus(stdid, classid, score);//成绩状态更新
		        	Point point=pointService.findPoint(stdid);
		        	int sum=point.getSum();
		        	sum=sum+score;
		        	pointService.updatePoint(stdid, type, sum, score);//学分情况更新
		        }
		        classService.updateClassStatus(classid, 2);//课程状态更新
		        System.out.print("class");
		        return GuanliUtil.getJSONString(0,"发布成功");
				}
				else
					return GuanliUtil.getJSONString(1,"请先登陆");
				
			} catch (Exception e) {
				return GuanliUtil.getJSONString(1,"发布失败");
			}
		}

}
