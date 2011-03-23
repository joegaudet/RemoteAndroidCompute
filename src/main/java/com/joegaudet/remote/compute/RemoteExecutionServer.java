package com.joegaudet.remote.compute;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.serialize.RemoteObjectDeserializer;

/**
 * Basic bones taken from:
 * 
 * http://rox-xmlrpc.sourceforge.net/niotut/
 * 
 * @author joe
 * 
 */
public class RemoteExecutionServer implements Runnable {

	// The host:port combination to listen on
	private String hostAddress;
	private int port;

	private ServerSocketChannel serverChannel;

	private Map<Integer, RemoteObject> storedObjects;

	public RemoteExecutionServer(String hostAddress, int port) throws IOException {
		this.hostAddress = hostAddress;
		this.port = port;
		storedObjects = new HashMap<Integer, RemoteObject>();
		
		serverChannel = ServerSocketChannel.open();
		serverChannel.socket().bind(new InetSocketAddress(hostAddress, port));
		
	}

	@Override
	public void run() {
		System.out.println("Listening.");
		while(true){
			try {
				SocketChannel channel = serverChannel.accept();
				if(channel != null){
					RemoteObject readObject = new RemoteObjectDeserializer().readObjectFromChannel(channel);
					System.out.println("Read Object: " + readObject.getClass());
					System.out.println(readObject.toString());
					channel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static void main(String[] args) throws IOException {
		Thread thread = new Thread(new RemoteExecutionServer("localhost", 5678));
		thread.start();
	}
}
