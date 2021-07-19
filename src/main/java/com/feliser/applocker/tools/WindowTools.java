package com.feliser.applocker.tools;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.feliser.applocker.entities.WindowEntity;
import com.feliser.applocker.services.ManagementService;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.PointerByReference;

import sun.awt.shell.ShellFolder;

public class WindowTools {
    private static final int MAX_LEN = 1024;
    private static final List<String> filter = Arrays.asList(new String[]{"Microsoft Text Input Application", "App Locker", "Select Application", "Realtek Audio Console"});

	// https://stackoverflow.com/questions/19279134/java-windows-taskbar-using-jna-how-do-i-convert-window-icons-hicon-to-java
	public static List<WindowEntity> GetActiveWindowList() {
		List<WindowEntity> ret = new ArrayList<WindowEntity>();
		u32.EnumWindows(new u32.WNDENUMPROC() {
			@Override
			public boolean callback(Pointer hWndPointer, Pointer arg) {
				HWND hWnd = new HWND(hWndPointer);
				if(u32.IsWindowVisible(hWndPointer) && !(ManagementService.CheckHWND(hWnd))) {
					int GWL_EXSTYLE = -20;
                    long WS_EX_TOOLWINDOW = 0x00000080L;
                    if((u32.GetWindowLongW(hWndPointer, GWL_EXSTYLE) & WS_EX_TOOLWINDOW) == 0) { 
                    	WindowEntity w = GetWindowEntity(hWnd);
                    	if(!(w.titleName == null || w.titleName.trim().equals(""))) {
                    		for(String check : filter) {
                    			if(w.titleName.trim().equals(check)) return true;
                    		}
                    		w.icon = GetWindowImage(w);
                    		ret.add(w);
                    	}
                    }
				}
				return true;
			}
		}, null);
		return ret;
	}
	
	public static WindowEntity GetWindowEntity(HWND hWnd) {
		if(hWnd == null) return null;
		
		char[] buf = new char[MAX_LEN*2];
		u32.GetWindowTextW(hWnd, buf, MAX_LEN);
		String title = Native.toString(buf).trim();
		
		PointerByReference ptr = new PointerByReference();
		u32.GetWindowThreadProcessId(hWnd, ptr);
		Pointer process = k32.OpenProcess(k32.PROCESS_QUERY_INFORMATION | k32.PROCESS_VM_READ, false, ptr.getValue());
		psAPI.GetModuleFileNameExW(process, null, buf, MAX_LEN);
		String procPath = Native.toString(buf);
		
		return new WindowEntity(hWnd, title, procPath);
	}
	
	public static Image GetWindowImage(WindowEntity w) {
		if(w.procPath == null || w.procPath.trim().equals("")) return null;
		try {
			File f = new File(w.procPath);
			if(f.exists()) {
				ShellFolder sf = ShellFolder.getShellFolder(f);
				if(sf != null) {
					return sf.getIcon(true);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// adapted from https://stackoverflow.com/questions/43279386/retrieve-order-of-open-windows-as-ordered-in-the-alt-tab-list
	static class psAPI {
        static {
            Native.register("psapi"); }
        public static native int GetModuleBaseNameW(Pointer hProcess, Pointer hmodule, char[] lpBaseName, int size);
        public static native int GetModuleFileNameExW(Pointer hProcess, Pointer hmodule, char[] lpBaseName, int size); }
    static class k32 {
        static {
            Native.register("kernel32"); }
        public static int PROCESS_QUERY_INFORMATION=0x0400;
        public static int PROCESS_VM_READ=0x0010;
        public static native Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer); }
    static class u32 {
        static {
            Native.register("user32"); }
        public static native int GetWindowThreadProcessId(HWND hWnd, PointerByReference pref);
        public static native int GetWindowTextW(HWND hWnd, char[] lpString, int nMaxCount);
        public static native boolean EnumWindows(WNDENUMPROC lpEnumFunc, Pointer arg);
        public static interface WNDENUMPROC extends com.sun.jna.win32.StdCallLibrary.StdCallCallback {
            boolean callback(Pointer hWnd, Pointer arg); }
        public static native boolean IsWindowVisible(Pointer hWnd);
        public static native boolean SetForegroundWindow(HWND hWnd);
        public static native int GetWindowLongW(Pointer hWnd, int nIndex); 
    }
}