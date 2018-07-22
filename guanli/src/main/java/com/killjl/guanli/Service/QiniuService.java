package com.killjl.guanli.Service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.killjl.guanli.guanliUtil.GuanliUtil;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

@Service

public class QiniuService {
	String accessKey = "l_jBLQtwotYFOxgUmg18gQD5A7hNCBdjETEuWcEg";
	String secretKey = "2K8dErcqAq-bj3_KcQW7VfVrhnhTJ9V6jZmPMH22";
	String bucket = "guanli";
	Auth auth = Auth.create(accessKey, secretKey);
	Configuration cfg = new Configuration(Zone.zone2());
	//...其他参数参考类注释
	UploadManager uploadManager = new UploadManager(cfg);
	//...生成上传凭证，然后准备上传
	String upToken = auth.uploadToken(bucket);
	
	public String getUpToken() {
		return upToken;
	}
	
	public String upload(MultipartFile file) throws IOException{
		try {
			int dotpos=file.getOriginalFilename().lastIndexOf(".");
			if (dotpos<0)
				return null;
			String dot=file.getOriginalFilename().substring(dotpos+1).toLowerCase();
			if(!GuanliUtil.isAllowed(dot))
				return null;
			
			String filename=UUID.randomUUID().toString().replaceAll("-", " ") + "." + dot;
			
			Response res=uploadManager.put(file.getBytes(), filename, getUpToken());
			
			if(res.isJson()&&res.isOK()) {
				String key=JSONObject.parseObject(res.bodyString()).get("key").toString();
				return GuanliUtil.QINIU_DOMAIN + key;
			}
			else
				return null;
		} catch (Exception e) {
			return null;
		}
	}

}
