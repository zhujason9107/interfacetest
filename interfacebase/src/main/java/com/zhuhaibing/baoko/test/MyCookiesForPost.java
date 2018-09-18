package com.zhuhaibing.baoko.test;


import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import junit.framework.Assert;



public class MyCookiesForPost {
	private String url;
	private ResourceBundle  bundle;
	private CookieStore store;
	
	@BeforeTest
	public void beforeTest(){
		bundle=ResourceBundle.getBundle("application",Locale.CHINA);
		this.url=bundle.getString("test.url");
		System.out.println("value:"+url);
	}
	
	@Test
	public void testGetCookies() throws Exception {
		String result;
		String uri=bundle.getString("getCookies.uri");
		String testUrl=this.url+uri;
		
		//测试逻辑代码
		HttpGet get=new HttpGet(testUrl);
		DefaultHttpClient client=new DefaultHttpClient();
		HttpResponse response=client.execute(get);
		
		this.store=client.getCookieStore();
		List<Cookie> cookieList = store.getCookies();
		System.out.println("cookieList--->>>>>>>>>:"+cookieList.size());
		
//		for(Cookie cookie:cookieList) {
//			
//			String name = cookie.getName();
//			String value = cookie.getValue();
//			System.out.println("name:"+name+"-----------value:"+value);
//		}	
		
		/*jdk1.8新特性*/
		cookieList.forEach( cookie->{
				System.out.println("name:"+cookie.getName()+"------value:"+cookie.getValue());
			}	
		);
		
		
		result=EntityUtils.toString(response.getEntity(),"utf-8");
		System.out.println("result---->"+result);
		
	}
	
	@Test(dependsOnMethods= {"testGetCookies"})
	public void testPostMethod() throws Exception {
		String uri=bundle.getString("test.post.with.cookies");
		//拼接 url
		String testUrl=this.url+uri;
		//声明client对象，用来进行方法的执行
		DefaultHttpClient client=new DefaultHttpClient();
		//声明一个post请求方法，
		HttpPost post=new HttpPost(testUrl);
		System.out.println("testURL是："+testUrl);
		
		//添加参数 以json格式
		JSONObject param=new JSONObject();
		param.put("name", "zhangsan");
		param.put("age", "18");
	System.out.println("JSON格式:"+param.toString());
		
		//设置请求头信息,设置header
		post.setHeader("Content-type", "application/json");//设置以json格式传输
		
		//将参数信息添加到body  post
		StringEntity entity=new StringEntity(param.toString(), "utf-8");
		System.out.println("entity是:"+entity.toString());
		post.setEntity(entity);
		
		//声明一个对象来进行响应结果的存储
		String result;
		//设置cookies信息
		client.setCookieStore(this.store);
		//执行post方法
		HttpResponse response=client.execute(post);
		System.out.println("我不信:"+response.getEntity());
		//获取响应结果		
		result=EntityUtils.toString(response.getEntity(),"utf-8");
		System.out.println("result结果是----》》》》》:"+result.length());
		//处理结果，添加断言 判断返回结果是否符合预期结果
		//将返回的响应结果字符串转化成为json对象
		JSONObject resultJson=new JSONObject(result);
		String success=resultJson.getString("name");
		String status=resultJson.getString("status");
		//具体的判断返回结果的值
		Assert.assertEquals("success", success);
		Assert.assertEquals("1", status);
	}
}
