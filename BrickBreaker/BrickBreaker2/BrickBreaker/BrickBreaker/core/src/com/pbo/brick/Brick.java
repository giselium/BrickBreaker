package com.pbo.brick;

import com.badlogic.gdx.math.Rectangle;




abstract class Brick extends Rectangle {
    public float width;
    public float height;
    public int warna;
    public int HP;
    public int score;
}

class RedBrick extends Brick {

    public RedBrick() {
        setSize(90,35);
        warna = 0;
        HP = 1;
        score = 10;
    }
}

class BlueBrick extends Brick {

    public BlueBrick() {
        setSize(90,35);
        warna = 1;
        HP = 2;
        score = 20;
    }
}

