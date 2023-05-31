package me.lowen.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import me.lowen.OutputReceiver;

/**
 * A JFrame that contains all the aspects needs for the Skript parser.
 * @author JLowen
 *
 */
public class SkUIFrame extends JFrame {

	private static final long serialVersionUID = 3969957804308027471L;
	
	private SkUITextPanel textPanel;
	private SkUIErrorPane errorPane;
	private SkUIHeader header;
	OutputReceiver server;
	
	public SkUIFrame(OutputReceiver server) {
		this.server = server;
		header = new SkUIHeader(this);
		textPanel = new SkUITextPanel(this);
		errorPane = new SkUIErrorPane(this);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		errorPane.setMaximumSize(new Dimension(3000, 400));
		
		this.add(header);
		this.add(textPanel);
		this.add(errorPane);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(WindowEvent e)
		    {
				server.sendText("stop");
		      System.out.println("Closed");
		      System.exit(0);
		    }
		});
	}
	
	
	public SkUITextPanel getTextPanel() {
		return textPanel;
	}
	public void setTextPanel(SkUITextPanel textPanel) {
		this.textPanel = textPanel;
	}
	public SkUIHeader getHeader() {
		return header;
	}
	public void setHeader(SkUIHeader header) {
		this.header = header;
	}


	public OutputReceiver getServer() {
		return server;
	}


	public SkUIErrorPane getErrorPane() {
		return errorPane;
	}
	



	public void setErrorLabel(SkUIErrorPane errorLabel) {
		this.errorPane = errorLabel;
	}
	
	public void setErrorPaneVisible(boolean show) {
		if (show) {
			this.add(errorPane);
		} else {
			this.remove(errorPane);
		}
		this.repaint();
		this.revalidate();
	}

	
	

//	public void setServer(OutputReceiver server) {
//		this.server = server;
//	}
	
	

}
