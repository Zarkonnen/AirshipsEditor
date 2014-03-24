package com.zarkonnen.airships;

import com.zarkonnen.catengine.Condition;
import com.zarkonnen.catengine.SlickEngine;

public class Main {
	public static void main(String[] args) {
		SlickEngine e = new SlickEngine("Airships", "/com/zarkonnen/airships/images/", "/com/zarkonnen/airships/sounds/", 60);
		e.setup(new AGame());
		e.runUntil(Condition.ALWAYS);
	}
}
