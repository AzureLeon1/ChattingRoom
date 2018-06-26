package client.util;

import java.util.HashMap;

import client.backstage.ConnectServer;


/**
 * 客户端与服务器端继续通信的线程集合类
 * @author Leon
 * Client_Connect_Server_Thread_Collection
 */
public class ClientThreadCollection {
	private	static HashMap<String, ConnectServer> hm = new HashMap<String, ConnectServer>();
	
	/**
	 * 添加客户端与服务器端继续通信的线程的方法
	 * @param Name 用户名
	 * @param clientConServer 客户端与服务器端继续通信的线程
	 */
	public static void addClientThreadCollection(String Name,ConnectServer clientConServer ){
		hm.put(Name, clientConServer);
	}
	
	/**
	 * 根据用户名返回客户端与服务器端继续通信的线程的方法
	 * @param Name 用户名
	 * @return	返回客户端与服务器端继续通信的线程
	 */
	public static ConnectServer getClientThreadCollection(String Name){
		return hm.get(Name);
	}
}
