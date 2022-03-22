package com.example.java_demo.middleware.clickhouse;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClickHouseTest {

    public static void main(String[] args) throws Exception {

        System.out.println("开始：" + new Date().toString());
        String url = "jdbc:clickhouse://10.171.7.45:8123";
        //插入总量
        int total = 10000000;
//        new ClickHouseTest().singleInsert(total, url);
//        new ClickHouseTest().batchInsert(total, url);
         new ClickHouseTest().poolQuery(url);
        System.out.println("结束：" + new Date().toString());

    }

    //使用线程池和连接池并发查询
    public void poolQuery(String url) throws Exception {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName("ru.yandex.clickhouse.ClickHouseDriver");
        dataSource.setMaxActive(3);
        dataSource.setInitialSize(3);
        dataSource.setKeepAlive(true);


        ExecutorService service = Executors.newFixedThreadPool(3);
        for (int i=0; i < 3; i++) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        DruidPooledConnection conn = dataSource.getConnection();
                        Statement statement = conn.createStatement();
                        ResultSet rs = statement.executeQuery("select * from structured where name like '%d%' and deviceSetId=2001\n" +
                                "and recognitionTime between '2018-10-06 11:04:11' and '2019-10-06 11:04:11'\n" +
                                "and category != 5 and wearHat != 9\n" +
                                "order by recognitionTime limit 1, 5");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    //单条查询
    public void query(String url) throws Exception {
        Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
        Connection connection = DriverManager.getConnection(url);

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from city");

        List<CityEntity> list = new ArrayList<>();
        while (rs.next()) {
            CityEntity entity = new CityEntity();
            entity.setId(rs.getString("id"));
            entity.setCity(rs.getString("city"));
            entity.setCountry(rs.getString("country"));
            entity.setProvince(rs.getString("province"));
            list.add(entity);
        }
        rs.close();
        statement.close();
        connection.close();

        list.forEach(System.out::println);
    }

    //批量插入
    public void batchInsert(int total, String url) throws Exception {

        Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
        Connection connection = DriverManager.getConnection(url);
        String sql = "insert into structured(reid, trackID, name, age, deviceId, deviceName, deviceSetId, deviceUuId, deviceLocation, " +
                "zoneId, zoneUuId, zoneName, cropRectLeft, hatColor, recognitionTime, fullRectRight, featureVersion, fullRectTop, " +
                "wearSafetycap, smokeStatus, fullRectBottom, reidRelScore, uniqueFlag, serverTime, relatedFaceId, bagType, visibleRatio, fullRectLeft, " +
                "quality, dressUpperColor, cropRectTop, watchPhoneStatus, reidRelTrackID, featureData, gender, dressLowerStyle, cropRectBottom, fallStatus, " +
                "dataFromCore, ruleType, fullUri, cropUri, runStatus, rawQuality, dressLowerColor, featureDataVector, usePhoneStatus, cropRectRight, wearHat, " +
                "dressUpperStyle, identifyFlag, category, isRealName, rideBike) values " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        connection.setAutoCommit(false);
        PreparedStatement ps = connection.prepareStatement(sql);


        long mill = 0;
        for(int i= 0; i < total; i++){
            int a = RandomUtils.nextInt(1, 200);
            Timestamp timestamp = new Timestamp(LocalDateTime.now().plusDays(-a).toInstant(ZoneOffset.of("+8")).toEpochMilli());

            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, RandomStringUtils.randomAlphabetic(15));
            ps.setString(3, RandomStringUtils.randomAlphabetic(4));
            ps.setInt(4, RandomUtils.nextInt(1, 30)); //age
            ps.setLong(5, RandomUtils.nextLong(1000, 1020)); //deviceId
            ps.setString(6, UUID.randomUUID().toString());
            ps.setLong(7, RandomUtils.nextLong(2000, 2030)); // deviceSetId
            ps.setString(8, RandomStringUtils.randomAlphabetic(4));
            ps.setString(9, RandomStringUtils.randomAlphabetic(4));
            ps.setLong(10, RandomUtils.nextLong(1000, 1010)); //zoneId
            ps.setString(11, UUID.randomUUID().toString());
            ps.setString(12, RandomStringUtils.randomAlphabetic(5));
            ps.setInt(13, RandomUtils.nextInt(1, 10));
            ps.setInt(14, RandomUtils.nextInt(2, 5));
            ps.setTimestamp(15, timestamp);
            ps.setInt(16, RandomUtils.nextInt(10, 100));
            ps.setString(17, RandomStringUtils.randomAlphabetic(16));
            ps.setInt(18, RandomUtils.nextInt(10, 100));
            ps.setInt(19, RandomUtils.nextInt(10, 100));
            ps.setInt(20, RandomUtils.nextInt(10, 100));
            ps.setInt(21, RandomUtils.nextInt(10, 100));
            ps.setDouble(22, RandomUtils.nextDouble(1, 100));
            ps.setString(23, RandomStringUtils.randomAlphabetic(2));
            ps.setTimestamp(24, timestamp);
            ps.setString(25, RandomStringUtils.randomAlphabetic(10));
            ps.setInt(26, RandomUtils.nextInt(2, 10));
            ps.setDouble(27, RandomUtils.nextDouble(1, 10));
            ps.setInt(28, RandomUtils.nextInt(2, 10));
            ps.setDouble(29, RandomUtils.nextDouble(2, 100));
            ps.setInt(30, RandomUtils.nextInt(1, 100));
            ps.setInt(31, RandomUtils.nextInt(1, 100));
            ps.setInt(32, RandomUtils.nextInt(1, 100));
            ps.setString(33, RandomStringUtils.randomAlphabetic(10));
            ps.setString(34, RandomStringUtils.randomAlphabetic(10));
            ps.setInt(35, RandomUtils.nextInt(1, 100));
            ps.setInt(36, RandomUtils.nextInt(1, 100));
            ps.setInt(37, RandomUtils.nextInt(1, 100));
            ps.setInt(38, RandomUtils.nextInt(1, 100));
            ps.setInt(39, RandomUtils.nextInt(1, 10));
            ps.setInt(40, RandomUtils.nextInt(1, 100));
            ps.setString(41, RandomStringUtils.randomAlphabetic(16));
            ps.setString(42, RandomStringUtils.randomAlphabetic(12));
            ps.setInt(43, RandomUtils.nextInt(1, 100));
            ps.setString(44, RandomStringUtils.randomAlphabetic(16));
            ps.setInt(45, RandomUtils.nextInt(1, 100));
            ps.setString(46, "[1, 23, 45, 1]");
            ps.setInt(47, RandomUtils.nextInt(1, 5));
            ps.setInt(48, RandomUtils.nextInt(1, 15));
            ps.setInt(49, RandomUtils.nextInt(1, 15));
            ps.setInt(50, RandomUtils.nextInt(1, 15));
            ps.setInt(51, RandomUtils.nextInt(1, 2)); //identifyFlag
            ps.setInt(52, RandomUtils.nextInt(1, 15));
            ps.setInt(53, RandomUtils.nextInt(1, 15));
            ps.setInt(54, RandomUtils.nextInt(1, 15));


            long s1 = System.currentTimeMillis();
            ps.addBatch();
            //每隔10000条提交一次
            if (i % 100000 == 0){
                ps.executeBatch();
                connection.commit();
                System.out.println("提交成功...." + i);
            }
            long s2 = System.currentTimeMillis() - s1;
            mill = mill + s2;
        }
        long h = System.currentTimeMillis();
        ps.executeBatch();
        connection.commit();
        connection.close();
        System.out.println("总消耗时间:" + (System.currentTimeMillis() - h + mill) / 1000);
    }

    //循环单条插入
    public void singleInsert(int total, String url) throws Exception {

        Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
        Connection connection = DriverManager.getConnection(url);


        String sql = "insert into structured(reid, trackID, name, age, deviceId, deviceName, deviceSetId, deviceUuId, deviceLocation, " +
                "zoneId, zoneUuId, zoneName, cropRectLeft, hatColor, recognitionTime, fullRectRight, featureVersion, fullRectTop, " +
                "wearSafetycap, smokeStatus, fullRectBottom, reidRelScore, uniqueFlag, serverTime, relatedFaceId, bagType, visibleRatio, fullRectLeft, " +
                "quality, dressUpperColor, cropRectTop, watchPhoneStatus, reidRelTrackID, featureData, gender, dressLowerStyle, cropRectBottom, fallStatus, " +
                "dataFromCore, ruleType, fullUri, cropUri, runStatus, rawQuality, dressLowerColor, featureDataVector, usePhoneStatus, cropRectRight, wearHat, " +
                "dressUpperStyle, identifyFlag, category, isRealName, rideBike) values " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);

        long mill = 0;
        for(int i= 0; i < total; i++){
            int a = RandomUtils.nextInt(1, 1000);
            Timestamp timestamp = new Timestamp(LocalDateTime.now().plusDays(-a).toInstant(ZoneOffset.of("+8")).toEpochMilli());

            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, RandomStringUtils.randomAlphabetic(15));
            ps.setString(3, RandomStringUtils.randomAlphabetic(4));
            ps.setInt(4, RandomUtils.nextInt(1, 100));
            ps.setLong(5, RandomUtils.nextLong(1000, 3000));
            ps.setString(6, UUID.randomUUID().toString());
            ps.setLong(7, RandomUtils.nextLong(1000, 3000)); //todo
            ps.setString(8, RandomStringUtils.randomAlphabetic(4));
            ps.setString(9, RandomStringUtils.randomAlphabetic(4));
            ps.setLong(10, RandomUtils.nextLong(1000, 3000));
            ps.setString(11, UUID.randomUUID().toString());
            ps.setString(12, RandomStringUtils.randomAlphabetic(5));
            ps.setInt(13, RandomUtils.nextInt(1, 10));
            ps.setInt(14, RandomUtils.nextInt(2, 5));
            ps.setTimestamp(15, timestamp);  //recotime
            ps.setInt(16, RandomUtils.nextInt(10, 100));
            ps.setString(17, RandomStringUtils.randomAlphabetic(16));
            ps.setInt(18, RandomUtils.nextInt(10, 100));
            ps.setInt(19, RandomUtils.nextInt(10, 100));
            ps.setInt(20, RandomUtils.nextInt(10, 100));
            ps.setInt(21, RandomUtils.nextInt(10, 100));
            ps.setDouble(22, RandomUtils.nextDouble(1, 100));
            ps.setString(23, RandomStringUtils.randomAlphabetic(2));
            ps.setTimestamp(24, timestamp); //serverTime
            ps.setString(25, RandomStringUtils.randomAlphabetic(10));
            ps.setInt(26, RandomUtils.nextInt(2, 10));
            ps.setDouble(27, RandomUtils.nextDouble(1, 10));
            ps.setInt(28, RandomUtils.nextInt(2, 10));
            ps.setDouble(29, RandomUtils.nextDouble(2, 100));
            ps.setInt(30, RandomUtils.nextInt(1, 100));
            ps.setInt(31, RandomUtils.nextInt(1, 100));
            ps.setInt(32, RandomUtils.nextInt(1, 100));
            ps.setString(33, RandomStringUtils.randomAlphabetic(10));
            ps.setString(34, RandomStringUtils.randomAlphabetic(10));
            ps.setInt(35, RandomUtils.nextInt(1, 100));
            ps.setInt(36, RandomUtils.nextInt(1, 100));
            ps.setInt(37, RandomUtils.nextInt(1, 100));
            ps.setInt(38, RandomUtils.nextInt(1, 100));
            ps.setInt(39, RandomUtils.nextInt(1, 10));
            ps.setInt(40, RandomUtils.nextInt(1, 100));
            ps.setString(41, RandomStringUtils.randomAlphabetic(16));
            ps.setString(42, RandomStringUtils.randomAlphabetic(12));
            ps.setInt(43, RandomUtils.nextInt(1, 100));
            ps.setString(44, RandomStringUtils.randomAlphabetic(16));
            ps.setInt(45, RandomUtils.nextInt(1, 100));
            ps.setString(46, "[1, 23, 45, 1]"); //featureDataVector
            ps.setInt(47, RandomUtils.nextInt(1, 5));
            ps.setInt(48, RandomUtils.nextInt(1, 15));
            ps.setInt(49, RandomUtils.nextInt(1, 15));
            ps.setInt(50, RandomUtils.nextInt(1, 15));
            ps.setInt(51, RandomUtils.nextInt(1, 15));
            ps.setInt(52, RandomUtils.nextInt(1, 15));
            ps.setInt(53, RandomUtils.nextInt(1, 15));
            ps.setInt(54, RandomUtils.nextInt(1, 15));

            long s1 = System.currentTimeMillis();
            ps.execute();
            long s2 = System.currentTimeMillis();
            mill = mill + (s2-s1);
        }
        connection.commit();
        connection.close();
        System.out.println("mill====================" + mill);

    }

}
