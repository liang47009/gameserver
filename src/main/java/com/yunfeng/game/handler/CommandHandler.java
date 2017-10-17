package com.yunfeng.game.handler;

import com.yunfeng.game.processor.UserProcessor;

public class CommandHandler {

	private static UserProcessor userProcessor;

	static {
		userProcessor = new UserProcessor();
	}

	public static void handlUserData(int id, long lid, String username,
			String password) {
		userProcessor.processUserData(id, lid, username, password);
	}
}
