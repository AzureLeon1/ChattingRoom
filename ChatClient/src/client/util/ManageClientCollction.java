package client.util;

import java.util.HashMap;

import client.ui.Client_Frame;

/**
 * 管理群聊界面的集合
 * @author Leon
 *
 */
public class ManageClientCollction {
	private	static HashMap<String, Client_Frame> hm = new HashMap<String, Client_Frame>();
	
	/**
	 * 添加管理聊天室界面的集合
	 * @param user 用户
	 * @param client_Frame 聊天室界面
	 */
	public static void addClientCollction(String user,Client_Frame client_Frame){
		hm.put(user, client_Frame);
	}
	/**
	 * 根据用户返回聊天室界面
	 * @param user 用户
	 */
	public static Client_Frame GetClient_Frame(String user){
		return hm.get(user);
	}
}
