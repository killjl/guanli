package com.killjl.guanli;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.killjl.guanli.DAO.*;
import com.killjl.guanli.Service.*;
import com.killjl.guanli.model.*;
import com.killjl.guanli.model.Class;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuanliApplicationTests {
	 	@Autowired
	    UserDao userDao;
	 	
	 	@Autowired
	    PointDao pointDao;

	    @Autowired
	    TokenDao tokenDao;

	    @Autowired
	    StdMsgDao stdMsgDao;
	    
	    @Autowired
	    ClassDao classDao;
	    
	    @Autowired
	    UserService userService;
	    
	    @Autowired
	    ClassService classService;
	    
	    @Autowired
	    SelectionDao selectionDao;
	    
	    @Autowired
	    SelectionService selectionService;
	    
	    @Autowired
	    ScoreDao scoreDao;

	    @Test
	    public void initData() {
	    	//stdMsgDao.updateMsg(2014052505, "name", "grade", "home", "major", "sex", 0, "college");
	    	 /* for (int i = 10; i < 11; ++i) {
            User user = new User();
            user.setName(String.format("USER%d", i));
            user.setUserid(i);
            user.setPic("");
            user.setPassword("");
            user.setSalt("");
            userDao.addUser(user);

            userDao.updatePassword(i, "newpassword");

            Token token = new Token();
            token.setStatus(0);
            token.setUserid(i);
            Date date=new Date();
            token.setDelay(date);
            token.setToken(String.format("Token%d", i));
            tokenDao.addToken(token);
            
            String tokennum=tokenDao.selectById(i).getToken();
            tokenDao.updateStatus(tokennum, 1);

        }
        
        for (int i = 10; i < 11; ++i) {
            Class clazz = new Class();
            clazz.setClassname(String.format("Class%d", i));
            clazz.setDepartment("Electronic");
            clazz.setMajor("major");
            clazz.setPlace("toilet");
            clazz.setPoint(1);
            clazz.setPrefercount(2);
            clazz.setTcrname("yuanyida");
            clazz.setTesttime("do not test");
            clazz.setTime("everyday");
            clazz.setYear("maybe 8012");

            classDao.addClass(clazz);
	 }
       */
	        //userService.reg(2014052502, "11111111");
	        //classDao.selectClassById(id);
	        //classDao.selectClass(0, "","", "", "", "", "", "", "", "", "")
	    	
	    	//List<Class> list=classDao.selectClass("miku", null, 0, null, null, null, null, null,  null,  null,0);
	    	/* List<Class> list=classService.findClass("", "", "", 0, "张明亮","", "", "", "","", 0);
	    	for(Class clazz: list) {
	    		System.out.println(clazz.getClassname());
	    	}*/
	    	//Class clazz=classDao.selectClassByName("miku", "asfas", 1);
	    	/*String classid="asd";
	    	List<SelectionShowII> list=selectionDao.selectByClassIdII(classid);
	    	for(SelectionShowII selection:list)
	    		System.out.println(selection.getStdid());
	    		/*Score scorept=new Score();
	    		scorept.setClassid("classid");
		        scorept.setScore(1);
		        scorept.setStatus("1");
		        scorept.setStdid(1211);
		        scorept.setTcrid(32);
		        scoreDao.addScore(scorept);*/
	    	//}
	    	/*String classid="asd";
	    	int stdid=2014052502;
	    	String s="s";
	    	scoreDao.updateStatus(stdid, classid, s);*/
	    	
	    	/*List<Stdmsg> list=stdMsgDao.selectStdMsg(0, null, "大一", null, null, null, 0, null);
	    	for(Stdmsg clazz: list) 
	    	    System.out.println(clazz.getUserid());*/
	    	
	    	/*List<ScoreShow> list=scoreDao.selectByStdId(2014052502);
	    	for(ScoreShow clazz: list) 
	    	    System.out.println(clazz.getScore());*/
	        
	    	//pointDao.updatePoint(2014052502, 10, 0, 0, 0, 0, 0, 0, 2, 0);
	    	/*List<Class> list=classService.IntelligentFindClass(2014052504);
	    	for(Class clazz: list) {
	    		System.out.println(clazz.getClassname());
	    	}*/
	    	
	    	/*Class list=classService.findClassByName("gumi", "张明亮", 1);
	    		System.out.println(list.getClassname());*/
	    	
	    	//stdMsgDao.selectStdMsg(0, null, null, null, null, null, 0, null);
	    	
	    	//classDao.updateClassById("sadsd", "", 5, "", "", "", "", "", "");
	    
	    /*	List<Class> list=selectionService.findClassBest(2014052504, "2018-2019上", "2019-2020上");
	    	for(Class c:list)
	    		System.out.println(c.getClassname());*/
	    	
	    	classService.updateClass("sadsd", "必修", 1, "张明亮", "星期二下午", "图书馆", "2018-2019上", "选修", "星期二下午");
	    }
	   
}
	    
