package Breaking_ball;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
   public static void main(String[] args) {
      JFrame obj = new JFrame();
      ImageIcon icon = new ImageIcon(Main.class.getResource("/Icons/ball.png"));
      obj.setIconImage(icon.getImage());
      obj.setBounds(300, 50, 700, 600);
      obj.setTitle("Breakout Ball");
      obj.setResizable(false);
      obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Instruction Panel
      JPanel instructionPanel = new JPanel();
      instructionPanel.setLayout(new BorderLayout());
      instructionPanel.setBackground(Color.BLACK);

      JLabel instructions = new JLabel("<html><h2 style='color:white;'>Welcome to Breakout Ball!</h2>"
              + "<ul style='color:white;'>"
              + "<li>Use Left and Right arrow keys to move the paddle.</li>"
              + "<li>Break all the bricks to win.</li>"
              + "<li>Don't let the ball fall!</li>"
              + "</ul></html>", JLabel.CENTER);

      instructions.setHorizontalAlignment(SwingConstants.CENTER);
      instructions.setVerticalAlignment(SwingConstants.CENTER);

      // Styled Start Button
      JButton startButton = new JButton("Start Game");
      startButton.setFont(new Font("Arial", Font.BOLD, 18));
      startButton.setBackground(new Color(30, 144, 255));  // Dodger Blue
      startButton.setForeground(Color.WHITE);
      startButton.setFocusPainted(false);
      startButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
      startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

      // Hover Effect
      startButton.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseEntered(java.awt.event.MouseEvent evt) {
            startButton.setBackground(new Color(0, 120, 215));  // Darker Blue
         }
         public void mouseExited(java.awt.event.MouseEvent evt) {
            startButton.setBackground(new Color(30, 144, 255));
         }
      });

      // Panel for Button
      JPanel buttonPanel = new JPanel();
      buttonPanel.setBackground(Color.BLACK);
      buttonPanel.add(startButton);

      instructionPanel.add(instructions, BorderLayout.CENTER);
      instructionPanel.add(buttonPanel, BorderLayout.SOUTH);

      obj.add(instructionPanel);
      obj.setVisible(true);

      // Start Button Action
      startButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            obj.remove(instructionPanel);
            Gameplay gameplay = new Gameplay(); // Create Gameplay only now
            obj.add(gameplay);
            obj.revalidate();
            obj.repaint();
            gameplay.requestFocus();
         }
      });
   }
}
