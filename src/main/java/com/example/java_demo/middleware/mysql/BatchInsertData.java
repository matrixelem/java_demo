package com.example.java_demo.middleware.mysql;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 快速批量往mysql插入数据
 */
public class BatchInsertData {

    public static void main(String[] args) {
        try {
            batchInsert1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void batchInsert() throws Exception {
        //共掺入的数据总量
        int total = 1000000;
        String url = "jdbc:mysql://10.171.5.126:3306/allinone?useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true&useServerPrepStmts=false";
        String user = "root";
        String pwd = "lzQ65DtDOE*";
        Connection connection = DbUtils.getConnection(url, user, pwd);

        String sql = "insert into person1(uuid, `name`, name_spell, image_uri, `type`, code, org_id, company_id, sex, gmt_modified, gmt_create) values " +
                "(?,?,?,?,?,?,?,?,?,?,?)";
        connection.setAutoCommit(false);
        PreparedStatement ps = connection.prepareStatement(sql);

        long start = System.currentTimeMillis();
        for(int i= 0; i < total; i++){
            String name = RandomStringUtils.randomAlphabetic(3, 10);
            int a = RandomUtils.nextInt(0, 1000);
            Timestamp timestamp = new Timestamp(LocalDateTime.now().plusDays(-a).toInstant(ZoneOffset.of("+8")).toEpochMilli());
            //数据拼装
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, name);
            ps.setString(3, name);
            ps.setString(4, RandomStringUtils.randomAlphabetic(66));
            ps.setInt(5, RandomUtils.nextInt(1, 4));
            ps.setString(6, UUID.randomUUID().toString().replaceAll("-", "")); //code要保证唯一
            ps.setInt(7, RandomUtils.nextInt(2000, 2005));
            ps.setInt(8, RandomUtils.nextInt(2000, 2002));
            ps.setInt(9, 0);
            ps.setTimestamp(10, timestamp);
            ps.setTimestamp(11, timestamp);

            ps.addBatch();
            //每隔10000条提交一次
            if (i % 10000 == 0){
                ps.executeBatch();
                connection.commit();
                System.out.println("提交成功...." + i);
            }
        }
        ps.executeBatch();
        connection.commit();
        ps.close();
        connection.close();
        System.out.println("总消耗时间:" + (System.currentTimeMillis() - start) / 1000);
    }


    /**
     * 使用数据库函数插入
     * @throws Exception
     */
    public static void batchInsert1() throws Exception {
        String enc = "Vr0L23hUHIu";
        int total = 1000000;
        String url = "jdbc:mysql://10.171.2.145:3306/allinone?useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true&useServerPrepStmts=false";
        String user = "root";
        String pwd = "vuJqTjnFs90";
        Connection connection = DbUtils.getConnection(url, user, pwd);
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();

        long start = System.currentTimeMillis();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (int i=0; i < total; i++){
            String name = RandomStringUtils.randomAlphabetic(3, 10);
            String time = formatter.format(LocalDateTime.now().plusDays(RandomUtils.nextInt(1, 400)));
            String code = RandomStringUtils.randomAlphabetic(15);
            String enCode = String.format("HEX(AES_ENCRYPT('%s','%s'))", code, enc);
            System.out.println("code=====" + code);
            String sql = String.format("insert into person1(uuid, `name`, name_spell, image_uri, code, `type`,org_id, company_id, sex, gmt_modified, gmt_create) values " +
                    "('%s','%s','%s','%s',%s,%d,%d,%d,%d,'%s','%s')",
                    UUID.randomUUID().toString(),
                    name,
                    name,
                    RandomStringUtils.randomAlphabetic(66),
                    enCode, //code
                    RandomUtils.nextInt(1, 4),
                    RandomUtils.nextInt(1000, 1010),
                    RandomUtils.nextInt(1000, 1003),
                    RandomUtils.nextInt(1, 3),
                    time,
                    time
                    );
            statement.addBatch(sql);
            if (i % 10000 == 0){
                statement.executeBatch();
                connection.commit();
                System.out.println("提交成功...." + i);
            }
        }
        statement.executeBatch();
        connection.commit();
        statement.close();
        connection.close();
        System.out.println("总消耗时间:" + (System.currentTimeMillis() - start) / 1000);
    }
}
