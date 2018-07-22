package com.killjl.guanli.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.killjl.guanli.model.Class;

@Mapper

public interface ClassDao {
	String TABLE_NAME=" class ";
	String INSERT_FIELDS=" classname,department,prefercount,point,tcrname,time,place,year,major,testtime,classid,status ";
	String SELECT_FIELDS=" id,classname,department,prefercount,point,tcrname,time,place,year,major,testtime,classid,status ";
	
	@Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
		") values (#{classname},#{department},#{prefercount},#{point},"
		+ "#{tcrname},#{time},#{place},#{year},#{major},#{testtime},#{classid},#{status})"})
	int addClass(Class clazz);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where classid=#{classid} "})
	Class selectClassById(String classid);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where classname=#{classname} and year=#{year}"})
	Class selectClassByYear(@Param("classname") String classname,@Param("year") String year);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where department=#{department} "})
	List<Class> selectClassByDepartment(String department);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where classname=#{classname} and tcrname=#{tcrname} and status=#{status}"})
	Class selectClassByName(@Param("classname") String classname,@Param("tcrname") String tcrname,@Param("status") int status);
	
	List<Class> selectClass(@Param("classname") String classname,@Param("department") String department,
			@Param("point") int point,@Param("tcrname") String tcrname,@Param("time") String time,
			@Param("place") String place,@Param("year") String year,@Param("major") String major,@Param("classid") String classid,
			@Param("testtime") String testtime,@Param("status") int status);
	
	@Update({"update",TABLE_NAME,"set department=#{department} "
			+ ", point=#{point} , tcrname=#{tcrname} , time=#{time} , place=#{place} "
			+ ", year=#{year} , major=#{major} , testtime=#{testtime} where classid=#{classid}"})
	int updateClassById(@Param("classid") String classid,@Param("department") String department,
			@Param("point") int point,@Param("tcrname") String tcrname,@Param("time") String time,
			@Param("place") String place,@Param("year") String year,@Param("major") String major,@Param("testtime") String testtime);

	@Update({"update",TABLE_NAME,"set status=#{status} where classid=#{classid}"})
	int updateStatus(@Param("classid") String classid,@Param("status") int status);
}
