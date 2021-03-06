package ch.aiko.pix.input;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;

import ch.aiko.pix.graphics.PixWindow;

/**
 * Class for the input handling and forwarding of the events to the layers
 * 
 * @author AIKO (Aaron Hodel) 2017
 *
 */
public class PixListeners implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, WindowListener, WindowFocusListener, ComponentListener {

	/**
	 * The maintainer of these listeners
	 */
	private PixWindow window;

	public PixListeners(PixWindow window) {
		this.window = window;
		addListeners(window.getSwingFrame());
		window.getSwingFrame().addWindowListener(this);
		window.getSwingFrame().addWindowFocusListener(this);
	}

	public final void addListeners(Component c) {
		removeListeners(c);
		
		c.addKeyListener(this);
		c.addMouseListener(this);
		c.addMouseMotionListener(this);
		c.addMouseWheelListener(this);
		c.addComponentListener(this);
	}

	public final void removeListeners(Component c) {
		c.removeKeyListener(this);
		c.removeMouseListener(this);
		c.removeMouseMotionListener(this);
		c.removeMouseWheelListener(this);
		c.removeComponentListener(this);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// window.getPanel().resetRenderer();
		// TODO decide if needed or not
		window.getPanel().getInput().newEvent(e);

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void componentShown(ComponentEvent e) {
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void windowIconified(WindowEvent e) {
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void windowActivated(WindowEvent e) {
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// window.getPanel().getInput().mouseWheelMoved(e.getPreciseWheelRotation());
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// window.getPanel().getInput().mouseMoved(e.getX(), e.getY());
		window.getPanel().getInput().newEvent(e);
		// window.getPanel().getInput().mousePressed(e.getButton()); // Useful for what?
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// window.getPanel().getInput().mouseMoved(e.getX(), e.getY());
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// window.getPanel().getInput().mousePressed(e.getButton());
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// window.getPanel().getInput().mouseReleased(e.getButton());
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// window.getPanel().getInput().keyPressed(e.getKeyCode());
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// window.getPanel().getInput().keyReleased(e.getKeyCode());
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		window.getPanel().getInput().newEvent(e);
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		window.getPanel().getInput().newEvent(e);
	}

}
