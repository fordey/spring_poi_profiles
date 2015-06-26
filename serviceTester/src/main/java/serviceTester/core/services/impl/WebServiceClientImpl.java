package serviceTester.core.services.impl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import serviceTester.core.services.api.WebServiceClient;

@Service
public class WebServiceClientImpl implements WebServiceClient {

	@Value(value="${request.url}")
	private String url;
	
	@Value(value="${request.read-timeout}")
	private String readTimeOut;
	
	@Value(value="${request.connection-timeout}")
	private String connectionTimeOut;

	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	
	private static final Logger logger  = LoggerFactory.getLogger(WebServiceClientImpl.class);
	
	@Override
	public String sendReceive(String payload) throws Exception {
		String responseString;
		//Document dom;
		
		HttpURLConnection httpUrlConnection = null;
		try{
			httpUrlConnection = (HttpURLConnection) new URL(url).openConnection();
			httpUrlConnection.setDoInput(true);
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setRequestMethod("POST");
			httpUrlConnection.setReadTimeout(Integer.parseInt(readTimeOut));
			httpUrlConnection.setConnectTimeout(Integer.parseInt(connectionTimeOut));
			httpUrlConnection.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");

			System.out.println("Connecting...");
			httpUrlConnection.getOutputStream().write(payload.getBytes("UTF-8"));

			System.out.println("Connected.. Waiting For Response");

			//responseString = convertStreamToString(httpUrlConnection.getInputStream());
			
			responseString = convertStreamToString(httpUrlConnection.getInputStream());
			
			
		}
		catch(Exception ex)
		{
			logger.error("Error occurred sending payload [{}] to endpoint : [{}], {}", payload, url);
			throw(ex);
		}
		finally
		{
			if(httpUrlConnection != null){
				httpUrlConnection.disconnect();
			}
		}
		
		return responseString;
	}
	
	@SuppressWarnings("unused")
	private String convertStreamToString(InputStream inputStream) throws Exception
	{
		InputStreamReader reader = new InputStreamReader(inputStream); //, "UTF-8");
		char[] buffer = new char[DEFAULT_BUFFER_SIZE];
		StringBuffer result = new StringBuffer();
		long count = 0;
		int n = 0;

		while (-1 != (n = reader.read(buffer)))
		{
			result.append(buffer, 0, n);
			count += n;
		}

		return result.toString();
	}
	
}
