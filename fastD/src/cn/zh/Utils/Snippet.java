package cn.zh.Utils;

import java.util.UUID;

public class Snippet {
	public static String uuid() {
			return UUID.randomUUID().toString().replace("-", "").toUpperCase();
		}
}

