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
            batchInsertPassRecord();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 普通批量插入写法
     * @throws Exception
     */
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
     * 使用数据库自带函数计算某些字段的批量插入写法
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

    /**
     * 批量插入通行记录
     * @throws Exception
     */
    public static void batchInsertPassRecord() throws Exception {
        //共掺入的数据总量
        int total = 10000000;
        String url = "jdbc:mysql://10.171.4.214:3306/allinone?useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true&useServerPrepStmts=false";
        String user = "root";
        String pwd = "gDt8UUX9dYA";
        Connection connection = DbUtils.getConnection(url, user, pwd);

        //49
        String sql = "INSERT INTO pass_record1 (uuid, device_id, device_location, device_model, device_name, device_status, device_type, " +
                "                                  device_set_id, device_uuid, gmt_create, gmt_modified, liveness_score, liveness_type, pass_time, " +
                "                                  pass_type, person_code, person_id, person_image_uri, person_name, person_type, person_uuid, playback_support," +
                "                                  recognition_score, recognition_type, record_source, record_type, snapshot_uri, verification_mode, zone_id, zone_name, zone_uuid, " +
                "                                  vistor_type, device_set_uuid, org_id, org_uuid, org_type, org_full_name, org_nick_name, org_parent_id, " +
                "                                  is_attendance, person_phone, person_identify_num, person_email, person_unique_identify, temperature, high_threshold, temperature_type, " +
                "                                  mask_type, healthy_state) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        connection.setAutoCommit(false);
        PreparedStatement ps = connection.prepareStatement(sql);
        long start = System.currentTimeMillis();
        for(int i= 0; i < total; i++){
            Timestamp timestamp = new Timestamp(LocalDateTime.now().minusDays(RandomUtils.nextInt(0, 20)).toInstant(ZoneOffset.of("+8")).toEpochMilli());
            //数据拼装
            ps.setString(1, UUID.randomUUID().toString());
            ps.setLong(2, RandomUtils.nextLong(1000, 1005));
            ps.setString(3, "辉煌");
            ps.setInt(4, RandomUtils.nextInt(1, 20));
            ps.setString(5, "设备名称");
            ps.setInt(6, 1);
            ps.setInt(7, RandomUtils.nextInt(1, 3));
            ps.setLong(8, RandomUtils.nextLong(1000, 3000));
            ps.setString(9, UUID.randomUUID().toString());
            ps.setTimestamp(10, timestamp);
            ps.setTimestamp(11, timestamp);
            ps.setInt(12, RandomUtils.nextInt(10, 30));
            ps.setInt(13, RandomUtils.nextInt(1, 4));
            ps.setTimestamp(14, timestamp);
            ps.setInt(15, RandomUtils.nextInt(1,3));
            ps.setString(16, RandomStringUtils.randomAlphabetic(8));//personcode
            ps.setLong(17, RandomUtils.nextLong(1000, 5000));
            ps.setString(18, RandomStringUtils.randomAlphabetic(30));
            ps.setString(19, RandomStringUtils.randomAlphabetic(5, 20));
            ps.setInt(20, RandomUtils.nextInt(1, 3));
            ps.setString(21, UUID.randomUUID().toString());
            ps.setInt(22, 1);
            ps.setInt(23, RandomUtils.nextInt(12, 30));
            ps.setInt(24, RandomUtils.nextInt(1,2));
            ps.setInt(25, 12);
            ps.setInt(26, 1);
            ps.setString(27, RandomStringUtils.randomAlphabetic(30));
            ps.setInt(28, RandomUtils.nextInt(1, 3));
            ps.setLong(29, RandomUtils.nextLong(1000, 1002));
            ps.setString(30, "dsdfdf");
            ps.setString(31, UUID.randomUUID().toString());
            ps.setInt(32, 1);
            ps.setString(33, UUID.randomUUID().toString());
            ps.setLong(34, RandomUtils.nextLong(1000, 1005));
            ps.setString(35, "juusudsdsd");
            ps.setInt(36, 1);
            ps.setString(37, "护士是的是的是的");
            ps.setString(38, "护士是的是的是的");
            ps.setLong(39, 1003);
            ps.setInt(40, 1);
            ps.setString(41, "17878743434");
            ps.setString(42, "17878743434");
            ps.setString(43, "17878743434");
            ps.setString(44, "kkkk");
            ps.setInt(45, 23);
            ps.setInt(46, 23);
            ps.setInt(47, 1);
            ps.setInt(48, 2);
            ps.setInt(49, 1);









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
        System.out.println("总消耗时间:" + (System.currentTimeMillis() - start));
    }
}
