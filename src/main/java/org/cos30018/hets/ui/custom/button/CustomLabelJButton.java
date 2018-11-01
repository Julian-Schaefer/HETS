package org.cos30018.hets.ui.custom.button;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.plaf.ButtonUI;

public class CustomLabelJButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 863688512242933695L;

	public CustomLabelJButton(String text, String fontName, int fontSize, ButtonUI ui) {

		this.setText(text);
		this.setFont(new Font(fontName, Font.BOLD, fontSize));
		this.setBackground(new Color(0x2dce98));
		this.setForeground(Color.WHITE);
		this.setUI(ui);

	}
}
