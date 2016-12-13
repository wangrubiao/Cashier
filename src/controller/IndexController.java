package controller;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.IndexModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import comm.SimpleHttpClient;

import test.CommSend;
import test.checkSwing;

public class IndexController {
	IndexModel index = new IndexModel();
	SimpleHttpClient httpGet = new SimpleHttpClient();

	public void checkNum() {
		System.out.println("点击了结算");
		checkSwing ck = new checkSwing();

	}

	/**
	 * 发送数据到串口(商品即时数据)
	 */
	public void setShow(String shop, String money, String kg, float zmoney) {
		CommSend port = new CommSend();
		boolean result = port.startComPort("COM8");
		if (result) {
			port.send(toStringHex("0C")); // 传16进制数据
			port.send("★商 品:" + shop); // 设置显示的内容
			// port.send(toStringHex("1B5B431B5B431B5B43")); //传16进制数据
			// port.send("单 价:"+money); //设置显示的内容
			port.send(toStringHex("1b5b4c0a0909")); // 设置游标左小角并下移
			port.send("重 量:" + kg); // 设置显示的内容
			port.send(toStringHex("0909")); // 传16进制数据
			port.send("单 价:" + money); // 设置显示的内容
			port.send(toStringHex("1b5b4c0a0909")); // 设置游标左小角并下移
			port.send("小 计:" + (float) (Math.round(zmoney * 10)) / 10); // 设置显示的内容
			port.send(toStringHex("090909")); // 设置游标左小角并下移
			port.send(" ----------"); // 设置显示的内容
			port.send(toStringHex("0909")); // 设置游标左小角并下移
			port.send(toStringHex("1F420A0A0A090909")); // 设置游标左小角并下移
			port.send("总 计:" + (float) (Math.round(zmoney * 10)) / 10); // 设置显示的内容
			port.send(toStringHex("1B5B431B5B431B5B430A0A0A0A")); // 设置游标左小角并下移
			port.send(" 取 重:" + kg); // 设置显示的内容
			port.ClosePort();
		} else {
			System.out.println("客显屏串口无法连接...");
		}
	}

	public void setShowRel(String zongji, String shishou, String yingzhao) {
		/*
		 * CommSend port = new CommSend(); boolean result =
		 * port.startComPort("COM8"); if(result) {
		 * port.send(toStringHex("0a0a")); //设置游标左小角并下移
		 * port.send(toStringHex("1b5b4c0a0909")); //设置游标左小角并下移
		 * port.send("总 计:"+zongji); //设置显示的内容 port.send(toStringHex("090909"));
		 * //设置游标左小角并下移 port.send(" ----------"); //设置显示的内容
		 * port.send(toStringHex("0909")); //设置游标左小角并下移
		 * port.send(toStringHex("1F420A0A0AOD0909OD")); //设置游标左小角并下移
		 * port.send("实 收:"+shishou); //设置显示的内容
		 * port.send(toStringHex("1B5B431B5B431B5B430A0A0A0A")); //设置游标左小角并下移
		 * port.send(" 应 找:"+yingzhao+"    "); //设置显示的内容 port.ClosePort();
		 * }else{ System.out.println("串口无法连接..."); }
		 */
	}

	/**
	 * 边框 样式重写
	 */
	public TitledBorder boder(String str) {
		Border b1 = BorderFactory.createLineBorder(new Color(184, 207, 229), 1);
		return BorderFactory.createTitledBorder(b1, str, TitledBorder.LEFT, TitledBorder.TOP,
				new Font("宋体", Font.BOLD, 18), Color.white);
	}

