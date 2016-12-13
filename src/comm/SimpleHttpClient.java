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
 * @date 2015年11月11日 下午6:36:49 
 * @version 1.0 
 */
public class SimpleHttpClient {

	/**  
	 * 模拟请求
	 * 
	 * @param url		资源地址
	 * @param map	参数列表
	 * @param encoding	编码
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String send(String url, Map<String,String> map,String encoding) throws ParseException, IOException{
		String body = "";

		//创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		//创建post方式请求对象
		HttpPost httpPost = new HttpPost(url);
		
		//装填参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if(map!=null){
			for (Entry<String, String> entry : map.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		//设置参数到请求对象中
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

		//System.out.println("请求地址："+url);
		//System.out.println("请求参数："+nvps.toString());
		
		//设置header信息
		//指定报文头【Content-type】、【User-Agent】
		httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		
		//执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = client.execute(httpPost);
		//获取结果实体
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			//按指定编码转换结果实体为String类型
			body = EntityUtils.toString(entity, encoding);
		}
		EntityUtils.consume(entity);
		//释放链接
		response.close();
        return body;
	}
	//获取会员信息
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
	    	   System.out.println("服务器请求繁忙，请重新请求！");
	       } 
	}*/
}