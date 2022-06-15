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
        String clientId = "_KnaGSuC4ibWCvuPAqAq"; // 애플리케이션 클라이언트 아이디값"
        String clientSecret = "jc9mDHNEJZ"; // 애플리케이션 클라이언트 시크릿값"

        String text = null;
        try {
            text = URLEncoder.encode("스펙", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/news?query=" + text + "&display=5&start=1&sort=sim"; // json
                                                                                                                  // 결과
        // String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text;
        // // xml 결과

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL, requestHeaders);

    }

    public static JSONArray getJson(String query) {
        String clientId = "_KnaGSuC4ibWCvuPAqAq"; // 애플리케이션 클라이언트 아이디값"
        String clientSecret = "jc9mDHNEJZ"; // 애플리케이션 클라이언트 시크릿값"

        String text = null;
        try {
            text = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/news?query=" + text + "&display=5&start=1&sort=sim"; // json
                                                                                                                  // 결과
        // String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text;
        // // xml 결과

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
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
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
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}
