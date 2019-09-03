import java.net.*;
import java.io.*;
import java.util.*;
// import ./.*;

// class Client
// {
	// String client_name;
	// int client_port;
	
	// public Client()
	// {
		
	// }
	
	// public Client(String name,int port)
	// {
		// client_name = name;
		// client_port = port;
	// }
// }

public class Server implements Runnable
{
    int clientport=10,serverport=20;
    DatagramSocket ds;
    Thread recthread;
	DatagramPacket dp;
	BufferedReader br;
	HashMap<Integer,String> clients = new HashMap<Integer,String>(); 
    public Server()throws IOException
    {
        ds=new DatagramSocket(serverport);
        br=new BufferedReader(new InputStreamReader(System.in));
        recthread=new Thread(this);
        recthread.start();
        // while(true)
        // { 
            // String data=br.readLine();
            // if(data.equals("EXIT"))
                // break;
             // dp=new DatagramPacket(data.getBytes(),data.length(),InetAddress.getLocalHost(),clientport);
            // ds.send(dp);
        // }
        // ds.close();
    }
	
	void send_mesg(String data,int port) throws IOException
	{
		dp=new DatagramPacket(data.getBytes(),data.length(),InetAddress.getLocalHost(),port);
        ds.send(dp);
	}
	
	void do_Work(String data,int port)
	{
		 // = new String(b,0,dp.getLength());
		// int port = dp.getPort();
		int index = data.indexOf(' ');
		if(index == -1 || data.equals("live"))
		{
			String msg = "";
			for(Integer i : clients.keySet())
			{
				msg = msg + " Port No " + i + " Person Name " + clients.get(i);
			}
			try {
			  send_mesg(msg,port);
			}
			catch(Exception e) {
			}
			
		}
		else
		{
			String command = data.substring(0,index);
			String message = "";
			if(command.equals("add"))
			{
				clients.put(new Integer(port),data.substring(index+1,data.length()));
				try {
					message = "You are added with port no " + port;
				  send_mesg(message,port);
				}
				catch(Exception e) {
				}
					
				}
			else if(command.equals("msg"))
			{
				int ind = data.indexOf(' ',index+1);
				int sending_port = Integer.valueOf(data.substring(index+1,ind));
				message = data.substring(ind+1,data.length());
				try {
				  send_mesg(message,sending_port);
				}
				catch(Exception e) {
				}
					
				}
				
			}
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
				String data = new String(b,0,dp.getLength());
				do_Work(data,dp.getPort());
				// String command = data.indexOf(' ');
				// System.out.println("Server:"+data+dp.getPort());
            }
            catch(Exception e)
            {
                
            }
        }
    }
	
    public static void main(String ar[])throws IOException
    {
       new Server();
    }
}