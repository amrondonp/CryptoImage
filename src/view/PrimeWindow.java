package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JButton;

import Model.RSA;
import Model.ReadAndSend;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PrimeWindow extends JFrame {
	private JTextField textField;
	private JTextField textField_1;
	private String mensaje,image;
	public PrimeWindow(final String mensaje, final String image) {
		this.mensaje = mensaje ; this.image = image;
		setTitle("Primos");
		getContentPane().setLayout(null);
		setBounds(650,450,450,230);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 0, 0));
		panel.setBounds(10, 11, 414, 83);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Crear RSA");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 41));
		lblNewLabel.setBounds(10, 11, 394, 61);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setText("1009");
		textField.setBounds(87, 105, 86, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setText("100003");
		textField_1.setBounds(87, 136, 86, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblPrimoP = new JLabel("Primo p");
		lblPrimoP.setBounds(15, 108, 62, 14);
		getContentPane().add(lblPrimoP);
		
		JLabel lblPrimoQ = new JLabel("Primo q");
		lblPrimoQ.setBounds(15, 139, 62, 14);
		getContentPane().add(lblPrimoQ);
		
		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int p = Integer.parseInt(textField.getText());
				int q = Integer.parseInt(textField_1.getText());
				boolean a = RSA.check(p,q);
				if(a){
					ReadAndSend.send(mensaje,image,p,q);
				}
			}
		});
		btnNewButton.setBounds(283, 119, 89, 23);
		getContentPane().add(btnNewButton);
	}
}
