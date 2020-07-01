package Model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Morphology {
  BufferedImage image, boundedImage;
  private int ee = 1;
  public int howMany;
  ArrayList<Point> bound = null;

  public Morphology(BufferedImage image) {
    this.image = image;
  }

  public ArrayList<Point> getBounds() {
    return this.bound;
  }

  //Be careful, in this moments it's made for black and white images
  public BufferedImage boundaryDetection() {
    bound = new ArrayList<Point>();
    int height = image.getHeight();
    int width = image.getWidth();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if (isPixelOfBound(i, j)) bound.add(new Point(i, j));
      }
    }

    //Creating a white image
    BufferedImage imageBounded = new BufferedImage(
      width,
      height,
      BufferedImage.TYPE_INT_RGB
    );
    Graphics2D graphics = imageBounded.createGraphics();
    graphics.setPaint(Color.white);
    graphics.fillRect(0, 0, imageBounded.getWidth(), imageBounded.getHeight());

    Point aux;
    for (int i = 0; i < bound.size(); i++) {
      aux = bound.get(i);
      imageBounded.setRGB(aux.x, aux.y, 0);
    }
    howMany = bound.size();
    boundedImage = imageBounded;
    return imageBounded;
  }

  private boolean isPixelOfBound(int i, int j) {
    boolean black = false, white = false;
    for (int k = i - ee; k <= i + ee; k++) {
      for (int w = j - ee; w <= j + ee; w++) {
        if (inside(k, w)) {
          if (
            image.getRGB(k, w) == Color.white.getRGB() ||
            (image.getRGB(k, w) == (Color.white.getRGB() ^ 1))
          ) white = true; else if (
            image.getRGB(k, w) == Color.black.getRGB() ||
            image.getRGB(k, w) == (Color.black.getRGB() | 1)
          ) black = true;
          if (black && white) return true;
        }
      }
    }
    return false;
  }

  private boolean inside(int k, int w) {
    return k >= 0 && k < image.getWidth() && w >= 0 && w < image.getHeight();
  }

  public BufferedImage getBoundedImage() {
    return this.boundedImage;
  }
}
