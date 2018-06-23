package prueba;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class POSTprueba {

	public static void main(String[] args) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost("http://localhost:8080/UBUassistant/vote");
		
		DateFormat formatForId = new SimpleDateFormat("yyMMddHHmmssSSS");
		
		String userID=formatForId.format(new Date()) + "_carlos"; 
		
		String json = "{\"userID\":[\"180616115556217\"],\"Palabras\":[[\"yahoo\"]],\"valoracion\":[5]}";
		
		//String json = "{\"code\":201}";
		
		System.out.println(json);
		
		try {
			postRequest.setEntity(new StringEntity(json));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    postRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
	    
	    try (CloseableHttpResponse httpResponse = httpClient.execute(postRequest)) {
	        String content = EntityUtils.toString(httpResponse.getEntity());

	        int statusCode = httpResponse.getStatusLine().getStatusCode();
	        System.out.println("statusCode = " + statusCode);
	        System.out.println("content = " + content);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
