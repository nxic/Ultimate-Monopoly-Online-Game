package com.nullPointer.UI;
import javax.imageio.ImageIO;
import javax.swing.*;
import com.nullPointer.Utils.ColorSet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Board extends JPanel{
	private BufferedImage image; 
	private File imageSrc = new File("./assets/ultimate_monopoly.png");
	
    private Color color = new Color(187, 229, 206);
    private Point position = new Point(10,10);
    private int length = 700;
    
    private int width, height;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public Board(Point position, int length) {
    	try {
			image = ImageIO.read(imageSrc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.position = position;
        this.length = length;
        width = image.getWidth(null);
        height = image.getHeight(null);

        setPreferredSize(new Dimension(length,length));
        
    }

    public void paint(Graphics g) {
        //g.setColor(color);
        //g.fillRect(position.x, position.y, length, length);
        g.drawImage(image, position.x, position.y, length, length, null);
    }
}

