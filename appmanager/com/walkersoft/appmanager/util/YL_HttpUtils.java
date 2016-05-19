package com.walkersoft.appmanager.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * <p>Title: </p>
 * <p>Description: http utils </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author LiLu
 * @version 1.
 */
public class YL_HttpUtils
{

	private static final String URL_PARAM_CONNECT_FLAG = "&";
	private static Log log = LogFactory.getLog(YL_HttpUtils.class);

	private YL_HttpUtils()
	{
	}

	/**
	 * GET METHOD
	 * @param strUrl String
	 * @param map Map
	 * @throws IOException
	 * @return List
	 */
	public static List<String> URLGet(String strUrl, Map<String, String> map) throws IOException
	{
		String strtTotalURL = "";
		List<String> result = new ArrayList<String>();
		if (strtTotalURL.indexOf("?") == -1)
		{
			strtTotalURL = strUrl + "?" + getContent(map);
		}
		else
		{
			strtTotalURL = strUrl + "&" + getContent(map);
		}
		log.debug("strtTotalURL:" + strtTotalURL);
		URL url = new URL(strtTotalURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setUseCaches(false);
		HttpURLConnection.setFollowRedirects(true);
		BufferedReader in = new BufferedReader(new InputStreamReader(con
				.getInputStream()));
		while (true)
		{
			String line = in.readLine();
			if (line == null)
			{
				break;
			}
			else
			{
				result.add(line);
			}
		}
		in.close();
		return (result);
	}

	/**
	 * POST METHOD
	 * @param strUrl String
	 * @param content Map
	 * @throws IOException
	 * @return List
	 */
	public static String URLPost(String strUrl, Map<String, String> map) throws IOException
	{
		String content = getContent(map);

		URL url = new URL(strUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setAllowUserInteraction(false);
		con.setUseCaches(false);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Charset", "utf-8");
		
		BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con
				.getOutputStream()));
		bout.write(content);
		bout.flush();
		bout.close();
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

		StringBuilder result = new StringBuilder();
		String line = null;
		while((line = br.readLine()) != null) {
			result.append(line);
		}
		con.disconnect();
		return URLDecoder.decode(result.toString(), "utf-8");	
	}

	public static String getContent(Map<String, String> map)
	{
		if (null == map || map.keySet().size() == 0)
		{
			return ("");
		}
		StringBuffer url = new StringBuffer();
		Set<String> keys = map.keySet();
		for (Iterator<String> i = keys.iterator(); i.hasNext();)
		{
			String key = String.valueOf(i.next());
			if (map.containsKey(key))
			{
				Object val = map.get(key);
				String str = val != null ? val.toString() : "";
				try
				{
					str = URLEncoder.encode(str, "UTF-8");
				}
				catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				}
				url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
			}
		}
		String strURL = "";
		strURL = url.toString();
		if (URL_PARAM_CONNECT_FLAG.equals(""
				+ strURL.charAt(strURL.length() - 1)))
		{
			strURL = strURL.substring(0, strURL.length() - 1);
		}
		return (strURL);
	}
	
	public static String getRemortIP(javax.servlet.http.HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}

	public static String getRemoteHost(javax.servlet.http.HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}
}
