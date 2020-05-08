package com.blinkfox.fenix.starter;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 用于在 application.yml 文件中的配置属性类，定义前缀为 'fenix'.
 *
 * @author blinkfox on 2019-08-14.
 */
@Getter
@Setter
@ConfigurationProperties("fenix")
class FenixProperties {

    /**
     * 是否打印 Fenix 加载完配置信息后的启动 banner，默认为 true.
     */
    private Boolean printBanner;

    /**
     * 是否打印 Fenix 的 SQL 的日志（INFO 级别），默认为 true.
     */
    private Boolean printSql;

    /**
     * 读取 Spring Data JPA Starter 中是否显示 SQL 的配置项.
     */
    @Value("${spring.jpa.show-sql:#{null}}")
    private Boolean showJpaSql;

    /**
     * 需要扫描的 XML 文件的位置，可以是目录也可以是具体的 XML 文件，不配置的话默认是 fenix 资源目录及其子目录下的位置，可以填写多个值.
     */
    private List<String> xmlLocations;

    /**
     * 需要扫描的自定义 handler 处理器所在的位置，可以是目录也可以是具体的 java 或 class 文件全路径，可以填写多个值.
     */
    private List<String> handlerLocations;

    /**
     * 继承了 {@link com.blinkfox.fenix.specification.handler.AbstractPredicateHandler} 抽象类的自定义的 handler 处理器的全路径名的集合.
     *
     * @since v2.2.0
     */
    private List<String> predicateHandlers;

}
