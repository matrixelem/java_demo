package com.example.java_demo.middleware.es.service;

import com.alibaba.fastjson.JSON;
import com.example.java_demo.middleware.es.entity.PersonEntity;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private static final String INDEX = "person";

    @Resource
    private RestHighLevelClient client;

    /**
     * 插入单条数据
     * @param entity
     */
    public void insert(PersonEntity entity) {
        IndexRequest request = new IndexRequest(INDEX);
        request.id(String.valueOf(entity.getId()));
        request.source(JSON.toJSONString(entity), XContentType.JSON);

        try {
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 批量插入
     */
    public void batchInsert(List<PersonEntity> list){
        BulkRequest request = new BulkRequest();
        list.forEach(item -> {
            IndexRequest request1 = new IndexRequest(INDEX);
            request1.id(String.valueOf(item.getId()));
            request1.source(JSON.toJSONString(item), XContentType.JSON);
            request.add(request1);
        });

        try {
            client.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Description: 搜索
     * @param builder 查询参数
     */
    public List<PersonEntity> search(SearchSourceBuilder builder) {
        SearchRequest request = new SearchRequest(INDEX);
        request.source(builder);
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            List<PersonEntity> res = new ArrayList<>(hits.length);
            for (SearchHit hit : hits) {
                res.add(JSON.parseObject(hit.getSourceAsString(), PersonEntity.class));
            }

            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
