package asteroids;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
/**
 * quando iniciarem o projeto vá em propriedades do projeto e alterem o local do slick
 * @author jmsgfhr,(add o nome de vcs)
 */
public class Asteroids extends StateBasedGame{

    public static final String gamename = "Asteroids"; //como a variavel ja diz nome do game
    public static final int menu = 0; //codio do estado da tela
    public static final int play = 1;
    
    public Asteroids(String gamename) throws SlickException{// construct asteroid
        super(gamename);
        this.addState(new Menu(menu)); // inciando estado menu e abrindo na tela inicial
        this.addState(new Play(play)); // iniciando estado play
    }
    
    @Override
    public void initStatesList(GameContainer gc) throws SlickException{
        this.getState(menu).init(gc, this);
        this.getState(menu).init(gc, this);
        this.enterState(menu); //inciando o estado do jogo como menu
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        AppGameContainer appgc;
        try {
            appgc = new AppGameContainer(new Asteroids(gamename)); // instanciando o container que é a tela do jogo
            appgc.setDisplayMode(900, 600, false); // setando tamanho
            appgc.setTargetFrameRate(60);
            appgc.start(); // iniciando tela
        } catch (SlickException e){
            e.printStackTrace();
        }
    }
    
}