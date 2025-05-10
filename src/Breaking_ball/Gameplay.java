package Breaking_ball;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = true; // Changed to true so paddle works immediately
    private int score = 0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballposX = 320;
    private int ballposY = 350;
    private int ballXdir = -2;
    private int ballYdir = -6;
    private Color ballColor = new Color(0x373777);
    private MapGenerator map;

    // Movement variables
    private boolean rightPressed = false;
    private boolean leftPressed = false;
    private final int PADDLE_SPEED = 8;
    private final int PADDLE_ACCEL = 2;
    private int currentSpeed = 0;

    public Gameplay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // background
        g.setColor(Color.white);
        g.fillRect(1, 1, 683, 592);

        // Bricks (map)
        map.draw((Graphics2D) g);

        // borders
        g.setColor(Color.black);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(683, 0, 3, 592);

        // scores
        g.setColor(new Color(0xB8B80A));
        g.setFont(new Font("Algerian", Font.BOLD, 25));
        g.drawString("Score: " + score, 520, 30);

        // paddle
        g.setColor(Color.darkGray);
        g.fillRect(playerX, 550, 100, 8);

        // ball
        g.setColor(ballColor);
        g.fillOval(ballposX, ballposY, 20, 20);

        if (totalBricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(new Color(0x0202CC));
            g.setFont(new Font("Serif", Font.BOLD, 25));
            g.drawString("Congratulation! You Won!!", 210, 300);
            g.drawString("Score:" + score, 305, 340);

            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Press Space for Level 1", 265, 380);
            g.drawString("Press Ctrl for Level 2", 270, 405);
            g.drawString("Press Shift for Level 3", 267, 430);
        }

        if (ballposY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("Serif", Font.ITALIC, 25));
            g.drawString("Game Over!", 285, 300);
            g.drawString("Score:" + score, 305, 340);

            g.setFont(new Font("Serif", Font.ITALIC, 20));
            g.drawString("Press Space for Level 1", 255, 375);
            g.drawString("Press Ctrl for Level 2", 267, 405);
            g.drawString("Press Shift for Level 3", 263, 435);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        // Paddle movement works regardless of game state
        if (rightPressed) {
            currentSpeed = Math.min(currentSpeed + PADDLE_ACCEL, PADDLE_SPEED);
        }
        else if (leftPressed) {
            currentSpeed = Math.max(currentSpeed - PADDLE_ACCEL, -PADDLE_SPEED);
        }
        else {
            // Decelerate when no key is pressed
            if (currentSpeed > 0) {
                currentSpeed = Math.max(currentSpeed - PADDLE_ACCEL, 0);
            } else if (currentSpeed < 0) {
                currentSpeed = Math.min(currentSpeed + PADDLE_ACCEL, 0);
            }
        }

        // Apply movement
        playerX += currentSpeed;

        // Keep paddle within bounds
        playerX = Math.max(10, Math.min(playerX, 580));

        // Only process ball physics if game is active
        if (play) {
            // Ball-paddle collision
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
                float hitPosition = (ballposX - playerX) / 100.0f;
                ballXdir = (int)((hitPosition - 0.5f) * 8);
            }

            // Brick collisions
            A: for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            ballColor = map.getBrickColor(i, j);
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }

            // Ball movement
            ballposX += ballXdir;
            ballposY += ballYdir;

            // Wall collisions
            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballposX > 665) {
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (key == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (key == KeyEvent.VK_SPACE) {
            if (!play) {
                resetGame(3, 7, 21);
            }
        } else if (key == KeyEvent.VK_CONTROL) {
            if (!play) {
                resetGame(4, 8, 32);
            }
        } else if (key == KeyEvent.VK_SHIFT) {
            if (!play) {
                resetGame(5, 10, 50);
            }
        }
        if (key == KeyEvent.VK_END) {
            play = !play; // Toggle pause
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if (key == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
    }

    private void resetGame(int rows, int cols, int bricks) {
        play = true;
        ballposX = 320;
        ballposY = 350;
        ballXdir = -2;
        ballYdir = -6;
        playerX = 310;
        score = 0;
        totalBricks = bricks;
        map = new MapGenerator(rows, cols);
        ballColor = new Color(0x373777);
        currentSpeed = 0;
        repaint();
    }
}