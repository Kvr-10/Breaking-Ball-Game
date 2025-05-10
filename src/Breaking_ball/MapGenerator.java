package Breaking_ball;

import java.awt.*;
import java.util.Random;

public class MapGenerator {
    public int map[][];
    public int brickWidth;
    public int brickHeight;
    private Color[][] brickColors; // 2D array to store brick colors
    private Random random;

    public MapGenerator(int row, int col) {
        map = new int[row][col];
        brickColors = new Color[row][col];
        random = new Random();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
                // Assign random color to each brick
                brickColors[i][j] = new Color(
                        random.nextInt(200) + 55,  // R (55-255)
                        random.nextInt(200) + 55,  // G (55-255)
                        random.nextInt(200) + 55   // B (55-255)
                );
            }
        }
        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    // Use the stored color for each brick
                    g.setColor(brickColors[i][j]);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }

    // New method to get brick color
    public Color getBrickColor(int row, int col) {
        return brickColors[row][col];
    }
}