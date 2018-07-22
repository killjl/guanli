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
import com.killjl.guanli.model.Tcrmsg;
import com.killjl.guanli.DAO.*;

@Service

public class TcrMsgService {
	@Autowired
	TcrMsgDao tcrMsgDao;
	
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
	
	public void addTcrMsg(Tcrmsg tcrMsg) {
		tcrMsgDao.addTcrMsg(tcrMsg);
	}
	
	public Tcrmsg getTcrMsgById(int userid) {
		return tcrMsgDao.selectByUserId(userid);
	}
	
	public Tcrmsg updateTcrMsg(int userid ,String name,String sex,String college,int phone){
		tcrMsgDao.updateTcrMsg(userid, name, sex, college, phone);
		return tcrMsgDao.selectByUserId(userid);
	}
	
	public List<Tcrmsg> getTcrMsg(int userid,String name,String sex,int phone,String college) {
		if(userid==0&&name.equals("")&&sex.equals("")&&phone==0&&college.equals(""))
			return tcrMsgDao.selectTcrMsg(userid, name, sex, college, phone);
		else {
			if(name.equals(""))
				name=null;
			if(sex.equals(""))
				sex=null;
			if(college.equals(""))
				college=null;
		}
		return tcrMsgDao.selectTcrMsg(userid, name, sex, college, phone);
	}
}
