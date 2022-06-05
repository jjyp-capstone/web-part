package com.capstone.trend;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Crawl{

    public static List<String> main(String args){

        final String naver_url = "https://search.naver.com/search.naver?where=news&ie=utf8&sm=nws_hty&query="+args;

        Connection naver_conn = Jsoup.connect(naver_url);
        try {

            Document naver_document = naver_conn.get();
            Elements naver_news_title = naver_document.select("div.group_news > ul.list_news > li div.news_area > a");
            System.out.println(naver_news_title.attr("href"));
            System.out.println(naver_news_title.attr("title"));
            System.out.println("Done.");

            String href1 = naver_news_title.attr("href");
            String title1 = naver_news_title.attr("title");

            List<String> naver_result = new ArrayList<>();

            naver_result.add(href1);
            naver_result.add(title1);

            return naver_result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}

