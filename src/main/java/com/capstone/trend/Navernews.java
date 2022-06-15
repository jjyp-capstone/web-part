package com.capstone.trend;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Navernews {

    public static void main(String[] args) {
        String clientId = "_KnaGSuC4ibWCvuPAqAq"; // ���ø����̼� Ŭ���̾�Ʈ ���̵�"
        String clientSecret = "jc9mDHNEJZ"; // ���ø����̼� Ŭ���̾�Ʈ ��ũ����"

        String text = null;
        try {
            text = URLEncoder.encode("����", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("�˻��� ���ڵ� ����", e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/news?query=" + text + "&display=5&start=1&sort=sim"; // json
                                                                                                                  // ���
        // String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text;
        // // xml ���

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL, requestHeaders);

    }

    public static JSONArray getJson(String query) {
        String clientId = "_KnaGSuC4ibWCvuPAqAq"; // ���ø����̼� Ŭ���̾�Ʈ ���̵�"
        String clientSecret = "jc9mDHNEJZ"; // ���ø����̼� Ŭ���̾�Ʈ ��ũ����"

        String text = null;
        try {
            text = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("�˻��� ���ڵ� ����", e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/news?query=" + text + "&display=5&start=1&sort=sim"; // json
                                                                                                                  // ���
        // String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text;
        // // xml ���

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL, requestHeaders);

        JSONObject json = new JSONObject(responseBody);
        System.out.println(responseBody);
        JSONArray items = json.getJSONArray("items");

        for (int i = 0; i < items.length(); i++) {
            JSONObject obj = items.getJSONObject(i);
            String title = obj.getString("title");
            String link = obj.getString("link");

            System.out.println(title);
            System.out.println(link);
        }

        return items;

    }

    private static String get(String apiUrl, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // ���� ȣ��
                return readBody(con.getInputStream());
            } else { // ���� �߻�
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API ��û�� ���� ����", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL�� �߸��Ǿ����ϴ�. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("������ �����߽��ϴ�. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API ������ �дµ� �����߽��ϴ�.", e);
        }
    }
}
