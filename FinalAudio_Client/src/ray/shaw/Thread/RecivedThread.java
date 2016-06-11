package ray.shaw.Thread;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.GregorianCalendar;
import java.util.Queue;

import ray.shaw.Bean.InfoBean;

public class RecivedThread extends Thread{
	private Queue<InfoBean> queue ;
	private boolean flag = true;
	public RecivedThread()
	{
	}
	public RecivedThread(Queue<InfoBean> queue)
	{
		this.setQueue(queue);
	}
	
	@Override
	public void run()
	{	
		//接收数据
		DatagramPacket dp = null;
		DatagramSocket ds = null;
		
		//写数据
		RandomAccessFile file = null; 
		byte[] data = null;
		String filename = null;
		GregorianCalendar gc = null;
		long length = 0;
		
		//写入队列
		InfoBean bean = null;
		
		//文件传送结束标记
		String eof[] = null;
		
		//调试标记
		int writetimes = 0;
		if(queue==null)
		{
			try 
			{
				throw new Exception("please set Queue<InfoBean> before start this thread");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				data = new byte[1024];
				ds = new DatagramSocket(1423);
				dp = new DatagramPacket(data, 1024);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			while(flag)
			{
				try {
					if(file == null)
					{
						//新的文件名
						gc = new GregorianCalendar();
						gc.add(GregorianCalendar.SECOND, 1);
						while(gc.after(new GregorianCalendar()));
						filename = "C:\\Sync\\" + gc.get(GregorianCalendar.YEAR) +"" +
								gc.get(GregorianCalendar.MONTH) +"" +
								gc.get(GregorianCalendar.DAY_OF_MONTH) +"" +
								gc.get(GregorianCalendar.HOUR) + "" +
								gc.get(GregorianCalendar.MINUTE) +"" +
								gc.get(GregorianCalendar.SECOND) +"" +
								gc.get(GregorianCalendar.MILLISECOND) + "" +
								".wav";
						file = new RandomAccessFile(filename, "rw");
					}
					writetimes = 0;
					while(file!=null)
					{
						System.out.println("write times is " + writetimes++);
						ds.receive(dp);
						data = dp.getData();
						length = file.length();
						file.seek(length);
						file.write(data);
						if(new String(data).contains("exit:"))
						{
							System.out.println("reciving ending");
							System.out.println("final data is " + new String(data));
							//exit:2016:01:01:24:59:59:1111
							bean = new InfoBean();
							bean.setFilePath(filename);
							eof = new String(data).split(":");
							gc = new GregorianCalendar();
							gc.set(GregorianCalendar.YEAR, Integer.valueOf(eof[1]));
							gc.set(GregorianCalendar.MONTH, Integer.valueOf(eof[2]));
							gc.set(GregorianCalendar.DAY_OF_MONTH, Integer.valueOf(eof[3]));
							gc.set(GregorianCalendar.HOUR_OF_DAY, Integer.valueOf(eof[4]));
							gc.set(GregorianCalendar.MINUTE, Integer.valueOf(eof[5]));
							gc.set(GregorianCalendar.SECOND, Integer.valueOf(eof[6]));
							gc.set(GregorianCalendar.MILLISECOND, Integer.valueOf(eof[7].substring(0, 4)));
							bean.setTimeLine(gc);
							queue.add(bean);
							System.out.println("after written queue size is " + queue.size());
							file.close();
							gc = null;
							file = null;
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public Queue<InfoBean> getQueue() {
		return queue;
	}
	public void setQueue(Queue<InfoBean> queue) {
		this.queue = queue;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
