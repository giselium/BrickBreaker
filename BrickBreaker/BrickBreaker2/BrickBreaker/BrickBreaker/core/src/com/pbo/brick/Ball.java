package com.pbo.brick;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Ball extends Rectangle {

    public float x;
    public float y;
    public float width;
    public float height;
    public float score;
    public float ballSpeedX;
    public float ballSpeedY;

    public Ball(){
        this.x = MathUtils.random(0, 800 - 64);
        this.y = 120;
        this. width = 16;
        this.height = 16;
        this.ballSpeedX = 180;
        this.ballSpeedY = -50;
    }

    void updateRect(){
        super.x = this.x;
        super.y = this.y;
        super.width = this.width;
        super.height = this.height;
    }
}
