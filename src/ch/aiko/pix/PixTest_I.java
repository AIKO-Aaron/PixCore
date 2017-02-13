package ch.aiko.pix;

import ch.aiko.pix.graphics.PixWindow;

public class PixTest_I {

	public static void main(String[] args) {
		PixWindow window = new PixWindow("Hello World", 960, 540);
		window.getPanel().renderable = (r) -> {r.fillRect(0, 0, 50, 50, 0xFF00FF);};
		
		while(true) {
			window.getPanel().preRender();
		}
	}
	
}
