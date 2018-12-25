package com.yunfeng.game.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtils {
    private static ApplicationContext applicationContext;

    private static void initSpringContext() {
        applicationContext = new ClassPathXmlApplicationContext("classpath:application.xml");
//        for (String bean : applicationContext.getBeanDefinitionNames()) {
//            System.out.println("bean: " + bean);
//        }
    }

    public static <T> T getBean(String beanName, Class<T> beanClass) {
        if (applicationContext == null) {
            initSpringContext();
        }
        return applicationContext.getBean(beanName, beanClass);
    }
}
