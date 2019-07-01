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
    
    public Color corStart = Color.yellow;
    public Color corExit = Color.orange;
    public static Sound space;
    
    public Menu(int state){
        
    }
    
    public String Pos=""; //apagar no final
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
        
        this.space = new Sound("Sounds/space.wav");
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{ // classe onde faz os desenhos na tela menu
        g.setColor(corStart); //selecionando cor
        g.drawString("Start", 400, 300); //desenhando uma string
        g.drawRect(380, 280, 100, 60);//desenhando um retangulo
        
        g.setColor(corExit); // selecionando cor
        g.drawString("Sair", 400, 400); //desenhando uma string
        g.drawRect(380, 380, 100, 60); //desenhando um retangulo
        
        Image logo = new Image("Art/logo-asteroids.png"); //selecionando a logo do jogo
        logo.draw(150, 0, 0.3f); // desenhando a logo
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
        Input mouse = gc.getInput(); //cria a variavel mouse que recebe o x e y do ponteiro
        int xpos = Mouse.getX(); //define pos x do ponteiro
        int ypos = Mouse.getY(); //define pos y do ponteiro
        Pos="x: "+xpos+"y: "+ypos;
        if((xpos>380 && xpos<480) && (ypos>255 && ypos<320)){ //verifica se o ponteiro esta sobre o retangulo start
            corStart = Color.green; //hover do retangulo start
            if(mouse.isMouseButtonDown(0)){ //evento apertar botao do mouse
                sbg.getState(1).init(gc, sbg);
                sbg.enterState(1); //altera o estado para trocar a "cena" do jogo
            }
        }
        else if((xpos>380 && xpos<480) && (ypos>160 && ypos<220)){ //verifica se o ponteiro esta sobre o retangulo sair
            corExit = Color.red; // hover do retangulo sair
            if(mouse.isMouseButtonDown(0)){
                System.exit(0); //fecha o programa
            }
        }
        else{
            corStart = Color.yellow;
            corExit = Color.orange;
        }
        if(!space.playing()) space.loop();
        
    }
    
    @Override
    public int getID(){
        return 0;
    }
}