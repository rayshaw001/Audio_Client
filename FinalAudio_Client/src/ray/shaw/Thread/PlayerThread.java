package ray.shaw.Thread;

import java.net.URL;
import java.util.GregorianCalendar;
import java.util.Queue;

import javax.media.Manager;
import javax.media.Player;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import ray.shaw.Bean.InfoBean;

public class PlayerThread extends Thread{
	//原始资源
	private Queue<InfoBean> queue = null;
	private boolean flag = true;
	
	public PlayerThread(Queue<InfoBean> queue)
	{
		this.queue = queue;
	}
	@Override
	public void run()
	{
		//播放资源
		Clip clip = null ;
		AudioInputStream ais =null;
		//FileInputStream fis = null;
		InfoBean bean = null;
		Player player = null;
		URL url = null;
		
		//时间控制
		GregorianCalendar gc = null;
		int second =0;
		int millsecond = 0;
		System.out.println("start playing");
		while(flag)
		{
			
			gc = new GregorianCalendar();
			gc.add(GregorianCalendar.MILLISECOND, 1);
			while(gc.after(new GregorianCalendar()))
			{
				
			}
			
			//System.out.println("after written queue size is " + queue.size());
			if(queue!=null&&queue.size()>0)
			{
				System.out.println("start playing");
				bean = queue.peek();
				try
				{
					clip = AudioSystem.getClip();
					System.out.println(bean.getFilePath());
					url = new URL("file:" + bean.getFilePath());
					ais = AudioSystem.getAudioInputStream(url);
					clip.open(ais);
					System.out.println("after " + bean.getTimeLine().getTime());
					System.out.println("before " + new GregorianCalendar().getTime());
					while(bean.getTimeLine().after(new GregorianCalendar())){};
					gc = new GregorianCalendar();
					second = (int)(clip.getMicrosecondLength() / 1000000);
					millsecond = (int)((clip.getMicrosecondLength() % 1000000) / 1000);
					gc.add(GregorianCalendar.MILLISECOND,millsecond);
					gc.add(GregorianCalendar.SECOND,second);
					clip.start();
					System.out.println(gc.getTime());
					//player = Manager.createPlayer(url);
					//player.start();
					
					while(gc.after(new GregorianCalendar()))
					{
						
					}
					queue.poll();
				}
				catch(Exception e)
				{
					e.printStackTrace();
					//queue.poll();
				}
			}
			else
			{
				if(queue==null)
				{
					try {
						throw new Exception("play queue hasn't been initialized");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			}
			
		}
	}
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Queue<InfoBean> getQueue() {
		return queue;
	}

	public void setQueue(Queue<InfoBean> queue) {
		this.queue = queue;
	}
	
}
