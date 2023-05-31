package me.lowen.gui;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import me.lowen.SettingsManager;
import me.lowen.threads.AutoParsingThread;
import not.me.TextLineNumber;

public class SkUITextPanel extends JPanel{

	private static final long serialVersionUID = -221014358881827844L;

	JTextPane pane;
	SkUIFrame frame;
	AutoParsingThread autoParsing;
	public SkUITextPanel(SkUIFrame frame) {
		this.frame = frame;
		this.setLayout(new GridLayout(1, 1));
		
		autoParsing = new AutoParsingThread(frame, frame.getHeader().getParsingThread());
		Thread thread = new Thread(autoParsing);
		thread.start();
		
		pane = new JTextPane();
		pane.addKeyListener(onType());
		JScrollPane scrollPane = new JScrollPane(pane);
		TextLineNumber tln = new TextLineNumber(pane);
		scrollPane.setRowHeaderView(tln);
		this.add(scrollPane);
	}
	public JTextPane getTextPane() {
		return pane;
	}
	public void setTextPane(JTextPane pane) {
		this.pane = pane;
	}
	
	private KeyListener onType() {
		KeyListener listen = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				frame.repaint();
				frame.revalidate();
				frame.setVisible(true);
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (SettingsManager.isAutoParsingEnabled())
				autoParsing.startCountingUp();
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				autoParsing.stopCountingUpAndResetTimer();
				
			}
		};
		return listen;
		
	}
	public AutoParsingThread getAutoParsingThread() {
		return autoParsing;
	}
	public void setAutoParsingThread(AutoParsingThread autoParsing) {
		this.autoParsing = autoParsing;
	}

	
	

}
