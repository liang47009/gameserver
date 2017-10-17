package com.yunfeng.game.util;

import java.util.Random;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yunfeng.game.transfer.DataTransfer;
import com.yunfeng.game.transfer.StringData;

public class AppUtils {

	private static ApplicationContext context = null;

	public static final void init() {
		if (context == null) {
			context = new ClassPathXmlApplicationContext("application.xml");
		}
		// SessionFactory sessionFactory = (SessionFactory) context
		// .getBean("sessionFactory");
	}

	public static final SessionFactory getSessionFactory() {
		if (context != null) {
			return (SessionFactory) context.getBean("sessionFactory");
		}
		return null;
	}

	private static final Random random = new Random();

	public static void random(DataTransfer dt) {
		byte[] arr = new byte[2];
		random.nextBytes(arr);
		dt.setMid((byte) 1);
		dt.setPid(arr[1]);
		StringData data = new StringData();
		dt.setData(data);
		String msg = "hello world!@!";
		data.setMsg(msg);
		data.setLength(msg.getBytes().length);
		int len = 4 + 1 + 1 + data.getLength();
		dt.setLen(len);
	}

}
