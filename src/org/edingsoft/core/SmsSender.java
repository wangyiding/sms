package org.edingsoft.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Scanner;

import org.springframework.util.StringUtils;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class SmsSender {
		public static String SMSCOMPORT;
		public static int PORTRATE;
		
		static{
			Properties properties=new Properties();
			try {
				properties.load(SmsSender.class.getClassLoader().getResourceAsStream("portConfig.properties"));
			} catch (IOException e) {
				System.err.println("初始化端口失败");
				e.printStackTrace();
			}
			SMSCOMPORT=properties.getProperty("SMSCOMPORT");
			PORTRATE=Integer.parseInt(properties.getProperty("PORTRATE"));
		}
		
		
		public static void sendSms(String phone,String content) throws Exception{
				System.out.println("start!");
				CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("COM5");
	            CommPort commPort = portIdentifier.open("COM5", 2000);
	            try{
	            	SerialPort serialPort = (SerialPort) commPort;
		            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE); 
		            OutputStream out = serialPort.getOutputStream();
		            InputStream ins = serialPort.getInputStream();
		            out.write("AT+CPIN?".getBytes());
		            out.write(0x0D);
		            out.flush();
		            Thread.sleep(500);
		        
		           if(!checkResult(ins)){ 
		        	    throw new Exception("短信卡未就绪");
		           }
		           out.write("AT+CMGF=1".getBytes());
		            out.write(0x0D);
		            out.flush();
		           Thread.sleep(500);
		           if(!checkResult(ins)){ 
		        	    throw new Exception("设置文本模式失败");
		           }
		           out.write("AT+CSMP=17,167,1,8".getBytes());
		            out.write(0x0D);
		            out.flush();
		           Thread.sleep(500);
		           if(!checkResult(ins)){ 
		        	    throw new Exception("设置中英文失败");
		           }
		           
		           out.write("AT+CSCS=\"UCS2\"".getBytes());
		            out.write(0x0D);
		            out.flush();
		           Thread.sleep(500);
		           if(!checkResult(ins)){ 
		        	    throw new Exception("设置为 UCS2 字符集编码失败");
		           }
		           
		           //中文信息转换
		           String phoneunic=stringToUnicodeforphone(phone);
		           out.write(("AT+CMGS=\""+phoneunic+"\" ").getBytes());
		           out.write(0x0D);
		           out.flush();
		           Thread.sleep(500);
		           
		           String contentunic=string2Unicodeforcontent(content);
		           out.write(contentunic.getBytes());
		           Thread.sleep(500);
		           out.write(0x0D);
		           Thread.sleep(500);
		           out.write(0x1A);
		           out.flush();
		           checkResult(ins);
		           System.out.println("gg");
		           System.out.println("finish");;
	            }catch(Exception e){
	            	e.printStackTrace();
	            }finally{
	            	commPort.close();
	            }
		}

		private static boolean checkResult(InputStream ins) {
				boolean flag=false;
				Scanner scanner=new Scanner(ins);
//	           List echoStrList=new ArrayList<String>();
	           while( scanner.hasNextLine()){
	        	   String  echostr=scanner.nextLine();
	        	   if(!StringUtils.isEmpty(echostr)){
//	        		   echoStrList.add(echostr);
	        		   System.out.println(echostr);
	        		   if("OK".equalsIgnoreCase(echostr)){
	        			   return true;
	        		   }
	        	   }
	           }
	           return false;
		}
		
		
		public static String stringToUnicodeforphone(String s) {  
		    try {  
		        StringBuffer out = new StringBuffer("");  
		        //直接获取字符串的unicode二进制  
		        byte[] bytes = s.getBytes("unicode");  
		        //然后将其byte转换成对应的16进制表示即可  
		        for (int i = 0; i < bytes.length - 1; i += 2) {  
		            out.append("\\u");  
		            String str = Integer.toHexString(bytes[i + 1] & 0xff);  
		            for (int j = str.length(); j < 2; j++) {  
		                out.append("0");  
		            }  
		            String str1 = Integer.toHexString(bytes[i] & 0xff);  
		            out.append(str1);  
		            out.append(str);  
		        }  
		        return out.toString().toUpperCase().replaceFirst("FEFF", "").replaceFirst("\\\\U", "").replaceAll("\\\\U", "0");
		    } catch (UnsupportedEncodingException e) {  
		        e.printStackTrace();  
		        return null;  
		    }  
		}  
		
		public static String string2Unicodeforcontent(String string) {
			 
		    StringBuffer unicode = new StringBuffer();
		 
		    for (int i = 0; i < string.length(); i++) {
		 
		        // 取出每一个字符
		        char c = string.charAt(i);
		 
		        // 转换为unicode
		        unicode.append(Integer.toHexString(c));
		    }
		 
		    return unicode.toString();
		}
		
		
		public static void main(String[] args) throws Exception {
			//8fd8662f4e0d884c
			sendSms("15869179854","短信测试！！！！！");
		}
}
