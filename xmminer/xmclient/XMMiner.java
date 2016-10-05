//Title:      XM-Miner
//Version:    
//Copyright:  Copyright (c) 1999
//Author:     Yong Uk Song
//Company:    DIS
//Description:XM-Miner

package xmminer.xmclient;

import javax.swing.UIManager;
import java.awt.*;
import java.awt.event.*;

public class XMMiner{
	
	boolean packFrame = false;

	public XMMiner(int lan){
		XMMinerFrame frame = new XMMinerFrame(lan);

		if (packFrame) frame.pack();
		else frame.validate();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		
		if (frameSize.height > screenSize.height) frameSize.height = screenSize.height;
		if (frameSize.width > screenSize.width) frameSize.width = screenSize.width;
		frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

		new XMSplash(frame, "xmminer/xmclient/images/title.gif");
		for(int i = 0; i < 3; i++){
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){}
		}

		frame.setVisible(true);
	}

	public static void main(String[] args){
		try{
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}catch(Exception e){}

		Runtime rt = Runtime.getRuntime();
		long total = rt.totalMemory();
		System.out.println("Total Memory: " + String.valueOf(total));

		if(args.length>0){
			new XMMiner(Integer.parseInt(args[0]));
		}else{
			new XMMiner(1);
		}
	}
}
