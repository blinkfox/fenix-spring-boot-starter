package com.blinkfox.fenix.starter;

import com.blinkfox.fenix.ar.RepositoryModelContext;
import com.blinkfox.fenix.config.FenixConfig;
import com.blinkfox.fenix.config.FenixConfigManager;
import com.blinkfox.fenix.consts.Const;
import com.blinkfox.fenix.specification.handler.AbstractPredicateHandler;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

/**
 * Fenix 自动配置类.
 *
 * @author blinkfox on 2019-08-14.
 * @see FenixProperties
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(FenixProperties.class)
public class FenixAutoConfiguration {

    private final ApplicationContext applicationContext;

    /**
     * {@link FenixProperties} 属性配置类的实例.
     */
    private final FenixProperties properties;

    /**
     * 构造方法注入.
     *
     * @param applicationContext Spring 应用上下文
     * @param properties {@link FenixProperties} 的属性配置对象
     */
    @Autowired
    public FenixAutoConfiguration(ApplicationContext applicationContext, FenixProperties properties) {
        if (applicationContext == null) {
            throw new ApplicationContextException("【Fenix 异常】未获取到 Spring 中的 applicationContext 对象!");
        }
        this.applicationContext = applicationContext;
        this.properties = properties;
        this.doConfig();
    }

    /**
     * 根据 properties 中的配置项做 Fenix 的配置.
     */
    private void doConfig() {
        // 获取配置值.
        Boolean debug = this.properties.getDebug();
        Boolean printBanner = this.properties.getPrintBanner();
        Boolean printSql = this.properties.getPrintSql();
        Boolean showJpaSql = this.properties.getShowJpaSql();
        List<String> xmlLocations = this.properties.getXmlLocations();
        List<String> handlerLocations = this.properties.getHandlerLocations();

        // 配置常规信息、 xml 和 handler 的扫描路径.
        // 如果未配置 printSql，则使用 jpa 中 show-sql 的配置值，否则使用 printSql 的值.
        FenixConfigManager.getInstance().initLoad(new FenixConfig()
                .setDebug(Boolean.TRUE.equals(debug))
                .setPrintBanner(printBanner == null || Boolean.TRUE.equals(printBanner))
                .setPrintSqlInfo(printSql == null ? Boolean.TRUE.equals(showJpaSql) : Boolean.TRUE.equals(printSql))
                .setXmlLocations(CollectionUtils.isEmpty(xmlLocations) ? null : String.join(Const.COMMA, xmlLocations))
                .setHandlerLocations(CollectionUtils.isEmpty(handlerLocations) ? null
                        : String.join(Const.COMMA, handlerLocations)));

        // 设置 Spring 应用上下文到 RepositoryModelContext 类中.
        RepositoryModelContext.setApplicationContext(applicationContext);

        // 如果自定义的 predicateHandlers 不为空，就初始化设置值到 配置信息中.
        List<String> predicateHandlers = this.properties.getPredicateHandlers();
        if (!CollectionUtils.isEmpty(predicateHandlers)) {
            for (String handlerName : predicateHandlers) {
                AbstractPredicateHandler handler = this.newHandlerInstance(handlerName.trim());
                if (handler != null) {
                    FenixConfig.add(handler);
                }
            }
        }
    }

    /**
     * 根据全路径名称实例化对象，并转换为 {@link AbstractPredicateHandler} 类型的实例.
     *
     * @param handlerName {@link AbstractPredicateHandler} 子类的全路径名
     * @return {@link AbstractPredicateHandler} 类型的实例
     */
    private AbstractPredicateHandler newHandlerInstance(String handlerName) {
        try {
            Object instance = Class.forName(handlerName).getDeclaredConstructor().newInstance();
            return instance instanceof AbstractPredicateHandler ? (AbstractPredicateHandler) instance : null;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException | ClassNotFoundException e) {
            log.error("【Fenix 异常】实例化【{}】类的对象失败，将忽略此类的实例化。", handlerName, e);
            return null;
        }
    }

}
