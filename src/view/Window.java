package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import Model.CounterChar;
import Model.ReadAndSend;

public class Window extends JFrame {
  private static final long serialVersionUID = -3270990773063370883L;
  private JTextField txtBinarybmp;
  private JTextArea textArea;
  private CounterChar cc;
  private JLabel counter;

  public JTextArea getTextArea() {
    return this.textArea;
  }

  public Window() {
    setResizable(false);

    setTitle("UnMorphology");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setLayout(null);
    setBounds(200, 200, 450, 480);
    JPanel panel = new JPanel();
    panel.setBackground(new Color(65, 105, 225));
    panel.setBounds(10, 11, 414, 97);
    getContentPane().add(panel);

    JLabel lblNewLabel = new JLabel("UnMorfol\u00F3gico");
    panel.add(lblNewLabel);
    lblNewLabel.setBackground(new Color(65, 105, 225));
    lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 60));
    lblNewLabel.setForeground(new Color(255, 255, 255));
    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JLabel lblRutaDeImagen = new JLabel("Ruta de imagen");
    lblRutaDeImagen.setHorizontalAlignment(SwingConstants.CENTER);
    lblRutaDeImagen.setBounds(10, 120, 118, 30);
    getContentPane().add(lblRutaDeImagen);

    txtBinarybmp = new JTextField();
    txtBinarybmp.setText("Binary.bmp");
    txtBinarybmp.setBounds(122, 125, 172, 20);
    getContentPane().add(txtBinarybmp);
    txtBinarybmp.setColumns(10);

    JLabel lblMensaje = new JLabel("Mensaje");
    lblMensaje.setBounds(33, 173, 75, 14);
    getContentPane().add(lblMensaje);

    JButton btnNewButton = new JButton("Examinar...");
    btnNewButton.addActionListener(
      new ActionListener() {

        public void actionPerformed(ActionEvent e) {
          JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
          fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
          fc.setFileFilter(
            new FileFilter() {

              @Override
              public String getDescription() {
                return "Bitmap image files";
              }

              @Override
              public boolean accept(File f) {
                return f.getName().endsWith(".bmp") || f.isDirectory();
              }
            }
          );
          fc.showOpenDialog(null);
          File f = fc.getSelectedFile();
          txtBinarybmp.setText(f.getAbsolutePath());
        }
      }
    );
    btnNewButton.setBounds(304, 124, 120, 23);
    getContentPane().add(btnNewButton);

    JButton btnEnviar = new JButton("Enviar");
    btnEnviar.addActionListener(
      new ActionListener() {

        public void actionPerformed(ActionEvent e) {
          String mensaje = textArea.getText();
          String image = txtBinarybmp.getText();
          PrimeWindow pw = new PrimeWindow(mensaje, image);
          pw.setVisible(true);
        }
      }
    );
    btnEnviar.setBounds(59, 425, 89, 23);
    getContentPane().add(btnEnviar);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBounds(33, 198, 380, 216);
    getContentPane().add(scrollPane);

    textArea = new JTextArea();
    scrollPane.setViewportView(textArea);

    JButton btnLeer = new JButton("Leer");
    btnLeer.addActionListener(
      new ActionListener() {

        public void actionPerformed(ActionEvent e) {
          ReadWindow w = new ReadWindow(txtBinarybmp.getText(), textArea);
          w.setVisible(true);
        }
      }
    );
    btnLeer.setBounds(158, 425, 89, 23);
    getContentPane().add(btnLeer);

    JButton btnCargarImagen = new JButton("Cargar Imagen");
    btnCargarImagen.addActionListener(
      new ActionListener() {

        public void actionPerformed(ActionEvent e) {
          ReadAndSend.chargeImage(txtBinarybmp.getText());
          cc = new CounterChar(ReadAndSend.howMany(), counter, textArea);
          Thread t = new Thread(cc);
          t.start();
        }
      }
    );
    btnCargarImagen.setBounds(306, 157, 118, 23);
    getContentPane().add(btnCargarImagen);

    counter = new JLabel("");
    counter.setBounds(257, 427, 154, 14);
    getContentPane().add(counter);
  }
}
