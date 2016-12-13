/***
* 
* @串口接收处理/实时获取串口数据
* 
*/
package test;

import gnu.io.CommPortIdentifier;

import java.util.Enumeration;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.IndexModel;

public class GetPort implements Runnable{
	
	IndexModel index = new IndexModel();
	
	@Override
	public void run() {
		CommUtil port = new CommUtil();
		try {
			//电子秤串口
			String[][] str = index.config();
			System.out.println(str[0][0]);
			boolean result = port.startComPort(str[0][0]);
			int i = 1;
			while(true)
			{
				//Thread.sleep(100);
				//System.out.println(str[0][0]);
				i++;
				
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "电子秤串口失联,请派航空母舰搜寻...");
		}
		
		port.ClosePort();

		
	}
	

}
