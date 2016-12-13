/**
 * ���ô���
 */
package test;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

import org.json.JSONException;
import org.json.JSONObject;

import model.IndexModel;

public class checkSwing extends JFrame{
	//����ؼ�
	JPanel jp1,jp2,jp3;
	JLabel jl1,jl2;
	JComboBox jcb1,jcb2;
	JButton jbt;
	IndexModel index = new IndexModel();
	
	public checkSwing()
	{
		//�������
        JFrame frame = new JFrame("����Ӧ��");

        jp1 = new JPanel();
        jl1 = new JLabel("���ӳӴ���");
        String []list={"COM1","COM2","COM3","COM4","COM5","COM6","COM7","COM8","COM9","COM10"};
        jcb1 = new JComboBox(list);
      //����ѡ�е�ֵ
        for(int i = 0;i<list.length;i++){
        	if(list[i].equals(getComm("weight"))){
        		 jcb1.setSelectedIndex(i); 
        	}
        }
        System.out.println(getComm("weight"));
        jp2 = new JPanel();
        jl2 = new JLabel("����������");
        String []list2={"COM1","COM2","COM3","COM4","COM5","COM6","COM7","COM8","COM9","COM10"};
        jcb2 = new JComboBox(list2);
      //����ѡ�е�ֵ
        for(int i = 0;i<list.length;i++){
        	if(list[i].equals(getComm("show"))){
        		 jcb2.setSelectedIndex(i); 
        	}
        }
        jp3 = new JPanel();
        jbt = new JButton("�� ��");
        jbt.addActionListener(new JbtActionListener());  
        //jbt.addActionListener(frame);//��������Դ
		//jbt.setActionCommand("save");//�����¼�����
        //���ò���
        frame.setLayout(new GridLayout(3,1));
        jp1.add(jl1);
        jp1.add(jcb1);
        jp2.add(jl2);
        jp2.add(jcb2);
        jp3.add(jbt);
        frame.add(jp1);
        frame.add(jp2);
        frame.add(jp3);
        
        frame.setSize(250,150);
        frame.setLocation(200,200);
        /**
         * ����ͼ��
         */
        ImageIcon icon = new ImageIcon("images/qq.gif");
        frame.setIconImage(icon.getImage());
        frame.setLocationRelativeTo(getOwner()); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //��ʾ����
        frame.setVisible(true);
	}
	/**
	 * ������ť�����¼�
	 */
	private class JbtActionListener implements ActionListener{  
        public void actionPerformed(ActionEvent e) {
        	String weight = (String)jcb1.getSelectedItem(); //��ȡ���ӳƴ���
        	String show = (String)jcb2.getSelectedItem(); //��ȡ���Դ���
        	
        	
			try {
				//����json��ʽ
				JSONObject jsonObj = new JSONObject();
				Map <String, String> config = new HashMap <String, String>();
				List list=new ArrayList();
				config.put("weight",weight);
				config.put("show",show);
				list.add(config);
	        	jsonObj.put("config",list);
	        	//System.out.println(jsonObj);
	        	//д��������Ʒ����
				index.saveAsFileWriter(jsonObj,"LocalData/config.json");
				JOptionPane.showMessageDialog(null, "�����޸ĳɹ�,�����Զ��رգ����˹���������.");
				System.exit(0);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            //System.out.println(petName);     
        }

		private Object jsonObj(String[] configs) {
			// TODO Auto-generated method stub
			return null;
		}     
    } 
	/**
	 * ��ȡ��ǰ����Ĵ���
	 * @param val ��ȡ���������� ����weight��ȡ����
	 * @return
	 */
	public String getComm(String val){
		String[][] str = index.config();
		if(val.equals("weight")){
			return str[0][0]; //���ص��ӳӴ���
		}else{
			return str[0][1]; //���ؿ��Դ���
		}
		
	}
}
