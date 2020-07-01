package Model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class RSA {

  public static int dec(int x, int d, int N) {
    BigInteger aux1 = new BigInteger(x + "");
    aux1 = aux1.modPow(new BigInteger("" + d), new BigInteger("" + N));
    return aux1.intValue();
  }

  public static boolean check(int p, int q) {
    BigInteger a = new BigInteger("" + p);
    BigInteger b = new BigInteger("" + q);
    if (!a.isProbablePrime(30)) {
      JOptionPane.showMessageDialog(null, p + " No es primo");
      return false;
    }
    if (!b.isProbablePrime(30)) {
      JOptionPane.showMessageDialog(null, q + " No es primo");
      return false;
    }

    if (a.multiply(b).compareTo(new BigInteger("" + Integer.MAX_VALUE)) > 0) {
      JOptionPane.showMessageDialog(
        null,
        "p*q excede el tama�o de un entero de 32 bits: " + Integer.MAX_VALUE
      );
      return false;
    }
    if (
      a.multiply(b).compareTo(new BigInteger("" + (int) Character.MAX_VALUE)) <
      0
    ) {
      JOptionPane.showMessageDialog(
        null,
        "p*q no es suficiente para cubrir el Codigo ASCII\np*q = " +
        (p * q) +
        ", pero el valor minimo exigido es: " +
        (int) Character.MAX_VALUE
      );
      return false;
    }
    return true;
  }

  private int N, p, q, e, d;

  public RSA(int p, int q) {
    this.p = p;
    this.q = q;
    this.N = p * q;
    getKeys();
  }

  private void getKeys() {
    int[] val;
    int phi = (p - 1) * (q - 1);
    for (int i = 100; true; i++) {
      val = gcd(phi, i);
      if (val[0] == 1) {
        e = i;
        break;
      }
    }
    //Applying the bezout theorem
    d = val[2];
    if (d < 0) d = d + phi;
  }

  //  return array [d, a, b] such that d = gcd(p, q), ap + bq = d
  public static int[] gcd(int p, int q) {
    if (q == 0) return new int[] { p, 1, 0 };

    int[] vals = gcd(q, p % q);
    int d = vals[0];
    int a = vals[2];
    int b = vals[1] - (p / q) * vals[2];
    return new int[] { d, a, b };
  }

  public String toString() {
    return (
      "N: " +
      this.N +
      " P: " +
      this.p +
      " q: " +
      this.q +
      " d: " +
      this.d +
      " e: " +
      this.e
    );
  }

  public int encrypt(int x) {
    BigInteger encripted = new BigInteger(x + "");
    encripted =
      encripted.modPow(new BigInteger(e + ""), new BigInteger(N + ""));
    return encripted.intValue();
  }

  public ArrayList<Integer> encryptedString(String str) {
    ArrayList<Integer> encrypted = new ArrayList<Integer>();
    for (int i = 0; i < str.length(); i++) {
      encrypted.add(this.encrypt((int) str.charAt(i)));
    }
    return encrypted;
  }

  public static String decrypt(ArrayList<Integer> encrypted, int d, int N) {
    StringBuilder sb = new StringBuilder();
    int c;
    char x;
    for (int i = 0; i < encrypted.size(); i++) {
      c = dec(encrypted.get(i), d, N);
      x = Character.toChars(c)[0];
      sb.append(x);
    }
    return sb.toString();
  }

  public int decrypt(int c) {
    BigInteger aux = new BigInteger(c + "");
    aux = aux.modPow(new BigInteger(d + ""), new BigInteger(N + ""));
    return aux.intValue();
  }

  public static void main(String[] args) {
    int p, q;
    for (int i = 1001; true; i += 2) {
      if (new BigInteger("" + i).isProbablePrime(30)) {
        System.out.println(i);
        p = i;
        break;
      }
    }

    for (int i = 100001; true; i += 2) {
      if (new BigInteger("" + i).isProbablePrime(30)) {
        System.out.println(i);
        q = i;
        break;
      }
    }

    RSA rsa = new RSA(p, q);
    Scanner input = new Scanner(System.in);
    String s = input.nextLine();
    System.out.println(rsa);
    for (int i = 0; i < s.length(); i++) {
      System.out.println(
        "Char ASCII: " +
        (int) s.charAt(i) +
        " Encripted: " +
        rsa.encrypt((int) s.charAt(i))
      );
    }

    input.close();
  }
}
