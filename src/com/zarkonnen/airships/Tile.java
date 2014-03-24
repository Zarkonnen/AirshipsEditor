package com.zarkonnen.airships;

public class Tile {
	public int x, y;
	public ArmourPlate armour;
	public Module module;

	public Tile(Module module, int x, int y) {
		this.module = module;
		this.x = x;
		this.y = y;
		armour = new ArmourPlate();
	}

	public void hit(Shot shot) {
		
	}
}
