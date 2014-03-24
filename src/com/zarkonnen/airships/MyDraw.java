package com.zarkonnen.airships;

import com.zarkonnen.catengine.Draw;
import com.zarkonnen.catengine.Frame;
import com.zarkonnen.catengine.Hook;
import com.zarkonnen.catengine.Hook.Type;
import com.zarkonnen.catengine.Hooks;
import com.zarkonnen.catengine.Input;
import com.zarkonnen.catengine.util.Clr;
import com.zarkonnen.catengine.util.Pt;

public class MyDraw extends Draw {
	public static class State {
		int msSinceLastClick = 0;
		public void tick(int ms) {
			msSinceLastClick += ms;
		}
	}
	
	public State state;
		
	public MyDraw(Frame f, State state) {
		super(f);
		this.state = state;
	}

	public MyDraw(Frame f, Hooks hs, State state) {
		super(f, hs);
		this.state = state;
	}
	
	public void button(int x, int y, int w, String text, final Runnable onClick) {
		rect(Clr.GREY, x, y, w, 14, new Hook(Hook.Type.MOUSE_1) {
			@Override
			public void run(Input in, Pt p, Type type) {
				if (state.msSinceLastClick > 200) {
					state.msSinceLastClick = 0;
					onClick.run();
				}
			}
		});
		text(text, AGame.FOUNT, x + 1, y + 1);
	}
}
