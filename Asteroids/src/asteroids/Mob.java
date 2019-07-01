package asteroids;

import static java.lang.Math.abs;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author jmsgfhr
 */
public class Mob {
    private Image mob;
    private float mobPosX = 0;
    private float mobPosY = 0;
    private float shiftMobX;
    private float shiftMobY;
    private float velx = 0;
    private float vely = 0;
    public Shape mobShape;
    private int child;
    
    public Mob(GameContainer gc, int child, float posX, float posY) throws SlickException{
        this.child = child;
        if(this.child>=0 && this.child<=2){
            this.mob = new Image("Art/asteroid.png");
        }
        if(this.child == 0){
            mobShape = new Circle(shiftMobX, shiftMobY, 30);
            int pos = (int) (4 * Math.random()); // variavel aleatorio que decide onde sera criado o asteroid
            switch (pos) { // verifica a variavel aleatorio para definir a posição X e Y do asteroid
                case 0:
                    mobPosX = -mob.getWidth();
                    mobPosY = (float) (600 * Math.random());
                    break;
                case 1:
                    mobPosX = gc.getWidth() + mob.getWidth();
                    mobPosY = (float) (600 * Math.random());
                    break;
                case 2:
                    mobPosX = (float) (900 * Math.random());
                    mobPosY = -mob.getHeight();
                    break;
                case 3:
                    mobPosX = (float) (900 * Math.random());
                    mobPosY = gc.getHeight() + mob.getHeight();
                    break;
                default:
                    break;
            }
            shiftMobX = mobPosX + mob.getWidth()/2;
            shiftMobY = mobPosY + mob.getHeight()/2;
        }
        else if (this.child == 1){ // asteroid medio
            mobShape = new Circle(shiftMobX, shiftMobY, 20);
            
            mobPosX=posX;
            mobPosY=posY;
            
            shiftMobX = mobPosX + mob.getWidth()/2;
            shiftMobY = mobPosY + mob.getHeight()/2;
        }
        else if (this.child == 2){// menor asteroid
            mobShape = new Circle(shiftMobX, shiftMobY, 14);
            
            mobPosX=posX;
            mobPosY=posY;
            
            shiftMobX = mobPosX ;
            shiftMobY = mobPosY ;
        }
    }
    
    public void init(GameContainer gc){
    }
    
    public void update(GameContainer gc, int delta) throws SlickException {
        shiftMobX += velx;
        shiftMobY += vely;
        
        // faz com que o asteroid apareça do outro lado da tela caso passe pelos limites
        if(shiftMobX>gc.getWidth()){
            shiftMobX=gc.getWidth()*(-1);
        }
        if(shiftMobY>gc.getHeight()){
            shiftMobY=gc.getHeight()*(-1);
        }
        if(shiftMobX + mob.getWidth()<0){
            shiftMobX=gc.getWidth();
        }
        if(shiftMobY + mob.getWidth()<0){
            shiftMobY=gc.getHeight();
        }
        mobShape.setLocation(shiftMobX, shiftMobY);
    }
    
    public void render(GameContainer gc, Graphics g) throws SlickException{
        if(child == 0){ // asteroid normal
            mob.draw(shiftMobX,shiftMobY,1);
        }
        else if(child == 1){
            mob.draw(shiftMobX,shiftMobY,0.7f);//asteroid medio
        }
        else if(child == 2){
            mob.draw(shiftMobX,shiftMobY,0.4f);//asteroid pequeno
        }
    }
    
    public Shape getShape(){ // retorna o shape de um unico asteroid
        return mobShape;
    }
    
    public void direcao(float playerx, float playery){ // metodo de definir a direção do asteroid
        if(playerx>shiftMobX ) velx = (float) (abs(playerx - shiftMobX))/(60*5);    
        else if(playerx<shiftMobX ) velx = (float) -(abs(playerx - shiftMobX)/(60*5));
        if(playery>shiftMobY) vely = (float) (abs(playery - shiftMobY))/(60*5);
        else if(playery<shiftMobY) vely = (float) -((abs(playery - shiftMobY))/(60*5));
    }
    
    public int getChild(){
        return child;
    }
    
    public float getX(){
        return shiftMobX;
    }
    
    public float getY(){
        return shiftMobY;
    }
}