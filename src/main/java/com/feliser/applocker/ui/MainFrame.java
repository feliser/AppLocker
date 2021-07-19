package com.feliser.applocker.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.feliser.applocker.entities.ManagementEntity;
import com.feliser.applocker.services.ManagementService;

public class MainFrame {
	public static final int dWidth=535, dHeight=400; 
	
	public static JFrame frame;
	public static JPanel panel;
	public static JButton addButton, delButton, tglButton;
	public static JScrollPane listScrollPane;
	public static DefaultListModel<ManagementEntity> listItems;
	public static JList<ManagementEntity> windowList;
		
	public static void init() {
		frame = new JFrame("App Locker");
		frame.setSize(dWidth, dHeight);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		
		try {
			frame.setIconImage(ImageIO.read(new File("res/lock.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		addButton = new JButton("Add Window");
		addButton.setBackground(new Color(0, 176, 41));
		addButton.setFocusPainted(false);
		addButton.setFont(new Font("Sans-Serif", Font.BOLD, 16));
		addButton.setForeground(Color.WHITE);
		addButton.setSize(160, 40);

		addButton.setBorder(BorderFactory.createLineBorder(new Color(0, 158, 37), 4));
		
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					SelectorFrame s = new SelectorFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		frame.add(addButton);
		addButton.setLocation(10, 10);
		
		delButton = new JButton("Delete Selected");
		delButton.setBackground(new Color(200, 200, 200));
		delButton.setEnabled(false);
		delButton.setFocusPainted(false);
		delButton.setFont(new Font("Sans-Serif", Font.BOLD, 16));
		delButton.setForeground(Color.WHITE);
		delButton.setSize(160, 40);
		
		delButton.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 4));
		
		delButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ManagementService.DelEntity(windowList.getSelectedValue());
			}
		});
	
		frame.add(delButton);
		delButton.setLocation(350, 10);
		
		tglButton = new JButton("Toggle Selected");
		tglButton.setBackground(new Color(200, 200, 200));
		tglButton.setFocusPainted(false);
		tglButton.setEnabled(false);
		tglButton.setFont(new Font("Sans-Serif", Font.BOLD, 16));
		tglButton.setForeground(Color.WHITE);
		tglButton.setSize(160, 40);
		
		tglButton.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 4));

		tglButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ManagementService.ToggleEntity(windowList.getSelectedValue());
			}
		});
		
		frame.add(tglButton);
		tglButton.setLocation(180, 10);
		
		// list
		
		listItems = new DefaultListModel<ManagementEntity>();
		
		windowList = new JList<ManagementEntity>(listItems);
		windowList.setCellRenderer(new ManagementEntityRenderer());
		
		listScrollPane = new JScrollPane();
		listScrollPane.setViewportView(windowList);
		listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		windowList.setLayoutOrientation(JList.VERTICAL);
		
		windowList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(windowList.getSelectedIndex() == -1) {
					delButton.setEnabled(false);
					delButton.setBackground(new Color(200, 200, 200));
					delButton.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 4));
					tglButton.setEnabled(false);
					tglButton.setBackground(new Color(200, 200, 200));
					tglButton.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 4));
				} else {
					delButton.setEnabled(true);
					delButton.setBackground(new Color(235, 30, 30));
					delButton.setBorder(BorderFactory.createLineBorder(new Color(209, 23, 23), 4));
					tglButton.setEnabled(true);
					tglButton.setBackground(new Color(237, 126, 31));
					tglButton.setBorder(BorderFactory.createLineBorder(new Color(224, 118, 27), 4));
				}
			}
			
		});
		
		listScrollPane.setSize(500, 292);
		frame.add(listScrollPane);
		listScrollPane.setLocation(10, 60);
		
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				ManagementService.Destroy();
				System.exit(0); 
			}
		});
	}
}