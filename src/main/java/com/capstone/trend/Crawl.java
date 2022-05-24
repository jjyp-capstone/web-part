package com.capstone.trend;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class Crawl {

    public static void main(String[] args){
        final String youtube_url = "https://www.youtube.com/results?search_query=search+test";
        final String naver_url = "https://search.naver.com/search.naver?sm=tab_sug.top&where=news&query=%EA%B8%B0%EC%83%81%EC%B2%AD%EC%82%AC%EB%9E%8C%EB%93%A4&oquery=%EB%89%B4%EC%8A%A4&tqi=hozHswprvOsssOoMR%2FhssssstvN-331897&acq=%EA%B8%B0%EC%82%AC&acr=2&qdt=0";



        Connection youtube_conn = Jsoup.connect(youtube_url).userAgent("Mozilla");
        Connection naver_conn = Jsoup.connect(naver_url);
        try {

            Document youtube_document = youtube_conn.get();
            Elements youtube_element = youtube_document.select("#title-wrapper > h3");

            System.out.println(youtube_element);
            System.out.println("Done.");

            Document naver_document = naver_conn.get();
            Element naver_news_title = naver_document.select("a.news_tit").first();
            System.out.println(naver_news_title.attr("href"));
            System.out.println(naver_news_title.attr("title"));
            System.out.println("Done.");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

