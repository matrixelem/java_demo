package com.example.java_demo.spider;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderTest {
    public static void parse() throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://mvnrepository.com/search?q=jsoup")
                .get()
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()){
            System.out.println("----------------" + response.code());
            return;
        }
        Document document = Jsoup.parse(response.body().string());
        Elements elements = document.select(".im-title > a");
        for (Element element: elements){
            System.out.println(element);
        }
    }

    public static void main(String[] args) throws Exception {
        parse();
    }
}
