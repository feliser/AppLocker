package com.feliser.applocker;

import java.io.IOException;

import com.feliser.applocker.services.ManagementService;
import com.feliser.applocker.tools.HWNDTools;
import com.feliser.applocker.ui.MainFrame;

public class Main {
    public static void main(String args[]) throws IOException {
    	MainFrame.init();
    	ManagementService.init();
    	HWNDTools.frameHWND = HWNDTools.GetHWND("App Locker");
    	HWNDTools.deskHWND = HWNDTools.GetDeskHWND();
    }
}
