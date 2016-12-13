/*
 * @����ʵ����
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

	InputStream inputStream; // �Ӵ�������������
	OutputStream outputStream;// �򴮿��������
	SerialPort serialPort; // ���ڵ�����
	CommPortIdentifier portId;
	String weight;     //���ڳ�����
	/*
	 * �ҵ�����ú����ǻ�ȡ����������
	 */
	public boolean startComPort(String name) {
		Enumeration portList = CommPortIdentifier.getPortIdentifiers(); //�õ���ǰ�����ϵĶ˿�
		while (portList.hasMoreElements()) {
			CommPortIdentifier temp = (CommPortIdentifier) portList.nextElement();// ��ȡ��Ӧ���ڶ���
			if (temp.getPortType() == CommPortIdentifier.PORT_SERIAL) {// �ж�����˿������Ǵ���
				// �ж�������봮�ڴ��ڣ��ʹ򿪸ô���
				if (temp.getName().equals(name)) {
					portId = temp;
				}else{
					System.out.println("���ڲ�����");
				}
			}
			
		}
		try {
			// �򿪴�������ΪCOM_4(��������),�ӳ�Ϊ2����
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
			serialPort.addEventListener(this); // ����ǰ�������һ��������
		} catch (TooManyListenersException e) {
			return false;
		}
		serialPort.notifyOnDataAvailable(true); // ��������ʱ֪ͨ
		try {
			serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, // ���ô��ڶ�д����
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		} catch (UnsupportedCommOperationException e) {
			return false;
		}
		return true;
	}
	 /**
     * SerialPort EventListene  �ķ���,���������˿����Ƿ���������
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
		
		case SerialPortEvent.DATA_AVAILABLE:// ���п�������ʱ��ȡ����,���Ҹ����ڷ�������
			byte[] readBuffer = new byte[20];
			try {
				while (inputStream.available() > 0) {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						//getWeight("���ӳӹر�"); 
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
					 //System.out.println("�������ϴ�һ��");//����һ��ʱ��������
				 }else{
					weight = val;
					getWeight(val); 
				 }
			} catch (IOException e) {
				getWeight("���ӳ�USB�߶Ͽ�."); 
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
