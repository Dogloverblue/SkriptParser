package me.lowen.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * hi
 * @author Jacob
 *
 */
public class SettingsFrame extends JPanel{

	
	private static final long serialVersionUID = -4555380955947375836L;

	static Preferences prefs = Preferences.userRoot();
	
	public SettingsFrame() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		StringCollectionPanel parseInterval = new StringCollectionPanel("Parsing delay after last keypress (milliseconds)", 2, "SkParser_parseIntervalMillis", "250");
		
		this.add(parseInterval);
		
		JPanel saveButtonPanel = new JPanel();
		saveButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton saveButton = new JButton("Save and Close");
		saveButton.setBackground(Color.WHITE);
		saveButton.setFocusPainted(false);
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SettingsFrame.this.save();
				SwingUtilities.getWindowAncestor(SettingsFrame.this).setVisible(false);
			}
			
		});
		saveButtonPanel.add(saveButton);
		this.add(saveButtonPanel);
		
	}
	
	
	public void save() {
		for (Component c : this.getComponents()) {
	
			
			if (c instanceof StringCollectionPanel) {
				StringCollectionPanel scp = (StringCollectionPanel) c;
				prefs.put(scp.getKey(), scp.getValue());
			
			}
		}
	}

}

//the swing library I made seems a bit bulky, so I'm remaking it simpler here

class StringCollectionPanel extends JPanel {

	private static final long serialVersionUID = -7256498103720792041L;
	Font font = new Font("Consolas", Font.BOLD, 14);
	private String key;
	private JTextField field;
	public StringCollectionPanel(String labelText, int columns, String key, String defaultValue) {
		this.setKey(key);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setField(new JTextField(SettingsFrame.prefs.get(key, defaultValue)));
//		getField().setColumns(columns);s
//		getField().setFont(font);
		JLabel label = new JLabel(labelText);
		
//		label.setFont(font);
		this.add(label);
		this.add(field);
	}
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}


	public JTextField getField() {
		return field;
	}

	public String getValue() {
		return field.getText();
	}

	public void setField(JTextField field) {
		this.field = field;
	}
	
}