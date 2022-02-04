package com.levy.game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Vector;

/**
 * @description：游戏的主逻辑
 * @author：LevyXie
 * @create：2022-02-03 10:40
 */
public class GameFrame extends JFrame {
    GameFrame frame;
    HeroPlane heroPlane;
    //采用线程安全的Vector集合，存储bullet及enemyPlane。
    Vector<Bullet> bullets = new Vector<Bullet>();
    Vector<EnemyPlane> enemyPlanes = new Vector<EnemyPlane>();

    public GameFrame() {
        //为保证线程中可复用GameFrame的this,将其提取生成frame。
        frame = this;
        //创建HeroPlane并启动其线程
        heroPlane = new HeroPlane(frame);
        heroPlane.start();
        //GUI窗口的基本信息
        this.setTitle("雷霆战机");
        this.setSize(512 ,768);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

        //设置生成敌机的线程，该线程主要为生成敌机，并进行敌机与子弹的碰撞判断
        new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                while(true){
                    EnemyPlane enemyPlane = new EnemyPlane(random.nextInt(482), 0, frame);
                    enemyPlane.start();
                    enemyPlanes.add(enemyPlane);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        ).start();

        //设置页面刷新线程，保证界面的动画效果
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    repaint();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        ).start();
    }


    public void paint(Graphics g) {
        //BufferedImage的效率相对更高
        BufferedImage image = (BufferedImage) this.createImage(this.getWidth(),this.getHeight());
        //获取Graphics对象
        Graphics graphics = image.getGraphics();
        //绘制背景视图
        graphics.drawImage(new ImageIcon("img/bg01.jpg").getImage(),0,0,null);
        //绘制飞机
        graphics.drawImage(new ImageIcon(heroPlane.img).getImage(),heroPlane.x,heroPlane.y,
                heroPlane.width,heroPlane.height,null);
        //绘制子弹
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if(bullet.y < 0) {
                bullets.remove(bullet);
            }else if ("death".equals(heroPlane.status)){
                //游戏结束后清空界面
                bullets.clear();
            }else{
                graphics.drawImage(new ImageIcon(bullet.img).getImage(),bullet.x,bullet.y -= bullet.speed,
                        bullet.width,bullet.height,null);
            }
        }
        //绘制敌机
        for (int i = 0; i < enemyPlanes.size(); i++) {
            EnemyPlane enemyPlane = enemyPlanes.get(i);
            if(enemyPlane.y > 800){
                enemyPlanes.remove(enemyPlane);
            }else if ("death".equals(heroPlane.status)){
                //游戏结束后清空界面
                enemyPlanes.clear();
            }else{
                graphics.drawImage(new ImageIcon(enemyPlane.img).getImage(), enemyPlane.x, enemyPlane.y += enemyPlane.speed,
                        enemyPlane.width,enemyPlane.height,null);
            }
        }
        //绘制飞机碰撞后游戏结束界面
        if("death".equals(heroPlane.status)){
            graphics.drawImage(new ImageIcon("img/gameOver.png").getImage(),56,360,null);
        }
        g.drawImage(image,0,0,null);

    }

    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
        PlayerControl playerControl = new PlayerControl(frame);
        frame.addKeyListener(playerControl);
    }
}
