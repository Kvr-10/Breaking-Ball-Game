package Breaking_ball;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
       JFrame obj =new JFrame();
       ImageIcon icon = new ImageIcon(Main.class.getResource("/Icons/ball.png"));
       obj.setIconImage(icon.getImage());
       Gameplay gameplay = new Gameplay();
       obj.setBounds(300,50,700,600);
       obj.setTitle("Breakout Ball");
       obj.setResizable(false);
       obj.setVisible(true);
       obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       obj.add(gameplay);
    }

}
