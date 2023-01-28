/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import pkg2048.Main;
import pkg2048.gameobject.GameObject;
import pkg2048.graphic.Renderer;
import pkg2048.graphic.Sprite;
import pkg2048.input.Keyboard;


public class Game {
    
    public static List<GameObject> objects;
    
    public static boolean moving = false, hasMoved = true, somethingIsMoving = false;
    public static int dir = 0;
    
    private Random rand = new Random();
    
    public Game(){
        init();
    }
    
    public void init(){
        objects = new ArrayList<GameObject>();
        moving = false;
        hasMoved = true;
        somethingIsMoving = false;
        spawn();
    }
    
    public void update(){
        if(Keyboard.keyUp(KeyEvent.VK_R)){
            init();
        }
        for(int i = 0; i < objects.size(); i++){
            objects.get(i).update();
        }
        
        checkForValueIncrease();
        
        movingLogic();
        /*if(Keyboard.keyDown(KeyEvent.VK_A)){
            System.out.println("Down a");
        }
        
        if(Keyboard.key(KeyEvent.VK_A)){
            System.out.println("a");
        }
        
        if(Keyboard.keyUp(KeyEvent.VK_A)){
            System.out.println("Up a");
        }*/
    }
    
    private void checkForValueIncrease() {
        for(int i = 0; i < objects.size(); i++){
            for(int j = 0; j < objects.size(); j++){
                if(i == j)continue;
                if(objects.get(i).x == objects.get(j).x && objects.get(i).y == objects.get(j).y && !objects.get(i).remove && !objects.get(j).remove){
                    objects.get(j).remove = true;
                    objects.get(i).value *= 2;
                    objects.get(i).createSprite();
                }
            }
        }
        for(int i = 0; i < objects.size(); i++){
            if(objects.get(i).remove)objects.remove(i);
        }
    }
    
    private void spawn() {
        if(objects.size() == 16)return;
        
        boolean available = false;
        int x = 0, y = 0;
        while(!available){
            x = rand.nextInt(4);
            y = rand.nextInt(4);
            boolean isAvailable = true;
            for(int i = 0; i < objects.size(); i++){
                if(objects.get(i).x / 100 == x && objects.get(i).y / 100 == y){
                    isAvailable = false;
                }
            }
            if(isAvailable)available = true;
        }
        objects.add(new GameObject(x * 100, y * 100));
    }
    
     private void movingLogic() {
         somethingIsMoving = false;
         for(int i = 0; i < objects.size(); i++){
             if(objects.get(i).moving){
                 somethingIsMoving = true;
             }
         }
         if(!somethingIsMoving){
             moving = false;
             for(int i = 0; i < objects.size(); i++){
                 objects.get(i).hasMoved = false;
             }
         }
         if(!moving && hasMoved){
             spawn();
             hasMoved = false;
         }
         if(!moving && !hasMoved){
             if(Keyboard.keyDown(KeyEvent.VK_A)){
                 hasMoved = true;
                 moving = true;
                 dir = 0;
             }else if(Keyboard.keyDown(KeyEvent.VK_D)){
                 hasMoved = true;
                 moving = true;
                 dir = 1;
             }else if(Keyboard.keyDown(KeyEvent.VK_W)){
                 hasMoved = true;
                 moving = true;
                 dir = 2;
             }else if(Keyboard.keyDown(KeyEvent.VK_S)){
                 hasMoved = true;
                 moving = true;
                 dir = 3;
             }
         }
    }
    
    public void render(){
        Renderer.renderBackground();
        
        for(int i = 0; i < objects.size(); i++){
            objects.get(i).render();
        }
        
        for(int i = 0; i < Main.pixels.length; i++){
            Main.pixels[i] = Renderer.pixels[i];
        }
    }
    
    public void renderText(Graphics2D g){
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(new Font("Verdana", 0, 100));
        g.setColor(Color.BLACK);
        
        for(int i = 0; i < objects.size(); i++){
            GameObject o = objects.get(i);
            String s = o.value + "";
            int sw = (int)(g.getFontMetrics().stringWidth(s) / 2 / Main.scale);
            g.drawString(s, (int)(o.x + o.width / 2 - sw) * Main.scale, (int)(o.y + o.height / 2 + 18) * Main.scale);
        }
    }

    

   


    
}
