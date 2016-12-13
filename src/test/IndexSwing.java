/*
 * @������ʵ��
 */
package test;
import gnu.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.json.JSONException;
import org.json.JSONObject;

import controller.IndexController;

public class IndexSwing extends JFrame implements ActionListener{
	//head���
	JPanel head;
	JLabel titleImg;
	//////////////////////////////////////
	//left���
	JPanel left;
	JButton guadan,qudan,chedan;
		//�������
	
	
	JPanel weightJpanel;
	JLabel weight,probably;
		//������Ϣ����
	JPanel info,infoTop,infoOrder,info_money,infoUser;
	JButton empty,thisDel;
		//������ϸ
	Vector rowData,columnNames;
	JTable tbl = null;
	JScrollPane scro = null;
	JLabel znum,ycount, amount,should,userId;
	////////////////////////////////////
	//right��
	JTabbedPane tab_right;
	JPanel type1,type2,type3,type4,right,right_head,right_fooder,fooder_number,fooder_buttom;
	JButton []num = new JButton[13];
	JButton numDel,allDel;
	JButton receipts,valence,jsonData,config;
	//JButton num0,num1,num2,num3,num4,num5,num6,num7,num8,num9;
	///////////////////////////////////
	//fooder���
	JPanel fooder;
	JButton ticket,user,discount,check;
	////
	IndexController index = new IndexController();
	static String upmey = "NULL";
	
	public static void main(String[] args)
	{
		//new IndexSwing();
		new Cashier();
	}

