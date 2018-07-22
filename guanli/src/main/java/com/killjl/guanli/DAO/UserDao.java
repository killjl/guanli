package com.killjl.guanli.DAO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.killjl.guanli.model.User;

@Mapper

public interface UserDao {
	String TABLE_NAME=" user ";
	String INSERT_FIELDS=" name,password,salt,userid,pic ";
	String SELECT_FIELDS=" id,name,password,salt,userid,pic ";
	
	@Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
			") values (#{name},#{password},#{salt},#{userid},#{pic})"})
	int addUser(User user);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where userid=#{userid}"})
	User selectByUserId(int userid);
	
	@Update({"update",TABLE_NAME,"set password=#{password} where userid=#{userid}"})
	void updatePassword(@Param("userid")int userid,@Param("password") String password);
	
	@Update({"update",TABLE_NAME,"set name=#{name} where userid=#{userid}"})
	void updateName(@Param("userid")int userid,@Param("name") String name);
	
	@Update({"update",TABLE_NAME,"set pic=#{pic} where userid=#{userid}"})
	void updatePic(@Param("userid")int userid,@Param("pic") String pic);

}
