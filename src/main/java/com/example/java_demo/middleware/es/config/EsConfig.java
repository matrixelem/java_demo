package com.example.java_demo.middleware.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsConfig {

//    @Value("${es.host}")
    public String host;
//    @Value("${es.port}")
    public int port;
//    @Value("${es.scheme}")
    public String scheme;
//
//    @Bean
    public RestHighLevelClient createClient() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, scheme)));
    }
}
