package asteroids;
import javax.swing.text.Position;
import org.lwjgl.input.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
/**
 *
 * @author jmsgfhr,(add o nome de vcs)
 */
public class Menu extends BasicGameState{
    
    public Menu(int state){
        
    }
    
    public String Pos="";
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
        
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{ // classe onde faz os desenhos na tela menu
        g.drawString("Start", 400, 300);
        g.drawRect(380, 280, 100, 60);
        
        g.drawString("Sair", 400, 400);
        g.drawRect(380, 380, 100, 60);
        
        Image logo = new Image("Art/logo-asteroids.png");
        logo.draw(150, 0, 0.3f);
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
        Input mouse = gc.getInput(); //cria a variavel mouse que recebe o x e y do ponteiro
        int xpos = Mouse.getX(); //define pos x do ponteiro
        int ypos = Mouse.getY(); //define pos y do ponteiro
        Pos="x: "+xpos+"y: "+ypos;
        if((xpos>380 && xpos<480) && (ypos>255 && ypos<320)){ //verifica se o ponteiro esta sobre o retangulo start
            if(mouse.isMouseButtonDown(0)){ //evento apertar botao do mouse
                sbg.enterState(1); //altera o estado para trocar a "cena" do jogo
            }
        }
        else if((xpos>380 && xpos<480) && (ypos>160 && ypos<220)){ //verifica se o ponteiro esta sobre o retangulo sair
            if(mouse.isMouseButtonDown(0)){
                System.exit(0); //fecha o programa
            }
        }
    }
    
    @Override
    public int getID(){
        return 0;
    }
}