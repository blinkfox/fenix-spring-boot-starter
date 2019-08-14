package com.blinkfox.fenix.starter;

import com.blinkfox.fenix.config.FenixConfig;
import com.blinkfox.fenix.config.FenixConfigManager;
import com.blinkfox.fenix.config.entity.NormalConfig;
import com.blinkfox.fenix.helper.StringHelper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 用于测试在 SpringBoot 中使用该 Fenix Spring Boot Starter 的测试类.
 *
 * @author blinkfox on 2018-08-14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FenixStarterApplicationTest {

    /**
     * 测试 Fenix 的配置加载情况是否正确.
     */
    @Test
    public void testFenixConfig() {
        // 测试 normalConfig.
        NormalConfig normalConfig = NormalConfig.getInstance();
        Assert.assertFalse(normalConfig.isDebug());
        Assert.assertTrue(normalConfig.isPrintBanner());
        Assert.assertFalse(normalConfig.isPrintSqlInfo());

        // 测试扫描位置的配置.
        FenixConfigManager configManager = FenixConfigManager.getInstance();
        Assert.assertEquals("fenix,myxml", configManager.getXmlLocations());
        Assert.assertTrue(StringHelper.isNotBlank(configManager.getHandlerLocations()));

        // 测试 xml 和 handler 的扫描的结果是否正确存储到了内存中.
        Assert.assertFalse(FenixConfig.getTagHandlerMap().isEmpty());
    }

}