import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Random;

import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener,ActionListener {
   
    // ---For Snake positions---
    private int[] snakexLen = new int[750];
    private int[] snakeyLen = new int[750];

    // ----For fruit positions---
    private int[] fruitxpos = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
	private int[] fruitypos = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};

    
    private boolean right =false;
    private boolean left =false;
    private boolean up =false;
    private boolean down =false;

    
    private ImageIcon downmouth;
    private ImageIcon upmouth;
    private ImageIcon leftmouth;
    private ImageIcon rightmouth;
    private ImageIcon body;

    private ImageIcon fruitimage;
    
    private ImageIcon titleImage;

    private Random random=new Random();
    // ---Selecting Random position of fruit---
    private int xpos = random.nextInt(34);
    private int ypos = random.nextInt(23);

    private Timer timer;
    private int delay=100;

    // ---Initial Length , moves & score of snake---
    private int lenSnake=3;
    private int moves=0;
    private int score=0;


    // ----Constructor----
    public Gameplay(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        timer = new Timer(delay, this);
        timer.start();
    }

    
    // -----Paint method to paint the board & Snake----
    public void paint(Graphics g){

        // ----Setting initial Snake Movements---
        if (moves==0) {
            snakexLen[0]=100;
            snakexLen[1]=75;
            snakexLen[2]=50;
            
            snakeyLen[0]=100;
            snakeyLen[1]=100;
            snakeyLen[2]=100;
        }

        
        // ---Border & Image of title---
        g.setColor(Color.white);
        g.drawRect(24, 10, 851, 55);
        titleImage = new ImageIcon("Icons/SnakeGameTitle.png");
        titleImage.paintIcon(this, g, 25, 11);


        // ---Border of game--
        g.setColor(Color.white);
        g.drawRect(24, 74, 851, 577);
        g.setColor(Color.black);
        g.fillRect(25, 75, 850, 575);


        // -----Draw score-----
        g.setColor(Color.white);
        g.setFont(new Font("arial",Font.PLAIN,14));
        g.drawString("Scores : "+score, 780, 30);
        g.drawString("Length : "+lenSnake, 780, 50);

    
        // ----Initial Snake Face-----
        rightmouth = new ImageIcon("Icons/rightmouth.png");
        rightmouth.paintIcon(this, g, snakexLen[0], snakeyLen[0]);

        // ---- Paint Snake according to its movement----
        for(int i=0;i<lenSnake;i++){
            if (i==0 && right) {
                rightmouth = new ImageIcon("Icons/rightmouth.png");
                rightmouth.paintIcon(this, g, snakexLen[i], snakeyLen[i]);
            }
            if (i==0 && left) {
                leftmouth = new ImageIcon("Icons/leftmouth.png");
                leftmouth.paintIcon(this, g, snakexLen[i], snakeyLen[i]);
            }
            if (i==0 && up) {
                upmouth = new ImageIcon("Icons/upmouth.png");
                upmouth.paintIcon(this, g, snakexLen[i], snakeyLen[i]);
            }
            if (i==0 && down) {
                downmouth = new ImageIcon("Icons/downmouth.png");
                downmouth.paintIcon(this, g, snakexLen[i], snakeyLen[i]);
            }

            if(i!=0){
                body = new ImageIcon("Icons/body.png");
                body.paintIcon(this, g, snakexLen[i], snakeyLen[i]);
            }
        }

        // ----fruit eating resolved----
        fruitimage = new ImageIcon("Icons/fruit.png");
        fruitimage.paintIcon(this, g, fruitxpos[xpos], fruitypos[ypos]);
        if (fruitxpos[xpos]==snakexLen[0] && fruitypos[ypos]==snakeyLen[0]) {
            lenSnake++;
            score++;
            xpos=random.nextInt(34);
            ypos=random.nextInt(23);
        }

        // ----Game over condition---
        for(int i=1;i<lenSnake;i++){
            if (snakexLen[i]==snakexLen[0] && snakeyLen[i]==snakeyLen[0]) {
                right=false;
                left=false;
                up=false;
                down=false;

                g.setColor(Color.white);
                g.setFont(new Font("SANS_SERIF",Font.BOLD,52));
                g.drawString("Game Over!", 300, 300);
                
                g.setFont(new Font("SANS_SERIF",Font.BOLD,19));
                g.drawString("Press Space to Restart the Game", 310, 340);

            }
        }


        g.dispose();
    }
    
    
    // ----Repaint snake and change its length----
    @Override
    public void actionPerformed(ActionEvent e){
        if (right ) {
            for(int i=lenSnake-1;i>=0;i--){
                snakeyLen[i+1]=snakeyLen[i];
            }
            for(int i=lenSnake;i>=0;i--){
                if (i==0) {
                    snakexLen[i]=snakexLen[i]+25;
                }
                else{
                    snakexLen[i]=snakexLen[i-1];
                }
                if (snakexLen[i]>850) {
                    snakexLen[i]=25;
                }
            }
            repaint();
        }
        if (left ) {
            for(int i=lenSnake-1;i>=0;i--){
                snakeyLen[i+1]=snakeyLen[i];
            }
            for(int i=lenSnake;i>=0;i--){
                if (i==0) {
                    snakexLen[i]=snakexLen[i]-25;
                }
                else{
                    snakexLen[i]=snakexLen[i-1];
                }
                if (snakexLen[i]<25) {
                    snakexLen[i]=850;
                }
            }
            repaint();
        }
        if (up ) {
            for(int i=lenSnake-1;i>=0;i--){
                snakexLen[i+1]=snakexLen[i];
            }
            for(int i=lenSnake;i>=0;i--){
                if (i==0) {
                    snakeyLen[i]=snakeyLen[i]-25;
                }
                else{
                    snakeyLen[i]=snakeyLen[i-1];
                }
                if (snakeyLen[i]<75) {
                    snakeyLen[i]=625;
                }
            }
            repaint();
        }
        if (down ) {
            for(int i=lenSnake-1;i>=0;i--){
                snakexLen[i+1]=snakexLen[i];
            }
            for(int i=lenSnake;i>=0;i--){
                if (i==0) {
                    snakeyLen[i]=snakeyLen[i]+25;
                }
                else{
                    snakeyLen[i]=snakeyLen[i-1];
                }
                if (snakeyLen[i]>625) {
                    snakeyLen[i]=75;
                }
            }
            repaint();
        }
    }
    

    // ---Settings movements of Snake according to keypressed-----
    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            moves++;
            if(!left){
                right=true;
            }
            else{
                right=false;
                left=true;
            }
                
            up=false;
            down=false;
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            moves++;
            if(!right){
                left=true;
            }
            else{
                left=false;
                right=true;
            }
            
            up=false;
            down=false;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP){
            moves++;
            if(!down){
                up=true;
            }
            else{
                up=false;
                down=true;
            }
                
            right=false;
            left=false;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN){
            moves++;
            if(!up){
                down=true;
            }
            else{
                down=false;
                up=true;
            }
            
            right=false;
            left=false;
        }

        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            score=0;
            moves=0;
            lenSnake=3;
            repaint();
        }
    }
    
    public void keyReleased(KeyEvent arg0){    }

    public void keyTyped(KeyEvent arg0){    }
    

}
