package com.expressTracking.entity;

import java.io.Serializable;


public class Posion implements Serializable {
	public Posion() {
	}

	private int posCode;

	private float x;

	private float y;
	
	private void setPosCode(int value) {
		this.posCode = value;
	}
	
	public int getPosCode() {
		return posCode;
	}
	
	public int getORMID() {
		return getPosCode();
	}
	
	public void setX(float value) {
		this.x = value;
	}
	
	public float getX() {
		return x;
	}
	
	public void setY(float value) {
		this.y = value;
	}
	
	public float getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getPosCode());
		}
		else {
			return "Posion[ " +
					"PosCode=" + getPosCode() + " " +
					"X=" + getX() + " " +
					"Y=" + getY() + " " +
					"]";
		}
	}
	
}
