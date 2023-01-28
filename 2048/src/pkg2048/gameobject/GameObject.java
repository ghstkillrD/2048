/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048.gameobject;

import java.util.Random;
import pkg2048.Main;
import pkg2048.game.Game;
import static pkg2048.game.Game.objects;
import pkg2048.graphic.Renderer;
import pkg2048.graphic.Sprite;


public class GameObject {
    public double x, y;
    public int width, height;
    public Sprite sprite;
    public int value, speed = 8;
    public boolean moving = false, remove = false, hasMoved = false;
    
    Random rand = new Random();
    
    public GameObject(double x, double y){
        this.x = x;
        this.y = y;
        this.value = (rand.nextBoolean() ? 2 : 4);
        createSprite();
        this.width = sprite.width;
        this.height = sprite.height;
    }

    public void createSprite() {
        if(this.value == 2){
            this.sprite = new Sprite(100, 100, 0xefe5db);
        }else if(this.value == 4){
            this.sprite = new Sprite(100, 100, 0xece0c8);
        }else if(this.value == 8){
            this.sprite = new Sprite(100, 100, 0xf1b078);
        }else if(this.value == 16){
            this.sprite = new Sprite(100, 100, 0xeb8c52);
        }else if(this.value == 32){
            this.sprite = new Sprite(100, 100, 0xf57c5f);
        }else if(this.value == 64){
            this.sprite = new Sprite(100, 100, 0xec563d);
        }else if(this.value == 128){
            this.sprite = new Sprite(100, 100, 0xf2d86a);
        }else if(this.value == 256){
            this.sprite = new Sprite(100, 100, 0xecc750);
        }else if(this.value == 512){
            this.sprite = new Sprite(100, 100, 0xe5bf2d);
        }else if(this.value == 1024){
            this.sprite = new Sprite(100, 100, 0xe2b913);
        }else if(this.value == 2048){
            this.sprite = new Sprite(100, 100, 0xedc22e);
        }else if(this.value == 4096){
            this.sprite = new Sprite(100, 100, 0x5ddb92);
        }else if(this.value == 8192){
            this.sprite = new Sprite(100, 100, 0xec4d58);
        }
    }
    
    public boolean canMove(){
        if(x < 0 || x + width > Main.WIDTH || y < 0 || y + height > Main.HEIGHT){
            return false;
        }
        for(int i = 0; i < Game.objects.size(); i++){
            GameObject o = Game.objects.get(i);
            if(this == o)continue;
            if(x + width > o.x && x < o.x + o.width && y + height > o.y && y < o.y + o.height && value != o.value){
                return false;
            }
        }
        return true;
    }

    public void update() {
        if(Game.moving){
            if(!hasMoved){
                hasMoved = true;
            }
            if(canMove()){
                moving = true;
            }
            if(moving){
                if(Game.dir == 0)x -= speed;
                if(Game.dir == 1)x += speed;
                if(Game.dir == 2)y -= speed;
                if(Game.dir == 3)y += speed;
            }
            if(!canMove()){
                moving = false;
                x = Math.round(x / 100) * 100;
                y = Math.round(y / 100) * 100;
            }
        }
    }
    
    public void render(){
        Renderer.renderSprite(sprite, (int)x, (int)y);
    }
}
