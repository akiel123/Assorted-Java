package com.manke.newgame;

import java.awt.image.BufferedImage;

public class InkMan {
	
	private int color, cr, cg, cb;
	private BufferedImage world;
	private int w, h;
	private int px, py;
	private double strength = 1, faultRate = 0;
	private int actions = 1;
	
	public InkMan(int color, BufferedImage world){
		this.color = color;
		this.world = world;
		cr = color / 0xFFFF;
		cg = color % 0xFFFF / 0xFF;
		cb = color % 0xFF;
		w = world.getWidth();
		h = world.getHeight();
		px = (int)(Math.random() * w);
		py = (int)(Math.random() * h);
	}
	
	public void move(){
		
	}
	
	private int[] sampleContamination(){
		int rgb[] = new int[8];
		
		rgb[0] = world.getRGB((px + 1 + w) % w, (py + 1 + h) % h);
		rgb[1] = world.getRGB((px + 1 + w) % w, (py) % h);
		rgb[2] = world.getRGB((px + 1 + w) % w, (py - 1 + h) % h);
		rgb[3] = world.getRGB((px) % w, (py + 1 + h) % h);
		rgb[4] = world.getRGB((px) % w, (py - 1 + h) % h);
		rgb[5] = world.getRGB((px - 1 + w) % w, (py + 1 + h) % h);
		rgb[6] = world.getRGB((px - 1 + w) % w, (py) % h);
		rgb[7] = world.getRGB((px - 1 + w) % w, (py - 1 + h) % h);
		
		return rgb;
	}
	
	private int getMostContaminated(int[] sample){
		int candVal = 0;
		int candID = 0;

		int r[] = new int[8];
		int g[] = new int[8];
		int b[] = new int[8];

		for(int i = 0; i < 8; i++){
			r[i] = sample[i] / 0xFFFF;
			g[i] = sample[i] % 0xFFFF / 0xFF;
			b[i] = sample[i] % 0xFF;
		}
		
		for(int i = 0; i < sample.length; i++){
			int temp = Math.abs(r[i] - cr) + Math.abs(g[i] - cg) + Math.abs(b[i] - cb);
			if (temp > candVal){
				candVal = temp;
				candID = i;
			}
			else if(temp == candVal && Math.random() > 0.5){
				candID = i;
			}
		}
		return (int)(Math.random() * 8);
	}
	
	private int[] getPosFromCandID(int candID){
		int[] pos = new int[2];
		switch(candID){
		case 0: pos [0] = (px + 1 + w) % w;
				pos [1] = (py + 1 + h) % h;
				break;
		case 1: pos [0] = (px + 1 + w) % w;
				pos [1] = (py) % h;
				break;
		case 2: pos [0] = (px + 1 + w) % w;
				pos [1] = (py - 1 + h) % h;
				break;
		case 3: pos [0] = (px) % w;
				pos [1] = (py + 1 + h) % h;
				break;
		case 4: pos [0] = (px) % w;
				pos [1] = (py - 1 + h) % h;
					break;
		case 5: pos [0] = (px - 1 + w) % w;
				pos [1] = (py + 1 + h) % h;
				break;
		case 6: pos [0] = (px - 1 + w) % w;
				pos [1] = (py) % h;
				break;
		case 7: pos [0] = (px - 1 + w) % w;
				pos [1] = (py - 1 + h) % h;
					break;
		default:	pos[0] = px;
					pos[1] = py;
					break;
		}
		return pos;
	}
	
	public void decontaminateSpace(){
		int x = px, y = py;
		int prevColor = world.getRGB(x, y);
		int pr = prevColor / 0xFFFF;
		int pg = prevColor % 0xFFFF / 0xFF;
		int pb = prevColor % 0xFF;
		int dr = cr - pr;
		int dg = cg - pg;
		int db = cb - pb;
		int nr = pr + (int)(dr * (strength * (1 - Math.random() * faultRate)));
		int ng = pg + (int)(dg * (strength * (1 - Math.random() * faultRate)));
		int nb = pb + (int)(db * (strength * (1 - Math.random() * faultRate)));
		int nrgb = nr * 0xFFFF + ng * 0xFF+ nb;
		world.setRGB(x, y, nrgb);
	}
	
	public void act(){
		for(int i = 0; i < actions; i++){
			int[] sample = sampleContamination();
			int candID = getMostContaminated(sample);
			int[] pos = getPosFromCandID(candID);
			px = pos[0];
			py = pos[1];
			decontaminateSpace();
		}
	}
}
