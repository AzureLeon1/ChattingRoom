package server.backstage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import common.ChatMessage;
import common.MessageCode;
import server.util.ServerThreadCollection;
import server.ui.ServerFrame;

/**
 * 登陆成功后继续与客户端通信的服务器后台线程类
 * @author Leon
 *	Server_Continue_Connect_Client_Thread
 */
public class ConnectClient implements Runnable{
	private Socket s = null;
	private ObjectInputStream ois;
	private ObjectOutputStream os;
	private String UserName;
	private boolean isConnect = true;
	//将客户端的Socket传入
	public ConnectClient(Socket s,String UserName){
		this.s = s;
		this.UserName = UserName;
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(isConnect){
				//接受客户端继续发来的信息
				try{
					ois = new ObjectInputStream(s.getInputStream());
				}catch(SocketException e){
					ServerThreadCollection.RemoveServerContinueConnetClient(UserName);
					//更新服务器的在线用户
					ServerUpdataOnline();
					//通知其他人更新在线用户
					UpdataOnline();
					//关闭Socket
					s.close();
					e.printStackTrace();
					break;
				}
				ChatMessage mess = (ChatMessage)ois.readObject();
				//判断信息的类型，并进行转发处理
				ServerFrame.showMessage(mess);
				//如果是发给全部人的信息
				if(mess.getMessageType().equals(MessageCode.Common_Message_ToAll)){
					//获得在线用户
					String string = ServerThreadCollection.GetOnline();
					String[] strings = string.split(" ");
					String Name = null;
					for(int i=0;i<strings.length;i++){
						Name = strings[i];
						if(!mess.getSender().equals(Name)){
							//设置接收用户
							mess.setGetter(Name);
							//获得其他服务器端与客户端通信的线程
							ConnectClient sccc = ServerThreadCollection.getServerContinueConnetClient(Name);
							os = new ObjectOutputStream(sccc.s.getOutputStream());
							os.writeObject(mess);
						}
					}
				}else if(mess.getMessageType().equals(MessageCode.Common_Message_ToPerson)){
					//根据获得者取得服务器端与客户端通信的线程
					ConnectClient sccc = ServerThreadCollection.getServerContinueConnetClient(mess.getGetter());
					os = new ObjectOutputStream(sccc.s.getOutputStream());
					os.writeObject(mess);
				}else if(mess.getMessageType().equals(MessageCode.Send_FileToAll)){
					ServerManager.Send_SystemMessage("系统消息："+mess.getSender()+"给所有人发送了文件名为："+mess.getContent()+"的文件\r\n");
					ServerFrame.ShowSystemMessage("系统消息："+mess.getSender()+"给所有人发送了文件名为："+mess.getContent()+"的文件\r\n");
					//如果是发送文件给所有人
					SendFileToClient r = new SendFileToClient(mess,0);
					Thread t = new Thread(r);
					t.start();

				}else if(mess.getMessageType().equals(MessageCode.Send_FileToPerson)){
					//如果是发送给个人
					ServerFrame.ShowSystemMessage(mess.getSender()+"给"+mess.getGetter()+"发送了文件名为："+mess.getContent()+"的文件\r\n");
					SendFileToClient r = new SendFileToClient(mess,1);
					Thread t = new Thread(r);
					t.start();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 通知其他人用户名为在线用户要更新了
	 * @param Name 用户名
	 */
	public void UpdataOnline(){
		//获得在线用户
		String string = ServerThreadCollection.GetOnline();
		String[] strings = string.split(" ");
		for(int i=0;i<strings.length;i++){
			String Getter = strings[i];
			ChatMessage mess = new ChatMessage();
			//发送在线用户的名单
			mess.setContent(string);	
			mess.setGetter(Getter);
			mess.setMessageType(MessageCode.Send_Online);
			try {
				//取出每个服务器端与客户端通信的线程
				ConnectClient scc = ServerThreadCollection.getServerContinueConnetClient(Getter);
				if(scc!=null){
					os = new ObjectOutputStream(scc.s.getOutputStream());
					os.writeObject(mess);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
			
	}

	/**
	 * 服务器更新在线用户列表的方法
	 */
	public void ServerUpdataOnline(){
		//获得在线用户
		String string = ServerThreadCollection.GetOnline();
		//设置在线用户
		ServerFrame.SetOnLline(string);
	}
	
	

	public Socket getS() {
		return s;
	}


	public void setS(Socket s) {
		this.s = s;
	}
	
	/**
	 * 关闭线程序的方法
	 */
	public void CloseThread(){
		this.isConnect = false;
		try {
			this.s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
