package com.killjl.guanli.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.killjl.guanli.model.Selection;
import com.killjl.guanli.model.SelectionShow;
import com.killjl.guanli.model.SelectionShowII;

@Mapper

public interface SelectionDao {
	String TABLE_NAME=" selection ";
	String INSERT_FIELDS=" classid,stdid,status ";
	String SELECT_FIELDS=" id,classid,stdid,status ";
	
	@Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,") values (#{classid},#{stdid},#{status})"})
	int addSelection(Selection selection);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where stdid=#{stdid} and status =#{status} order by id desc "})
	List<Selection> selectByStdId(@Param("stdid") int stdid, @Param("status") int status);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where stdid=#{stdid} and classid=#{classid} order by id desc "})
	Selection selectByIdAndClassId(@Param("stdid") int stdid,@Param("classid") String classid);
	
	@Select({"select selection.*,stdmsg.name,class.classname,class.year from",TABLE_NAME," join stdmsg on selection.stdid=stdmsg.userid "
			+ " join class on class.classid=selection.classid where selection.classid=#{classid} and selection.status=#{status} order by id "})
	List<SelectionShow> selectByClassId(@Param("classid") String classid,@Param("status") int status);
	
	@Select({"select selection.classid,selection.stdid,stdmsg.name,class.classname,class.year,score.score,score.status from",TABLE_NAME," join stdmsg on selection.stdid=stdmsg.userid "
			+ " join class on class.classid=selection.classid join score on class.classid=score.classid and stdmsg.userid=score.stdid where selection.classid=#{classid} "})
	List<SelectionShowII> selectByClassIdII(@Param("classid") String classid);
	
	
	@Update({"update",TABLE_NAME,"set status=#{status} where stdid=#{stdid} and classid=#{classid} "})
	int updateStatus(@Param("stdid") int stdid, @Param("classid") String classid, @Param("status") int status);

	@Delete({"delete from",TABLE_NAME,"where classid=#{classid} and stdid=#{stdid}"})
	int deleteById(@Param("stdid") int stdid, @Param("classid") String classid);
}
