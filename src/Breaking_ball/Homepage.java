package Breaking_ball;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Homepage extends JPanel {
    private JFrame frame;

    public Homepage(JFrame frame) {
        this.frame = frame;
        setLayout(null);
        setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("BREAKOUT BALL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(200, 50, 300, 40);
        add(titleLabel);

        JTextArea instructions = new JTextArea("Instructions:\n"
                + "- Use LEFT and RIGHT arrow keys to move the paddle.\n"
                + "- Bounce the ball to break all bricks.\n"
                + "- If the ball falls below the paddle, you lose a life.\n"
                + "- Clear all bricks to win the game!");
        instructions.setFont(new Font("Arial", Font.PLAIN, 18));
        instructions.setForeground(Color.WHITE);
        instructions.setBackground(Color.BLACK);
        instructions.setEditable(false);
        instructions.setBounds(150, 120, 400, 150);
        add(instructions);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setBounds(250, 300, 200, 50);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.add(new Gameplay());

            }
        });

        add(startButton);
    }
}
