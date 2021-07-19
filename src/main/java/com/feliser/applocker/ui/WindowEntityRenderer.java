package com.feliser.applocker.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import com.feliser.applocker.entities.WindowEntity;

public class WindowEntityRenderer extends DefaultListCellRenderer {
	@Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if(((WindowEntity)value).icon != null) label.setIcon(new ImageIcon(((WindowEntity)value).icon));
		label.setFont(new Font("Sans-Serif", Font.BOLD, 16));
		label.setBackground(new Color(225, 240, 252));
		label.setIconTextGap(8);
		label.setBorder(BorderFactory.createLineBorder(new Color(210, 231, 247), 4));
		if(isSelected) {
			label.setBackground(new Color(210, 231, 247));
			label.setBorder(BorderFactory.createLineBorder(new Color(180, 213, 237), 4));
		}
		return label;
    }
}
