
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimeZone;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;


public class Window extends Canvas{


	private static final long serialVersionUID = 1882842017213253971L;
	Timer timer;
	TimeZone timeZone;
	boolean clicked = true;
	String city;

	public Window(ClockGui clock) {
		JFrame frame = new JFrame("AtmosClock");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setBounds(700, 100, 400, 430);
		frame.getContentPane().setBackground(new Color(212, 234, 255));
		frame.setLayout(new BorderLayout());
		//frame.setVisible(true);
		JLabel question = new JLabel("Enter a city or a zip code");
		question.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		question.setBounds(75, 0, 300, 40);
		frame.add(question);
	
		JTextField field = new JTextField(10);
		field.setBounds(75,100,250,20);  
		frame.add(field);
		
		JButton button = new JButton("Enter");
		button.setBounds(150,100,50,20);  
		frame.getContentPane().add(button, BorderLayout.SOUTH);
		frame.addMouseListener(clock);
		frame.setVisible(true);
		button.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				city = field.getText();
				AtmosClockPre.atmos(city);
				frame.remove(button);
				frame.remove(field);
				frame.remove(question);
				
				frame.add(clock);
				frame.setVisible(true);
				clock.start();
			}


		});
		
		

		


	}

}