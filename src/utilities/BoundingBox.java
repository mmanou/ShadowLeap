/**
 * BoundingBox complete class for SWEN20003: Object Oriented Software Development 2018
 * by Eleanor McMurtry, University of Melbourne
 */
package utilities;

import org.newdawn.slick.Image;

public class BoundingBox {
	private static final float FUZZ = 0.95f;

	private float left;
	private float top;
	private float width;
	private float height;

	public BoundingBox(float x, float y, float width, float height) {
		setWidth(width);
		setHeight(height);
		setX(x);
		setY(y);
	}
	public BoundingBox(Image img, float x, float y) {
		setWidth(img.getWidth());
		setHeight(img.getHeight());
		setX(x);
		setY(y);
	}
	public BoundingBox(BoundingBox bb) {
		width = bb.width;
		height = bb.height;
		left = bb.left;
		top = bb.top;
	}

	/*
	 * Sets the x and y position at the centre of the bounding box.
	 */
	public void setX(float x) {
		left = x - width / 2;
	}
	public void setY(float y) {
		top = y - height / 2;
	}
	
	public void setWidth(float w) {
		width = w * FUZZ;
	}
	public void setHeight(float h) {
		height = h * FUZZ;
	}
	
	public float getLeft() {
		return left;
	}
	public float getTop() {
		return top;
	}
	public float getRight() {
		return left + width;
	}
	public float getBottom() {
		return top + height;
	}
	
	public float getWidth() {
		return width;
	}
	public float getHeight() {
		return height;
	}
	
	public boolean intersects(BoundingBox other) {
		return !(other.left > getRight()
			  || other.getRight()  < left
			  || other.top > getBottom()
			  || other.getBottom() < top);
	}
}
