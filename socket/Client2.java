import java.net.*;
import java.io.*;
import java.util.*;
// import ./.*;

// class Client
// {
	// String client_name;
	// int client_port;
	// String message;
	// public Client()
	// {
		
	// }
	
	// public Client(String name,int port,String msg)
	// {
		// client_name = name;
		// client_port = port;
		// message = msg;
	// }
// }

public class Client2 implements Runnable
{
    int clientport=30,serverport=20;
    DatagramSocket ds;
	DatagramPacket dp;
    Thread recThread;
	public Client2() throws Exception
    {
        ds = new DatagramSocket(clientport);
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        String client_name = br.readLine();
		client_name = "add " + client_name;
		dp = new DatagramPacket(client_name.getBytes(),client_name.length(),InetAddress.getLocalHost(),serverport);
        ds.send(dp);
		recThread=new Thread(this);
        recThread.start();
        while(true)
        {
            String data=br.readLine();
            if(data.equals("EXIT"))
                break;
            dp=new DatagramPacket(data.getBytes(),data.length(),InetAddress.getLocalHost(),serverport);
            ds.send(dp);
        }
        ds.close();
    }
	
    public void sends() throws Exception
    {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        while(true)
        {
            String data=br.readLine();
            if(data.equals("EXIT"))
                break;
            dp=new DatagramPacket(data.getBytes(),data.length(),InetAddress.getLocalHost(),serverport);
            ds.send(dp);
        }
        ds.close();
    }
	
	public void send_Object()
	{
		
	}
	
    public void run()
    {
        byte b[]=new byte[1000];
        while(true)
        {
            try
            {
            DatagramPacket dp=new DatagramPacket(b,b.length);
            ds.receive(dp);
            String data=new String(b,0,dp.getLength());
            System.out.println("Client:"+data);
            }
            catch(Exception e)
            {
                
            }
        }
    
    }
    public static void main(String ar[]) throws Exception
    {
        new Client2();
    }
}
