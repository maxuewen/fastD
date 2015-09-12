package cn.zh.Utils;


public class myUUID {
	public static String getUUID(){
		return java.util.UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
}
