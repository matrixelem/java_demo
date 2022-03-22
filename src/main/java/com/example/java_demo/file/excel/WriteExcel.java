package com.example.java_demo.file.excel;

import com.alibaba.excel.EasyExcel;
import com.example.java_demo.middleware.mysql.DbUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WriteExcel {

    public static void main(String[] args) throws Exception {
        String filename = "D:\\test\\employee_template (15).xlsx";

        Connection connection = DbUtils.getConnection(null, null, null);
        String sql = "select name from middle_end_base.person order by name_spell, gmt_create desc limit 100000";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<PersonDto> names = new ArrayList<>();
        while (rs.next()){
            String code = rs.getString("name");
            PersonDto dto = new PersonDto();
            dto.setName(code);
            names.add(dto);
        }
        rs.close();
        statement.close();
        connection.close();

        EasyExcel.write(filename, PersonDto.class).sheet(1).doWrite(names);

    }
}
