package test;
/**
 * @����ϵͳ ���Ĺ���
 */
public class Cashier {

	private String moeny; //�۸�
	private String weight; //����
	private String goods; //��Ʒ
	private int    order[];
	
	/**
	 * @���캯��
	 */
	public Cashier()
	{
		getWeight();//���ּ���
	}
	/**
	 * @�������ӳ�����
	 */
	private void getWeight()
	{
		//�߳�ʵʱ���ּ�������
		GetPort getport	= new GetPort();
		Thread t = new Thread(getport);
		t.start();
	}
	/**
	 * @�������ݵ�����
	 */
	private String sendInfo()
	{
		return "0.1654";
	}
}
