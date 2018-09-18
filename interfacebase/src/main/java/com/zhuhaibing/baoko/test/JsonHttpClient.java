package com.zhuhaibing.baoko.test;


import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;


public class JsonHttpClient {
	@Test
	public void test1() throws ClientProtocolException, IOException {
		String result;
		HttpGet get=new HttpGet("http://www.baidu.com");
		//用来执行get请求
		HttpClient client=new DefaultHttpClient();
		HttpResponse response=client.execute(get);
		//得到响应结果包含头和体
		result=EntityUtils.toString(response.getEntity(),"utf-8");
		System.out.println(result);
	}
}
