package com.blinkfox.fenix.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Fenix 测试运行时需要的应用主入口启动类.
 *
 * <p>注：为了防止该文件打入正式包中，将其防止在 test 测试目录下.</p>
 *
 * @author blinkfox on 2019-08-14.
 */
@SpringBootApplication
public class FenixStarterApplication {

    /**
     * SpringBoot 应用程序主入口.
     *
     * @param args 数组参数
     */
    public static void main(String[] args) {
        SpringApplication.run(FenixStarterApplication.class, args);
    }

}