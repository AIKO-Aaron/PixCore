package ch.aiko.pix.graphics.renderer;

import java.awt.Font;

public class OnScreenText {

	private String toRender;
	private boolean isMoving = false;
	private int maxWidth, x, y, color, offset, startedMoving;
	private Font f;

	public OnScreenText(int x, int y, int width, String text, int color, Font f) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.f = f;
		maxWidth = width;
		toRender = text;
	}

	public void render(Renderer r) {
		int i = r.drawTextLimit(x - offset, y, maxWidth + offset, toRender, color, f);
		if (offset < 0 || i > 0) {
			offset++;
		} else if (!isMoving && offset != 0) {
			isMoving = true;
			startedMoving = offset;
		} else if (isMoving) {
			offset++;
			if (offset >= startedMoving + maxWidth) {
				isMoving = false;
				offset = -maxWidth;
			}
		}
	}

}
