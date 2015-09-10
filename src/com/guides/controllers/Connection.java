package com.guides.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Connection {

	public static String connect(String serviceUrl, String urlParameters,
			String methodType, String contentType) {
			//	String serverPath = "";  //online url
				String serverPath = "http://localhost:8888/";	 //local url
		try {
			URL url = new URL(serverPath + serviceUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod(methodType);
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds
			connection.setRequestProperty("Content-Type", contentType);
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			return retJson;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}