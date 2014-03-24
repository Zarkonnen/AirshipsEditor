package com.zarkonnen.airships;

import static com.zarkonnen.airships.Appearance.app;
import com.zarkonnen.catengine.Draw;
import com.zarkonnen.catengine.util.Clr;

public enum ModuleType {
	CORRIDOR("Corridor", 1, 1, 40, 20, -1, 0, 400, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, app().frame(6, 0)),
	COAL_STORE("Coal store", 3, 2, 300, 200, -1, 0, 800, 80, 120, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, app().frame(0, 1, 3, 2)),
	AMMO_STORE("Ammo store", 3, 2, 300, 150, 100, 500, 800, 100, 0, 100, 0, 0, 0, 0, 0, 0, 0, 0, false, false, app().frame(0, 3, 3, 2)),
	SICKBAY("Sickbay", 3, 1, 150, 50, -1, 0, 800, 40, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, false, false, app().frame(7, 0, 3, 1)),
	REPAIR_BAY("Repair bay", 3, 1, 200, 100, -1, 0, 1200, 100, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, false, false, app().frame(10, 0, 3, 1)),
	FIRE_POINT("Fire point", 2, 2, 200, -1, -1, 0, 800, 200, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, false, false, app().frame(0, 5, 2, 2)),
	QUARTERS("Quarters", 3, 1, 150, 80, -1, 0, 1200, 40, 0, 0, 0, 0, 0, 15, 0, 0, 0, 0, false, false, app().frame(13, 0, 3, 1)),
	BRIDGE("Bridge", 3, 1, 200, 80, -1, 0, 800, 50, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, true, false, app().frame(16, 0, 3, 1)),
	SUSPENDIUM_CHAMBER("Suspendium Chamber", 3, 2, 250, 100, 50, 200, 1200, 120, 0, 0, 0, 0, 0, 0, 0, 2000, 0, 3000, false, false, app().frame(0, 7, 3, 2)),
	PROPELLER("Propeller", 2, 2, 200, 120, 50, 50, 1200, 80, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 5000, false, true, app().frame(0, 9, 3, 2)),
	RIFLE("Rifle", 1, 1, 50, 20, -1, 0, 1200, 8, 1000, 10, 1.2, 0, 5, app().frame(19, 0), null),
	GATLING_GUN("Gatling gun", 2, 1, 50, 20, -1, 0, 1800, 20, 100, 300, 5, 0, 3, app().frame(20, 0, 2, 1), null),
	CANNON("Cannon", 2, 1, 120, 50, 20, 50, 1800, 70, 4000, 1, 3, 0, 70, app().frame(22, 0, 2, 1), null),
	CANNON_EXPLODING("Cannon (exploding shell)", 2, 1, 120, 60, 40, 120, 1800, 70, 4000, 1, 3, 50, 0, app().frame(22, 0, 2, 1), null),
	HV_CANNON("Heavy cannon", 4, 2, 300, 120, 50, 100, 1800, 200, 8000, 1, 2.5, 0, 120, app().frame(0, 11, 4, 2), null);
		
	public String name;
	public int w, h;
	public int hp;
	public int fireHP;
	public int explodeHP;
	public int explodeDmg;
	public int moveDelay;
	public int weight;
	public int coal;
	public int ammo;
	public int sickbay;
	public int repair;
	public int water;
	public int quarters;
	public int command;
	public int lift;
	public int propulsion;
	public int coalReload; // ms
	public int reload; // ms
	public int clip; // in rounds
	public double inaccuracy; // distance of 1 standard deviation from target tile
	public int blastDmg;
	public int penDmg;
	public boolean frontOnly;
	public boolean backOnly;
	public Appearance app;
	public Appearance shotApp;

	private ModuleType(String name, int w, int h, int hp, int fireHP, int explodeHP, int explodeDmg, int moveDelay, int weight, int coal, int ammo, int sickbay, int repair, int water, int quarters, int command, int lift, int propulsion, int coalReload, boolean frontOnly, boolean backOnly, Appearance app) {
		this.name = name;
		this.w = w;
		this.h = h;
		this.hp = hp;
		this.fireHP = fireHP;
		this.explodeHP = explodeHP;
		this.explodeDmg = explodeDmg;
		this.moveDelay = moveDelay;
		this.weight = weight;
		this.coal = coal;
		this.ammo = ammo;
		this.sickbay = sickbay;
		this.repair = repair;
		this.water = water;
		this.quarters = quarters;
		this.command = command;
		this.lift = lift;
		this.propulsion = propulsion;
		this.coalReload = coalReload;
		this.frontOnly = frontOnly;
		this.backOnly = backOnly;
		this.app = app;
	}

	// Weapon
	private ModuleType(String name, int w, int h, int hp, int fireHP, int explodeHP, int explodeDmg, int moveDelay, int weight, int reload, int clip, double inaccuracy, int blastDmg, int penDmg, Appearance app, Appearance shotApp) {
		this.name = name;
		this.w = w;
		this.h = h;
		this.hp = hp;
		this.fireHP = fireHP;
		this.explodeHP = explodeHP;
		this.explodeDmg = explodeDmg;
		this.moveDelay = moveDelay;
		this.weight = weight;
		this.reload = reload;
		this.clip = clip;
		this.inaccuracy = inaccuracy;
		this.blastDmg = blastDmg;
		this.penDmg = penDmg;
		this.app = app;
		this.shotApp = shotApp;
		frontOnly = true;
	}

	public void draw(Draw d, double x, double y, int ms) {
		if (backOnly && app.width() > w) {
			app.draw(d, x - (app.width() - w) * AGame.SPRITE_GRID_SIZE, y, ms);
		} else {
			app.draw(d, x, y, ms);
		}
	}

	void draw(Draw d, double x, double y, int ms, Clr clr) {
		if (backOnly && app.width() > w) {
			app.draw(d, x - (app.width() - w) * AGame.SPRITE_GRID_SIZE, y, ms, clr);
		} else {
			app.draw(d, x, y, ms, clr);
		}
	}
}
