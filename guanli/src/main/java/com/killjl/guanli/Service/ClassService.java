package com.killjl.guanli.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.killjl.guanli.DAO.*;
import com.killjl.guanli.guanliUtil.GuanliUtil;
import com.killjl.guanli.model.Class;
import com.killjl.guanli.model.Point;

@Service

public class ClassService {
	@Autowired
	ClassDao classDao;
	
	@Autowired
	SelectionService selectionService;
	
	@Autowired
	PointDao pointDao;
	
	@Autowired
	StdMsgDao stdMsgDao;
	
	public void addClass(Class clazz) {
		classDao.addClass(clazz);
	}
	
	public Class findClassByName(String classname,String tcrname,int status) {
		return classDao.selectClassByName(classname, tcrname, status);
	}
	
	public Class findClassByYear(String classname,String year) {
		return classDao.selectClassByYear(classname, year);
	}
	
	public Class findClassById(String classid) {
		return classDao.selectClassById(classid);
	}
	
	public List<Class> findClass(String classid, String classname, String department, int point, String tcrname, String time, String place, String year, String major,String testtime,int status) {
			if(classname.equals(""))
				classname=null;
			if(department.equals(""))
				department=null;
			if(tcrname.equals(""))
				tcrname=null;
			if(time.equals(""))
				time=null;
			if(place.equals(""))
				place=null;
			if(year.equals(""))
				year=null;
			if(major.equals(""))
				major=null;
			if(classid.equals(""))
				classid=null;
			if(testtime.equals(""))
				testtime=null;
		
		return classDao.selectClass(classname, department, point, tcrname, time, place, year, major, classid,testtime,status);
	}
	
	public Class updateClass(String classid, String department, int point, String tcrname, String time, String place, String year, String major, String testtime) {
		classDao.updateClassById(classid, department, point, tcrname, time, place, year, major, testtime);
		return classDao.selectClassById(classid);
	}
	
	public void updateClassStatus(String classid,int status) {
		classDao.updateStatus(classid, status);
	}

	public List<Class> IntelligentFindClass(int stdid){
		Point point=pointDao.selectByStdId(stdid);
		int majorp=point.getMajor();
		int minor=point.getMinor();
		int art=point.getArt();
		int eco=point.getEconomy();
		int lit=point.getLiterature();
		int lan=point.getLanguage();
		int sport=point.getSport();
		int ino=point.getInnovation();
		List<Class> clist=selectionService.findSeletionByStd(stdid);
		for(Class c:clist) {
			String de=c.getDepartment();
			if(de.equals("必修"))
				majorp=majorp+c.getPoint();
			else if(de.equals("专业选修"))
				minor=minor+c.getPoint();
			else if(de.equals("艺术素养"))
				art=art+c.getPoint();
			else if(de.equals("经管法类"))
				eco=eco+c.getPoint();
			else if(de.equals("文史哲类"))
				lit=lit+c.getPoint();
			else if(de.equals("语言类"))
				lan=lan+c.getPoint();
			else if(de.equals("体育类"))
				sport=sport+c.getPoint();
			else if(de.equals("创新类"))
				ino=ino+c.getPoint();
		}
		
		String major=stdMsgDao.selectByUserId(stdid).getMajor();
		List<Class> list=new ArrayList<>();
		if (major.equals("Electronic")) {
			if(majorp<GuanliUtil.StandardElectronic[1]) {
				List<Class> l=classDao.selectClassByDepartment("必修");
				for(Class clazz:l)
					list.add(clazz);
			}
			if(minor<GuanliUtil.StandardElectronic[2]){
				List<Class> l=classDao.selectClassByDepartment("专业选修");
				for(Class clazz:l)
					list.add(clazz);
			}
			if(art<GuanliUtil.StandardElectronic[3]) 
			{
				List<Class> l=classDao.selectClassByDepartment("艺术素养");
				for(Class clazz:l)
					list.add(clazz);
			}
			if(eco<GuanliUtil.StandardElectronic[4]) 
			{
				List<Class> l=classDao.selectClassByDepartment("经管法类");
				for(Class clazz:l)
					list.add(clazz);
			}
			if(lit<GuanliUtil.StandardElectronic[5]) 
			{
				List<Class> l=classDao.selectClassByDepartment("文史哲类");
				for(Class clazz:l)
					list.add(clazz);
			}
			if(lan<GuanliUtil.StandardElectronic[6]) 
			{
				List<Class> l=classDao.selectClassByDepartment("语言类");
				for(Class clazz:l)
					list.add(clazz);
			}
			if(sport<GuanliUtil.StandardElectronic[7]) 
			{
				List<Class> l=classDao.selectClassByDepartment("体育类");
				for(Class clazz:l)
					list.add(clazz);
			}
			if(ino<GuanliUtil.StandardElectronic[8]) 
			{
				List<Class> l=classDao.selectClassByDepartment("创新类");
				for(Class clazz:l)
					list.add(clazz);
			}
		}
		
		return list;
	}
}
