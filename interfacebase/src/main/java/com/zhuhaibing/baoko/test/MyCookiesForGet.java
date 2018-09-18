package com.zhuhaibing.baoko.test;


import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.http.HttpResponse;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;




public class MyCookiesForGet {
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
		for(Cookie cookie:cookieList) {
			
			String name = cookie.getName();
			String value = cookie.getValue();
			System.out.println("name:"+name+"-----------value:"+value);
		}	
		
		result=EntityUtils.toString(response.getEntity(),"utf-8");
		System.out.println("result---->"+result);
		
	}
	
	@Test(dependsOnMethods= {"testGetCookies"})
	public void testGetWithCookies() throws Exception {
		String uri=bundle.getString("test.get.with.cookies");
		String testUrl=this.url+uri;
		System.out.println("testUrl===========:"+testUrl);
		HttpGet get=new HttpGet(testUrl);
		DefaultHttpClient client=new DefaultHttpClient();
		
		client.setCookieStore(this.store);
		HttpResponse response=client.execute(get);
		int statusCode=response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		if(statusCode==200) {
			
			String result=EntityUtils.toString(response.getEntity(),"utf-8");
			System.out.println("result---->"+result);
		}

	}
}
