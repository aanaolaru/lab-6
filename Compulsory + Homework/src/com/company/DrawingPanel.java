package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawingPanel extends JPanel {
    private final MainFrame frame;
    int rows, cols;
    int canvasWidth = 400, canvasHeight = 400;
    int boardWidth, boardHeight;
    int cellWidth, cellHeight;
    int padX, padY;
    int stoneSize = 20;

    BufferedImage image; //the offscreen image
    Graphics2D offscreen; //the offscreen graphics

    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        createOffscreenImage();
        init(frame.configPanel.getRows(), frame.configPanel.getCols());
    }

    private void createOffscreenImage() {
        image = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        offscreen = image.createGraphics();
        offscreen.setColor(Color.WHITE); //fill the image with white
        offscreen.fillRect(0, 0, canvasWidth, canvasHeight);
    }

    final void init(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.padX = stoneSize + 10;
        this.padY = stoneSize + 10;
        this.cellWidth = (canvasWidth - 2 * padX) / (cols - 1);
        this.cellHeight = (canvasHeight - 2 * padY) / (rows - 1);
        this.boardWidth = (cols - 1) * cellWidth;
        this.boardHeight = (rows - 1) * cellHeight;

        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        paintGrid();
        graphics.drawImage(image, 0, 0, this);
    }

    private void paintGrid() {
        offscreen.setColor(Color.DARK_GRAY);

        //horizontal lines
        for (int row = 0; row < rows; row++) {
            int x1 = padX;
            int y1 = padY + row * cellHeight;
            int x2 = padX + boardWidth;
            int y2 = y1;

            offscreen.drawLine(x1, y1, x2, y2);
        }

        //vertical lines
        for (int col = 0; col < cols; col++) {
            int x1 = padX + col * cellWidth;
            int y1 = padY;
            int x2 = x1;
            int y2 = padY + boardHeight;

            offscreen.drawLine(x1, y1, x2, y2);
        }

        offscreen.setColor(Color.LIGHT_GRAY);

        //intersections
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = padX + col * cellWidth;
                int y = padY + row * cellHeight;

                offscreen.drawOval(x - stoneSize / 2, y - stoneSize / 2, stoneSize, stoneSize);
            }
        }
    }

    @Override
    public void update(Graphics g) {
    } //No need for update

    public void paintSticks(Node o1, Node o2) {
        offscreen.setStroke(new BasicStroke(3));

        offscreen.setColor(Color.BLACK);

        int x1, y1, x2, y2;

        x1 = padX + o1.getCol() * cellWidth;
        y1 = padY + o1.getRow() * cellHeight;

        if (o1.getRow() == o2.getRow()) {
            x2 = x1 + cellWidth;
            y2 = y1;
        } else {
            x2 = x1;
            y2 = y1 + cellHeight;
        }

        offscreen.drawLine(x1, y1, x2, y2);

        offscreen.setStroke(new BasicStroke(0));
    }

    public void paintStones(int x, int y) {
        offscreen.setColor(Color.RED);

        int copyOfx = x / cellWidth;
        int copyOfy = y / cellHeight;

        int a = padX + copyOfx * cellWidth;
        int b = padY + copyOfy * cellHeight;

        if (x <= a + stoneSize / 2 && x >= a - stoneSize / 2 && y <= b + stoneSize/2 && y>= b-stoneSize/2)
            offscreen.fillOval(a - stoneSize / 2, b - stoneSize / 2, stoneSize, stoneSize);
    }
}
