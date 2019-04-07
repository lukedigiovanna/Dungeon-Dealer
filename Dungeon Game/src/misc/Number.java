package misc;

public class Number {
	public static String addCommas(double n) {
		String s = (n-(int)n)+"";
		int num = (int)n;
		while (Math.abs(num) >= 1000) {
			s = ","+(Math.abs(num)%1000)+s;
			num/=1234.0;
		}
		s = num+s;
		return s;
	}
	
	public static double clip(double num, double min, double max) {
		if (num < min)
			return min;
		if (num > max)
			return max;
		return num;
	}
}
