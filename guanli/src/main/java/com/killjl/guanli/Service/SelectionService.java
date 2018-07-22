package com.killjl.guanli.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.killjl.guanli.DAO.ClassDao;
import com.killjl.guanli.DAO.ScoreDao;
import com.killjl.guanli.DAO.SelectionDao;
import com.killjl.guanli.DAO.StdMsgDao;
import com.killjl.guanli.DAO.TcrMsgDao;
import com.killjl.guanli.model.Class;
import com.killjl.guanli.model.ScoreShow;
import com.killjl.guanli.model.Selection;
import com.killjl.guanli.model.SelectionShow;
import com.killjl.guanli.model.SelectionShowII;

@Service

public class SelectionService {
	@Autowired
	SelectionDao selectionDao;
	
	@Autowired
	ScoreDao scoreDao;
	
	@Autowired
	TcrMsgDao tcrMsgDao;
	
	@Autowired
	StdMsgDao stdMsgDao;
	
	@Autowired
	ClassService classService;
	
	@Autowired
	ClassDao classDao;
	
	
	public void addSelection(Selection selection) {
		selectionDao.addSelection(selection);
	}
	
	public Selection findSelectionByIdAndClassId(int stdid,String classid) {
		return selectionDao.selectByIdAndClassId(stdid, classid);
	}
	
	public List<Class> findSeletionByStd(int stdid){
		List<Selection> selectlist=selectionDao.selectByStdId(stdid,1);
		List<Class> list=new ArrayList<>();
    	for(Selection s:selectlist) {
    		String str=s.getClassid();
    		Class c=classDao.selectClassById(str);
    		list.add(c);
    	}
		return list;
	}
	
	public List<SelectionShow> findSeletionByClassId(String classid,int status){
		List<SelectionShow> list=selectionDao.selectByClassId(classid,status);
		return list;
	}
	
	public List<SelectionShowII> findSeletionByClassIdII(String classid){
		List<SelectionShowII> list=selectionDao.selectByClassIdII(classid);
		return list;
	}
	
	public void updateSelectionStatus(int stdid,String classid,int status) {
		selectionDao.updateStatus(stdid, classid, status);
	}
	
	public void deleteSelection(int stdid,String classid) {
		selectionDao.deleteById(stdid, classid);
	}

	public List<Class> findClassBest(int stdid,String year,String nextyear){
		List<Class> bestlist=new ArrayList<>();//最优课程集
		List<Class> listI=new ArrayList<>();
		List<Class> listII=new ArrayList<>();
		List<Class> listIII=new ArrayList<>();
		List<Class> pointlist=classService.IntelligentFindClass(stdid);//符合学分条件的课程集
    	List<Selection> selectlist=selectionDao.selectByStdId(stdid, 1);//已选课程集
    	List<ScoreShow> scorelist=scoreDao.selectByStdId(stdid);//已修课程集
    	String grade=stdMsgDao.selectByUserId(stdid).getGrade()+"必修";
    	int havepoint=stdMsgDao.selectByUserId(stdid).getSelectpoint();
    	
    	if(selectlist.isEmpty()) {
    		for(Class c:pointlist) {
    			listI.add(c);
    		}
    	}
    	else {
	    	for(Class c:pointlist) {
	    		boolean select=false;
	    		for(Selection s:selectlist) {
		    		String str=s.getClassid();
		    		Class sc=classDao.selectClassById(str);
	    			if(c.getClassid().equals(str)) {				//筛选符合集中未选修的课程
	    				select=true;
	    				break;}
	    			else if(!c.getYear().equals(sc.getYear()))	//同一学年不能在同一时间上课和考试
	    				continue;	
	    			else if(sc.getTime().equals(c.getTime())||sc.getTesttime().equals(c.getTesttime())) { 
	    				select=true;
	    				break;}
	    			else
	    				continue;
	    		}
	    		if(select==false)
	    			listI.add(c);
	    	}
    	}
    	
    	if(scorelist.isEmpty()) {
    		for(Class c:listI) {
    			listII.add(c);
    		}
    	}
    	else {
	    	for(Class c:listI) {
	    		boolean select=false;
	    		for(ScoreShow s:scorelist) {				//进一步筛选符合集中未曾修读完成的课程
		    		String str=s.getClassid();
		    		Class sc=classService.findClassById(str);
	    			if(sc.getClassname().equals(c.getClassname())) {
	    				select=true;
	    				break;}
	    		}
	    		if(select==false)
	    			listII.add(c);
	    	}
    	}
    	
    	for(Class c:listII) {                   	//筛选学分足够选择的课程
    		if(c.getPoint()<=havepoint)
    			listIII.add(c);
    	}
    	
    	List<Class> listIV=new ArrayList<>();
    	List<Class> listV=new ArrayList<>();
    	for(Class c:listIII) {                  	 //按课程优先度大致排列课程
    		if(c.getYear().equals(year)) {    		 //本年课程
	    		if(c.getMajor().equals(grade))		 //该课程为该生必修课程
	    			bestlist.add(c);
	    		else if(c.getMajor().equals("选修"))	 //选修课程入列
	    			listV.add(c);
	    		else
	    			listIV.add(c);					//其余年级课程列
	    	}
    	}
    	for(Class c:listV) {
    		bestlist.add(c);
    	}
    	for(Class c:listIV) {
    		bestlist.add(c);
    	}
    	return bestlist;
	}
}
