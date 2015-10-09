package com.inz.bucket;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class AccBucket extends ApplicationAdapter {

	SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private Sound dropSound;
	private Texture playerTexture, dropTexture;
	private Player player;
	private Drop drop1, drop2, drop3, drop4;
	private Random random;
	private BitmapFont font;
	private int drop, height, width;
	private float deltaTime;
	private boolean gameRun;
	private int playerX, playerY;
	private int plX, plY;
	public static Preferences prefs;
	private String highScore; 

	private int life = 0;
	private int score = 0;

	int tmp = 300;

	@Override
	public void create() {
		loadData();
		init();

	}

	private void init() {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		player = new Player(playerTexture, 435, 0);
		drop1 = new Drop(dropTexture);
		drop2 = new Drop(dropTexture);
		drop3 = new Drop(dropTexture);
		drop4 = new Drop(dropTexture);
		random = new Random();

		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();

		resetDrops();

		gameRun = true;

//		playerX = Math.round(Gdx.input.getAccelerometerX());
//		playerY = Math.round(Gdx.input.getAccelerometerY());

	}

	private void loadData() {
		playerTexture = new Texture("bucket.png");
		dropTexture = new Texture("drop.png");
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.ogg"));
		font = new BitmapFont(Gdx.files.internal("text.fnt"));
		prefs = Gdx.app.getPreferences("AccBucket");
		
		if(!prefs.contains("highscore")){
			prefs.putInteger("highscore", 0);
		}
	}
	
	public static void setHighScore(int val){
		prefs.putInteger("highscore", val);
		prefs.flush();
	}
	
	public static int getHighScore(){
		return prefs.getInteger("highscore");
	}

	@Override
	public void render() {
		deltaTime = Gdx.graphics.getDeltaTime();

		update(deltaTime);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		player.draw(batch);

		drop1.draw(batch);
		drop2.draw(batch);
		drop3.draw(batch);
		drop4.draw(batch);

		font.draw(batch, "" + score, width / 2, height - 400);

		font.draw(batch, "DROPS OUT: " + life + "/5", 50, height - 30);

		if (!gameRun) {
			font.draw(batch, "GAME OVER", width / 3, height - 300);
			font.draw(batch, "RETRY ?", 420, height - 700);
			font.draw(batch, "HIGH SCORE", width / 3, height - 1000);
			highScore = getHighScore() + "";
			font.draw(batch, "" + highScore, width / 2, height - 1100);

			if (Gdx.input.justTouched()) {
				gameRun = true;
				life = 0;
				score = 0;
				resetDrops();
			}
		}

		batch.end();

//		 shapeRenderer.begin(ShapeType.Filled);
//		 shapeRenderer.setColor(Color.RED);
//		 shapeRenderer
//		 .circle(player.getBoundingCircle().x,
//		 player.getBoundingCircle().y,
//		 player.getBoundingCircle().radius);
//		 shapeRenderer.circle(drop1.getBoundingCircle().x,
//		 drop1.getBoundingCircle().y, drop1.getBoundingCircle().radius);
//		 shapeRenderer.end();
	}

	private void update(float delta) {

		if (life == 5) {
			gameRun = false;
			
			if (score > getHighScore()){
				setHighScore(score);
			}

		}

		if (gameRun) {

			collide();

			switch (drop) {
			case 1:
				drop1.y = height + random.nextInt(300);
				drop1.x = random.nextInt(width - 50);
				drop = 5;
				break;
			case 2:
				drop2.y = height + random.nextInt(300);
				drop2.x = random.nextInt((width - 50));
				drop = 5;
				break;
			case 3:
				drop3.y = height + random.nextInt(300);
				drop3.x = random.nextInt(width - 50);
				drop = 5;
				break;
			case 4:
				drop4.y = height + random.nextInt(300);
				drop4.x = random.nextInt(width - 50);
				drop = 5;
				break;

			default:
				break;
			}

			drop1.y -= 300 * deltaTime;
			drop2.y -= 450 * deltaTime;
			drop3.y -= 350 * deltaTime;
			drop4.y -= 300 * deltaTime;

			drop1.update();
			drop2.update();
			drop3.update();
			drop4.update();

			handleInput(deltaTime);

		}
	}

	private void collide() {
		if (player.getBoundingCircle().overlaps(drop1.getBoundingCircle())
				&& drop1.y > 170) {
			dropSound.play();
			score++;
			drop = 1;
		} else if (drop1.y < 0) {
			drop = 1;
			life++;
		}

		if (player.getBoundingCircle().overlaps(drop2.getBoundingCircle())
				&& drop2.y > 170) {
			dropSound.play();
			score++;
			drop = 2;
		} else if (drop2.y < 0) {
			drop = 2;
			life++;
		}

		if (player.getBoundingCircle().overlaps(drop3.getBoundingCircle())
				&& drop3.y > 170) {
			dropSound.play();
			score++;
			drop = 3;
		} else if (drop3.y < 0) {
			drop = 3;
			life++;
		}

		if (player.getBoundingCircle().overlaps(drop4.getBoundingCircle())
				&& drop4.y > 170) {
			dropSound.play();
			score++;
			drop = 4;
		} else if (drop4.y < 0) {
			drop = 4;
			life++;
		}

	}

	private void handleInput(float delta) {


		 if (Gdx.input.getAccelerometerX() > 2 && player.x > 0) {
		 player.x -= 1200 * delta;
		 player.update();
		 }
		
		 if (Gdx.input.getAccelerometerX() < -2
		 && (player.x + player.width) < width) {
		 player.x += 1200 * delta;
		 player.update();
		 }

	}

	private void resetDrops() {
		drop1.y = height + random.nextInt((int)(height * 0.8));
		drop2.y = height + random.nextInt((int)(height * 0.8));
		drop3.y = height + random.nextInt((int)(height * 0.8));
		drop4.y = height + random.nextInt((int)(height * 0.8));
		drop1.x = width - random.nextInt((int) (width * 0.8));
		drop2.x = width - random.nextInt((int) (width * 0.8));
		drop3.x = width - random.nextInt((int) (width * 0.8));
		drop4.x = width - random.nextInt((int) (width * 0.8));

	}

	@Override
	public void dispose() {
		playerTexture.dispose();
		dropTexture.dispose();
		dropSound.dispose();
		font.dispose();
	}

}
