package com.ebiz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.ebiz.dao")
@ServletComponentScan
public class EastEbiz {
    public static void main(String[] args) {
        SpringApplication.run(EastEbiz.class, args);
    }
/**
 *test  github
    devlop1 test

 * */

}


