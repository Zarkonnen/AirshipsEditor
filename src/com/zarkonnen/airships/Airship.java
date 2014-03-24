package com.zarkonnen.airships;

import com.zarkonnen.catengine.Draw;
import com.zarkonnen.catengine.util.Clr;
import java.util.ArrayList;
import java.util.Iterator;

public class Airship {
	public static final Clr ARMOUR_BACK = new Clr(0, 0, 0, 180);
	
	public String name;
	
	private ArrayList<Module> modules = new ArrayList<Module>();
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private int w, h;
	public int x, y;
	
	public void draw(Draw d, double x, double y, int ms, boolean outside) {
		if (outside) {
			for (Module m : modules) {
				m.type.draw(d, x + m.x * AGame.SPRITE_GRID_SIZE, y + m.y * AGame.SPRITE_GRID_SIZE, ms);
			}
			for (Tile t : tiles) {
				t.armour.type.app.draw(d, x + t.x * AGame.SPRITE_GRID_SIZE, y + t.y * AGame.SPRITE_GRID_SIZE, ms);
			}
		} else {
			for (Tile t : tiles) {
				t.armour.type.app.draw(d, x + t.x * AGame.SPRITE_GRID_SIZE, y + t.y * AGame.SPRITE_GRID_SIZE, ms, ARMOUR_BACK);
			}
			for (Module m : modules) {
				m.type.draw(d, x + m.x * AGame.SPRITE_GRID_SIZE, y + m.y * AGame.SPRITE_GRID_SIZE, ms);
			}
		}
	}
	
	public int getWidth() {
		return w;
	}
	
	public int getHeight() {
		return h;
	}
	
	public boolean canAddModule(ModuleType type, int x, int y) {
		if (modules.isEmpty()) { return true; }
		// Check no modules overlap and at least one borders.
		boolean borders = false;
		for (Module m : modules) {
			int aLeftEdge = x;
			int aRightEdge = x + type.w;
			int aTopEdge = y;
			int aBottomEdge = y + type.h;
			
			int bLeftEdge = m.x;
			int bRightEdge = m.x + m.type.w;
			int bTopEdge = m.y;
			int bBottomEdge = m.y + m.type.h;
			
			if (aRightEdge > bLeftEdge && aLeftEdge < bRightEdge &&
				aBottomEdge > bTopEdge && aTopEdge < bBottomEdge)
			{
				return false;
			}
			
			if (type.backOnly && bLeftEdge < aRightEdge &&
				aBottomEdge > bTopEdge && aTopEdge < bBottomEdge)
			{
				return false;
			}
			
			if (type.frontOnly && bLeftEdge >= aRightEdge &&
				aBottomEdge > bTopEdge && aTopEdge < bBottomEdge)
			{
				return false;
			}
			
			// Vertical
			if (m.x + m.type.w > x && m.x < x + type.w &&
				(m.y + m.type.h == y || m.y == y + type.h)
			)
			{
				borders = true;
			}
			// Horizontal
			if (m.y + m.type.h > y && m.y < y + type.h &&
				(m.x + m.type.w == x || m.x == x + type.w)
			)
			{
				borders = true;
			}
		}
		return borders;
	}
	
	public void addModule(ModuleType type, int x, int y) {
		Module m = new Module(type, x, y);
		modules.add(m);
		for (int dy = 0; dy < type.h; dy++) {
			for (int dx = 0; dx < type.w; dx++) {
				tiles.add(new Tile(m, x + dx, y + dy));
			}
		}
		layout();
	}
	
	public void removeModuleAt(int x, int y) {
		for (Module m : modules) {
			if (m.x <= x && m.y <= y && m.x + m.type.w > x && m.y + m.type.h > y) {
				removeModule(m);
				layout();
				return;
			}
		}
	}
	
	public void removeModule(Module m) {
		modules.remove(m);
		for (Iterator<Tile> it = tiles.iterator(); it.hasNext();) {
			if (it.next().module == m) {
				it.remove();
			}
		}
		layout();
	}
	
	private void layout() {
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		for (Module m : modules) {
			minX = Math.min(minX, m.x);
			minY = Math.min(minY, m.y);
			maxX = Math.max(maxX, m.x + m.type.w);
			maxY = Math.max(maxY, m.y + m.type.h);
		}
		int xShift = -minX;
		int yShift = -minY;
		w = maxX - minX;
		h = maxY - minY;
		for (Module m : modules) {
			m.x += xShift;
			m.y += yShift;
		}
		for (Tile t : tiles) {
			t.x += xShift;
			t.y += yShift;
		}
		x -= xShift * AGame.SPRITE_GRID_SIZE;
		y -= yShift * AGame.SPRITE_GRID_SIZE;
	}
	
	public Tile tileAt(int x, int y) {
		return null;
	}
	
	public void hit(Shot shot) {
		Tile t = tileAt(shot.tX, shot.tY);
		if (t != null) { t.hit(shot); }
	}
}
