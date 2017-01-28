package ru.andr.chatClient;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField nick;
	private JTextField adresIP;
	private JTextField port;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setTitle("Вход");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,500);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("Выход");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		button.setBounds(195, 438, 89, 23);
		contentPane.add(button);
		
		JLabel label = new JLabel("Ник:");
		label.setFont(new Font("Tahoma", Font.BOLD, 13));
		label.setBounds(124, 60, 46, 14);
		contentPane.add(label);
		
		nick = new JTextField();
		nick.setFont(new Font("Tahoma", Font.BOLD, 15));
		nick.setBounds(81, 85, 119, 20);
		contentPane.add(nick);
		nick.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("       Добро пожаловать");
		lblNewLabel.setFont(new Font("Sylfaen", Font.ITALIC, 20));
		lblNewLabel.setBounds(20, 11, 240, 28);
		contentPane.add(lblNewLabel);
		
		JLabel lblIp = new JLabel("IP адрес:");
		lblIp.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIp.setBounds(121, 146, 79, 14);
		contentPane.add(lblIp);
		
		adresIP = new JTextField();
		adresIP.setFont(new Font("Tahoma", Font.BOLD, 11));
		adresIP.setBounds(61, 171, 184, 20);
		contentPane.add(adresIP);
		adresIP.setColumns(10);
		
		JLabel label_1 = new JLabel("Порт:");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_1.setBounds(121, 241, 46, 14);
		contentPane.add(label_1);
		
		port = new JTextField();
		port.setFont(new Font("Tahoma", Font.BOLD, 11));
		port.setBounds(100, 266, 86, 20);
		contentPane.add(port);
		port.setColumns(10);
		
		JButton button_1 = new JButton("Вход");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int portAdr=Integer.parseInt(port.getText());
				dispose();
				new ChatClient(nick.getText(),adresIP.getText(), portAdr);
			}
		});
		button_1.setBounds(81, 333, 135, 38);
		contentPane.add(button_1);
	}
}
