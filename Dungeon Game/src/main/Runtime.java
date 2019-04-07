package main;

import java.util.Date;

import console.Console;

public class Runtime {
	private static Date initDate = new Date();
	private static long memUsedPrior = java.lang.Runtime.getRuntime().totalMemory()-java.lang.Runtime.getRuntime().freeMemory();
	
	public static void init() {
		Console.sysLog("Runtime initiated at: "+initDate);
	}
	
	public static float getElapsedTimeInMs() {
		Date curDate = new Date();
		float elapsedTime = curDate.getTime()-initDate.getTime();
		return elapsedTime;
	}
	
	public static boolean timer(int millis) {
		return (getElapsedTimeInMs()%(millis*2) > millis);
	}
	
	private static long avg = 0;
	private static int count = 0;
	public static long getUsedMemory() {
		long memUsedPost = java.lang.Runtime.getRuntime().totalMemory()-java.lang.Runtime.getRuntime().freeMemory();
		long used = memUsedPost-memUsedPrior;
		avg+=used;
		count++;
		used = avg/count;
		return used;
	}
}
