package test;

import Model.*;

public class TestBoundsDetector {

  public static void main(String[] args) {
    ReadImage reader = new ReadImage();
    reader.readBMP("Binary.bmp");

    Morphology morf = new Morphology(reader.image);

    reader.image = morf.boundaryDetection();

    double percent =
      morf.howMany / (reader.image.getHeight() * reader.image.getWidth() * 1.0);
    percent *= 100;

    System.out.println("Percent of usage: " + percent);
    System.out.println(
      "How many bits can the boundary esteganograph?: " + (morf.howMany)
    );
    System.out.println(
      "How many characters can the boundary esteganograph?: " +
      (morf.howMany / 8)
    );

    reader.display();
  }
}
