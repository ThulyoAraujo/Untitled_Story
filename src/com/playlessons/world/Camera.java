package com.playlessons.world;

public class Camera {
	
	public static int x = 10, y = 10;
	
	public static int clamp(int xAtual, int xMin, int xMax) {
		if(xAtual < xMin) {
			xAtual = xMin;
		}
		
		if (xAtual > xMax) {
			xAtual = xMax;
		}
		
		return xAtual;
	}

}
