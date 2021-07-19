package com.feliser.applocker.tools;

import java.awt.Rectangle;

import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

public class HWNDTools {
	public static WinDef.HWND frameHWND, deskHWND;
	
	// returns hWnd of window with requested title
	public static WinDef.HWND GetHWND(String title) {
		return User32.INSTANCE.FindWindow(null, title);
	}
	
	// will put one window inside of another
	public static void SetParent(WinDef.HWND child, WinDef.HWND parent) {
		User32.INSTANCE.SetParent(child, parent);
	}
	
	// remove window from others
	public static void DelParent(WinDef.HWND child) {
		SetParent(child, null);
	}
	
	public static void MoveWindow(WinDef.HWND hwnd, int x, int y, int w, int h) {
		User32.INSTANCE.MoveWindow(hwnd, x, y, w, h, true);
	}
	
	public static WinDef.HWND GetDeskHWND() {
		return User32.INSTANCE.GetDesktopWindow();
	}
	
	public static void ShowWindow(WinDef.HWND hWnd, int SHOW) {
		User32.INSTANCE.ShowWindow(hWnd, SHOW);
	}
	
	public static Rectangle GetWindowSize(WinDef.HWND hWnd) {
		final Rectangle rect = new Rectangle(0, 0, 0, 0);
		WindowUtils.getAllWindows(true).forEach(desktopWindow -> {
		    if(desktopWindow.getHWND().equals(hWnd)) {
		        rect.setRect(desktopWindow.getLocAndSize());
		    }
		});	
		return rect;
	}
	
	public static void SetFocus(WinDef.HWND hWnd) {
		User32.INSTANCE.SetFocus(hWnd);
	}
}