	/**
	 * 添加商品记录
	 */
	public Vector addGoods(String str, String weight, String is_b, String upmey) {
		String[] arr = str.split("_");
		// System.out.println(upmey);
		int i = weight.indexOf("-");
		if (i >= 0) {
			if (is_b.equals("NULL")) {
				return null;
			}

		}
		if (weight.equals("0.000")) {
			if (is_b.equals("NULL")) {
				return null;
			}
		}
		if (!upmey.equals("NULL")) {
			float uparr = (float) (Float.valueOf(upmey) * 2); // 改变价格
			arr[2] = String.valueOf(uparr);

			float weightInt = Float.valueOf(weight);
			float danjia = Float.valueOf(arr[2]);
			float dj = weightInt * danjia;
			float mey = (float) (Math.round(dj * 100)) / 100;
			// 追加到商品到表格
			Vector list = new Vector();
			list.add(arr[0]); // 编号
			list.add(arr[1]); // 名字
			list.add(weight); // 重量
			list.add(arr[2]); // 单价
			list.add(mey); // 小计
			setShow(arr[1], arr[2], weight, mey);
			return list;

		} else if (!is_b.equals("NULL")) {
			// 按个数算
			float weightInt = Float.valueOf(is_b); // 获取重量,不起称重量便是数量
			float danjia = Float.valueOf(arr[2]); // 获取单价
			float dj = weightInt * danjia; // 获取小计
			float mey = (float) (Math.round(dj * 100)) / 100;
			// 追加到商品到表格
			Vector list = new Vector();
			list.add(arr[0]); // 编号
			list.add(arr[1]); // 名字
			list.add(is_b); // 重量
			list.add(arr[2]); // 单价
			list.add(mey); // 小计
			setShow(arr[1], arr[2], weight, mey);
			return list;
		} else {
			float weightInt = Float.valueOf(weight);
			float danjia = Float.valueOf(arr[2]);
			float dj = weightInt * danjia;
			float mey = (float) (Math.round(dj * 100)) / 100;
			// 追加到商品到表格
			Vector list = new Vector();
			list.add(arr[0]); // 编号
			list.add(arr[1]); // 名字
			list.add(weight); // 重量
			list.add(arr[2]); // 单价
			list.add(mey); // 小计
			setShow(arr[1], arr[2], weight, mey);
			return list;
		}
	}

	/**
	 * 把json对象转成数组
	 * 
	 * @return
	 */
	public String[][] getGoodsList(String typeId) {
		JSONArray type = index.GoodsList(typeId);
		String[][] str = new String[type.length()][3];
		try {
			for (int i = 0; i < type.length(); i++) {
				JSONObject object = type.getJSONObject(i);
				// System.out.println("编号："+object.getString("number")+"名称：" +
				// object.getString("name") + " 价格："
				// + object.getString("money"));
				str[i][0] = object.getString("gid");
				str[i][1] = object.getString("name");
				str[i][2] = object.getString("gds_price");

			}

			return str;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 转16进制转2进制
	 */
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

	public static String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	/**
	 * Md5加密函数
	 * 
	 * @param txt
	 * @return
	 */
	public String md5(String txt) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(txt.getBytes("GBK")); // Java的字符串是unicode编码，不受源码文件的编码影响；而PHP的编码是和源码文件的编码一致，受源码编码影响。
			StringBuffer buf = new StringBuffer();
			for (byte b : md.digest()) {
				buf.append(String.format("%02x", b & 0xff));
			}
			return buf.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 一键更新商品数据
	public JSONObject getGoodsData() {
		String url = "http://xs.yiyuangy.com/admin/api.line/goods";
		Map<String, String> map = new HashMap<String, String>();
		map.put("NULL", "不需要参数");
		// System.out.println(body);
		try {
			// 处理接口返回的json数据
			String body = SimpleHttpClient.send(url, map, "utf-8");
			JSONObject bodys = new JSONObject(body);
			return bodys;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			JOptionPane.showMessageDialog(null, "1");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			JOptionPane.showMessageDialog(null, "更新失败.Api访问失败.检查网络是否正常");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			JOptionPane.showMessageDialog(null, "更新失败.Api返回数据非JSON格式.");
		}

		return null;
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

	// 获取会员信息
	public JSONObject getUserInfo(String account) throws ParseException, IOException {
		String keys = getKey(account);
		String url = "http://fruits.123xk.cn/Fruits/Fruits/getUserInfo";
		Map<String, String> map = new HashMap<String, String>();
		map.put("pckey", keys);
		map.put("account", account);
		String body = SimpleHttpClient.send(url, map, "utf-8");

		try {
			// 处理接口返回的json数据
			JSONObject bodys = new JSONObject(body);
			return bodys;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获取key
	private String getKey(String account) throws ParseException, IOException {
		String txt = account + "xbjrw";
		double times = (double) Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0, 10))
				* 74;
		BigDecimal a = new BigDecimal(times);
		String keys = this.md5(txt) + a.toString();
		return keys;

	}

	// 订单插入
	public JSONObject orderAdd(String account, JSONObject orderList, String zmey, String yhui, String shishou,
			String yingzhao) throws ParseException, IOException {
		if (account.equals("NULL")) {
			account = "1000";
		}
		String url = "http://fruits.123xk.cn/Fruits/Fruits/orderAdd";
		String keys = this.getKey(account);
		Map mapData = new HashMap();
		mapData.put("pckey", keys);
		mapData.put("account", account);
		mapData.put("order", orderList.toString());
		mapData.put("zmey", zmey);
		mapData.put("yhui", yhui);
		mapData.put("shishou", shishou);
		mapData.put("yingzhao", yingzhao);
		String body = SimpleHttpClient.send(url, mapData, "utf-8");
		System.out.println(body);
		try {
			JSONObject bodys = new JSONObject(body);
			return bodys;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
