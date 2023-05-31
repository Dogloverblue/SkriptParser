package me.lowen.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

import me.lowen.SkUtils;

public class SkUIErrorPane extends JScrollPane {

	private static final long serialVersionUID = 7533633572981476173L;

	boolean poppedOut = false;
	
	JButton popOut;
	JTextPane pane = new JTextPane();
	StyledDocument sd = pane.getStyledDocument();
	SkUIFrame frame;
	
	public SkUIErrorPane(SkUIFrame frame) {
		super();
		this.frame = frame;
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		mainPanel.add(errorHeader());
		pane.setEditable(false);
		mainPanel.add(pane);
		this.setViewportView(mainPanel);
		
	}


	PoppedOutFrame popOutFrame;
	
	public ActionListener popOutButtonClick() {
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				poppedOut = !poppedOut;
				popOut(poppedOut);
				
			}
			
		};
		return listener;
		
	}
	public void popOut(boolean poppedOut) {
		if (poppedOut) {
//			frame.getErrorPane().remove(SkUIErrorPane.this);
			frame.setErrorPaneVisible(false);
			popOutFrame = new PoppedOutFrame("Error Pane", frame);
			
			popOutFrame.add(SkUIErrorPane.this);
			
			try {
				popOut.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/popIn.png")).getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			popOutFrame.pack();
			SkUtils.setWindowSizeIfLessThanSize(popOutFrame, 400, 250);
			popOutFrame.setVisible(true);
		} else {
			popOutFrame.remove(SkUIErrorPane.this);
			popOutFrame.dispose();
			
			try {
				popOut.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/popOut.png")).getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
//			frame.getErrorScrollPane().add(new JLabel("Hi"));
			frame.setErrorPaneVisible(true);
		}
	}
	
	public void addErrorToPane() {
		
	}
	private JPanel errorHeader() {
		JPanel header = new JPanel();
		header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
		
		JPanel leftHeader = new JPanel();
		leftHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
		leftHeader.add(new JLabel("Error Pane"));
		
		JPanel rightHeader = new JPanel();
		FlowLayout rightHeaderFlow = new FlowLayout(FlowLayout.RIGHT);
		rightHeaderFlow.setHgap(3);
		rightHeader.setLayout(rightHeaderFlow);
		
		popOut = new JButton();
		popOut.setFocusPainted(false);
		popOut.addActionListener(popOutButtonClick());
		SkUtils.showJButton(popOut, false);
		try {
			popOut.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/popOut.png")).getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		rightHeader.add(popOut);
		
		JButton clearButton = new JButton("Clear");
		clearButton.setBackground(Color.WHITE);
		clearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pane.setText("");
				
				frame.repaint();
				frame.revalidate();
				
			}
		});
		rightHeader.add(clearButton);
		header.add(leftHeader);
		header.add(rightHeader);
		return header;
	}
	public void setText(String text) {
		pane.setText(text);
	}
//	public void setTextWithAppropriateLineBreaks(String text) {
//		this.setT
//
//	}
	
	

}

class PoppedOutFrame extends JFrame {
	private static final long serialVersionUID = 1545412097311278511L;

	public PoppedOutFrame(SkUIFrame frame) throws HeadlessException {
		this("", frame);
	}

	public PoppedOutFrame(String title, SkUIFrame frame) throws HeadlessException {
		super(title);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(frame);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(WindowEvent e) {
				frame.getErrorPane().poppedOut = false;
			frame.getErrorPane().popOut(false);
		    }
		});
	}
	
	
	
}
