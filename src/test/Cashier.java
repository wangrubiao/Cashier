package test;
/**
 * @收银系统 核心功能
 */
public class Cashier {

	private String moeny; //价格
	private String weight; //重量
	private String goods; //商品
	private int    order[];
	
	/**
	 * @构造函数
	 */
	public Cashier()
	{
		getWeight();//保持监听
	}
	/**
	 * @监听电子秤重量
	 */
	private void getWeight()
	{
		//线程实时保持监听串口
		GetPort getport	= new GetPort();
		Thread t = new Thread(getport);
		t.start();
	}
	/**
	 * @发送数据到客显
	 */
	private String sendInfo()
	{
		return "0.1654";
	}
}
