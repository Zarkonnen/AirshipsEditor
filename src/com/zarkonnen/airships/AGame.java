package com.zarkonnen.airships;

import com.zarkonnen.catengine.Draw;
import com.zarkonnen.catengine.Fount;
import com.zarkonnen.catengine.Frame;
import com.zarkonnen.catengine.Game;
import com.zarkonnen.catengine.Hook;
import com.zarkonnen.catengine.Hook.Type;
import com.zarkonnen.catengine.Hooks;
import com.zarkonnen.catengine.Input;
import com.zarkonnen.catengine.util.Clr;
import com.zarkonnen.catengine.util.Pt;
import com.zarkonnen.catengine.util.Rect;
import com.zarkonnen.catengine.util.ScreenMode;

public class AGame implements Game {
	public static final String ALPHABET = " qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890-=+_!?<>,.;:\"'@£$%^&*()[]{}|\\~/±";
	public static final Fount FOUNT = new Fount("Courier12", 10, 15, 7, 15, ALPHABET);
	public static final Fount BIG_FOUNT = new Fount("LiberationMono18", 14, 24, 12, 24, ALPHABET);
	public static final int SPRITE_GRID_SIZE = 16;
	
	public static final Clr HIGHLIGHT = new Clr(120, 255, 120, 127);
	public static final Clr FORBIDDEN = new Clr(255, 120, 120, 127);
	public static final Clr SKY = new Clr(150, 160, 255);
	
	Pt cursor = new Pt(0, 0);
	Airship ship = new Airship();
	Hooks hs = new Hooks();
	ModuleType sel = null;
	double zoom = 2.0;
	boolean switchedSel = false;
	boolean armourMode = false;
	MyDraw.State drawState = new MyDraw.State();
	int ms = 0;
	
	boolean first = true;

	public AGame() {
		ship.x = 200;
		ship.y = 100;
	}
	
	@Override
	public void input(Input in) {
		if (first) {
			in.setMode(new ScreenMode(1024, 768, false));
			first = false;
		}
		cursor = in.cursor();
		if (in.keyDown("Q") || in.keyDown("ESCAPE")) {
			in.quit();
			System.exit(0);
		}
		if (in.keyDown("UP")) { ship.y += 5; }
		if (in.keyDown("DOWN")) { ship.y -= 5; }
		if (in.keyDown("LEFT")) { ship.x += 5; }
		if (in.keyDown("RIGHT")) { ship.x -= 5; }
		hs.hit(in);
		Pt clicked = in.clicked();
		if (clicked != null && !switchedSel) {
			int modX = (int) Math.floor((clicked.x / zoom - ship.x) / SPRITE_GRID_SIZE);
			int modY = (int) Math.floor((clicked.y / zoom - ship.y) / SPRITE_GRID_SIZE);
			if (sel == null) {
				ship.removeModuleAt(modX, modY);
			} else {
				boolean modPermitted = ship.canAddModule(sel, modX, modY);
				if (modPermitted) {
					ship.addModule(sel, modX, modY);
				}
			}
		}
		switchedSel = false;
		ms += in.msDelta();
		drawState.tick(in.msDelta());
	}

	@Override
	public void render(Frame f) {
		MyDraw d = new MyDraw(f, hs, drawState);
		ScreenMode m = f.mode();
		d.rect(SKY, 0, 0, m.width, m.height);
		d.scale(zoom, zoom);
		ship.draw(d, ship.x, ship.y, ms, armourMode);
		
		d.resetTransforms();
		d.rect(Clr.RED, 5, 5, SPRITE_GRID_SIZE, SPRITE_GRID_SIZE, new Hook(Hook.Type.MOUSE_1) {
			@Override
			public void run(Input in, Pt p, Type type) {
				sel = null;
				switchedSel = true;
			}
		});
		d.text((sel == null ? "[GREEN][bg=886219]Remove" : "[BLACK][bg=886219]Remove"), FOUNT, 10 + SPRITE_GRID_SIZE, 5);
		int my = 5 + SPRITE_GRID_SIZE + 5;
		for (final ModuleType mt : ModuleType.values()) {
			mt.app.draw(d, 5, my, ms, mt == sel ? HIGHLIGHT : null);
			hs.add(new Rect(5, my, mt.w * SPRITE_GRID_SIZE, mt.h * SPRITE_GRID_SIZE), new Hook(Hook.Type.MOUSE_1) {
				@Override
				public void run(Input in, Pt p, Type type) {
					sel = mt;
					switchedSel = true;
				}
			});
			d.text((mt == sel ? "[GREEN][bg=886219]" : "[BLACK][bg=886219]") + mt.name, FOUNT, 10 + mt.app.width() * SPRITE_GRID_SIZE, my);
			my += SPRITE_GRID_SIZE * mt.h + 5;
		}
		
		int modX = (int) Math.floor((cursor.x / zoom - ship.x) / SPRITE_GRID_SIZE);
		int modY = (int) Math.floor((cursor.y / zoom - ship.y) / SPRITE_GRID_SIZE);
		d.scale(zoom, zoom);
		if (sel == null) {
			d.rect(Clr.RED, modX * SPRITE_GRID_SIZE + ship.x, modY * SPRITE_GRID_SIZE + ship.y, SPRITE_GRID_SIZE, SPRITE_GRID_SIZE);
		} else {
			boolean modPermitted = ship.canAddModule(sel, modX, modY);
			sel.draw(d, modX * SPRITE_GRID_SIZE + ship.x, modY * SPRITE_GRID_SIZE + ship.y, ms, modPermitted ? HIGHLIGHT : FORBIDDEN);
		}
		d.resetTransforms();
		
		d.button(m.width - 105, 5, 100, armourMode ? "Edit modules" : "Edit armour", new Runnable() {
			@Override
			public void run() {
				armourMode = !armourMode;
			}
		});
	}
}
