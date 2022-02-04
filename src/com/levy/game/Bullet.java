package com.levy.game;

import javax.swing.*;
import java.awt.*;

/**
 * @description：子弹对象
 * @author：LevyXie
 * @create：2022-02-03 12:21
 */
public class Bullet {
    int x;
    int y;
    int width = 60;
    int height = 60;
    int speed = 15;


    Image img = new ImageIcon("img/bullet01.png").getImage();

    public Bullet() {
    }

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
