/*
 * @串口实现类
 */
package test;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import javax.print.attribute.standard.SheetCollate;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

public class CommUtil extends IndexSwing implements SerialPortEventListener {

	InputStream inputStream; // 从串口来的输入流
	OutputStream outputStream;// 向串口输出的流
	SerialPort serialPort; // 串口的引用
	CommPortIdentifier portId;
	String weight;     //串口称重量
	/*
	 * 我的理解该函数是获取串口连接上
	 */
	public boolean startComPort(String name) {
		Enumeration portList = CommPortIdentifier.getPortIdentifiers(); //得到当前连接上的端口
		while (portList.hasMoreElements()) {
			CommPortIdentifier temp = (CommPortIdentifier) portList.nextElement();// 获取相应串口对象
			if (temp.getPortType() == CommPortIdentifier.PORT_SERIAL) {// 判断如果端口类型是串口
				// 判断如果传入串口存在，就打开该串口
				if (temp.getName().equals(name)) {
					portId = temp;
				}else{
					System.out.println("串口不存在");
				}
			}
			
		}
		try {
			// 打开串口名字为COM_4(名字任意),延迟为2毫秒
			serialPort = (SerialPort) portId.open("My"+name, 1000);
		} catch (PortInUseException e) {
			return false;
		}
		try {
			inputStream = serialPort.getInputStream();
			outputStream = serialPort.getOutputStream();
		} catch (IOException e) {
			return false;
		}
		try {
			serialPort.addEventListener(this); // 给当前串口天加一个监听器
		} catch (TooManyListenersException e) {
			return false;
		}
		serialPort.notifyOnDataAvailable(true); // 当有数据时通知
		try {
			serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, // 设置串口读写参数
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		} catch (UnsupportedCommOperationException e) {
			return false;
		}
		return true;
	}
	 /**
     * SerialPort EventListene  的方法,持续监听端口上是否有数据流
     */
	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		
		case SerialPortEvent.DATA_AVAILABLE:// 当有可用数据时读取数据,并且给串口返回数据
			byte[] readBuffer = new byte[20];
			try {
				while (inputStream.available() > 0) {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						//getWeight("电子秤关闭"); 
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.println(inputStream.available());
					int numBytes = inputStream.read(readBuffer);
					//System.out.println(numBytes);
				}
				 //System.out.println(new String(readBuffer).trim());
				 String val = new String(readBuffer).trim();
				 if(val.equals(weight)){
					 //System.out.println("重量与上次一致");//重量一致时不作处理
				 }else{
					weight = val;
					getWeight(val); 
				 }
			} catch (IOException e) {
				getWeight("电子秤USB线断开."); 
				//e.printStackTrace();
			}
			break;
		}
	}
	
	
	private static String byte2hex(byte[] buffer) {  
	        String h = "";  
	  
	        for (int i = 0; i < buffer.length; i++) {  
	            String temp = Integer.toHexString(buffer[i] & 0xFF);  
	            if (temp.length() == 1) {  
	                temp = "0" + temp;  
	            }  
	            h = h + " " + temp;  
	        }  
	  
	        return h;  
	  
	    }  
	
	 public static String Float16(String msg) {  
	        Float value = Float.intBitsToFloat(Integer.valueOf(msg.trim(), 16));  
	        Float f = 0.15490197f;  
	        return value + "";  
	    }  

	
	public void send(String content){
		try {
			outputStream.write(content.getBytes("GBK"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void ClosePort() {
	    if (serialPort != null) {
	      serialPort.close();
	    }
	  }

	
}

