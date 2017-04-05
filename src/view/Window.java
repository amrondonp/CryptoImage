package view;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;

import Model.CounterChar;
import Model.ReadAndSend;
import Model.ReadImage;

public class Window extends JFrame{
	private JTextField txtBinarybmp;
	private JTextArea textArea;
	private CounterChar cc;
	private JLabel counter;
	private int fullScreenHeight;
	private int fullScreenWidth;
	private int abosluteFullScreenHeight;
	private int abosluteFullScreenWidth;
	
	public JTextArea getTextArea(){
		return this.textArea;
	}
	public Window() {
		getFullScreenDimensions();
		setResizable(false);
		
		setTitle("UnMorphology");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setBounds();
		JPanel panel = new JPanel();
		panel.setBackground(new Color(65, 105, 225));
		
		//fullScreenHeight*10/100
		panel.setBounds(fullScreenWidth*1/200, fullScreenHeight*1/200, fullScreenWidth - fullScreenWidth*5/400, fullScreenHeight*10/100);
		
		
		getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("UnMorfol\u00F3gico");
		panel.add(lblNewLabel);
		lblNewLabel.setBackground(new Color(65, 105, 225));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, fullScreenHeight*10/100 - fullScreenHeight*300/10000));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		JLabel lblRutaDeImagen = new JLabel("Ruta de imagen");
		lblRutaDeImagen.setHorizontalAlignment(SwingConstants.LEFT);
		lblRutaDeImagen.setFont(new Font("Tahoma", Font.PLAIN, fullScreenHeight*8/100 - fullScreenHeight*300/10000));
		lblRutaDeImagen.setBounds(fullScreenWidth*1/200, fullScreenHeight*10/100 + fullScreenHeight*100/10000,fullScreenWidth*30/100, fullScreenHeight*10/100);
		getContentPane().add(lblRutaDeImagen);
		
		txtBinarybmp = new JTextField();
		txtBinarybmp.setText("Binary.bmp");
		txtBinarybmp.setFont(new Font("Tahoma", Font.PLAIN, fullScreenHeight*2/100));
		txtBinarybmp.setBounds(fullScreenWidth*30/100 + fullScreenWidth*1/200, fullScreenHeight*10/100 + fullScreenHeight*500/10000, fullScreenWidth*25/100, fullScreenHeight*3/100);
		getContentPane().add(txtBinarybmp);
		txtBinarybmp.setColumns(10);
		
		JLabel lblMensaje = new JLabel("Mensaje");
		lblMensaje.setBounds(72, 605, 75, 14);
		getContentPane().add(lblMensaje);
		
		JButton btnNewButton = new JButton("Examinar...");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode( JFileChooser.FILES_ONLY );
				fc.setFileFilter( new FileFilter() {
					@Override
					public String getDescription() {
						return "Bitmap image files";
					}
					@Override
					public boolean accept(File f) {
						return f.getName().endsWith( ".bmp" ) || f.isDirectory();
					}
				} );
				fc.showOpenDialog(null);
				File f = fc.getSelectedFile();
				txtBinarybmp.setText(f.getAbsolutePath());
				
			}
		});
		btnNewButton.setBounds(343, 556, 120, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mensaje = textArea.getText();
				String image = txtBinarybmp.getText();
				PrimeWindow pw = new PrimeWindow(mensaje,image);
				pw.setVisible(true);
				/*
				*/
			}
		});
		btnEnviar.setBounds(72, 841, 89, 23);
		getContentPane().add(btnEnviar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(502, 589, 380, 216);
		getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JButton btnLeer = new JButton("Leer");
		btnLeer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReadWindow w = new ReadWindow(txtBinarybmp.getText(),textArea);
				w.setVisible(true);
			}
		});
		btnLeer.setBounds(389, 841, 89, 23);
		getContentPane().add(btnLeer);
		
		JButton btnCargarImagen = new JButton("Cargar Imagen");
		btnCargarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReadAndSend.chargeImage(txtBinarybmp.getText());
				cc = new CounterChar(ReadAndSend.howMany(),counter,textArea);
				Thread t = new Thread(cc);
				t.start();
			}
		});
		btnCargarImagen.setBounds(345, 589, 118, 23);
		getContentPane().add(btnCargarImagen);
		
		counter = new JLabel("");
		counter.setBounds(726, 818, 154, 14);
		getContentPane().add(counter);
	}
	private void setBounds() {
		fullScreenWidth = abosluteFullScreenWidth - abosluteFullScreenWidth*25/100;
		fullScreenHeight = abosluteFullScreenHeight - abosluteFullScreenHeight*10/100;
		super.setBounds(0, 0, fullScreenWidth, fullScreenHeight);
	}
	private void getFullScreenDimensions() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		abosluteFullScreenHeight = gd.getDisplayMode().getHeight();
		abosluteFullScreenWidth = gd.getDisplayMode().getWidth();
	}
}
