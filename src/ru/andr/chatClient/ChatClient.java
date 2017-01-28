package ru.andr.chatClient;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JTextArea;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JScrollBar;

public class ChatClient extends JFrame {

	private JPanel contentPane;
	BufferedReader reader;
	PrintWriter writer;
	Socket sock;
	JTextArea outText;
	JTextArea inText;
	String nick;
	
	
	public ChatClient(String nick,String ip,int port) {
		setTitle("Чат-Клиент");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.nick=nick;
		setSize(450,600);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("Выход");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				writer.println(nick+"/"+"вышел из сети");
				writer.flush();
				sock.close();
				}catch(Exception ex){ex.printStackTrace();}
				System.exit(0);
			}
		});
		button.setBounds(324, 514, 89, 23);
		contentPane.add(button);
		
		inText = new JTextArea();
		inText.setLineWrap(true);
		inText.setWrapStyleWord(true);
		inText.setFont(new Font("Monospaced", Font.ITALIC, 15));
		
		
		
		JLabel label = new JLabel("Чат");
		label.setFont(new Font("Tahoma", Font.ITALIC, 25));
		label.setBounds(162, 11, 56, 20);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Текс:");
		label_1.setFont(new Font("Tahoma", Font.ITALIC, 17));
		label_1.setBounds(10, 288, 74, 14);
		contentPane.add(label_1);
		
		JButton send = new JButton("Отправить");
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					writer.println(nick+"/"+inText.getText());
					writer.flush();
				}catch(Exception ex){ex.printStackTrace();}
				inText.setText("");
				inText.requestFocus();
			}
		});
		send.setBounds(147, 449, 127, 37);
		contentPane.add(send);
		
		outText = new JTextArea();
		outText.setEditable(false);
		outText.setFont(new Font("Monospaced", Font.ITALIC, 13));
		outText.setWrapStyleWord(true);
		outText.setLineWrap(true);
		outText.setBounds(10, 42, 403, 235);
		
		
		JScrollPane qScroll=new JScrollPane(outText);
		qScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		qScroll.setBounds(10, 42, 403, 235);
		contentPane.add(qScroll);
		
		JScrollPane aScroll=new JScrollPane(inText);
		aScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		aScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		aScroll.setBounds(10, 313, 403, 125);
		contentPane.add(aScroll);
		
        setNetworkingUp(ip,port,nick);
		
		Thread readerThread=new Thread(new IncomingReader());
		readerThread.start();
	}
	
	public void keyPressed(KeyEvent e){
		int key=e.getKeyCode();
		if(key==KeyEvent.VK_ENTER){
			try{
				writer.println(nick+"/"+inText.getText());
				writer.flush();
			}catch(Exception ex){ex.printStackTrace();}
			inText.setText("");
			inText.requestFocus();
		}
	}
	
	public void setNetworkingUp(String ip, int port, String nick){
		try{
			sock=new Socket(ip,port);
			InputStreamReader streamReader=new InputStreamReader(sock.getInputStream());
			reader=new BufferedReader(streamReader);
			writer=new PrintWriter(sock.getOutputStream());
			outText.append("Вход... \n");
			writer.println(nick+"/"+"вошел в сеть");
			writer.flush();
		}catch(IOException ex){ex.printStackTrace();}
	}
	
	public class IncomingReader implements Runnable{
		public void run(){
			String message;
			try{
				while((message=reader.readLine())!=null){
					outText.append(message+"\n");
				}
			}catch(Exception ex){ex.printStackTrace();}
		}
	}
}
