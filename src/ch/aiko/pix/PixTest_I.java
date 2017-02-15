package ch.aiko.pix;

import ch.aiko.pix.graphics.PixWindow;

public class PixTest_I {

	static int xSpeed = 1;
	static int ySpeed = 1;
	static int xPos = 0;
	static int yPos = 0;

	public static void main(String[] args) {
		PixWindow window = new PixWindow("Hello World", 960, 540);
		window.getPanel().renderable = (r) -> {
			r.clear();
			r.drawRect(xPos, yPos, 50, 50, 0xFFFF0000);
			return false;
		};
		window.getPanel().updatable = () -> {
			xPos += xSpeed;
			yPos += ySpeed;

			if (xPos + 50 + xSpeed >= window.getActualWidth() || xPos + xSpeed < 0) xSpeed = -xSpeed;
			if (yPos + 50 + ySpeed >= window.getActualHeight() || yPos + ySpeed < 0) ySpeed = -ySpeed;
			return false;
		};

		System.out.println("Program finished....");
		// Can do wathever you want
	}

}
