package Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ReadImage {
	
	private static final int NUMBER_OF_BITS = 32;
	public BufferedImage image;
	public Morphology mor;
	public ArrayList<Point> bound;
	private int globalInt;
	
	public int theKthBit(int n,int k){
		return (n & ( 1 << k )) >> k ;
	}
	
	
	public void readBMP(String file){
		try {
			File f = new File(file);
			image = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//Put the bit in the least important bit of the pixel in position i,j
	public void putBit(int i , int j , int bit){
		//System.out.println(Integer.toBinaryString(image.getRGB(i, j)));
		if(bit==1){
			if((image.getRGB(i, j) & 1) == 0 ){
				image.setRGB(i, j, (image.getRGB(i, j) | 1) );
			}
		}else{
			if( (image.getRGB(i, j) & 1 ) == 1){
				image.setRGB(i, j, ( image.getRGB(i, j) ^ 1 ) );
			}
			
		}
	}
	
	
	public void display(){
		JFrame frame = new JFrame("UnMorphology_0.2");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JLabel jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon(this.image));
        frame.getContentPane().add(jLabel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
	
	

	public void esteganograph(String mensaje) {
		int index=0;
		for(int i=0 ; i<mensaje.length() && index<bound.size() ; i++){
			index = putChar(index,mensaje.charAt(i));
		}
	}
	
	public String discover(int length){
		StringBuilder sb = new StringBuilder();
		int index = 0;
		for(int i=0 ; i<length ; i++){
			index = readChar(index);
			sb.append(global);
		}
		
		return sb.toString();
	}
	

	//Puts a char starting at the start.x as row and start.y as col and returns the ending point
	//Warning if there is no 8 pixels to put the char, it moves inmedately to the next row
	public int putChar(int index, char x ){
		int aux = (int)x;
		Point start; 
		int putbits=0;
		while(putbits<8){
			start = bound.get(index);
			putBit(start.x,start.y,(aux&1));
			aux=aux>>1;
			putbits++;
			index++;
		}
		return index;
	}
	
	static char global;
	
	public int readChar(int index){
		int readedBits=0;
		int c=0;
		Point start;
		while(readedBits < 8){
			start = bound.get(index);
			int a = (image.getRGB(start.x, start.y) & 1);
			a = a<<readedBits;
			c = c | a;
			readedBits++;
			index++;
		}
		global = Character.toChars(c)[0];
		return index;
	}
	
	
	public void setMessageLength(int length){	
		for(int i=0 ; i<NUMBER_OF_BITS ; i++){
			this.putBit(0, i, (length & 1 ) );
			length = length>>1;
		}
	}
	

	public void save(String file,String format){
		try {
			File f = new File(file);
			ImageIO.write(image, format, f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save(String file){
		save(file+".bmp","bmp");
	}
	
	
	public int getMessageLength(){
		int length = 0;
		for(int i=0  ; i<NUMBER_OF_BITS ; i++){
			length = length | ( (image.getRGB(0, i) & 1) << i); 
		}
		return length;
	}


	public void esteganographInts(ArrayList<Integer> encryptedString) {
		int index=0;
		for(int i=0 ; i<encryptedString.size() && index<bound.size() ; i++){
			index = putInt(index,encryptedString.get(i));
		}
	}


	private int putInt(int index, Integer integer) {
		int aux = integer;
		Point start; 
		int putbits=0;
		while(putbits<32){
			start = bound.get(index);
			putBit(start.x,start.y,(aux&1));
			aux=aux>>1;
			putbits++;
			index++;
		}
		return index;
	}
	
	
	public ArrayList<Integer> discoverInts(int length){
		ArrayList<Integer> nums = new ArrayList<Integer>();
		int index = 0;
		for(int i=0 ; i<length ; i++){
			index = readInt(index);
			if(index==-1) break;
			nums.add(globalInt);
		}
		
		return nums;
	}


	private int readInt(int index) {
		int readedBits=0;
		int c=0;
		Point start;
		while(readedBits < 32){
			try{
				start = bound.get(index);
			}catch(IndexOutOfBoundsException e){
				JOptionPane.showMessageDialog(null, "Error!! verifque la clave pública 'd' o el numero N\n"
						+ "o verifique que la imagen si haya sido procesada anterior mente");
				return -1;
			}
			int a = (image.getRGB(start.x, start.y) & 1);
			a = a<<readedBits;
			c = c | a;
			readedBits++;
			index++;
		}
		globalInt = c;
		return index;
	}
	
	
}	
