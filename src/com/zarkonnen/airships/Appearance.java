package com.zarkonnen.airships;

import com.zarkonnen.catengine.Draw;
import com.zarkonnen.catengine.Img;
import com.zarkonnen.catengine.util.Clr;
import java.util.ArrayList;
import org.newdawn.slick.Image;

public class Appearance {
	private static final String SPRITESHEET = "spritesheet";
	
	private ArrayList<Img> frames = new ArrayList<Img>();
	private int interval = 300;
	private int w = 0;
	
	public void draw(Draw d, double x, double y, int ms) {
		Img img = frames.get((ms / interval) % frames.size());
		if (img.machineImgCache != null) {
			((Image) img.machineImgCache).setFilter(Image.FILTER_NEAREST);
		}
		d.blit(frames.get((ms / interval) % frames.size()), x, y);
	}
	
	public void draw(Draw d, double x, double y, int ms, Clr tint) {
		Img img = frames.get((ms / interval) % frames.size());
		if (img.machineImgCache != null) {
			((Image) img.machineImgCache).setFilter(Image.FILTER_NEAREST);
		}
		d.blit(frames.get((ms / interval) % frames.size()), tint, x, y);
	}
	
	public Appearance frame(int x, int y) {
		return frame(x, y, 1, 1);
	}
	
	public Appearance frame(int x, int y, int w, int h) {
		frames.add(new Img(SPRITESHEET, x * AGame.SPRITE_GRID_SIZE, y * AGame.SPRITE_GRID_SIZE, w * AGame.SPRITE_GRID_SIZE, h * AGame.SPRITE_GRID_SIZE, false));
		this.w = Math.max(this.w, w);
		return this;
	}
	
	public Appearance interval(int interval) {
		this.interval = interval;
		return this;
	}
	
	public static Appearance app() { return new Appearance(); }
	public static Appearance app(int x, int y) { return new Appearance(x, y); }

	public Appearance() {}
	
	public Appearance(int x, int y) {
		frame(x, y);
	}

	public int width() { return w; }
}