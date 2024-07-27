/*
x=0 -------> +
y=0
|
|
v
+

*/

package Breaking_ball;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play= false;
    private int score=0;

    private int totalBricks=21;

    private Timer timer;
    private int delay=3; // speed of ball

    private int playerX=310;

    private int ballposX=320;
    private int ballposY=350;
    private int ballXdir= -2; // movement of ball. example 120-1,119-1 i.e going up
    private int ballYdir= -6; // same. We can control games speed too.

    private MapGenerator map;
    public Gameplay (){
        map =new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        //background
        g.setColor(Color.black);
        g.fillRect(1,1,683,592);

        //Bricks (map)
        map.draw((Graphics2D)g);


        //borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(683,0,3,592);

        //scores
        g.setColor(Color.BLUE);
        g.setFont(new Font("Algerian",Font.BOLD,25));
        g.drawString(""+score,630,30);

        //paddle
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(playerX,550,100,8);

        //ball
        g.setColor(Color.cyan);
        g.fillOval(ballposX,ballposY,20,20);

        if(totalBricks <= 0){
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.pink);
            g.setFont(new Font("Pacific",Font.BOLD,25));
            g.drawString("Congratz! You Won!!",215,300);
            g.drawString("Score:"+score,285,340);


            g.setFont(new Font("Pacific",Font.BOLD,20));
            g.drawString("Press Space to restart",240,380);
        }

        if(ballposY>570){
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.red);
            g.setFont(new Font("Algerian",Font.ITALIC,25));
            g.drawString("Game Over!",265,300);
            g.drawString("Score:"+score,285,340);

            g.setFont(new Font("Algerian",Font.ITALIC,20));
            g.drawString("Press Space to restart",220,375);
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8)))
            {
                ballYdir=-ballYdir;
            }

          A:  for(int i=0;i<map.map.length;i++){
                for (int j=0;j<map.map[0].length;j++){
                    if(map.map[i][j]>0){
                        int brickX=j* map.brickWidth +80;
                        int brickY=i* map.brickHeight +50;
                        int brickWidth= map.brickWidth;
                        int brickHeight= map.brickHeight;

                        Rectangle rect =new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect =new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect =rect;

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score+=5;

                            if(ballposX+19<=brickRect.x||ballposX +1 >= brickRect.x+brickRect.width){
                                ballXdir=-ballXdir;
                            }else {
                                ballYdir=-ballYdir;
                            }
                            break A; // break Label A so that it leaves the outer loop
                        }
                    }
                }
            }

            ballposX +=ballXdir;
            ballposY +=ballYdir;
            if(ballposX <0){
                ballXdir= -ballXdir;
            }
            if(ballposY <0){
                ballYdir= -ballYdir;
            }
            if(ballposX >665){
                ballXdir= -ballXdir;
            }
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) { } // Not using them
    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
        if(playerX>580){
            playerX=580;
        }
        else {
            moveRight();
        }
    }
        if(e.getKeyCode()== KeyEvent.VK_LEFT){
        if(playerX<10){
            playerX=10;
        }
            else{
            moveLeft();
            }
        }
        if (e.getKeyCode()== KeyEvent.VK_SPACE){
            if(!play){
                play =true;
                ballposX = 520;
                ballposY = 250;
                ballXdir = -2;
                ballYdir = -5;
                playerX = 310;
                score =0;
                totalBricks=32;
                map =new MapGenerator(4,8);
                repaint();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_END){
            play=false;
        }
    }

    public void moveRight(){
        play = true;
        playerX+=20;
    }

    public void moveLeft(){
        play=true;
        playerX-=20;
    }


}
