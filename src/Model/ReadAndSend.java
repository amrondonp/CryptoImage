package Model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class ReadAndSend {
  private static ReadImage reader;
  private static RSA rsa;
  private static int howMany;

  public static int howMany() {
    return howMany;
  }

  public static void chargeImage(String path) {
    reader = new ReadImage();
    reader.readBMP(path);
    reader.mor = new Morphology(reader.image);
    reader.mor.boundaryDetection();
    reader.bound = reader.mor.getBounds();

    JOptionPane.showMessageDialog(
      null,
      "Tienes " + reader.bound.size() / 32 + " caracteres."
    );
    howMany = reader.bound.size() / 32;
  }

  public static void send(String mensaje, String image, int p, int q) {
    //Reading the image and founding its bounds
    if (reader == null) {
      JOptionPane.showMessageDialog(
        null,
        "Para enviar un mensaje, debes primero cargar la imagen"
      );
      return;
    }
    rsa = new RSA(p, q);
    JOptionPane.showMessageDialog(
      null,
      "Datos RSA: \nRecuerdelos, los datos N y d necesitar� el receptor\n" + rsa
    );
    reader.setMessageLength(rsa.encrypt(mensaje.length()));

    ArrayList<Integer> encryptedString = rsa.encryptedString(mensaje);
    reader.esteganographInts(encryptedString);

    reader.save("Binary_est"); //The image will be saved as bmp
    reader.display();
    ReadImage aux = new ReadImage();
    aux.image = reader.mor.getBoundedImage();
    aux.display();
    JOptionPane.showMessageDialog(
      null,
      "Mensaje Guardado correctamente en: Binary_est.bmp \nPuede cerrar el programa"
    );
  }

  public static String read(String image, int d, int N) {
    ReadImage reader = new ReadImage();
    reader.readBMP(image);
    reader.mor = new Morphology(reader.image);
    reader.mor.boundaryDetection();
    reader.bound = reader.mor.getBounds();

    int length = reader.getMessageLength();

    length = RSA.dec(length, d, N);

    return (RSA.decrypt(reader.discoverInts(length), d, N));
  }

  public static void main(String[] args) {
    ReadImage reader = new ReadImage();
    Scanner sc = new Scanner(System.in);
    System.out.println("[V]er, [E]nviar");
    char c = sc.nextLine().charAt(0);

    switch (c) {
      case 'V':
        reader.readBMP("Binary_est.bmp");
        reader.mor = new Morphology(reader.image);
        reader.mor.boundaryDetection();
        reader.bound = reader.mor.getBounds();

        int length = reader.getMessageLength();
        System.out.println("Ingrese la clave publica y N");
        int d = 79843181;
        int N = 100903027;

        length = RSA.dec(length, d, N);

        System.out.println("Longitud del mensaje guardado: " + length);
        //System.out.println(reader.discoverInts(length));
        System.out.println(RSA.decrypt(reader.discoverInts(length), d, N));

        break;
      case 'E':
        //Reading the image and founding its bounds
        reader.readBMP("Binary.bmp");
        reader.mor = new Morphology(reader.image);
        reader.mor.boundaryDetection();
        reader.bound = reader.mor.getBounds();

        String mensaje = "";

        System.out.println(
          "Tienes " + reader.bound.size() / 32 + " caracteres."
        );
        System.out.println("Ingresa un mensaje: ");
        mensaje = sc.nextLine();

        RSA rsa = new RSA(1009, 100003);
        reader.setMessageLength(rsa.encrypt(mensaje.length()));

        ArrayList<Integer> encryptedString = rsa.encryptedString(mensaje);
        reader.esteganographInts(encryptedString);

        reader.save("Binary_est"); //The image will be saved as bmp
        System.out.println("Para terminar el programa, cierre la ventana");
        reader.display();

        ReadImage aux = new ReadImage();
        aux.image = reader.mor.getBoundedImage();
        aux.display();

        break;
    }
    sc.close();
  } //main()ends
}
