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
		System.out.println("����˽���");
		checkSwing ck = new checkSwing();

	}

	/**
	 * �������ݵ�����(��Ʒ��ʱ����)
	 */
	public void setShow(String shop, String money, String kg, float zmoney) {
		CommSend port = new CommSend();
		boolean result = port.startComPort("COM8");
		if (result) {
			port.send(toStringHex("0C")); // ��16��������
			port.send("���� Ʒ:" + shop); // ������ʾ������
			// port.send(toStringHex("1B5B431B5B431B5B43")); //��16��������
			// port.send("�� ��:"+money); //������ʾ������
			port.send(toStringHex("1b5b4c0a0909")); // �����α���С�ǲ�����
			port.send("�� ��:" + kg); // ������ʾ������
			port.send(toStringHex("0909")); // ��16��������
			port.send("�� ��:" + money); // ������ʾ������
			port.send(toStringHex("1b5b4c0a0909")); // �����α���С�ǲ�����
			port.send("С ��:" + (float) (Math.round(zmoney * 10)) / 10); // ������ʾ������
			port.send(toStringHex("090909")); // �����α���С�ǲ�����
			port.send(" ----------"); // ������ʾ������
			port.send(toStringHex("0909")); // �����α���С�ǲ�����
			port.send(toStringHex("1F420A0A0A090909")); // �����α���С�ǲ�����
			port.send("�� ��:" + (float) (Math.round(zmoney * 10)) / 10); // ������ʾ������
			port.send(toStringHex("1B5B431B5B431B5B430A0A0A0A")); // �����α���С�ǲ�����
			port.send(" ȡ ��:" + kg); // ������ʾ������
			port.ClosePort();
		} else {
			System.out.println("�����������޷�����...");
		}
	}

	public void setShowRel(String zongji, String shishou, String yingzhao) {
		/*
		 * CommSend port = new CommSend(); boolean result =
		 * port.startComPort("COM8"); if(result) {
		 * port.send(toStringHex("0a0a")); //�����α���С�ǲ�����
		 * port.send(toStringHex("1b5b4c0a0909")); //�����α���С�ǲ�����
		 * port.send("�� ��:"+zongji); //������ʾ������ port.send(toStringHex("090909"));
		 * //�����α���С�ǲ����� port.send(" ----------"); //������ʾ������
		 * port.send(toStringHex("0909")); //�����α���С�ǲ�����
		 * port.send(toStringHex("1F420A0A0AOD0909OD")); //�����α���С�ǲ�����
		 * port.send("ʵ ��:"+shishou); //������ʾ������
		 * port.send(toStringHex("1B5B431B5B431B5B430A0A0A0A")); //�����α���С�ǲ�����
		 * port.send(" Ӧ ��:"+yingzhao+"    "); //������ʾ������ port.ClosePort();
		 * }else{ System.out.println("�����޷�����..."); }
		 */
	}

	/**
	 * �߿� ��ʽ��д
	 */
	public TitledBorder boder(String str) {
		Border b1 = BorderFactory.createLineBorder(new Color(184, 207, 229), 1);
		return BorderFactory.createTitledBorder(b1, str, TitledBorder.LEFT, TitledBorder.TOP,
				new Font("����", Font.BOLD, 18), Color.white);
	}

	/**
	 * �����Ʒ��¼
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
			float uparr = (float) (Float.valueOf(upmey) * 2); // �ı�۸�
			arr[2] = String.valueOf(uparr);

			float weightInt = Float.valueOf(weight);
			float danjia = Float.valueOf(arr[2]);
			float dj = weightInt * danjia;
			float mey = (float) (Math.round(dj * 100)) / 100;
			// ׷�ӵ���Ʒ�����
			Vector list = new Vector();
			list.add(arr[0]); // ���
			list.add(arr[1]); // ����
			list.add(weight); // ����
			list.add(arr[2]); // ����
			list.add(mey); // С��
			setShow(arr[1], arr[2], weight, mey);
			return list;

		} else if (!is_b.equals("NULL")) {
			// ��������
			float weightInt = Float.valueOf(is_b); // ��ȡ����,�����������������
			float danjia = Float.valueOf(arr[2]); // ��ȡ����
			float dj = weightInt * danjia; // ��ȡС��
			float mey = (float) (Math.round(dj * 100)) / 100;
			// ׷�ӵ���Ʒ�����
			Vector list = new Vector();
			list.add(arr[0]); // ���
			list.add(arr[1]); // ����
			list.add(is_b); // ����
			list.add(arr[2]); // ����
			list.add(mey); // С��
			setShow(arr[1], arr[2], weight, mey);
			return list;
		} else {
			float weightInt = Float.valueOf(weight);
			float danjia = Float.valueOf(arr[2]);
			float dj = weightInt * danjia;
			float mey = (float) (Math.round(dj * 100)) / 100;
			// ׷�ӵ���Ʒ�����
			Vector list = new Vector();
			list.add(arr[0]); // ���
			list.add(arr[1]); // ����
			list.add(weight); // ����
			list.add(arr[2]); // ����
			list.add(mey); // С��
			setShow(arr[1], arr[2], weight, mey);
			return list;
		}
	}

	/**
	 * ��json����ת������
	 * 
	 * @return
	 */
	public String[][] getGoodsList(String typeId) {
		JSONArray type = index.GoodsList(typeId);
		String[][] str = new String[type.length()][3];
		try {
			for (int i = 0; i < type.length(); i++) {
				JSONObject object = type.getJSONObject(i);
				// System.out.println("��ţ�"+object.getString("number")+"���ƣ�" +
				// object.getString("name") + " �۸�"
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
	 * ת16����ת2����
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
	 * Md5���ܺ���
	 * 
	 * @param txt
	 * @return
	 */
	public String md5(String txt) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(txt.getBytes("GBK")); // Java���ַ�����unicode���룬����Դ���ļ��ı���Ӱ�죻��PHP�ı����Ǻ�Դ���ļ��ı���һ�£���Դ�����Ӱ�졣
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

	// һ��������Ʒ����
	public JSONObject getGoodsData() {
		String url = "http://xs.yiyuangy.com/admin/api.line/goods";
		Map<String, String> map = new HashMap<String, String>();
		map.put("NULL", "����Ҫ����");
		// System.out.println(body);
		try {
			// ����ӿڷ��ص�json����
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
			JOptionPane.showMessageDialog(null, "����ʧ��.Api����ʧ��.��������Ƿ�����");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			JOptionPane.showMessageDialog(null, "����ʧ��.Api�������ݷ�JSON��ʽ.");
		}

		return null;
	}

	// �����ַ������ļ���
	public void saveAsFileWriter(JSONObject content,String path) {
		try {
			BufferedWriter bwxl = null;
			String xiliePath = path;//LocalData/goods.json
			bwxl = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(xiliePath, false), "UTF-8"));// ���Է�ֹд���������������
			// �ļ������--�����д����--����д
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

	// ��ȡ��Ա��Ϣ
	public JSONObject getUserInfo(String account) throws ParseException, IOException {
		String keys = getKey(account);
		String url = "http://fruits.123xk.cn/Fruits/Fruits/getUserInfo";
		Map<String, String> map = new HashMap<String, String>();
		map.put("pckey", keys);
		map.put("account", account);
		String body = SimpleHttpClient.send(url, map, "utf-8");

		try {
			// ����ӿڷ��ص�json����
			JSONObject bodys = new JSONObject(body);
			return bodys;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	// ��ȡkey
	private String getKey(String account) throws ParseException, IOException {
		String txt = account + "xbjrw";
		double times = (double) Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0, 10))
				* 74;
		BigDecimal a = new BigDecimal(times);
		String keys = this.md5(txt) + a.toString();
		return keys;

	}

	// ��������
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
