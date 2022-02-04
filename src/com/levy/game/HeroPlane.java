package com.levy.game;

import javax.swing.*;
import java.awt.*;

/**
 * @description：主人公机对象
 * @author：LevyXie
 * @create：2022-02-03 11:12
 */
public class HeroPlane extends Thread{
    int x = 216;
    int y = 668;
    int width = 80;
    int height = 80;
    int speed = 10;
    String status = "alive";
    GameFrame frame;

    boolean up,down,left,right;

    Image img = new ImageIcon("img/myPlane01.png").getImage();

    public HeroPlane() {
    }

    public HeroPlane(GameFrame frame) {
        this.frame = frame;
    }

    public HeroPlane(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void run() {
        while (true) {
            //*****以下为键盘操控逻辑*****
            if(up && y >= 20) {
                y -= speed;
            }
            if(down && y <= 688) {
                y += speed;
            }
            if(left && x >= 0) {
                x -= speed;
            }
            if(right && x < 432) {
                x += speed;
            }
            //*****以下为碰撞后爆炸及逻辑*****
            if(crash()){
                this.speed = 0;
                this.img = new ImageIcon("img/myBoom.png").getImage();
                this.status = "death";
                try {
                    this.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Description:  判断飞机是否碰撞，返回为真则确认碰撞 
     * Param:        []
     * Return:       java.lang.Boolean
    **/
    public Boolean crash(){
        Rectangle myRect = new Rectangle(this.x, this.y, this.width - 5, this.height - 5);
        Rectangle enemyRect = null;
        for (int i = 0; i < frame.enemyPlanes.size(); i++) {
            EnemyPlane enemyPlane = frame.enemyPlanes.get(i);
            enemyRect = new Rectangle(enemyPlane.x,enemyPlane.y,enemyPlane.width - 5,enemyPlane.height - 5);

            if(myRect.intersects(enemyRect)){
                return true;
            }
        }
        return false;
    }

}
