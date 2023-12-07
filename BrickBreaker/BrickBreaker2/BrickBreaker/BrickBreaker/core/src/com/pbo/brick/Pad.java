package com.pbo.brick;

import com.badlogic.gdx.math.Rectangle;

public class Pad extends Rectangle {
    public float x;
    public float y;
    public float width;
    public float height;

    public Pad() {
        this.x = 0;
        this.y = 20;
        this.width = 90;
        this.height = 20;
    }

    void updateRect(){
        super.x = this.x;
        super.y = this.y;
        super.width = this.width;
        super.height = this.height;
    }

}
