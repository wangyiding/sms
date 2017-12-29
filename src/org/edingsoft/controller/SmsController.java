package org.edingsoft.controller;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.edingsoft.core.SmsSender;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

@Controller
public class SmsController {
	 String COMPORT=null;
		
	@RequestMapping("list_ports")
	public @ResponseBody Map listPorts (@RequestBody Map inData){
		Map retMap=new HashMap();
		 ArrayList<String> portNameList = new ArrayList<>();
		try{
			Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();    
	        while (portList.hasMoreElements()) {
	            String portName = portList.nextElement().getName();
	            portNameList.add(portName);
	        }
		}catch (Exception e) {
				retMap.put("ret_code", "500");
				retMap.put("ret_msg", e.getMessage());
				return retMap;
		}
		
		
        retMap.put("info", portNameList);
		retMap.put("ret_code", "000");
		retMap.put("ret_msg", "ok");
		return retMap;
	}
	
	
	@RequestMapping("send_sms")
	public @ResponseBody Map sendSms (@RequestBody Map inData){
		Map retMap=new HashMap();
		//参数处理极端
		String phone=null;
		String content=null;
		try{
			phone=(String) inData.get("phone");
			content=(String)inData.get("content");
			if("".equals(content)){
				throw new Exception("短信内容为空");
			}
			if(content.length()>60){
				throw new Exception("短信内容不可超过60个字");
			}
			if(StringUtils.isEmpty(phone)){
				throw new Exception("手机号为空!");
			}
			SmsSender.sendSms(phone, content);
		}catch (Exception e) {
				retMap.put("ret_code", "500");
				retMap.put("ret_msg", "参数错误:"+ e.getMessage());
				return retMap;
		}
		
		//短信发送
		
		
		retMap.put("ret_code", "000");
		retMap.put("ret_msg", "ok");
		return retMap;
	}
}
