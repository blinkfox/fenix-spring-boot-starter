package com.blinkfox.fenix.starter;

import com.blinkfox.fenix.config.FenixConfig;
import com.blinkfox.fenix.config.FenixConfigManager;
import com.blinkfox.fenix.helper.StringHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 用于测试在 SpringBoot 中使用该 Fenix Spring Boot Starter 的测试类.
 *
 * @author blinkfox on 2018-08-14.
 */
@SpringBootTest
class FenixStarterApplicationTest {

    /**
     * 测试 Fenix 的配置加载情况是否正确.
     */
    @Test
    void testFenixConfig() {
        // 测试常规配置.
        FenixConfig fenixConfig = FenixConfigManager.getInstance().getFenixConfig();
        Assertions.assertTrue(fenixConfig.isPrintBanner());
        Assertions.assertFalse(fenixConfig.isPrintSqlInfo());

        // 测试扫描位置的配置.
        Assertions.assertEquals("fenix,myxml", fenixConfig.getXmlLocations());
        Assertions.assertTrue(StringHelper.isNotBlank(fenixConfig.getHandlerLocations()));

        // 测试 xml 和 handler 的扫描的结果是否正确存储到了内存中.
        Assertions.assertFalse(FenixConfig.getTagHandlerMap().isEmpty());
    }

}
