
import java.awt.Color;
import java.util.Scanner;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;


import javax.swing.JFrame;
import javax.swing.border.LineBorder;

/* 
 * Clock analog
 */

public class ClockGui extends JFrame implements MouseListener{

	private static JFrame frame;
	private int status=0;
	private static final int spacing = 35;
	private static final float radPerSecMin = (float)(Math.PI / 30.0);
	private static final float radPerNum = (float)(Math.PI/ -6);
	private int size;
	private int centerX;
	private int centerY;
	int curr = -1;
	//
	SimpleDateFormat sf;

	Calendar cal;
	int hour;
	int minute;
	int second;
	Color colorSecond,colorMHour,colorNumber;

	Timer timer;
	TimeZone timeZone;
	static String str;

	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		System.out.println("What city?");
		str = sc.nextLine();
		
		frame = new ClockGui();



		frame.setTitle("AtmosClock");

		frame.setVisible(true);
		AtmosClockPre.atmos(str);
	}

	public ClockGui() {

		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		setBounds(700, 100, 400, 430);
		getContentPane().setBackground(new Color(212, 234, 255));

		addMouseListener(this);


		timer = new Timer();
		timeZone = TimeZone.getDefault();
		
		timer.schedule(new TickTimerTask(), 0, 1000); //after 1s once repaint
		
	}
