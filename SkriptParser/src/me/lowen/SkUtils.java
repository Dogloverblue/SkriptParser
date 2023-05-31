package me.lowen;

import java.awt.Window;
import java.util.ArrayList;

import javax.swing.JButton;

public class SkUtils {

	public SkUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public static void showJButton(JButton btn, boolean isShown) {
		btn.setOpaque(isShown);
		btn.setContentAreaFilled(isShown);
		btn.setBorderPainted(isShown);
	}
	public static String turnArrayListIntoString(ArrayList<String> arrStr) {
		StringBuilder returnString = new StringBuilder();
		for (String w: arrStr) {
			returnString.append(w);
		}
		return returnString.toString();
	}
	
	public static void setWindowSizeIfLessThanSize(Window frame, int width, int height) {
		System.out.println("windows width is " + frame.getWidth() + " and you want it " + width);
		if (frame.getWidth() < width) {
			frame.setSize(width, frame.getHeight());
		}
		System.out.println("windows height is " + frame.getHeight() + " and you want it " + height);
		if (frame.getHeight() < height) {
			frame.setSize(frame.getWidth(), height);
		}
	}
}
