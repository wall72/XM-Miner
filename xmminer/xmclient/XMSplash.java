
//Title:      XM-Miner
//Version:    
//Copyright:  Copyright (c) 1999
//Author:     Chang Ho Lee
//Company:    DIS
//Description:XM-Miner

package xmminer.xmclient;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JWindow;

public class XMSplash extends JWindow
{
  private Image splashImage;
  private int imgWidth, imgHeight;
  private static final int BORDERSIZE = 0;
  private static final Color BORDERCOLOR = Color.gray;

  public XMSplash(Frame f, String imgName)
  {
    super(f);
    splashImage = loadSplashImage(imgName);
    showSplashScreen();
    f.addWindowListener(new WindowListener());
  }

  public Image loadSplashImage(String imgName)
  {
    MediaTracker tracker = new MediaTracker(this);
    Image result = Toolkit.getDefaultToolkit().getImage(imgName);
    tracker.addImage(result, 0);
    try
    {
      tracker.waitForAll();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    imgWidth = result.getWidth(this);
    imgHeight = result.getHeight(this);
    return(result);
  }

  public void showSplashScreen()
  {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setBackground(BORDERCOLOR);
    int w = imgWidth + (BORDERSIZE * 2);
    int h = imgHeight + (BORDERSIZE * 2);
    int x = (screenSize.width - w) / 2;
    int y = (screenSize.height - h) / 2;
    setBounds(x, y, w, h);
    setVisible(true);
  }

  public void paint(Graphics g)
  {
    g.drawImage(splashImage, BORDERSIZE, BORDERSIZE, imgWidth, imgHeight, this);
  }

  class WindowListener extends WindowAdapter
  {
    public void windowActivated(WindowEvent we)
    {
      setVisible(false);
      dispose();
    }
  }
}
