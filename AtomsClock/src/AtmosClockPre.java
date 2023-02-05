  
import java.util.Scanner;



public class AtmosClockPre {
	
	private static String [] level = null;
	private static double [][] data = null;
	private static boolean[] raining = null;
	
	public static void atmos(String city) {
		Scanner keyboard = new Scanner (System.in);
		
		
	level = new String[24];
		raining = new boolean[24];
		
		
		
		
		data = AtmosClockData.getData(city);

		double [] pointSystem = new double[24];
		
		for (int x = 0; x < 24; x++) {
			
			pointSystem[x] = (data[0][x] + humid(data[1][x]) + data [2][x]);
			
			if (data[3][x] > 0.0) {
				raining[x] = true;
			}
		}
		
		for (int x = 0; x < 24; x++) {
			
			if (pointSystem[x] < 30) {
				level[x] = "Purple";
			}
			
			else if ((pointSystem[x] >= 30) && (pointSystem[x] < 50)) {
				level[x] = "Dark Blue";
			}
			
			else if ((pointSystem[x] >= 50) && (pointSystem[x] < 65)) {
				level[x] = "Cyan";
			}
			
			else if ((pointSystem[x] >= 65) && (pointSystem[x] < 75)) {
				level[x] = "Yellow";
			}
			
			else if ((pointSystem[x] >= 75) && (pointSystem[x] < 85)) {
				level[x] = "Orange";
			}
			
			else if (pointSystem[x] >= 85) {
				level[x] = "Red";
			}
			
		}
	

	}

	
	
	public static double humid(double humidity) {
		
		double humidityAdjusted = 0;
		
		
			humidityAdjusted = (((humidity/100) - .50) * 10);
		
		
		return humidityAdjusted;
	}
	
	
	public static String getColor(int n) {
		return level[n];
	}

	public static boolean getRaining(int index) {
		return raining[index];
	}

	public static void update() {
		
	}

	
	

}
