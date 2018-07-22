package com.killjl.guanli.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.killjl.guanli.DAO.MessageDao;
import com.killjl.guanli.model.Message;

@Service

public class MessageService {
	 @Autowired
	    private MessageDao messageDao;

	    public int addMessage(Message message) {
	        return messageDao.addMessage(message);
	    }

	    public List<Message> getConversationList(int userid, int offset, int limit) {
	        return messageDao.getConversationList(userid, offset, limit);
	    }

	    public List<Message> getConversationDetail(String conversationid, int offset, int limit) {
	        return messageDao.getConversationDetail(conversationid, offset, limit);
	    }

	    public int getUnreadCount(int userid, String conversationid) {
	        return messageDao.getConversationUnReadCount(userid, conversationid);
	    }
	    
	    public int getConversationTotalCount(int userid,String conversationid) {
	    	return messageDao.getConversationTotalCount(userid, conversationid);
	    }

}
