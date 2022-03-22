package com.example.java_demo.file;


import com.example.java_demo.middleware.mysql.DbUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件批量重命名
 */
public class FileBatchRename {
    public static void main(String[] args) {
        try {
            reName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 1. 遍历得到所有路径下的文件
     * 2. 得到100个员工编号
     * 3. 将其中的100个文件重命名
     */
    public static void reName() throws Exception{
        //待重名的文件路径
        String oriFilePath = "D:\\test\\photo.jpg";
        //输出文件路径
        String outPutPath = "D:\\test\\66555";

        List<String> codeList = new ArrayList<>();

        String url = "jdbc:mysql://10.122.100.7:3306/middle_end_base?useUnicode=true&characterEncoding=utf8";
        String user = "root";
        String pwd = "Dx3LEIomfHL";
        Connection connection = DbUtils.getConnection(url, user, pwd);
        String sql = "select code from middle_end_base.person order by name_spell desc limit 100000";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            String code = rs.getString("code");
            codeList.add(code);
        }
        rs.close();
        statement.close();
        connection.close();

        codeList.forEach(item -> {
            System.out.println(item);
            try {
                Files.copy(Paths.get(oriFilePath), Paths.get(outPutPath, item+".jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
