package com.feliser.applocker.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import com.feliser.applocker.entities.ManagementEntity;

public class ManagementEntityRenderer extends DefaultListCellRenderer {
	@Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setIcon(new ImageIcon(((ManagementEntity)value).window.icon));
		label.setFont(new Font("Sans-Serif", Font.BOLD, 16));
		label.setIconTextGap(8);
		if(((ManagementEntity) value).state == true) {
			label.setBackground(new Color(231, 255, 219));
			label.setBorder(BorderFactory.createLineBorder(new Color(218, 255, 199), 4));
		} else {
			label.setBackground(new Color(255, 215, 215));
			label.setBorder(BorderFactory.createLineBorder(new Color(252, 194, 194), 4));
		}
		
		if(isSelected) {
			label.setBackground(new Color(label.getBackground().getRed() - 20, label.getBackground().getGreen() - 20, label.getBackground().getBlue() - 20));
			if(((ManagementEntity) value).state == true) {
				label.setBorder(BorderFactory.createLineBorder(new Color(197, 250, 170), 4));
			} else {
				label.setBorder(BorderFactory.createLineBorder(new Color(255, 176, 176), 4));
			}
		}
		
        return label;
    }
}