//	public void run() {
//		long lastTime = System.nanoTime();
//		double amount0fTicks = 60.0;
//		double ns = 1000000000 / amount0fTicks;
//		double delta = 0;
//		long timer = System. currentTimeMillis ();
//		int frames = 0;
//		while(true){
//			long now = System.nanoTime();
//			delta += (now - lastTime) / ns;
//			lastTime = now;
//			while (delta >= 1){
//				tick();
//				delta--;
//			}
//			if (true)
//				//render();
//				frames++;
//			if(System.currentTimeMillis() - timer > 1000){
//				timer += 1000;
//				System.out.println("FPS: " + frames);
//				frames = 0;
//			}
//
//
//		}
//	}
//	public void tick() {
//		cal = (Calendar) Calendar.getInstance(timeZone);
//
//		BufferStrategy bs = getBufferStrategy();
//		if (bs == null){
//			this.createBufferStrategy(3);
//			return;
//		}
//		Graphics g = bs.getDrawGraphics();
//
//		repaint();
//		g.dispose();
//		bs.show();
//
//	}

		class TickTimerTask extends TimerTask{

			@Override
			public void run() {

				cal = (Calendar) Calendar.getInstance(timeZone);			
//				BufferStrategy bs = getBufferStrategy();
//				if (bs == null){
//					createBufferStrategy(3);
//					return;
//				}
//				Graphics g = bs.getDrawGraphics();
				repaint();
			
			}

		}

		public void paint(Graphics g) {

			super.paint(g); 

			//border clock
			if(status == 0){
				g.setColor(new Color(124, 136, 162));
				g.fillOval(25, spacing, 350, 350);	
				g.setColor(Color.WHITE);
				g.fillOval(35, spacing+10, 330, 330);
			}else if(status == 1){
				g.setColor(Color.BLACK);
				g.fillOval(25, spacing, 350, 350);	
				g.setColor(Color.WHITE);
				g.fillOval(35, spacing+10, 330, 330);
			}

			size = 400 -spacing;		
			centerX = 400/2;
			centerY = 400/2+10;


			//clock face

			drawClockFace(g);

			//number clock face
			drawNumberClock(g);

			//get system time
			cal = Calendar.getInstance();
			hour = cal.get(Calendar.HOUR);
			minute = cal.get(Calendar.MINUTE);
			second = cal.get(Calendar.SECOND);	

			//	//draw digital clock
			if(status==1){
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(centerX-40, centerY-35, 90, 20);
				g.setColor(Color.BLACK);
				g.drawRect(centerX-40, centerY-35, 90, 20);
				sf = new SimpleDateFormat("hh:mm:ss a");
				g.setColor(Color.BLACK);
				g.setFont(new Font("Tahoma", Font.BOLD, 12));
				g.drawString(sf.format(cal.getTime()), centerX-35+3, centerY-35+15);
			}

			//draw date in clock 3
			if(status ==2){
				g.setColor(Color.YELLOW);
				g.drawRect(centerX+60, centerY-10, 40, 20);
				sf = new SimpleDateFormat("dd");
				g.drawString(sf.format(cal.getTime()), centerX+60+12, centerY+5);
			}

			//draw hands
			if(status==2){
				drawHands(g,hour,minute,second,colorSecond.RED,colorMHour.YELLOW);
			}else{
				drawHands(g,hour,minute,second,colorSecond.RED,colorMHour.BLACK);
			}

			//draw point clock
			g.setColor(Color.BLACK);
			g.fillOval(centerX-5, centerY-5, 10, 10);
			g.setColor(Color.RED);
			g.fillOval(centerX-3, centerY-3, 6, 6);

		}

		/*-------------Clock Face----------------*/
		private void drawClockFace(Graphics g) {
			// TODO Auto-generated method stub

			// tick marks
			for (int sec = 0; sec<60; sec++) {
				int ticStart;
				if (sec%5 == 0) {
					ticStart = size/2-10;
				}else{

					ticStart = size/2-5;
				}

				if(status ==2){
					drawRadius(g, centerX, centerY, radPerSecMin*sec, ticStart-20, size/2-20,colorNumber.YELLOW);
				}else{
					drawRadius(g, centerX, centerY, radPerSecMin*sec, ticStart-20, size/2-20,colorNumber.BLACK);
				}

			}
		}

		private void drawRadius(Graphics g, int x, int y, double angle,
				int minRadius, int maxRadius, Color colorNumber) {
			float sine = (float)Math.sin(angle);
			float cosine = (float)Math.cos(angle);
			int dxmin = (int)(minRadius * sine);
			int dymin = (int)(minRadius * cosine);
			int dxmax = (int)(maxRadius * sine);
			int dymax = (int)(maxRadius * cosine);
			g.setColor(colorNumber);
			g.drawLine(x+dxmin, y+dymin, x+dxmax, y+dymax);
		}
		/*---------------------------------------------*/

		/*----------------Clock Number-----------------*/
		private void drawNumberClock(Graphics g) {

			for(int num = 12 ;num > 0 ;num-- ){			
				drawnum(g,radPerNum*num,num);			
			}


		}

		private void drawnum(Graphics g, float angle,int n) {
			// TODO Auto-generated method stub
			float sine = (float)Math.sin(angle);
			float cosine = (float)Math.cos(angle);
			int dx = (int)((size/2-20-25) * -sine);
			int dy = (int)((size/2-20-25) * -cosine);		

			g.drawString(""+n,dx+centerX-5,dy+centerY+5);


			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH");
			DateTimeFormatter dtfspec = DateTimeFormatter.ofPattern("HHmmss");
			LocalTime now = LocalTime.now();
			boolean check = true;
			if(Integer.parseInt(dtfspec.format(now)) == 000002 && check){
				AtmosClockPre.atmos(str);
				System.out.print("Hello");
				check = false;

			}
			if(Integer.parseInt(dtfspec.format(now)) == 000003){
				check = true;
			}

			int n1 = 0;
			//Midnight Correction
			if (Integer.parseInt(dtf.format(now)) == 0 && n == 12) {
				n1 -= 12;

			}

			//for pm shift
			if (Integer.parseInt(dtf.format(now)) > 12) {
				n1 += 12;

			}

			//for updating passed hours 
			if (Integer.parseInt(dtf.format(now)) > n + n1) {
				n1 += 12;

			}
			//for main temp colors on clock
			g.setColor(AtmosClockPre.getColor(n + n1));


			g.fillOval(centerX-10 +(int)((size/2-20-50) * -sine), centerY-10 + (int)((size/2-20-50) * -cosine), 20, 20);

			g.setColor(AtmosClockPre.getRaining(n + n1));


			g.fillOval(centerX-5 +(int)((size/2-20-75) * -sine), centerY-5 + (int)((size/2-20-75) * -cosine), 10, 10);


			g.setColor(Color.BLACK);
		}
		/*-----------------------------------------------*/

		/*----------------Clock Hands--------------------*/
		private void drawHands(Graphics g, double hour, double minute, double second, Color colorSecond, Color colorMHour) {
			// TODO Auto-generated method stub
			double rsecond = (second*6)*(Math.PI)/180;
			double rminute = ((minute + (second / 60)) * 6) * (Math.PI) / 180;
			double rhours = ((hour + (minute / 60)) * 30) * (Math.PI) / 180;

			g.setColor(colorSecond);
			g.drawLine(centerX, centerY, centerX + (int) (150 * Math.cos(rsecond - (Math.PI / 2))), centerY + (int) (150 * Math.sin(rsecond - (Math.PI / 2))));
			g.setColor(colorMHour);
			g.drawLine(centerX, centerY, centerX + (int) (120 * Math.cos(rminute - (Math.PI / 2))), centerY + (int) (120 * Math.sin(rminute - (Math.PI / 2))));
			g.drawLine(centerX, centerY, centerX + (int) (90 * Math.cos(rhours - (Math.PI / 2))), centerY + (int) (90 * Math.sin(rhours - (Math.PI / 2))));
		}





		/*-------------------------------------------------*/

		// event change interface clock

		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if(status==0){
				status =1;
				frame.getContentPane().setBackground(Color.LIGHT_GRAY);
			}else if(status ==1){
				status=2;		
				frame.getContentPane().setBackground(Color.BLACK);
			}else{
				status =0;
				frame.getContentPane().setBackground(new Color(212, 234, 255));
			}
			repaint();
		}






		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
