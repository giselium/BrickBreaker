package com.pbo.brick;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.Iterator;


public class GameScreen implements Screen {
    final BrickBreaker game;


    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Pad pad;
    private Ball ball;
    private Array<Brick> bricks;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private int score;

    public GameScreen(final BrickBreaker game) {
        this.game = game;


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480); //W H disamain uk window
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        pad = new Pad();
        ball = new Ball();
        bricks = new Array<>();
        spawnBrick();
    }

    void spawnBrick(){
        for (int x = 0; x <= 800; x += 100) {
            for (int y = 400; y >= 200; y -= 60) {
                if (MathUtils.random(0,1) == 0){
                    Brick brick = new RedBrick();
                    brick.setPosition(x, y);
                    bricks.add(brick);
                }
                else {
                    Brick brick = new BlueBrick();
                    brick.setPosition(x, y);
                    bricks.add(brick);
                }
            }
        }
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(pad.x, pad.y, pad.width, pad.height);


        for (Brick brick : bricks) {
            if(brick.warna == 0){
                shapeRenderer.setColor(Color.FIREBRICK);
                shapeRenderer.rect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
            }
            else {
                shapeRenderer.setColor(Color.BLACK);
                shapeRenderer.rect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
            }
        }

        shapeRenderer.setColor(Color.GOLD);
        shapeRenderer.ellipse(ball.x, ball.y, ball.width, ball.height);
        shapeRenderer.end();


        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            pad.x = touchPos.x - 64 / 2;
            if (pad.x < 0) {
                pad.x = 0;
            }
            if (pad.x > 800 - 90) {
                pad.x = 800 - 90;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            pad.x -= 200 * Gdx.graphics.getDeltaTime();
            if (pad.x < 0) pad.x = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            pad.x += 200 * Gdx.graphics.getDeltaTime();
            if (pad.x > 800 - 90) pad.x = 800 - 90;
        }

        ball.y += ball.ballSpeedY * Gdx.graphics.getDeltaTime();
        ball.x -= ball.ballSpeedX * Gdx.graphics.getDeltaTime();

        //mentok kiri
        if (ball.x <= ball.width / 2) {
            ball.ballSpeedX *= -1;
        }
        //mentok kanan
        if (ball.x >= 800 - ball.width / 2) {
            ball.ballSpeedX *= -1;
        }
        //mentok atas
        if (ball.y >= Gdx.graphics.getHeight() - ball.height) {
            ball.ballSpeedY *= -1;
        }


        ball.updateRect();
        pad.updateRect();

        if (ball.overlaps(pad)) {
            ball.ballSpeedY *= -1;
            //pad kiri
            if ((ball.x + ball.width) >= pad.x && (ball.x + ball.width) < (pad.x + 30)) {
                ball.ballSpeedX *= -1;
            }
            //pad kanan
            else if ((ball.x + ball.width) <= (pad.x + pad.width) && (ball.x + ball.width) > (pad.x + 60)) {
                ball.ballSpeedX *= -1;
            }
        }


        for (Iterator<Brick> iter = bricks.iterator(); iter.hasNext();) {
            Brick temp = iter.next();
            if (temp.overlaps(ball)) {
                ball.ballSpeedY *= -1;
                temp.HP -= 1;
                if(temp.HP == 0){
                    iter.remove();
                    score = score + temp.score;
                }
                break;
            }
        }

        batch.begin();
        font.draw(batch, "Score: " + score, 10, 470);
        batch.end();

        // game over
        if (ball.y<0) game.setScreen(new GameOverScreen(game));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }
}
