package comm;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/** 
 * httpclient
 * 
 * @author arron
 * @date 2015��11��11�� ����6:36:49 
 * @version 1.0 
 */
public class SimpleHttpClient {

	/**  
	 * ģ������
	 * 
	 * @param url		��Դ��ַ
	 * @param map	�����б�
	 * @param encoding	����
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String send(String url, Map<String,String> map,String encoding) throws ParseException, IOException{
		String body = "";

		//����httpclient����
		CloseableHttpClient client = HttpClients.createDefault();
		//����post��ʽ�������
		HttpPost httpPost = new HttpPost(url);
		
		//װ�����
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if(map!=null){
			for (Entry<String, String> entry : map.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		//���ò��������������
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

		//System.out.println("�����ַ��"+url);
		//System.out.println("���������"+nvps.toString());
		
		//����header��Ϣ
		//ָ������ͷ��Content-type������User-Agent��
		httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		
		//ִ��������������õ������ͬ��������
		CloseableHttpResponse response = client.execute(httpPost);
		//��ȡ���ʵ��
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			//��ָ������ת�����ʵ��ΪString����
			body = EntityUtils.toString(entity, encoding);
		}
		EntityUtils.consume(entity);
		//�ͷ�����
		response.close();
        return body;
	}
	//��ȡ��Ա��Ϣ
	/*public void getUserInfo(String key,Map map) throws ParseException, IOException {
	      	String txt = "xbjrw";
	          double times =  (double) Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0,10))*74;
	          BigDecimal a =new BigDecimal(times); 
	          String keys = key+a.toString();
	          
	        String url="http://huiket.com/Fruits/getUserInfo";
	  		Map<String, String> map = new HashMap<String, String>();
	  		map.put("pckey",keys);
	  		map.put("account","270264127");
	  		String body = send(url, map,"utf-8");
	  		System.out.println(body.getClass().getName());
	  		System.out.println(body);
	              
	       }catch( Exception e ){
	           //e.printStackTrace(); 
	    	   System.out.println("����������æ������������");
	       } 
	}*/
}