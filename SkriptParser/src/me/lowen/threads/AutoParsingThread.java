package me.lowen.threads;

import java.awt.Frame;
import java.util.Timer;
import java.util.TimerTask;

import me.lowen.SettingsManager;
import me.lowen.gui.SkUIFrame;

public class AutoParsingThread implements Runnable{
	
	private int currentCounter = 0;
	private static int countTo = SettingsManager.getAutoParseMillis();
	private boolean countUp = false;
	private boolean parsing = false;
	ParsingThread thread;
	SkUIFrame frame;
	public AutoParsingThread(SkUIFrame frame, ParsingThread thread) {
		this.thread = thread;
		this.frame = frame;
	}
	
	Timer timer;
	@Override
	public void run() {
		
		timer = new Timer();
		timer.schedule(new TimerTask() {
		  @Override
		  public void run() {
			  if (countUp) {
				  currentCounter++;
				  if (currentCounter >= countTo && !parsing) {
					  countUp = false;
					  thread.setStringToParse(frame.getTextPanel().getTextPane().getText());
					  thread.parse();
				  }
			  }
		  }
		}, 0, 1);
		
	}
	public void resetCounter() {
		this.currentCounter = 0;
	}
	
	public static void manuallyUpdateAutoParseMillis(int millis) {
		countTo = millis;
	}
	public void startCountingUp() {
		countTo = SettingsManager.getAutoParseMillis();
		this.countUp = true;
	}
	public void stopCountingUp() {
		this.countUp = false;
	}
	public void stopCountingUpAndResetTimer() {
		this.resetCounter();
		this.countUp = false;
	}
	public boolean isParsing() {
		return parsing;
	}
	public void setParsing(boolean parsing) {
		this.parsing = parsing;
	}
	
	

}
