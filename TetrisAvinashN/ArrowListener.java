/**
 * This interface is the ArrowListener interface of tetris. It has instance variables
 * that keep track of a block display and a block bounded grid.
 * 
 * @author Avinash Nayak 
 * @version April 23, 2013
 */
public interface ArrowListener
{
	void upPressed();
	void downPressed();
	void leftPressed();
	void rightPressed();
	void spacePressed();
}