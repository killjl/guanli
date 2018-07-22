package com.killjl.guanli.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.killjl.guanli.model.Stdmsg;

@Mapper

public interface StdMsgDao {
	String TABLE_NAME=" stdmsg ";
	String INSERT_FIELDS=" name,sex,grade,home,userid,college,major,point,phone,selectpoint ";
	String SELECT_FIELDS=" id,name,sex,grade,home,userid,college,major,point,phone,selectpoint ";
	
	@Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
			") values (#{name},#{sex},#{grade},#{home},"
			+ "#{userid},#{college},#{major},#{point},#{phone},#{selectpoint})"})
	int addStdMsg(Stdmsg stdMsg);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where userid=#{userid}"})
	Stdmsg selectByUserId(int userid);
	
	List<Stdmsg> selectStdMsg(@Param("userid") int userid,@Param("name") String name,@Param("grade") String grade,
			@Param("home") String home,@Param("major") String major,@Param("sex") String sex,
			@Param("phone") int phone,@Param("college") String college);
	
	@Update({"update",TABLE_NAME,"set sex=#{sex} , home=#{home} , grade=#{grade} , college=#{college} ,"
			+ "major=#{major} , phone=#{phone},name=#{name} where userid=#{userid}"})
	int updateMsg(@Param("userid") int userid,@Param("name") String name,@Param("grade") String grade,
			@Param("home") String home,@Param("major") String major,@Param("sex") String sex,
			@Param("phone") int phone,@Param("college") String college);

	@Update({"update",TABLE_NAME,"set selectpoint=#{selectpoint} where userid=#{userid}"})
	int updateSelectpoint(@Param("userid")int userid,@Param("selectpoint") int selectpoint);
}
