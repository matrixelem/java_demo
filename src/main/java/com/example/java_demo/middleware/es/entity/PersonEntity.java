package com.example.java_demo.middleware.es.entity;

import lombok.Data;

@Data
public class PersonEntity {

    private Long id;
    private String name;
    private Integer age;
    private String address;
    private String phone;
    private String email;
    private Long  zoneId;
    private String zoneUuid;
    private Integer sex;
    private Long orgId;
    private String orgUuid;
    private String ext;
}
