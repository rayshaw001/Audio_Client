package ray.shaw.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Test {

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
		String ip = InetAddress.getLocalHost().toString();
		System.out.println(ip.substring(ip.lastIndexOf('/')+1));
	}

}
