package com.capstone.trend;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

@Getter @Setter
public class Crawl{

    public static String[] main(String args){
        final String youtube_url = "https://www.youtube.com/results?search_query="+args;
        final String naver_url = "https://search.naver.com/search.naver?where=news&ie=utf8&sm=nws_hty&query="+args;


        Connection youtube_conn = Jsoup.connect(youtube_url);
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

            String href1 = naver_news_title.attr("href");
            String title1 = naver_news_title.attr("title");

            return new String[] {href1,title1};
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String[0];
    }

}

