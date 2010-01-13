package org.wordbuster.util;

import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.nio.charset.Charset;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class HtmlCrowler {
	public String getHtml(String urlStr) {
		String returnStr = "";
		URL url;// URL 주소 객체
		URLConnection connection;// URL접속을 가지는 객체
		InputStream is;// URL접속에서 내용을 읽기위한 Stream
		InputStreamReader isr;
		BufferedReader br;

		try {
			// URL객체를 생성하고 해당 URL로 접속한다..
			url = new URL(urlStr);
			connection = url.openConnection();
			// 내용을 읽어오기위한 InputStream객체를 생성한다..
			is = connection.getInputStream();
			Charset ch = Charset.forName("utf-8");
			isr = new InputStreamReader(is, ch);
			br = new BufferedReader(isr);

			// 내용을 읽어서 화면에 출력한다..
			String buf = null;
			while (true) {
				buf = br.readLine();
				if (buf == null)
					break;
				//System.out.println(buf);
				returnStr += buf;
			}
		} catch (MalformedURLException mue) {
			System.err
					.println("잘못되 URL입니다. 사용법 : java URLConn http://hostname/path]");
			System.exit(1);
		} catch (IOException ioe) {
			System.err.println("IOException " + ioe);
			ioe.printStackTrace();
			System.exit(1);
		}
		return returnStr;
	}
};