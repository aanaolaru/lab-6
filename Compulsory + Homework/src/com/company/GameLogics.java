package com.company;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogics {
    private final MainFrame frame;

    List<Node> nodes = new ArrayList<>();

    public GameLogics(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        for (int i = 0; i < (frame.canvas.rows * frame.canvas.cols); i++) {
            int row = i / frame.canvas.rows;
            int col = i - (row * frame.canvas.cols);

            nodes.add(new Node(i, false, row, col));
        }

        for (int i = 0; i < frame.canvas.rows; i++) {
            for (int j = 0; j < frame.canvas.cols - 1; j++) {
                Random random = new Random(); // creating Random object

                // generate random boolean
                if (random.nextBoolean()) {
                    nodes.get(frame.canvas.cols * i + j).add(nodes.get((frame.canvas.cols * i + j) + 1));
                    nodes.get(frame.canvas.cols * i + j).setKey(true);
                    nodes.get((frame.canvas.cols * i + j) + 1).setKey(true);
                }
            }
        }

        for (int i = 0; i < frame.canvas.rows - 1; i++) {
            for (int j = 0; j < frame.canvas.cols; j++) {
                Random random = new Random(); // creating Random object

                if (random.nextBoolean()) {
                    nodes.get(frame.canvas.cols * i + j).add(nodes.get((frame.canvas.cols * (i + 1) + j)));
                    nodes.get(frame.canvas.cols * i + j).setKey(true);
                    nodes.get(frame.canvas.cols * (i + 1) + j).setKey(true);
                }
            }
        }

        for (Node o1 : nodes) {
            for (Node o2 : o1.getAdj()) {
                frame.canvas.paintSticks(o1, o2);
            }
        }

        frame.canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                frame.canvas.paintStones(e.getX(), e.getY());
                frame.canvas.repaint();
            }
            //Can’t use lambdas, JavaFX does a better job in these cases
        });
    }
}
