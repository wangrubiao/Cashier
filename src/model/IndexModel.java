package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IndexModel {

	/**
	 * 获取本地json数据
	 * @param typeId
	 * @return
	 */
	 public JSONArray GoodsList(String typeId){
		   String goodsData = ReadFile("LocalData/goods.json");
			try {
				JSONObject type = new JSONObject(goodsData);
				JSONArray types = new JSONArray(type.getString("type-"+typeId));
				//System.out.println(type.getString("type-"+typeId));
				return types;
			} catch (JSONException e) {
				//e.printStackTrace();
				JOptionPane.showMessageDialog(null, "应用奔溃.本地JSON数据被破坏");
			}
			return null;
			
		}
	 /**
	  * 获取本地配置JSON数据 同时转成数组
	  * @return
	  */
	 public String[][] config(){
		 String jsonData = ReadFile("LocalData/config.json");
			try {
				JSONObject type = new JSONObject(jsonData);
				JSONArray types = new JSONArray(type.getString("config"));
				//把json转成数组
				String[][] str = new String[type.length()][2];
				try {
					for (int i = 0; i < types.length(); i++) {
						JSONObject object = types.getJSONObject(i);
						str[i][0] = object.getString("weight");
						str[i][1] = object.getString("show");
					}

					return str;

				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			} catch (JSONException e) {
				//e.printStackTrace();
				JOptionPane.showMessageDialog(null, "应用奔溃.本地配置数据被破坏");
			}
			return null;
	 }
	 public String ReadFile(String Path) {
			BufferedReader reader = null;
			String laststr = "";
			try {
				FileInputStream fileInputStream = new FileInputStream(Path);
				InputStreamReader inputStreamReader = new InputStreamReader(
						fileInputStream, "UTF-8");
				reader = new BufferedReader(inputStreamReader);
				String tempString = null;
				while ((tempString = reader.readLine()) != null) {
					laststr += tempString;
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return laststr;
		}
	// 保存字符串到文件中
		public void saveAsFileWriter(JSONObject content,String path) {
			try {
				BufferedWriter bwxl = null;
				String xiliePath = path;//LocalData/goods.json
				bwxl = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(xiliePath, false), "UTF-8"));// 可以防止写入的内容乱码问题
				// 文件输出流--输出流写操作--缓冲写
				bwxl.write(String.valueOf(content));
				bwxl.close();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
