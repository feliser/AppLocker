package com.feliser.applocker.services;

import java.util.HashSet;

import javax.swing.ListModel;

import com.feliser.applocker.entities.ManagementEntity;
import com.feliser.applocker.entities.WindowEntity;
import com.feliser.applocker.tools.HWNDTools;
import com.feliser.applocker.ui.MainFrame;
import com.sun.jna.platform.win32.WinDef.HWND;

public class ManagementService {
	private static HashSet<HWND> activeHWnd;
	
	public static void init() {
		activeHWnd = new HashSet<HWND>();
	}
	
	public static boolean CheckHWND(HWND hWnd) {
		return activeHWnd.contains(hWnd);
	}
	
	public static void AddEntity(ManagementEntity w) {
		MainFrame.listItems.addElement(w);
		activeHWnd.add(w.window.hWnd);
		w.HideWindow();
	}
	
	public static void DelEntity(ManagementEntity w) {
		MainFrame.listItems.removeElement(w);
		activeHWnd.remove(w.window.hWnd);
		if(w.state == false) {
			w.ShowWindow();
		}
	}
	
	public static void ToggleEntity(ManagementEntity w) {
		if(w.state == false) {
			w.ShowWindow();
		} else {
			w.HideWindow();
		}
	}
	
	public static void Destroy() {
		ListModel<ManagementEntity> model = MainFrame.windowList.getModel();
		for(int i = 0; i < model.getSize(); ++i) {
			ManagementEntity e = model.getElementAt(i);
			DelEntity(e);
		}
	}
}