	/*
	 * @���캯��
	 */
	public IndexSwing(){
		//����head
		head = new JPanel();
			head.setBackground(new Color(0, 46, 69));
		//titleImg = new JLabel(new ImageIcon("images/top.gif"));
		titleImg = new JLabel("�� �� ϵ ͳ",JLabel.CENTER);
			titleImg.setPreferredSize(new Dimension(300,40));
			titleImg.setFont(new Font("����", Font.BOLD, 26));
			titleImg.setForeground(new Color(82, 171, 236));
			titleImg.setBackground(new Color(238, 243, 255));
		head.add(titleImg);
		this.add(head,"North");
		//����left
		left();
		this.add(left,"West");
		//����right
		right();
		this.add(right,"Center");
		
		//����fooder
		fooder();
		this.add(fooder,"South");
		
		//this.setUndecorated(true);�����ޱ߿�
		UIManager UI=new UIManager();
		 UI.put("TabbedPane.background", Color.red);
		ImageIcon icon = new ImageIcon("images/qq.gif");              //�Ƿ��ڸ�Ŀ¼�£�src���ϼ�Ŀ¼
		this.setIconImage(icon.getImage());   
		//���ô�������
		this.setTitle("��������ϵͳ");
		this.setSize(1210,800);
		this.setLocation(200,200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//��ʾ����
		this.setLocationRelativeTo(getOwner()); //���ھ���
		this.setVisible(true);
		
	}
	//��ȡ������ ģ��
	  public int getRowCount()   {   
		  return rowData.size();   
	  }
	  //ɾ������ ģ��
	 private void removeData(){   
		  removeRows(0,getRowCount());   
		  tbl.updateUI();   
	  }
	  public void removeRows(int row, int count){   
		  for(int i=0;i<count;i++){
			  if(rowData.size()>row){
				  rowData.remove(row);   
			  }
		  }
	  }
	  //�õ�ĳ��ĳ�е�����
	  public Object getValueAt(int row,int col){
		  //return row+col;
		  return ((Vector) rowData.get(row)).get(col);
	  }
	  /**
	   * ɾ��ָ����
	   */
	  private void removeDataRow(int row){
		  
		  removeRow(row);
		  tbl.updateUI();   
		  
	  }
	  public void removeRow(int row){
		  try {
			rowData.remove(row);
		} catch (Exception e) {
			System.out.print("����ȫ��ɾ�����");
			//e.printStackTrace();
		}   
	  }    
	  /**
	   * ͳ�ƽ��
	   */
	   private void upMoney(){
		   //int col = tbl.getColumnCount(); //�������
		   int col = 5; //�������
		   int row = getRowCount(); //�������
		   float num = 0;
		   float c = 0;
		  // float c = null;
		   for(int i=0;i<row;i++){
			   c = Float.parseFloat(getValueAt(i,4).toString());
			   num = num+c;
		   }
		   float numb = (float)(Math.round(num*10))/10;//
		   znum.setText("<html>�ܼۣ�<b>"+numb+"</b></html>");
		   
	   }
	/**
	 * ����ģ����̵���¼�
	 */
	  public void is_num(String str,String val){
		//JOptionPane.showMessageDialog(null,str);
		  if(str.equals("empty")){
			  probably.setText("NULL");
		  }else if(val.equals("NULL")){
				//JOptionPane.showMessageDialog(null,str);
				//System.out.println("ֻ��NULL");
				probably.setText(str);
			}else{
				probably.setText(val+str);
			}
	  }
	/**
	 * һ��������Ʒ
	 */
	  public void goodsData() throws ParseException{
		  JSONObject bodys = index.getGoodsData();
		  //System.out.println(bodys);
		  if(bodys == null){
			 // JOptionPane.showMessageDialog(null, "��Ա��ֻ��������!");
		  }else{
			  //д��������Ʒ����
			  index.saveAsFileWriter(bodys,"LocalData/goods.json");
		  }
		  
	  }
	/**
	 * �����Ա��ť�¼�
	 */
	 public void userVip(String val)
	 {
		 if(!val.equals("NULL")){
				int i = val.indexOf(".");
				if(i >= 0){
					JOptionPane.showMessageDialog(null, "��Ա��ֻ��������!");
				}else{
					try {
						JSONObject bodys = index.getUserInfo(val);
						//System.out.println(bodys);
						if(bodys.has("msg")){
							JOptionPane.showMessageDialog(null, "����:��Ա������");
						}else{
							Iterator iterator = bodys.keys();
							while(iterator.hasNext()){
					        	String key = (String) iterator.next();
					        	System.out.println(key+":"+bodys.getString(key));
							}
							userId.setText(val);
							probably.setText("NULL");
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
				}
			}
	 }
	 /**
	  * ȥ��html ȡb��ǩ����
	  */
	 public String htmldel(JLabel name){
		 String strb = name.getText();	//�ܼ�
		 String a = strb.substring(12,strb.indexOf("</b>"));
		 return a;
	 }

	 /**
	  * ���㴦��ʱ��
	 * @throws JSONException 
	  */
	public void checkNum()
	{
		String[][] datalist = saveData();
		Map map = new HashMap();
		map.put("0","goods_id"); 
		map.put("1","name"); 
		map.put("2","weight"); 
		map.put("3","price"); 
		map.put("4","subtotal");
		
		JSONObject obj = new JSONObject();
		try {
			for(int i=0;i<datalist.length;i++)
			{
				JSONObject obja = new JSONObject();
				 String[] arr2 = datalist[i];
				 for (int j = 0; j < arr2.length; j++) {
					 //System.out.println(map.get(String.valueOf(j)).toString()+"&"+arr2[j]);
					  obja.put(map.get(String.valueOf(j)).toString(),arr2[j]);	  
				  }
				  obj.put(String.valueOf(i), obja);
			}
			JSONObject bodys = index.orderAdd(userId.getText(), obj,htmldel(znum),htmldel(ycount),htmldel(amount),htmldel(should));
			System.out.println(bodys+bodys.getString("status"));
			if(bodys.getString("status").equals("1")){
				JOptionPane.showMessageDialog(null, "Ok�������ɹ���ɣ�");
			}else{
				JOptionPane.showMessageDialog(null, "�����ƶ�¼��ʧ��!");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	  /**
	   * ��ȡ�������
	 * @return 
	   */
	  private String[][] saveData(){
		  int col = 5;
		  int row = getRowCount();   //���ݵ�����
		  String arr[][]=new String[row][5];
		  for(int i=0;i<row;i++){ 
			  for(int j=0;j<col;j++){   
		  		//System.out.print(getValueAt(i,j)+"\t");
		  		arr[i][j] = getValueAt(i,j).toString();
			  }
		  	  //System.out.print("\r\n");   
		  }
		return arr;
	  }
	/**
	 * ��ť�¼�����(non-Javadoc)
	 */
	public void actionPerformed(ActionEvent e) {
		String val = probably.getText(); //����
		//��۴���
		if(e.getActionCommand().equals("valenceCheck")){
			this.upmey = val;
			probably.setText("NULL");
		}
		//ʵ�մ���
		else if(e.getActionCommand().equals("receiptsCheck")){
			String strb = znum.getText();	//�ܼ�
			String a = strb.substring(12,strb.indexOf("</b>")); //��ȡ�ܼ�����
			//JOptionPane.showMessageDialog(null, a);
			if(val.equals("NULL")){
				amount.setText("<html>ʵ�գ�<b>"+a+"</b></html>");
				ycount.setText("<html>�Żݣ�<b>0.00</b></html>");
				should.setText("<html>Ӧ�ң�<b>0.00</b></html>");
				index.setShowRel(a,a,"0.00");
			}else{
				if(Float.valueOf(val)<Float.valueOf(a)){
					float yhnum = Float.valueOf(val)-Float.valueOf(a);
					double yhnumb = (double)(Math.round(yhnum*100))/100;//
					ycount.setText("<html>�Żݣ�<b>"+String.valueOf(yhnumb)+"</b></html>");
					should.setText("<html>Ӧ�ң�<b>0.00</b></html>");
					index.setShowRel(a,val,"0.00");
				}else{
					float yzhao = Float.valueOf(val)-Float.valueOf(a); //Ӧ�ҽ��
					float yzhaob = (float)(Math.round(yzhao*100))/100;//
					//float relnum = Float.valueOf(val)-Float.valueOf(a);	//ʵ�ս��
					ycount.setText("<html>�Żݣ�<b>0.00</b></html>");
					should.setText("<html>Ӧ�ң�<b>"+String.valueOf(yzhaob)+"</b></html>");
					index.setShowRel(a,val,String.valueOf(yzhaob));
				}
				double valb = Double.valueOf(val.toString());
				double valc = (double)(Math.round(valb*100))/100;//
				amount.setText("<html>ʵ�գ�<b>"+String.valueOf(valc)+"</b></html>");
				probably.setText("NULL");
				//index.setShowRel(a,String.valueOf(valc),"???");
			}
		}
		//ģ����̴���
		else if(e.getActionCommand().equals("numberCheck")){
			 String str = ((JButton)e.getSource()).getText().trim() ;
			 is_num(str,val);
		}
		//�����Ա����
		else if(e.getActionCommand().equals("user"))
		{
			this.userVip(val);
		}
		//��ռ�¼
		else if(e.getActionCommand().equals("empty"))
		{
			removeData();
			upMoney();
			
		}else if(e.getActionCommand().equals("thisDel")){
			//ɾ��ѡ����
			int rowNum = tbl.getSelectedRow();  //��ȡѡ�������
			System.out.println(tbl.getSelectedRow());
			if(rowNum == -1){
				JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ���ļ�¼");
			}else{
					removeDataRow(rowNum);
					upMoney();
			}
		}else if(e.getActionCommand().equals("check")){
			//�������
			this.checkNum();
		}else if(e.getActionCommand().equals("jsonDataCheck")){
			//һ��������Ʒ
			//д��json�ļ�
			try {
				this.goodsData();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "1222");
			}
			//������ͼview
			tab_right.removeAll();
			goodsview("1","������");
			goodsview("2","��ʳ��");
			goodsview("3","�ؼ���");
			goodsview("4","�����");
			right.updateUI();
		}else if(e.getActionCommand().equals("configCheck")){
			new checkSwing();
		}else{
			try {
				Object list = index.addGoods(e.getActionCommand(),weight.getText(),val,this.upmey);
				if(list==null){
					JOptionPane.showMessageDialog(null, "��������δ֪������δ֪��");
				}else{
					rowData.add(list);
					tbl.updateUI();
					this.upmey = "NULL";
					upMoney();
					probably.setText("NULL");
				}
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "����һ���쳣��������룡");
				//e1.printStackTrace();
			}
		}
	 }
	/**
	 * ������Ⱦ��ͼ
	 * @param val
	 */
	public void goodsview(String typeId,String name){
		IndexController index = new IndexController();
		String[][] result = index.getGoodsList(typeId);
		JButton []typeList = new JButton[result.length];
		type1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			for(int i=0;i<result.length;i++){
				typeList[i] = new JButton("<html>"+result[i][1]+"<br><font color='red'>"+result[i][2]+"</font></html>",new ImageIcon("images/goods.jpg"));
				typeList[i].setVerticalTextPosition(SwingConstants.BOTTOM);
				typeList[i].setHorizontalTextPosition(JButton.CENTER);
				//typeList[i].setBorderPainted(false);//ȥ���߾�
				typeList[i].setBorder(BorderFactory.createLineBorder(new Color(208, 183, 199), 2));//���ñ߾���ɫ�ʹ�С
				//typeList[i].setContentAreaFilled(false);//���ñ���͸��
				typeList[i].setBackground(new Color(251, 225, 242));
				type1.add(typeList[i]);
				typeList[i].addActionListener(this);//��������Դ
				typeList[i].setActionCommand(result[i][0]+"_"+result[i][1]+"_"+result[i][2]);//�����¼�����
			}
			tab_right.add(name,type1);
			right_head.add(tab_right);
	}
	/*
	 * ʵʱ�ı�Swing���� ���յ��ӳ�����
	 */
	public void getWeight(String val){
		 String a=val.substring(val.length()-3,val.length());  //��ȡ����3λ��
		 int b = val.length()-3; //�ó��ָ�λ��
		 String weightNew = val.substring(0,b);
		 weight.setText(weightNew+"."+a);
	}
	private void right(){
		right = new JPanel();
			right.setBackground(new Color(0, 46, 69));
		right_head = new JPanel(new GridLayout(1,1));
			//right_head.setBackground(new Color(238, 119, 120));
			right_head.setPreferredSize(new Dimension(770,360));
		tab_right = new JTabbedPane();
		IndexController index = new IndexController();
		String[][] result = index.getGoodsList("1");
		JButton []typeList = new JButton[result.length];
		type1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			for(int i=0;i<result.length;i++){
				typeList[i] = new JButton("<html>"+result[i][1]+"<br><font color='red'>"+result[i][2]+"</font></html>",new ImageIcon("images/goods.jpg"));
				typeList[i].setVerticalTextPosition(SwingConstants.BOTTOM);
				typeList[i].setHorizontalTextPosition(JButton.CENTER);
				//typeList[i].setBorderPainted(false);//ȥ���߾�
				typeList[i].setBorder(BorderFactory.createLineBorder(new Color(208, 183, 199), 2));//���ñ߾���ɫ�ʹ�С
				//typeList[i].setContentAreaFilled(false);//���ñ���͸��
				typeList[i].setBackground(new Color(251, 225, 242));
				type1.add(typeList[i]);
				typeList[i].addActionListener(this);//��������Դ
				typeList[i].setActionCommand(result[i][0]+"_"+result[i][1]+"_"+result[i][2]);//�����¼�����
			}
		//type2����
			String[][] resultb = index.getGoodsList("2");
			JButton []typeListb = new JButton[resultb.length];
			type2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			for(int i=0;i<resultb.length;i++){
				typeListb[i] = new JButton("<html>"+resultb[i][1]+"<br><font color='red'>"+resultb[i][2]+"</font></html>",new ImageIcon("images/goods.jpg"));
				typeListb[i].setVerticalTextPosition(SwingConstants.BOTTOM);
				typeListb[i].setHorizontalTextPosition(JButton.CENTER);
				//typeList[i].setBorderPainted(false);//ȥ���߾�
				typeListb[i].setBorder(BorderFactory.createLineBorder(new Color(208, 183, 199), 2));//���ñ߾���ɫ�ʹ�С
				//typeList[i].setContentAreaFilled(false);//���ñ���͸��
				typeListb[i].setBackground(new Color(251, 225, 242));
				type2.add(typeListb[i]);
				typeListb[i].addActionListener(this);//��������Դ
				typeListb[i].setActionCommand(resultb[i][0]+"_"+resultb[i][1]+"_"+resultb[i][2]);//�����¼�����
			}
			//type3����
			String[][] resultc = index.getGoodsList("3");
			JButton []typeListc = new JButton[resultc.length];
			type3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			for(int i=0;i<resultc.length;i++){
				typeListc[i] = new JButton("<html>"+resultc[i][1]+"<br><font color='red'>"+resultc[i][2]+"</font></html>",new ImageIcon("images/goods.jpg"));
				typeListc[i].setVerticalTextPosition(SwingConstants.BOTTOM);
				typeListc[i].setHorizontalTextPosition(JButton.CENTER);
				//typeList[i].setBorderPainted(false);//ȥ���߾�
				typeListc[i].setBorder(BorderFactory.createLineBorder(new Color(208, 183, 199), 2));//���ñ߾���ɫ�ʹ�С
				//typeList[i].setContentAreaFilled(false);//���ñ���͸��
				typeListc[i].setBackground(new Color(251, 225, 242));
				type3.add(typeListc[i]);
				typeListc[i].addActionListener(this);//��������Դ
				typeListc[i].setActionCommand(resultc[i][0]+"_"+resultc[i][1]+"_"+resultc[i][2]);//�����¼�����
			}
			//type4����
			String[][] resultd = index.getGoodsList("4");
			JButton []typeListd = new JButton[resultd.length];
			type4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			for(int i=0;i<resultd.length;i++){
				typeListd[i] = new JButton("<html>"+resultd[i][1]+"<br><font color='red'>"+resultd[i][2]+"</font></html>",new ImageIcon("images/goods.jpg"));
				typeListd[i].setVerticalTextPosition(SwingConstants.BOTTOM);
				typeListd[i].setHorizontalTextPosition(JButton.CENTER);
				//typeList[i].setBorderPainted(false);//ȥ���߾�
				typeListd[i].setBorder(BorderFactory.createLineBorder(new Color(208, 183, 199), 2));//���ñ߾���ɫ�ʹ�С
				//typeList[i].setContentAreaFilled(false);//���ñ���͸��
				typeListd[i].setBackground(new Color(251, 225, 242));
				type4.add(typeListd[i]);
				typeListd[i].addActionListener(this);//��������Դ
				typeListd[i].setActionCommand(resultd[i][0]+"_"+resultd[i][1]+"_"+resultd[i][2]);//�����¼�����
			}
		right_fooder =new JPanel(new GridLayout(1,2));
			right_fooder.setPreferredSize(new Dimension(770,270));
		fooder_number = new JPanel();
			fooder_number.setBackground(new Color(238, 169, 120));
			fooder_number.setPreferredSize(new Dimension(350,270));
		fooder_buttom = new JPanel();
			fooder_buttom.setBackground(new Color(238, 199, 120));
			fooder_buttom.setPreferredSize(new Dimension(420,270));
			for(int i=1;i<13;i++){
				if(i==10){
					num[i] = new JButton("0");
				}else if(i==11){
					num[i] = new JButton(".");
				}else if(i==12){
					num[i] = new JButton("empty");
				}else{
					num[i] = new JButton(String.valueOf(i));
				}
				num[i].setPreferredSize(new Dimension(90,80));
				num[i].setFont(new Font("����", Font.BOLD, 20));
				num[i].setForeground(new Color(82, 171, 236));
				num[i].setBackground(new Color(238, 243, 255));
				fooder_number.add(num[i]);
				num[i].addActionListener(this);//��������Դ
				num[i].setActionCommand("numberCheck");//�����¼�����
			}
		receipts = new JButton("ʵ ��");
			receipts.setPreferredSize(new Dimension(110,80));
			receipts.setFont(new Font("����", Font.BOLD, 26));
			receipts.setForeground(new Color(82, 171, 236));
			receipts.setBackground(new Color(238, 243, 255));
			receipts.addActionListener(this);//��������Դ
			receipts.setActionCommand("receiptsCheck");//�����¼�����
		valence = new JButton("�� ��");
			valence.setPreferredSize(new Dimension(110,80));
			valence.setFont(new Font("����", Font.BOLD, 26));
			valence.setForeground(new Color(82, 171, 236));
			valence.setBackground(new Color(238, 243, 255));
			valence.addActionListener(this);//��������Դ
			valence.setActionCommand("valenceCheck");//�����¼�����
		jsonData = new JButton("һ��������Ʒ");
			jsonData.setPreferredSize(new Dimension(225,80));
			jsonData.setFont(new Font("����", Font.BOLD, 26));
			jsonData.setForeground(new Color(82, 171, 236));
			jsonData.setBackground(new Color(238, 243, 255));
			jsonData.addActionListener(this);//��������Դ
			jsonData.setActionCommand("jsonDataCheck");//�����¼�����
		config = new JButton("����Ӧ��");
			config.setPreferredSize(new Dimension(225,80));
			config.setFont(new Font("����", Font.BOLD, 26));
			config.setForeground(new Color(82, 171, 236));
			config.setBackground(new Color(238, 243, 255));
			config.addActionListener(this);//��������Դ
			config.setActionCommand("configCheck");//�����¼�����
		fooder_buttom.add(receipts );
		fooder_buttom.add(valence);
		fooder_buttom.add(jsonData);
		fooder_buttom.add(config);
		right_fooder.add(fooder_number);
		right_fooder.add(fooder_buttom);
		
		tab_right.add("������",type1);
		tab_right.add("��ʳ��",type2);
		tab_right.add("�ؼ���",type3);
		tab_right.add("�����",type4);
		right_head.add(tab_right);

		right.add(right_head);
		right.add(right_fooder);
		
		
	}
	private void fooder(){
		fooder = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			fooder.setBackground(new Color(0, 46, 69));
		ticket = new JButton("С Ʊ");
		user = new JButton("�� Ա");
			user.addActionListener(this);//��������Դ
			user.setActionCommand("user");//�����¼�����
		discount = new JButton("�� ��");
		check = new JButton("�� ��");
			check.addActionListener(this);//��������Դ
			check.setActionCommand("check");//�����¼�����
		fooder.add(ticket);
		fooder.add(user);
		fooder.add(discount);
		fooder.add(check);
	}
	
	/**
	 * ����left
	 */
	public void left(){
		left = new JPanel();
			//left.setBackground(new Color(191,126,173));
			left.setBackground(new Color(0, 46, 69));

			left.setPreferredSize(new Dimension(400,0));
		guadan = new JButton("�ҵ�");
			guadan.setPreferredSize(new Dimension(114,80));
			guadan.setFont(new Font("����", Font.BOLD, 20));
			guadan.setForeground(new Color(117, 55, 104));
			guadan.setBackground(new Color(169, 203, 241));
		qudan = new JButton("ȡ��");
	    	qudan.setPreferredSize(new Dimension(114,80));
	    	qudan.setFont(new Font("����", Font.BOLD, 20));
	    	qudan.setForeground(new Color(117, 55, 104));
	    	qudan.setBackground(new Color(169, 203, 241));
		chedan = new JButton("����");
				chedan.setPreferredSize(new Dimension(114,80));
				chedan.setFont(new Font("����", Font.BOLD, 20));
				chedan.setForeground(new Color(117, 55, 104));
				chedan.setBackground(new Color(169, 203, 241));
		left.add(guadan);
		left.add(qudan);
		left.add(chedan);
		//���������
		weightJpanel = new JPanel(new GridLayout(2,1));
		weightJpanel.setPreferredSize(new Dimension(350, 100));
			weightJpanel.setBackground(new Color(0, 46, 69));
		weight = new JLabel("��ʼ��...",JLabel.CENTER);
			weight.setBorder (index.boder("����/Kg"));
			//weight.
			weight.setForeground(new Color(117, 55, 104));
		probably = new JLabel("NULL",JLabel.CENTER);
			probably.setOpaque(true);
			
			probably.setBorder (index.boder("����"));
			probably.setForeground(new Color(117, 55, 104));
			probably.setFont(new Font("����", Font.BOLD, 22));
			probably.setBackground(new Color(0, 46, 69));
			weight.setFont(new Font("����", Font.BOLD, 22));
		weightJpanel.add(weight);
		weightJpanel.add(probably);
		left.add(weightJpanel);
		//������Ϣ���� ������ϸ
		info = new JPanel();
			info.setPreferredSize(new Dimension(350, 480));
			info.setBackground(new Color(0, 46, 69));
		infoTop = new JPanel(new GridLayout(1,1,0,0));
			infoTop.setPreferredSize(new Dimension(350,30));
			empty = new JButton("�� �� �� ��");
			empty.setFont(new Font("����", Font.BOLD, 20));
			empty.setForeground(new Color(117, 55, 104));
			//empty.setBackground(new Color(255, 251, 240));
				empty.addActionListener(this);//��������Դ
				empty.setActionCommand("empty");//�����¼�����
			thisDel = new JButton("ɾ �� ѡ ��");
			thisDel.setFont(new Font("����", Font.BOLD, 20));
			thisDel.setForeground(new Color(117, 55, 104));
			//thisDel.setBackground(new Color(255, 251, 240));
				thisDel.addActionListener(this);//��������Դ
				thisDel.setActionCommand("thisDel");//�����¼�����
		infoTop.add(empty);
		infoTop.add(thisDel);
		//������Ϣ���� ������ϸ
		infoOrder = new JPanel();
			//infoOrder.setPreferredSize(new Dimension(350,250));
			BoxLayout layout=new BoxLayout(infoOrder, BoxLayout.Y_AXIS); 
			infoOrder.setLayout(layout);
		columnNames = new Vector();
		columnNames.add("I D");
		columnNames.add("Ʒ ��");
		columnNames.add("��/����");
		columnNames.add("�� ��");
		columnNames.add("С ��");
		//������
		rowData = new Vector();
		Vector list = new Vector();
		
		/*for(int i=0;i<2;i++)
		{
			list.add("1");
			list.add("�ʳ����");
			list.add("1.7841");
			list.add("68.00");
			list.add("180.00");
			rowData.add(list);
		}
		*/      
		//��ʼ��JTable
		tbl = new JTable(rowData,columnNames);
		
		tbl.getColumnModel().getColumn(0).setPreferredWidth(50);
		tbl.getColumnModel().getColumn(1).setPreferredWidth(100);
		tbl.getColumnModel().getColumn(2).setPreferredWidth(40);
		tbl.getColumnModel().getColumn(3).setPreferredWidth(40);
		tbl.getColumnModel().getColumn(4).setPreferredWidth(60);
		tbl.setRowHeight(25);
		//tbl.setEnabled(false);
		//��ʼ��JScrollPane
		scro = new JScrollPane(tbl);
		scro.setPreferredSize(new Dimension(350,250));
		infoOrder.add(scro);
		//������ʾ���
		info_money = new JPanel(new GridLayout(2,1));
			info_money.setPreferredSize(new Dimension(350, 100));
			info_money.setBackground(new Color(0, 46, 69));
		znum = new JLabel("<html>�ܼۣ�<b>0.00</b></html>",JLabel.CENTER);
			znum.setFont(new Font("����", Font.BOLD, 18));
			znum.setForeground(Color.red);
		ycount = new JLabel("<html>�Żݣ�<b>0.00</b></html>",JLabel.CENTER);
			ycount.setFont(new Font("����", Font.BOLD, 18));
			ycount.setForeground(Color.red);
		amount = new JLabel("<html>ʵ�գ�<b>0.00</b></html>",JLabel.CENTER);
			amount.setFont(new Font("����", Font.BOLD, 18));
			amount.setForeground(Color.red);
		should = new JLabel("<html>Ӧ�ң�<b>0.00</b></html>",JLabel.CENTER);
			should.setFont(new Font("����", Font.BOLD, 18));
			should.setForeground(Color.red);
		info_money.add(znum);
		info_money.add(ycount);
		info_money.add(amount);
		info_money.add(should);
		
		infoOrder.add(info_money);
		
		//�����Ա
		infoUser = new JPanel(new GridLayout(1,1));
			infoUser.setPreferredSize(new Dimension(350, 50));
			infoUser.setBackground(new Color(0, 46, 69));
		userId = new JLabel("NULL",JLabel.CENTER);
			userId.setBorder (index.boder("��ԱID"));
		userId.setFont(new Font("����", Font.BOLD, 22));
		userId.setForeground(new Color(82, 171, 236));
		infoUser.add(userId);
		infoOrder.add(infoUser);
		
		info.add(infoTop);
		info.add(infoOrder);
		left.add(info);
		
	}
	
}
