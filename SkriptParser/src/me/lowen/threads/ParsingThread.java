package me.lowen.threads;

import me.lowen.OutputReceiver;
import me.lowen.gui.SkUIFrame;

public class ParsingThread implements Runnable {

	OutputReceiver receiver;
	volatile String stringToParse;
	volatile boolean commenceParsing = false;
	SkUIFrame frame;
	public ParsingThread(OutputReceiver receiver, SkUIFrame frame) {
		this.receiver = receiver;
		this.frame = frame;
	}

	@Override
	public void run() {
		while (true) {
			if (commenceParsing) {
				System.out.println("TREEEE");
				commenceParsing = false;
				frame.getTextPanel().getAutoParsingThread().setParsing(true);
				String parsedString = OutputReceiver.trimOutput(receiver.parseSkript(stringToParse));
				frame.getErrorPane().setText(parsedString);
				frame.getHeader().setIfInLoadingState(false);
				frame.getTextPanel().getAutoParsingThread().setParsing(false);
				frame.repaint();
				frame.revalidate();
				frame.setVisible(true);
				System.out.println("parsed skript results: " + parsedString + " END OF PARSED SKRIPT");
			}
		}
		
	}

	public String getStringToParse() {
		return stringToParse;
	}

	public void setStringToParse(String stringToParse) {
		this.stringToParse = stringToParse;
	}

	public boolean isParsingEnabled() {
		return commenceParsing;
	}

	/**
	 * Will NOT return a string with the parsed result
	 */
	public void parse() {
		this.commenceParsing = true;;
	}
	
	

}
