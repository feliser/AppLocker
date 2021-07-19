package com.feliser.applocker.entities;

import java.awt.Image;

import com.sun.jna.platform.win32.WinDef.HWND;

public class WindowEntity {
	public HWND hWnd;
	public String titleName, procPath;
	public Image icon;
	
	public WindowEntity(HWND _hWnd, String _titleName, String _procPath) {
		hWnd = _hWnd;
		titleName = _titleName;
		procPath = _procPath;
	}
	
	@Override
	public String toString() {
		return titleName;
	}
}