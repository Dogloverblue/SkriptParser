package me.lowen;

import java.io.File;

import me.lowen.gui.SkUIFrame;

public class SkriptParserMain {
	
	static File serverDirectory = new File("C:\\Users\\Jacob\\Desktop\\SkriptParserServer");
	public static void main(String[] args) {
		System.out.println("Booting SkriptParser...");
		
		OutputReceiver or = new OutputReceiver(serverDirectory);
		
		SkUIFrame frame = new SkUIFrame(or);
		frame.getHeader().setLoadingStateMessage("Parser Booting...");
		frame.getHeader().setIfInLoadingState(true);
		frame.getHeader().setLoadingStateMessage("Parsing...");
		frame.setTitle("Skript Parser");
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		or.setLinkedFrame(frame);
		
		Thread outputThread = new Thread(or);
		outputThread.start();
		System.out.println("Booted! Should be currently running");
//		try {
//			Thread.sleep(12000);
//			System.out.println("TRIYN TO SLEEP");
//			long timeRN = System.currentTimeMillis();
//			System.out.println("parsing: " + or.parseSkript("on chat:\n  the fog") + " end og thing");
//			System.out.println(System.currentTimeMillis() - timeRN);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		System.out.println("akk sionbe");
		
		
	}

}
