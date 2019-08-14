package com.blinkfox.fenix.starter;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 用于在 application.yml 文件中的配置属性类，定义前缀为 'fenix'.
 *
 * @author blinkfox on 2019-08-14.
 */
@Getter
@Setter
@ConfigurationProperties("fenix")
public class FenixProperties {

    /**
     * 是否开启调试模式，如果开启的话，每次调用都会实时从最新的xml文件中获取sql，默认值为false.
     */
    private boolean debug;

    /**
     * 是否打印zealot的启动banner，默认为true.
     */
    private boolean printBanner;

    /**
     * 是否打印zealot的sql日志，默认为true.
     */
    private boolean printSql;

    /**
     * Fenix 需要扫描的 XML 文件所在的位置，可以填写多个，多个用逗号隔开，可以是目录也可以是具体的 xml 文件，
     * 默认是 fenix 资源目录及子目录下的位置.
     */
    private List<String> xmlLocations;

    /**
     * Fenix 需要扫描的自定义 handler 处理器所在的位置，可以填写多个，多个用逗号隔开，
     * 可以是目录也可以是具体的 java 或 class 文件路径.
     */
    private List<String> handlerLocations;

    /**
     * 构造方法，构造对象时赋予默认值.
     */
    public FenixProperties() {
        this.printBanner = true;
        this.printSql = true;
    }

}
