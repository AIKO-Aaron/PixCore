package ch.aiko.pix;

import ch.aiko.pix.core.Renderable;
import ch.aiko.pix.core.Updatable;
import ch.aiko.pix.graphics.PixLayer;
import ch.aiko.pix.graphics.PixWindow;

import com.sun.glass.events.KeyEvent;

public class PixTest_I {

	static int xSpeed = 5;
	static int ySpeed = 5;
	static int xPos = 0;
	static int yPos = 0;

	public static void main(String[] args) {
		PixWindow window = new PixWindow("Hello World", 960, 540);
		Renderable re = (r) -> {
			r.clear();
			r.drawRect(xPos, yPos, 50, 50, 0xFFFF0000);
			for (int i = 0; i < 960; i += 50) {
				for (int j = 0; j < 540; j += 50)
					r.drawPixel(i, j, 0xFFFFFFFF);
			}
			return false;
		};
		Updatable up = (l) -> {
			// xPos += xSpeed;
			// yPos += ySpeed;

			if (l.getInput().popKeyPressed(KeyEvent.VK_ESCAPE)) System.exit(0);

			if (l.getInput().isKeyPressed(KeyEvent.VK_RIGHT)) xPos += xSpeed;
			if (l.getInput().isKeyPressed(KeyEvent.VK_LEFT)) xPos -= xSpeed;
			if (l.getInput().isKeyPressed(KeyEvent.VK_DOWN)) yPos += ySpeed;
			if (l.getInput().isKeyPressed(KeyEvent.VK_UP)) yPos -= ySpeed;

			// if (xPos + 50 + xSpeed >= window.getActualWidth() || xPos + xSpeed < 0) xSpeed = -xSpeed;
			// if (yPos + 50 + ySpeed >= window.getActualHeight() || yPos + ySpeed < 0) ySpeed = -ySpeed;
			return false;
		};

		window.getPanel().addChild(new PixLayer(re, up, 0));

		System.out.println("Program finished....");
		// Can do whatever you want
	}

}
