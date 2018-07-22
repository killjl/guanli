package com.killjl.guanli.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.killjl.guanli.DAO.TokenDao;
import com.killjl.guanli.model.Token;

@Service

public class TokenService {
	@Autowired
	TokenDao tokenDao;
	
	public Token findToken(String token) {
		return tokenDao.selectByToken(token);
	}

}
