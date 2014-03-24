package com.zarkonnen.airships;

import static com.zarkonnen.airships.Appearance.app;

public enum ArmourType {
	LT_WOOD("Wood wall", 1, 4, 0, 1, app(0, 0)),
	MED_WOOD("Wooden armour", 3, 10, 1, 4, app(1, 0)),
	HV_WOOD("Reinforced wooden armour", 10, 20, 3, 10, app(2, 0)),
	LT_STEEL("Steel wall", 2, 2, 4, 2, app(3, 0)),
	MED_STEEL("Steel armour", 6, 6, 8, 4, app(4, 0)),
	HV_STEEL("Heavy steel armour", 12, 20, 20, 6, app(5, 0));
	public String name;
	public int weight;
	public int hp;
	public int blastDmgAbsorb;
	public int penDmgAbsorb;
	public Appearance app;

	private ArmourType(String name, int weight, int hp, int blastDmgAbsorb, int penDmgAbsorb, Appearance app) {
		this.name = name;
		this.weight = weight;
		this.hp = hp;
		this.blastDmgAbsorb = blastDmgAbsorb;
		this.penDmgAbsorb = penDmgAbsorb;
		this.app = app;
	}
}
