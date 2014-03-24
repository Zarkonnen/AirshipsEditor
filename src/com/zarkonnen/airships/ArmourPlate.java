package com.zarkonnen.airships;

public class ArmourPlate {
	public ArmourType type;
	public int hp;

	public ArmourPlate() {
		type = ArmourType.LT_WOOD;
		hp = type.hp;
	}
}
