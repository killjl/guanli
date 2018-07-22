package com.killjl.guanli.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.killjl.guanli.model.Score;
import com.killjl.guanli.model.ScoreShow;

@Mapper

public interface ScoreDao {
	String TABLE_NAME=" score ";
	String INSERT_FIELDS=" stdid,tcrid,classid,score,status ";
	String SELECT_FIELDS=" id,stdid,tcrid,classid,score,status ";
	
	@Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
		") values (#{stdid},#{tcrid},#{classid},#{score},#{status})"})
	int addScore(Score score);
	
	@Select({"select score.*,class.classname,class.department,class.point,class.tcrname,class.year,class.major from ",TABLE_NAME," join class on class.classid=score.classid where score.stdid=#{stdid} "})
	List<ScoreShow> selectByStdId(@Param("stdid") int stdid);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where stdid=#{stdid} and classid=#{classid} "})
	Score selectScore(@Param("stdid") int stdid,@Param("classid") String classid);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where classid=#{classid} "})
	List<Score> selectByClassId(@Param("classid") String classid);
	
	@Update({"update",TABLE_NAME,"set score=#{score} where stdid=#{stdid} and classid=#{classid} "})
	int updateScore(@Param("stdid") int stdid, @Param("classid") String classid, @Param("score") int score);
	
	@Update({"update",TABLE_NAME,"set status=#{status} where stdid=#{stdid} and classid=#{classid} "})
	int updateStatus(@Param("stdid") int stdid, @Param("classid") String classid, @Param("status") String status);

}
