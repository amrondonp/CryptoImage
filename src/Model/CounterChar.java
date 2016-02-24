package Model;

import javax.swing.JLabel;
import javax.swing.JTextArea;

public class CounterChar implements Runnable {

	private int howMany;
	private int spent;
	private JLabel  jlabel;
	private JTextArea textArea;

	public CounterChar(int howMany, JLabel jlabel, JTextArea textArea){
		this.spent = 0;
		this.jlabel = jlabel;
		this.textArea = textArea;
		this.howMany= howMany;
	}

	public void setSpent(int spent){
		this.spent = spent;
	}

	@Override
	public void run() {
		while(true){
			this.jlabel.setText("Quedan: "+(howMany-spent)+" caracteres.");
			spent = textArea.getText().length();
			try {
				Thread.sleep(90);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
