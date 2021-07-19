package com.feliser.applocker.entities;

import java.awt.Rectangle;

import com.feliser.applocker.tools.HWNDTools;
import com.feliser.applocker.ui.MainFrame;

public class ManagementEntity {
	public WindowEntity window;
	public Rectangle oldSize;
	public boolean state;
	
	public ManagementEntity(WindowEntity w) {
		window = w;
	}
	
	public void ShowWindow() {
		HWNDTools.SetParent(window.hWnd, HWNDTools.deskHWND);
		HWNDTools.MoveWindow(window.hWnd, oldSize.x, oldSize.y, oldSize.width, oldSize.height);
		//HWNDTools.SetFocus(window.hWnd);
		HWNDTools.ShowWindow(window.hWnd, 5);
		HWNDTools.ShowWindow(HWNDTools.frameHWND, 5);
		state = true;
		
		// update background color of item in list
		MainFrame.windowList.repaint();
	}
	
	public void HideWindow() {
		oldSize = HWNDTools.GetWindowSize(window.hWnd);		
		HWNDTools.MoveWindow(window.hWnd, 5000, 5000, 100, 100);
		HWNDTools.SetParent(window.hWnd, HWNDTools.frameHWND);
		HWNDTools.MoveWindow(window.hWnd, 5000, 5000, oldSize.width, oldSize.height);
		state = false;
		
		// update background color of item in list
		MainFrame.windowList.repaint();
	}
	
	@Override
	public String toString() {
		return window.toString();
	}
}
