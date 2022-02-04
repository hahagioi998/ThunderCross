package com.levy.game;

import javax.swing.*;
import java.awt.*;

/**
 * @description：敌机
 * @author：LevyXie
 * @create：2022-02-03 13:00
 */
public class EnemyPlane extends Thread{
    int x;
    int y;
    int width = 60;
    int height = 60;
    int speed = 6;
    GameFrame frame;
    Image img = new ImageIcon("img/enemyPlane01.png").getImage();


    @Override
    public void run() {
        //while true循环，持续判断是否碰撞
        while (true){
            if(crash()){
                this.speed = 0;
                this.img = new ImageIcon("img/boom.png").getImage();
                try {
                    this.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                frame.enemyPlanes.remove(this);
                break;
            }
            if(this.y > 760){
                break;
            }
            try {
                this.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public EnemyPlane() {
    }

    public EnemyPlane(int x, int y, GameFrame frame) {
        this.x = x;
        this.y = y;
        this.frame = frame;
    }
    
    /**
     * Description:  判断敌机是否碰撞子弹，返回为真则确认碰撞 
     * Param:        []
     * Return:       java.lang.Boolean
    **/
    public Boolean crash(){
        Rectangle myRect = new Rectangle(this.x, this.y, this.width, this.height);
        Rectangle bulletRect = null;
        for (int i = 0; i < frame.bullets.size(); i++) {
            Bullet bullet = frame.bullets.get(i);
            bulletRect = new Rectangle(bullet.x,bullet.y,bullet.width,bullet.height);

            if(myRect.intersects(bulletRect)){
                return true;
            }
        }
        return false;
    }
}
