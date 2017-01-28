package ru.andr.chatServer;

import java.io.*;
import java.net.*;
import java.util.*;


public class Server {
	
ArrayList clientOutputStreams;

	
	public class ClientHandler implements Runnable{
		BufferedReader reader;
		Socket sock;
		
		public ClientHandler(Socket clientSocket){
			try{
				sock=clientSocket;
				InputStreamReader isReader=new InputStreamReader(sock.getInputStream());
				reader=new BufferedReader(isReader); 
			}catch(Exception ex){ex.printStackTrace();}
		}
		
		public void run(){
			String message;
			String[] nick;
			try{
				while((message=reader.readLine())!=null){
					nick=message.split("/");
					System.out.println("(Написал) "+nick[0]+": " +nick[1]);
					tellEveryone(nick[0],nick[1]);
				}
			}catch(Exception ex){ex.printStackTrace();}
		}
	}

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("===============Добро пожаловать в сервер чата===============\n");
		System.out.println("\n");
		System.out.print("Введите порт: ");
		System.out.println("\n");
		int port=sc.nextInt();
		new Server().go(port);
	}
	
	public void go(int port){
		clientOutputStreams=new ArrayList();
		try{
			ServerSocket serverSock=new ServerSocket(port);
			
			while(true){
				Socket clientSocket=serverSock.accept();
				PrintWriter writer=new PrintWriter(clientSocket.getOutputStream());
				clientOutputStreams.add(writer);
				
				Thread t=new Thread(new ClientHandler(clientSocket));
				t.start();
				
			}
		}catch(Exception ex){ex.printStackTrace();}
	}
	
	public void tellEveryone(String nick,String message){
		
		Iterator it=clientOutputStreams.iterator();
		while(it.hasNext()){
			try{
				PrintWriter writer=(PrintWriter)it.next();
				writer.println(nick+": "+message);
				writer.flush();
			}catch(Exception ex){ex.printStackTrace();}
		}
	}

}
