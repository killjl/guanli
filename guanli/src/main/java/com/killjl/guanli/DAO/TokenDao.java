package com.killjl.guanli.DAO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.killjl.guanli.model.Token;

@Mapper

public interface TokenDao {
	String TABLE_NAME=" token ";
	String INSERT_FIELDS=" userid,status,delay,token ";
	String SELECT_FIELDS=" id,userid,status,delay,token ";
	
	@Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
			") values (#{userid},#{status},#{delay},#{token})"})
	int addToken(Token token);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where userid=#{userid}"})
	Token selectById(int userid);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where token=#{token}"})
	Token selectByToken(String token);	
	
	@Update({"update",TABLE_NAME,"set status=#{status} where token=#{token}"})
	void updateStatus(@Param("token") String token,@Param("status") int status);

}
