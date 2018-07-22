package com.killjl.guanli.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.killjl.guanli.model.Message;

@Mapper

public interface MessageDao {
	String TABLE_NAME=" message ";
	String INSERT_FIELDS=" fromid,toid,content,createdate,hasread,conversationid ";
	String SELECT_FIELDS=" id,fromid,toid,content,createdate,hasread,conversationid ";
	
	@Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
	") values (#{fromid},#{toid},#{content},#{createdate},#{hasread},#{conversationid})"})
	int addMessage(Message message);
	
	@Select({"select ", INSERT_FIELDS, " ,count(id) as id from ( select * from ", TABLE_NAME, " where fromid=#{userid} or toid=#{userid} order by id desc) tt group by conversationid order by id desc limit #{offset},#{limit}"})
    List<Message> getConversationList(@Param("userid") int userid, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from ", TABLE_NAME, " where hasread = 0 and toid=#{userid} and conversationid=#{conversationid}"})
    int getConversationUnReadCount(@Param("userid") int userid, @Param("conversationid") String conversationid);

    @Select({"select count(id) from ", TABLE_NAME, " where hasread = 0 and toid=#{userid}"})
    int getConversationTotalCount(@Param("userid") int userid, @Param("conversationid") String conversationid);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where conversationid=#{conversationid} order by id desc limit #{offset},#{limit}"})
    List<Message> getConversationDetail(@Param("conversationid") String conversationid, @Param("offset") int offset, @Param("limit") int limit);

}
