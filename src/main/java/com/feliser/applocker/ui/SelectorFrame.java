package com.feliser.applocker.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.feliser.applocker.entities.ManagementEntity;
import com.feliser.applocker.entities.WindowEntity;
import com.feliser.applocker.services.ManagementService;
import com.feliser.applocker.tools.WindowTools;

public class SelectorFrame {
	public JFrame frame;
	public JList<WindowEntity> windowList;
	public DefaultListModel<WindowEntity> listItems;
	public JScrollPane listScrollPane;
	public JButton addButton;

	public SelectorFrame() {
		frame = new JFrame("Select Application");
		frame.setSize(400, 400);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		
		try {
			frame.setIconImage(ImageIO.read(new File("res/lock.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		addButton = new JButton("Add Selected");
		addButton.setFocusable(false);
		addButton.setFont(new Font("Sans-Serif", Font.BOLD, 16));
		addButton.setForeground(Color.WHITE);
		addButton.setBackground(new Color(200, 200, 200));
		addButton.setEnabled(false);
		addButton.setSize(365, 40);
		
		addButton.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 4));
		
		frame.add(addButton);
		addButton.setLocation(10, 10);
		
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ManagementService.AddEntity(new ManagementEntity(windowList.getSelectedValue()));
				frame.dispose();
			}
		});
		
		listItems = new DefaultListModel();
		
		windowList = new JList<WindowEntity>(listItems);
		windowList.setCellRenderer(new WindowEntityRenderer());
		
		listScrollPane = new JScrollPane();
		listScrollPane.setViewportView(windowList);
		listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		windowList.setLayoutOrientation(JList.VERTICAL);
		
		listScrollPane.setSize(365, 292);
		frame.add(listScrollPane);
		listScrollPane.setLocation(10, 60);
		
		frame.setVisible(true);
		populateWindowList();
		
		windowList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(windowList.getSelectedIndex() == -1) {
					addButton.setEnabled(false);
					addButton.setBackground(new Color(200, 200, 200));
					addButton.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 4));
				} else {
					addButton.setEnabled(true);
					addButton.setBackground(new Color(0, 176, 41));
					addButton.setBorder(BorderFactory.createLineBorder(new Color(0, 158, 37), 4));
				}	
			}
		});
	}
	
	public void populateWindowList() {
		List<WindowEntity> activeWindowList = WindowTools.GetActiveWindowList();
		for(WindowEntity w : activeWindowList) {
			listItems.addElement(w);
		}
	}
}
