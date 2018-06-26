package server.util;

import java.util.HashMap;
import java.util.Iterator;

import common.User;
import server.backstage.ConnectClient;
import server.backstage.ConnectDataBase;

/**
 * 服务器端与客户端通信的线程集合类
 * @author Leon
 *	Server_Connect_Client_Thread_Collection
 */
public class ServerThreadCollection {

	private static HashMap<String, ConnectClient> hm = new HashMap<String, ConnectClient>();
	
	/**
	 * 添加服务器端与客户端通信的线程集合的方法
	 * @param Name 用户名
	 * @param serverConClient 服务器端与客户端通信的线程
	 */
	public static void addServerConnectClientThreadCollection(String Name, ConnectClient serverConClient){
		hm.put(Name, serverConClient);
	}
	
	/**
	 * 根据用户名返回服务器端与客户端通信的线程的方法
	 * @param Name 用户名
	 * @return 返回服务器端与客户端通信的线程
	 */
	public static ConnectClient getServerContinueConnetClient(String Name){
		return hm.get(Name);
	}
	
	/**
	 * 根据用户名移除服务器端与客户端通信的线程的方法
	 * 同时将其设置为没有登陆的状态
	 * @param Name
	 */
	public static void RemoveServerContinueConnetClient(String Name){
		ConnectDataBase connect_Database = new ConnectDataBase();
		User u = new User();
		u.setName(Name);
		//设置没有登陆的状态
		connect_Database.Update_IsLogin(u, 0);
		hm.remove(Name);
	}
	
	/**
	 * 遍历集合返回在线用户
	 * @return 带有在线用户的字符串
	 */
	public static String GetOnline(){
		String Online = "";
		Iterator<String> it  = hm.keySet().iterator();
		while(it.hasNext()){
			Online += it.next().toString()+" ";
		}
		return Online;
	}
}
