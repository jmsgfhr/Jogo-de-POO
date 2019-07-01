package asteroids;
import static java.lang.Math.abs;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
/**
 *
 * @author jmsgfhr,(add o nome de vcs)
 */
public class Play extends BasicGameState{
    // variaveis do estado play
    public Player player1; // jogador
    private ArrayList<Mob> mob = new ArrayList<Mob>(); // inicia a lista de asteroid
    
    public double tickerTiro = System.currentTimeMillis(); // tempo para o botão de atirar
    public double tickerMob = System.currentTimeMillis(); // tempo para spawnar o asteroid
    public double tickerGameOver;
    private int tempo; //Contagem regressiva para voltar ao menu
    private final Sound explosion;
    private final Sound explosionfinal;
    private final Sound bigexplosion;
    private boolean tocou;
   
    
    public Play(int state) throws SlickException{
        this.explosion = new Sound("Sounds/explosion.wav");
        this.bigexplosion = new Sound("Sounds/bigexplosion.wav");
        this.explosionfinal = new Sound("Sounds/explosionfinal.wav");
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
        player1 = new Player(1);// instancia do jogador
        player1.init(gc); //inicia as variaveis do player
        tickerGameOver = 0;
        this.tocou = false;
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
        if(player1.get_vidas() <= 0){
            Image gameover = new Image("Art/gameover.png");
            gameover.draw(gc.getWidth()/2 - (gameover.getWidth()/2)*0.3f, gc.getHeight()/3 - (gameover.getHeight()/2)*0.3f, 0.3f );
            g.drawString("Pontuação final: " + player1.get_pontuacao(), gc.getWidth()/2 - (gameover.getWidth()/2)*0.3f, (gc.getHeight()/3)+50);
            g.drawString("Voltando ao menu em: " + (10 - get_timeGameOver()), gc.getWidth()/2 - (gameover.getWidth()/2)*0.3f, (gc.getHeight()/3)+100);
        }else{
            player1.render(gc, g); //renderiza o player
            for(Mob m: mob){
                m.render(gc, g); //renderiza cada asteroid da lista
                m.update(gc, 0); // atualiza cada asteroid da lista
            }
        }
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
        
        if(player1.get_vidas() <= 0){
            if(!tocou) {
                explosionfinal.play();
                tocou = true;
            }
            if(tickerGameOver == 0) tickerGameOver = System.currentTimeMillis();
            if(System.currentTimeMillis() - tickerGameOver >= 10000 ){
                player1.getShot().removeAll(player1.getShot());//Remove os tiros
                mob.removeAll(mob);//Remove os asteroides
                sbg.getState(0).init(gc, sbg);//Reinicia o estado do menu
                sbg.enterState(0);//Volta para o menu
            }else{
                set_timeGameOver((int) abs((tickerGameOver - System.currentTimeMillis())/1000));
            }
            
        }else{
        
            player1.commands(gc, delta);//verifica os comandos do player
            player1.update(gc, delta);//atualiza o player

            if((System.currentTimeMillis()-tickerMob)>4000){//medidor de tempo para spawnar o asteroid
                Mob m = new Mob(gc,0,0,0); // cria um asteroid
                m.direcao(player1.getPlayerPosX(), player1.getPlayerPosY()); //define a direção do asteroid
                mob.add(m);// adiciona asteroid em uma lista de asteroid
                tickerMob = System.currentTimeMillis();// zera o tempo do asteroid
            }
            ListIterator<Mob> m = mob.listIterator();//itera a lista de asteroid
            while (m.hasNext()) {
                Mob next = m.next();
                ArrayList<Shot> auxShot = player1.getShot(); //cria uma lista de tiros que recebe a lista de tiros do player
                Iterator<Shot> i = auxShot.iterator();// itera a lista d tiros
                if(next.mobShape.intersects(player1.playerShape)){
                    m.remove(); // remove o asteroid
                    if (next.getChild()>=0 && next.getChild()<2){//toda a logica de criar filhos do asteroid ao ser atingido pela nave
                        float x1 = (float) (900 * Math.random());//define posiçoes x e y aleatorias para o asteroid seguir
                        float y1 = (float) (600 * Math.random());
                        Mob child1 = new Mob(gc, next.getChild()+1,next.getX(),next.getY()); //cria o asteroid filho
                        child1.direcao(x1, y1); // define a direção do asteroid
                        m.add(child1); //adiciona o filho a lista
                        float x2 = (float) (900 * Math.random()); // mesma coisa que antes
                        float y2 = (float) (600 * Math.random());
                        Mob child2 = new Mob(gc, next.getChild()+1,next.getX(),next.getY());
                        child2.direcao(x2, y2);
                        m.add(child2);
                    }
                    bigexplosion.play();
                    player1.set_vidas(player1.get_vidas()-1);

                    //player1.reset();//prototipo de perder vida, só coloca o player no meio da tela novamente
                }
                while(i.hasNext()){
                    Shot snext = i.next();
                    if(next.mobShape.intersects(snext.shot)){// verifica se o asteroid esta colidindo com o tiro
                        i.remove(); // remove o tiro
                        m.remove(); // remove o asteroid

                        player1.set_pontuacao(player1.get_pontuacao()+ (next.getChild()+1)*100); //Calcula a pontuação

                        if (next.getChild()>=0 && next.getChild()<2){ // igual ali em cima só que aqui o asteroid é atingido por um tiro
                            float x1 = (float) (900 * Math.random());
                            float y1 = (float) (600 * Math.random());
                            Mob child1 = new Mob(gc, next.getChild()+1,next.getX(),next.getY());
                            child1.direcao(x1, y1);
                            m.add(child1);
                            float x2 = (float) (900 * Math.random());
                            float y2 = (float) (600 * Math.random());
                            Mob child2 = new Mob(gc, next.getChild()+1,next.getX(),next.getY());
                            child2.direcao(x2, y2);
                            m.add(child2);
                        }
                        explosion.play();
                    }
                }
            }
        }
    }
    
    @Override
    public int getID(){
        return 1;
    }
    
    public void set_timeGameOver(int time){
        tempo = time;
    }
    
    public int get_timeGameOver(){
        return tempo;
    }
    
}