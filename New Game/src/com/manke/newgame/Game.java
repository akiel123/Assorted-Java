package com.manke.newgame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel {
	
	static int w = 1980, h = 1020;
	InkMan[] inkmen = new InkMan[100000];
	
	BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

    int x = 0;
    int y = 0;

    public Game(){
    	for(int i = 0; i < inkmen.length; i++){
    		inkmen[i] = new InkMan((int)(255 * Math.random() * 0xFFFF +255 * Math.random() * 0xFF + 255 * Math.random()) , img);
    	}
	}
	
    private void initializeWorld(){
    	for(int x = 0; x < w; x++){
    		for(int y = 0; y < h; y++){
    			img.setRGB(x, y, 0xFFFFFF);
    		}
    	}
    }
    private void updateWorld() {
    	for(int i = 0; i < inkmen.length; i++){
    		inkmen[i].act();
    	}
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, 0, 0, this);
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Sample Frame");
        Game game = new Game();
        frame.add(game);
        frame.setSize(w, h);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.initializeWorld();
        
        while (true) {
            game.updateWorld();
            game.repaint();
            //Thread.sleep(10);
        }
    }
}