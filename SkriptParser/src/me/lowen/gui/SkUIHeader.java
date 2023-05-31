package me.lowen.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import me.lowen.SkUtils;
import me.lowen.threads.ParsingThread;
import not.me.hansolo.custom.SteelCheckBox;
import not.me.hansolo.tools.ColorDef;


public class SkUIHeader extends JPanel {

	private static final long serialVersionUID = -8476325438871689088L;
	SkUIFrame frame;
	
	Preferences prefs = Preferences.userRoot();
	
	private boolean isInLoadingState = false;
	
	SteelCheckBox scb;
	JButton parseButton;
	
	String loadingStateMessage = "Parsing...";
	ParsingThread thread;
	
	
	public SkUIHeader(SkUIFrame frame) {
		
		thread = new ParsingThread(frame.getServer(), frame);
		Thread realThread = new Thread(thread);
		realThread.start();
//		thread.run();
		
		this.frame = frame;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setMaximumSize(new Dimension(3000, 200));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		parseButton = new JButton("Parse!"); 
		// looks so much better whie off
		parseButton.addActionListener(parseButtonActionListener());
		parseButton.setFocusPainted(false);
		parseButton.setBackground(Color.red);
		parseButton.setForeground(Color.white);

		leftPanel.add(parseButton);
		scb = new SteelCheckBox();
		scb.setSelected(prefs.getBoolean("autoParse", true)); //$NON-NLS-1$
		scb.addActionListener(autoParseActionListener(scb));
		scb.setText("Auto Parse"); //$NON-NLS-1$
		scb.setColored(true);
		scb.setSelectedColor(ColorDef.JUG_GREEN);
		leftPanel.add(scb);
		this.add(leftPanel);
		
		
		JButton settingsButton = new JButton();
		SkUtils.showJButton(settingsButton, false);
		settingsButton.setFocusPainted(false);
		
		SettingsFrame s = new SettingsFrame();
		JFrame settingsFrame = new JFrame("Configure Settings"); //$NON-NLS-1$
		settingsFrame.add(s);
		settingsFrame.pack();
		settingsFrame.setResizable(false);
		settingsFrame.setLocationRelativeTo(null);
		settingsButton.setIcon(new ImageIcon(getIcon("/settings_icon.png", 15, 15)));
		
		settingsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				settingsFrame.pack();
				settingsFrame.setVisible(true);
				
			}
			
		});
		rightPanel.add(settingsButton);
		
		this.add(leftPanel);
		this.add(rightPanel);
		
		
	
	}
	public ActionListener parseButtonActionListener() {
		ActionListener action = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("parsing!");
				setIfInLoadingState(true);
				thread.setStringToParse(frame.getTextPanel().getTextPane().getText());
				thread.parse();
				System.out.println("action listener end");
			}
			
		};
		return action;
	}
	
	/**
	 * Used for when the parser backend is still setting up. When loading state is true,
	 * the parse button will be replaced with a loading gif, and auto-parse slider will be disabled
	 * @param loading if it should be in loading state
	 */
	public void setIfInLoadingState(boolean loading) {
		isInLoadingState = loading;
		if (loading) {
			parseButton.setForeground(Color.black);
			parseButton.setText(loadingStateMessage);
			
			URL s = this.getClass().getResource("/loading.gif");
			ImageIcon imgI = new ImageIcon(s);
			Image img = imgI.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_DEFAULT);
			parseButton.setIcon(new ImageIcon(img));
			
			
		} else {
			parseButton.setForeground(Color.white);
			parseButton.setText("Parse!");
			parseButton.setIcon(null);
		}
		SkUtils.showJButton(parseButton, !loading);
		//scb.setEnabled(loading);
	}
	public void setIfInErroredState(boolean isInError) {
		System.out.println("error");
		if (isInError) {
			parseButton.setForeground(Color.black);
			parseButton.setText("Parsing Failed!");
			
			URL s = this.getClass().getResource("/x.png");
			ImageIcon imgI = new ImageIcon(s);
			parseButton.setIcon(imgI);
			
			
		} else {
			parseButton.setForeground(Color.white);
			parseButton.setText("Parse!");
			parseButton.setIcon(null);
		}
		SkUtils.showJButton(parseButton, !isInError);
		//scb.setEnabled(loading);
	}

	private ActionListener autoParseActionListener(SteelCheckBox scb) {
		ActionListener oneToReturn = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Just to ensure you dont have to type a character to begin parsing
				if (scb.isSelected())
					frame.getTextPanel().getAutoParsingThread().startCountingUp();
				prefs.putBoolean("SkParser_autoParse", scb.isSelected());
				
			}
			
		};
		return oneToReturn;
	}
	
	
	
	private Image getIcon(String iconPath, int width, int height) {
		try {
			Image img = ImageIO.read(getClass().getResourceAsStream(iconPath)).getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH );
			return img;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public boolean isInLoadingState() {
		return isInLoadingState;
	}
	
	public String getLoadingStateMessage() {
		return loadingStateMessage;
	}
	public void setLoadingStateMessage(String loadingStateMessage) {
		this.loadingStateMessage = loadingStateMessage;
	}
	public ParsingThread getParsingThread() {
		return thread;
	}
//	public void setParsingThread(ParsingThread thread) {
//		this.thread = thread;
//	}

	
	
	

	

}

