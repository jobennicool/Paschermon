import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{


    //Screen settings
    final int originalTileSize = 16; //16x16 tile
    final int scale = 4;

    public final int tileSize = originalTileSize * scale; //48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    //WELT SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //FPS
    int FPS = 60;

    //SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound soundFX = new Sound();
    AssetSetter aSetter = new AssetSetter(this);
    CollisonChecker cChecker = new CollisonChecker(this);
    UI ui = new UI(this);
    Thread gameThread;


    // ENTITY UND OBJECT
    Player player = new Player(this, keyH);
    SuperObject obj[] = new SuperObject[10];
    Entity npc[] = new Entity[10];

    //GAMESTATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;


    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){

        aSetter.setObject();
        aSetter.setNPC();
        //playMusic(0);
        gameState = titleState;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
            }
        }
    }
    public void update(){

        if(gameState == playState){
            //PLAYER
            player.update();
            //NPC
            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }

        }
        if (gameState == pauseState) {
            //nix
        }
        if(gameState == dialogueState){

        }


    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }
        //OTHERS
        else{

            //DEBUG
            long drawStart = 0;
            if (keyH.debugMode) {
                drawStart = System.nanoTime();
            }

            //TILE
            tileM.draw(g2);

            //OBJECT
            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    obj[i].draw(g2, this);
                }
            }

            //NPC
            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    npc[i].draw(g2);
                }
            }

            //PLAYER
            player.draw(g2);

            //UI
            ui.draw(g2);



            if(keyH.debugMode){
                long drawEnd = System.nanoTime();
                long passedTime = drawEnd - drawStart;
                g2.setColor(Color.white);
                g2.drawString("Draw Time: "+ passedTime, 10, 400);
                System.out.println("Draw Time: "+ passedTime);
            }


            g2.dispose();
        }
    }

    public void playMusic(int i){

        music.setFIle(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){

        music.stop();
    }
    public void playSE(int i){

        soundFX.setFIle(i);
        soundFX.play();
    }
}

