package com.killjl.guanli.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.killjl.guanli.guanliUtil.GuanliUtil;
import com.killjl.guanli.model.Stdmsg;

@Service

public class StdMsgService {
	@Autowired
	com.killjl.guanli.DAO.StdMsgDao stdMsgDao;
	
	public String uploadPic(MultipartFile file) throws IOException{
		int dotpos=file.getOriginalFilename().lastIndexOf(".");
		if (dotpos<0)
			return null;
		String dot=file.getOriginalFilename().substring(dotpos+1).toLowerCase();
		if(!GuanliUtil.isAllowed(dot))
			return null;
		
		String filename=UUID.randomUUID().toString().replaceAll("-", " ") + "." + dot;
		
		 Files.copy(file.getInputStream(), new File(GuanliUtil.IMAGE_DIR + filename).toPath(),
	                StandardCopyOption.REPLACE_EXISTING);
	        return GuanliUtil.DOMAIN + "image?name=" + filename;
	}
	
	public void addStdMsg(Stdmsg stdMsg) {
		stdMsgDao.addStdMsg(stdMsg);
	}

	public Stdmsg getStdMsgById(int userid) {
		return stdMsgDao.selectByUserId(userid);
	}
	
	
	public List<Stdmsg> getStdMsg(int userid,String name,String grade,String home,String major,String sex,int phone,String college) {
		if(userid==0&&name.equals("")&&grade.equals("")&&home.equals("")&&major.equals("")&&sex.equals("")&&phone==0&&college.equals(""))
			return stdMsgDao.selectStdMsg(userid, name, grade, home, major, sex, phone, college);
		else {
			if(name.equals(""))
				name=null;
			if(grade.equals(""))
				grade=null;
			if(home.equals(""))
				home=null;
			if(major.equals(""))
				major=null;
			if(sex.equals(""))
				sex=null;
			if(college.equals(""))
				college=null;
		}
		return stdMsgDao.selectStdMsg(userid, name, grade, home, major, sex, phone, college);
	}

	public Stdmsg updateMsg(int userid ,String college, String name,String grade,String home,String major,String sex,int phone){
		stdMsgDao.updateMsg(userid, name, grade, home, major, sex, phone, college);
		return stdMsgDao.selectByUserId(userid);
	}

	public void updateSelectPoint(int userid,int selectpoint){
		stdMsgDao.updateSelectpoint(userid, selectpoint);
	}
}

	