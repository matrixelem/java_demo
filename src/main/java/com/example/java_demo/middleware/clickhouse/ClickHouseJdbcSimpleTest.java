package com.example.java_demo.middleware.clickhouse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class ClickHouseJdbcSimpleTest {

    public static void main(String[] args) throws Exception {
        batchInsert(30000000);
    }

    //建表语句
    String createTable = "CREATE TABLE person (\n" +
            "id UUID,\n" +
            "name String,\n" +
            "age Int32,\n" +
            "time DateTime\n" +
            ") ENGINE = MergeTree\n" +
            "order by time\n" +
            "partition by toYYYYMM(time)";
    private static final String jdbcUrl = "jdbc:clickhouse://10.171.2.180:8123";

    public static void batchInsert(int totalNum) throws Exception {
        Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
        Connection connection = DriverManager.getConnection(jdbcUrl);
        String sql = "insert into person(id,name,age,time) values(?,?,?,?)";
        connection.setAutoCommit(false);
        PreparedStatement ps = connection.prepareStatement(sql);


        for(int i= 0; i < totalNum; i++){
            int a = RandomUtils.nextInt(1, 200);
            Timestamp timestamp = new Timestamp(LocalDateTime.now().plusDays(-a).toInstant(ZoneOffset.of("+8")).toEpochMilli());
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, RandomStringUtils.randomAlphabetic(3, 10));
            ps.setInt(3, RandomUtils.nextInt(1, 100));
            ps.setTimestamp(4, timestamp);

            ps.addBatch();
            //每隔1000条提交一次
            if (i % 1000 == 0){
                ps.executeBatch();
                connection.commit();
                System.out.println("提交成功...." + i);
            }
        }
        ps.executeBatch();
        connection.commit();
        connection.close();
    }
}
