package com.example.demo.controller;


import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Format;




@RestController
@RequestMapping("/test")
public class HelloWorldController {
	
	Format oo = new Format();
    
    @RequestMapping("/get3")
    public void uploadFile(@RequestBody(required=false)String reqContent) throws Exception{
    	oo.open(reqContent);
		oo.addFixItem("emstimateNo", 6, 85, 8, "String");
		oo.addFixItem("accountNo", 6, 102, 8, "String");
		oo.addFixItem("nameAndAddress", 7, 1, 40, "String");
		oo.addFixItem("nameAndAddress", 8, 1, 40, "String");
		oo.addFixItem("nameAndAddress", 9, 1, 40, "String");
		oo.addFixItem("nameAndAddress", 10, 1, 40, "String");
		oo.addFixItem("nameAndAddress", 11, 1, 40, "String");
		oo.addFixItem("jobNo", 9, 61, 6, "String");
		oo.addFixItem("wipNo", 9, 83, 5, "String");
		oo.addFixItem("regNo", 15, 1, 20, "String");
		oo.addFixItem("chassisNo", 15, 28, 20, "String");
		oo.addBodyItem("no", 3, 3, "String");
		oo.addBodyItem("code", 9, 20, "String");
		oo.addBodyItem("descripion", 36, 24, "String");
		oo.addBodyItem("unitPrace", 89, 14, "String");
		oo.addBodyItem("qty", 104, 14, "String");
		oo.addBodyItem("disc", 120, 6, "String");
		oo.addBodyItem("amount", 129, 24, "String");
		oo.addFixItem("lingjian", 48, 12, 14, "String");
		oo.addFixItem("gongshi", 49, 12, 14, "String");
		oo.addFixItem("taocan", 51, 12, 14, "String");
		oo.addFixItem("zongji", 52, 12, 14, "String");
		System.out.println(oo.getItemHeader("emstimateNo"));
		System.out.println(oo.getItemHeader("accountNo"));
		System.out.println(oo.getItemHeader("nameAndAddress"));
		System.out.println(oo.getItemHeader("jobNo"));
		System.out.println(oo.getItemHeader("wipNo"));
		System.out.println(oo.getItemHeader("regNo"));
		System.out.println(oo.getItemHeader("chassisNo"));
		System.out.println(oo.getBodyRow(12).toString());
		System.out.println(oo.getBodyItem("descripion"));
		System.out.println(oo.getItemFooter("lingjian"));
		System.out.println(oo.getItemFooter("gongshi"));
		System.out.println(oo.getItemFooter("taocan"));
		System.out.println(oo.getItemFooter("zongji"));
		
		
		//System.out.println(bytes2hex01(md5("中国 ")));
		oo.close();
	}
     @RequestMapping("/get3/{value1}")
    public String getFixItem(@PathParam("value1") String itemName) throws Exception {
    	return oo.getItemHeader(itemName);
    }
    @RequestMapping("/get3/{value2}")
    public String getItemFoot(@PathParam("value2") String itemName) throws Exception {
    	return oo.getItemFooter(itemName);
    }
    @RequestMapping("/get3/{value3}")
    public List<Object> getBodyRow(@PathParam("value3") int rownum) throws Exception {
    	return oo.getBodyRow(rownum);
    }
    @RequestMapping("/get3/{value4}")
    public List<Object> getBodyItem(@PathParam("value4") String itemName) throws Exception{
    	return oo.getBodyItem(itemName);
    }
    public void itemHInit() {
    	oo.addFixItem("", 0, 0, 0, "");
    	oo.addBodyItem("", 0, 0, "");
    	
    }
    @RequestMapping("/get")
    public void close() {
    	oo.close();
    }
    
    public void open2() {}
}
