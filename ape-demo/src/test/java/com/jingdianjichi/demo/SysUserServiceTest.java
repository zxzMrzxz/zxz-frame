package com.jingdianjichi.demo;

import com.jingdianjichi.user.DemoApplication;
import com.jingdianjichi.user.entity.po.SysUser;
import com.jingdianjichi.user.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.support.AopUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class SysUserServiceTest {

    @Resource
    private SysUserService sysUserService;

    @Test
    public void testQuery() {
        SysUser sysUser = sysUserService.queryById(1L);
        System.out.println(sysUser);
    }

    @Test
    public void testAopUtils() {
        Class<?> targetClass = AopUtils.getTargetClass(sysUserService);
        System.out.println(targetClass);
    }

}
