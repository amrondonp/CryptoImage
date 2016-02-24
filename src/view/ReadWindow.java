package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JButton;

import Model.ReadAndSend;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReadWindow extends JFrame{
	private JTextField textField;
	private final String image;
	public String message;
	private JTextField textField_1;
	public ReadWindow(final String image, final JTextArea text) {
		this.image = image;
		setBounds(650, 200, 450, 250);
		setTitle("Datos RSA");
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(50, 205, 50));
		panel.setBounds(10, 11, 402, 82);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Datos RSA");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 5, 382, 66);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Clave privada 'd'");
		lblNewLabel_1.setBounds(93, 108, 104, 21);
		getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setText("79843181");
		textField.setBounds(207, 108, 86, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblMdulon = new JLabel("M\u00F3dulo 'N'");
		lblMdulon.setBounds(93, 140, 74, 14);
		getContentPane().add(lblMdulon);
		
		textField_1 = new JTextField();
		textField_1.setText("100903027");
		textField_1.setColumns(10);
		textField_1.setBounds(207, 139, 86, 20);
		getContentPane().add(textField_1);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int d,N;
				d = Integer.parseInt(textField.getText());
				N = Integer.parseInt(textField_1.getText());
				message = ReadAndSend.read(image,d,N);
				text.setText(message);
			}
		});
		btnEnviar.setBounds(154, 170, 89, 23);
		getContentPane().add(btnEnviar);
	}

}
