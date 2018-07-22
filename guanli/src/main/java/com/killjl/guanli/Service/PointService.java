package com.killjl.guanli.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.killjl.guanli.DAO.PointDao;
import com.killjl.guanli.model.Point;

@Service

public class PointService {
	
	@Autowired
	PointDao pointDao;
	
	public void addPoint(Point point) {
		pointDao.addPoint(point);
	}
	
	public Point findPoint(int stdid) {
		return pointDao.selectByStdId(stdid);
	}
	
	public void updatePoint(int stdid,String type,int sum,int score) {
		if(type.equals("必修"))
			pointDao.updatePoint(stdid, sum, score, 0, 0, 0, 0, 0, 0, 0);
		if(type.equals("专业选修"))
			pointDao.updatePoint(stdid, sum, 0, score, 0, 0, 0, 0, 0, 0);
		if(type.equals("艺术素养"))
			pointDao.updatePoint(stdid, sum, 0, 0, score, 0, 0, 0, 0, 0);
		if(type.equals("经管法类"))
			pointDao.updatePoint(stdid, sum, 0, 0, 0, score, 0, 0, 0, 0);
		if(type.equals("文史哲类"))
			pointDao.updatePoint(stdid, sum, 0, 0, 0, 0, score, 0, 0, 0);
		if(type.equals("语言类"))
			pointDao.updatePoint(stdid, sum, 0, 0, 0, 0, 0, score, 0, 0);
		if(type.equals("体育类"))
			pointDao.updatePoint(stdid, sum, 0, 0, 0, 0, 0, 0, score, 0);
		if(type.equals("创新类"))
			pointDao.updatePoint(stdid, sum, 0, 0, 0, 0, 0, 0, 0 , score);	
	}

}
