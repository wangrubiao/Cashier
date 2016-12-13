package test;
import gnu.io.CommPortIdentifier;
import java.util.Enumeration;

public class Test {
	static String money = "13.50元";
	static String zmoney = "103.00元";
	static String shop = "苹 果";
	static String kg = "0.54kg";
	public static void main(String[] args) throws InterruptedException {
		CommUtil port = new CommUtil();
		boolean result = port.startComPort("COM8");
		//System.out.println(result);
		if(result)
		{
			int i = 0;
			while(i<5)
			{
				Thread.sleep(1000);    //设置线程等待时间
				port.send(toStringHex("0C09090909090909"));  //传16进制数据
				port.send("商 品:"+shop); 	  //设置显示的内容
				port.send(toStringHex("1b5b4c0a"));  //设置游标左小角并下移
				port.send("------------------------------"); //设置显示的内容
				port.send(toStringHex("1F420A0A0909"));  //设置游标左小角并下移
				port.send("单 价:"+money); 
				port.send(toStringHex("0909"));  //传16进制数据
				port.send("重  量:"+kg);		  //设置显示的内容
				port.send(toStringHex("1F420A0A0A"));  //设置游标左小角并下移
				port.send(toStringHex("0909"));  //传16进制数据
				port.send("总 价:"+zmoney); 			  //设置显示的内容
				i++;
			}
			port.ClosePort();	
		}else{
			System.out.println("串口无法连接...");
		}
	}
	 public static String str2HexStr(String str) {  
	        char[] chars = "0123456789ABCDEF".toCharArray();  
	        StringBuilder sb = new StringBuilder("");
	        byte[] bs = str.getBytes();  
	        int bit;  
	        for (int i = 0; i < bs.length; i++) {  
	            bit = (bs[i] & 0x0f0) >> 4;  
	            sb.append(chars[bit]);  
	            bit = bs[i] & 0x0f;  
	            sb.append(chars[bit]);  
	        }  
	        return sb.toString();  
	    }
	 public static String toStringHex(String s) 
	 { 
		 byte[] baKeyword = new byte[s.length()/2]; 
		 for(int i = 0; i < baKeyword.length; i++) 
		 { 
			 try 
			 { 
				 baKeyword[i] = (byte)(0xff & Integer.parseInt(s.substring(i*2, i*2+2),16)); 
			 }catch(Exception e){ 
				 e.printStackTrace(); 
			 } 
		 } 
		 try 
		 { 
			 s = new String(baKeyword, "utf-8");//UTF-16le:Not 
		 } catch (Exception e1){ 
			 e1.printStackTrace(); 
		 } 
		 return s; 
	 } 

}
