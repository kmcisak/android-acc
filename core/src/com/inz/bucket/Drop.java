package com.inz.bucket;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class Drop extends Rectangle {

	private Texture texture;
	private Circle boundingCircle;

	public Drop(Texture texture) {
		this.texture = texture;
		this.height = texture.getHeight();
		this.width = texture.getWidth();
		boundingCircle= new Circle();
	}

	public void draw(SpriteBatch batch) {
		batch.draw(texture, x, y);
	}
	
	public void update(){
		boundingCircle.set(x + width/2, y +height/2, 25);
	}

	public  Circle getBoundingCircle() {
		return boundingCircle;
	}
}
