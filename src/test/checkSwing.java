/**
 * 配置窗口
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
	//定义控件
	JPanel jp1,jp2,jp3;
	JLabel jl1,jl2;
	JComboBox jcb1,jcb2;
	JButton jbt;
	IndexModel index = new IndexModel();
	
	public checkSwing()
	{
		//点击配置
        JFrame frame = new JFrame("配置应用");

        jp1 = new JPanel();
        jl1 = new JLabel("电子秤串口");
        String []list={"COM1","COM2","COM3","COM4","COM5","COM6","COM7","COM8","COM9","COM10"};
        jcb1 = new JComboBox(list);
      //设置选中的值
        for(int i = 0;i<list.length;i++){
        	if(list[i].equals(getComm("weight"))){
        		 jcb1.setSelectedIndex(i); 
        	}
        }
        System.out.println(getComm("weight"));
        jp2 = new JPanel();
        jl2 = new JLabel("客显屏串口");
        String []list2={"COM1","COM2","COM3","COM4","COM5","COM6","COM7","COM8","COM9","COM10"};
        jcb2 = new JComboBox(list2);
      //设置选中的值
        for(int i = 0;i<list.length;i++){
        	if(list[i].equals(getComm("show"))){
        		 jcb2.setSelectedIndex(i); 
        	}
        }
        jp3 = new JPanel();
        jbt = new JButton("保 存");
        jbt.addActionListener(new JbtActionListener());  
        //jbt.addActionListener(frame);//创建监听源
		//jbt.setActionCommand("save");//监听事件名称
        //设置布局
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
         * 设置图标
         */
        ImageIcon icon = new ImageIcon("images/qq.gif");
        frame.setIconImage(icon.getImage());
        frame.setLocationRelativeTo(getOwner()); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //显示窗体
        frame.setVisible(true);
	}
	/**
	 * 监听按钮保存事件
	 */
	private class JbtActionListener implements ActionListener{  
        public void actionPerformed(ActionEvent e) {
        	String weight = (String)jcb1.getSelectedItem(); //获取电子称串口
        	String show = (String)jcb2.getSelectedItem(); //获取客显串口
        	
        	
			try {
				//生成json格式
				JSONObject jsonObj = new JSONObject();
				Map <String, String> config = new HashMap <String, String>();
				List list=new ArrayList();
				config.put("weight",weight);
				config.put("show",show);
				list.add(config);
	        	jsonObj.put("config",list);
	        	//System.out.println(jsonObj);
	        	//写入最新商品数据
				index.saveAsFileWriter(jsonObj,"LocalData/config.json");
				JOptionPane.showMessageDialog(null, "配置修改成功,程序将自动关闭，请人工启动引擎.");
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
	 * 获取当前请求的串口
	 * @param val 获取参数的名称 例如weight获取重量
	 * @return
	 */
	public String getComm(String val){
		String[][] str = index.config();
		if(val.equals("weight")){
			return str[0][0]; //返回电子秤串口
		}else{
			return str[0][1]; //返回客显串口
		}
		
	}
}
