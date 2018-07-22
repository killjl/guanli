package com.killjl.guanli.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.killjl.guanli.DAO.*;
import com.killjl.guanli.model.Score;
import com.killjl.guanli.model.ScoreShow;

@Service

public class ScoreService {
	@Autowired
	ScoreDao scoreDao;
	
	public void addScore(Score score) {
		scoreDao.addScore(score);
	}
	
	public List<ScoreShow> selectScoreByStd(int stdid){
		List<ScoreShow> list=scoreDao.selectByStdId(stdid);
		return list;
	}
	
	public Score selectScore(int stdid,String classid){
		Score score=scoreDao.selectScore(stdid,classid);
		return score;
	}
	
	public List<Score> selectScoreByClassName(String classid){
		List<Score> list=scoreDao.selectByClassId(classid);
		return list;
	}
	
	public void updateScore(int stdid, String classid, int score) {
		scoreDao.updateScore(stdid, classid, score);	
	}
	
	public void updateStatus(int stdid, String classid, int score) {
		if(score>=60)
			scoreDao.updateStatus(stdid, classid, "通过");
		else
			scoreDao.updateStatus(stdid, classid, "未通过");
	}

}
