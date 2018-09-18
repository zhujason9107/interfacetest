package com.zhuhaibing.baoko.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ProUtil {
	private Properties prop;
	private String filePath;
	/**
	 * 构造方法
	 * */
	public ProUtil(String filePath){
		this.filePath = filePath;
		this.prop = readProperties();
	}
	
	/**
	 * 读取配置文件
	 * */
	private Properties readProperties(){
		Properties properties = new Properties();
		try {
//			InputStream inputStream = new FileInputStream(filePath);
//			BufferedInputStream in = new BufferedInputStream(inputStream);
//			properties.load(in);
			
			InputStream inputStream=new FileInputStream(filePath);

			InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"UTF-8");
			
			properties.load(inputStreamReader);

			inputStream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
	
	/**
	 * 根据key读取关键字内容
	 * */
	public String  getPro(String key){
		if(prop.containsKey(key)){
			String value = prop.getProperty(key);
			return value;
		}else{
			System.out.println("获取到的key:"+key+",你获取key值不对");
			return "";
		}	
	}
	/**
	 * 写入内容
	 * */
	public void writePro(String key,String value){
		Properties pro = new Properties();
			try {
				FileOutputStream file = new FileOutputStream(filePath);
				pro.setProperty(key, value);
				pro.store(file, key);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
