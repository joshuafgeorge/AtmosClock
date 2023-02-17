import java.awt.Canvas;
import java.awt.Color;
import java.util.TimeZone;
import java.util.Timer;

import javax.swing.JFrame;



public class Window extends Canvas{


	private static final long serialVersionUID = 1882842017213253971L;
	Timer timer;
	TimeZone timeZone;

	

	public Window(ClockGui clock) {
		JFrame frame = new JFrame("AtmosClock");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setBounds(700, 100, 400, 430);
		frame.getContentPane().setBackground(new Color(212, 234, 255));
		frame.setVisible(true);
		
		frame.addMouseListener(clock);

		
		frame.add(clock);
		frame.setVisible(true);
		

		clock.start();
		
	
	}
	
}