package ray.shaw.Audio;

import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.Scanner;

import com.sun.media.rtsp.Connection;

import ray.shaw.Bean.InfoBean;
import ray.shaw.Thread.PlayerThread;
import ray.shaw.Thread.RecivedThread;

public class Main {
	//接收端口1423
	//发送端口1234
	public static void main(String[] args) {
		Queue<InfoBean> queue = new ArrayDeque<InfoBean>();
		RecivedThread rt = new RecivedThread(queue);
		PlayerThread pt = new PlayerThread(queue);
		Tool tool = new Tool();
		tool.connection();
		rt.start();
		pt.start();
		Scanner s = new Scanner(System.in);
		String quit = s.nextLine();
		System.out.println("main");
		if(quit.equals("quit"))
		{
			rt.setFlag(false);
			pt.setFlag(false);
			s.close();
			tool.connection();
		}
	}
	
}


class Tool
{
	public Tool(){}
	
	protected  void connection()
	{
		//发送链接请求数据报
		DatagramPacket dp = null;
		DatagramSocket ds = null;
		byte[] data = null;
		Properties pro = null;
		String ip = null;
		try
		{
			data = new byte[20];
			ip = InetAddress.getLocalHost().toString();
			ip = ip.substring(ip.lastIndexOf("/")+1)+".0";
			data = ip.getBytes();
			System.out.println(ip);
			dp = new DatagramPacket(data, data.length);
			dp.setPort(1342);	//设置服务端接收端口号
			pro = new Properties();
			pro.load(new FileInputStream(this.getClass().getResource("/").getPath()+"settings.properties"));
			ds = new DatagramSocket(1324);	//设置客户端发送端口
			dp.setAddress(InetAddress.getByName(pro.getProperty("remoteIP")));
			ds.send(dp);
			System.out.println("remote IP is " + pro.getProperty("remoteIP"));
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
