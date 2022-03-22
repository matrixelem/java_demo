package com.example.java_demo.middleware.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QueryTimeTest {

    public static void main(String[] args) throws Exception {
       new QueryTimeTest().query();
    }

    public void query() throws Exception {
        String enc = "huangwei123";

        String url = "jdbc:mysql://10.171.5.126:3306/allinone?useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true&useServerPrepStmts=false";
        String user = "root";
        String pwd = "lzQ65DtDOE*";
        Connection connection = DbUtils.getConnection(url, user, pwd);

        String sql = "SELECT `uuid`, `name`, `name_spell`, `image_uri`, `type`, `code`, `identify_num`, `org_id`, `company_id`, `sex`, `visit_type`, `visit_start_time`, `visit_end_time`, `visit_reason`, `email`, `phone`, `card_num`, `password`, `entry_time`, `postion`, `ext`, `gmt_modified`, `gmt_create`, `visited`, `birthday`, `unique_identify`, `visit_firm`, `visit_num_plate` " +
                "from person1";

//        String sql = "SELECT " +
//                "`uuid`, `name`, `name_spell`, `image_uri`, `type`, AES_DECRYPT(UNHEX(code),'Vr0L23hUHIu') AS `code`, AES_DECRYPT(UNHEX(email),'Vr0L23hUHIu') as email, `identify_num`,\n" +
//                "`org_id`, `company_id`, `sex`, `visit_type`, `visit_start_time`, `visit_end_time`, `visit_reason`, `email`, AES_DECRYPT(UNHEX(phone),'Vr0L23hUHIu') as phone, `card_num`, `password`, `entry_time`, `postion`, `ext`, `gmt_modified`, `gmt_create`, `visited`, `birthday`, `unique_identify`, `visit_firm`, `visit_num_plate` " +
//                "FROM person1";
        PreparedStatement ps = connection.prepareStatement(sql);

        long start = System.currentTimeMillis();
        boolean resultSet = ps.execute();
        long t = System.currentTimeMillis() - start;
        System.out.println(String.format("执行结果：%s 执行时间：%d", resultSet, t));




        ps.close();
        connection.close();

    }

}
