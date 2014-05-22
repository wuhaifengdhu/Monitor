package com.whf.network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.ClientProtocolException;

import android.util.Base64;

import com.whf.date.CurrentCalendar;
import com.whf.sqlite.DatabaseHandler;
import com.whf.sqlite.Transaction;

public class DataDownload {

	private String buildPath() {
		String path = "http://http://54.178.222.217/snowpea/data/request/miningType/"
				+ "TransactionFormat";

		CurrentCalendar.setCurrentCalendar(String
				.valueOf(DataDownloadService.latestTime));
		int yf = CurrentCalendar.getCurrentYear(), mf = CurrentCalendar
				.getCurrentMonthInt(), df = CurrentCalendar.getCurrentDay();
		int hf = CurrentCalendar.getCurrentHour(), mif = CurrentCalendar
				.getCurrentMinute(), sf = CurrentCalendar.getCurrentSecond();

		CurrentCalendar.setCurrentCalendar();
		int yt = CurrentCalendar.getCurrentYear(), mt = CurrentCalendar
				.getCurrentMonthInt(), dt = CurrentCalendar.getCurrentDay();
		int ht = CurrentCalendar.getCurrentHour(), mit = CurrentCalendar
				.getCurrentMinute(), st = CurrentCalendar.getCurrentSecond();

		System.out.println("CurrentCalendar.getCurrentTimeTotalSecond()="
				+ CurrentCalendar.getCurrentTimeTotalSecond() + " VS  latest:"
				+ DataDownloadService.latestTime);
		if (CurrentCalendar.getCurrentTimeTotalSecond() > DataDownloadService.latestTime) {
			DataDownloadService.latestTime = CurrentCalendar.getCurrentTimeTotalSecond();
		}

		path += "/from/" + yf + "/" + mf + "/" + df + "/" + hf + ":" + mif
				+ ":" + sf;
		path += "/to/" + yt + "/" + mt + "/" + dt + "/" + ht + ":" + mit + ":"
				+ st;

		System.out.println(path);
		return path;
	}

	private static void trustAllHosts() {

		X509TrustManager easyTrustManager = new X509TrustManager() {

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
			@Override
			public void checkClientTrusted(
					java.security.cert.X509Certificate[] arg0, String arg1)
					throws java.security.cert.CertificateException {
				// TODO Auto-generated method stub
			}
			@Override
			public void checkServerTrusted(
					java.security.cert.X509Certificate[] arg0, String arg1)
					throws java.security.cert.CertificateException {
				// TODO Auto-generated method stub
			}
		};

		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { easyTrustManager };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	private ArrayList<Transaction> getResponse() {
		String path = buildPath();
		// String
		// path="http://54.178.222.217/snowpea/data/request/miningType/TransactionFormat/from/2014/5/2/0:00:00/to/2014/5/2/0:59:00";
		StringBuilder builder = new StringBuilder();
		try {
			trustAllHosts();
			URL url = new URL(path);
			String s = "sprtpa:Sprtpa2014#";
			String base64 = "Basic "
					+ Base64.encodeToString(s.getBytes(), Base64.DEFAULT);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String urlHostName, SSLSession session) {
					return true;
				}
			});

			conn.setDoInput(true);
			conn.setRequestProperty("Authorization", base64);
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			if (200 == conn.getResponseCode()) {
				InputStream instream = conn.getInputStream();
				String line = null;
				BufferedReader lineReader = new BufferedReader(
						new InputStreamReader(instream));

				while ((line = lineReader.readLine()) != null) {
					builder.append(line);
				}

			}
			System.out.println(conn.getResponseCode() + "hello");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JsonParse().parse(builder.toString());
	}
	
	public void download(DatabaseHandler db) {
		ArrayList<Transaction> datas = getResponse();
		for (int i = 0; i < datas.size(); i++) {
			db.addTransaction(datas.get(i));
		}
	}
}
