package com.killjl.guanli.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.killjl.guanli.model.Tcrmsg;

@Mapper

public interface TcrMsgDao {
	String TABLE_NAME=" tcrMsg ";
	String INSERT_FIELDS=" name,sex,college,phone,userid ";
	String SELECT_FIELDS=" id,name,sex,college,phone,userid ";
	
	@Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
			") values (#{name},#{sex},#{college},#{phone},#{userid})"})
	int addTcrMsg(Tcrmsg tcrMsg);
	
	List<Tcrmsg> selectTcrMsg(@Param("userid") int userid, @Param("name") String name, @Param("sex") String sex, @Param("college") String college, @Param("phone") int phone);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where userid=#{userid}"})
	Tcrmsg selectByUserId(int userid);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where name=#{name}"})
	Tcrmsg selectByName(String name);
	
	@Update({"update",TABLE_NAME,"set name=#{name},sex=#{sex},college=#{college},phone=#{phone} where userid=#{userid}"})
	int updateTcrMsg(@Param("userid") int userid, @Param("name") String name, @Param("sex") String sex, @Param("college") String college, @Param("phone") int phone);
	
}
