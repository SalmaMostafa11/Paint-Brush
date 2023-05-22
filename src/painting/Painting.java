/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package painting;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Painting extends JPanel implements MouseListener, ActionListener,
        MouseMotionListener {

    private static final long serialVersionUID = 1L;
    public static int stroke, eraser = 0;
    private int xX1, yY1, xX2, yY2, choice;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private int eraserWidth = 20;
    private int eraserHeight = 20;
    private List<List<Point>> points;
    private List<Point> currentPath;

    public static void main(String[] args) {
        new Painting();
    }

    Painting() {

        points = new ArrayList<>(25);
        JFrame frame = new JFrame("Paint Program");
        frame.setSize(1200, 800);
        frame.setBackground(BACKGROUND_COLOR);
        frame.getContentPane().add(this);
        this.setFocusable(true);
        this.setLayout(null);

        JButton clear = new JButton("Clear");
        clear.addActionListener(this);
        clear.setLocation(950, 50);
        clear.setSize(150, 40);

        JButton erase = new JButton("Erase?");
        erase.addActionListener(this);
        erase.setLocation(950, 100);
        erase.setSize(150, 40);

        JButton undo = new JButton("Undo");
        undo.addActionListener(this);
        undo.setLocation(950, 550);
        undo.setSize(150, 40);
        
        JButton save = new JButton("Save");
        save.addActionListener(this);
        save.setLocation(950, 600);
        save.setSize(150, 40);
        
        JButton image = new JButton("Drag Image");
        image.addActionListener(this);
        image.setLocation(950, 650);
        image.setSize(150, 40);
        
        
        JButton color = new JButton("Color");
        color.addActionListener(this);
        color.setLocation(50, 50);
        color.setSize(150, 40);

        JButton emptyrect = new JButton("Empty Rect");
        emptyrect.addActionListener(this);
        emptyrect.setLocation(50, 150);
        emptyrect.setSize(150, 40);

        JButton fillrect = new JButton("Filled Rect");
        fillrect.addActionListener(this);
        fillrect.setLocation(50, 200);
        fillrect.setSize(150, 40);

        JButton emptyoval = new JButton("Empty oval");
        emptyoval.addActionListener(this);
        emptyoval.setLocation(50, 300);
        emptyoval.setSize(150, 40);

        JButton filloval = new JButton("Filled oval");
        filloval.addActionListener(this);
        filloval.setLocation(50, 350);
        filloval.setSize(150, 40);

        JButton line = new JButton("Line");
        line.addActionListener(this);
        line.setLocation(50, 450);
        line.setSize(150, 40);
        //line.setBackground(Color.BLUE);

        JButton freehand = new JButton("Free Hand");
        freehand.addActionListener(this);
        freehand.setLocation(50, 500);
        freehand.setSize(150, 40);

        JCheckBox dotted = new JCheckBox("Dotted");
        dotted.setLocation(50, 600);
        dotted.setSize(150, 40);
        JCheckBox filled = new JCheckBox("Filled");
        filled.setLocation(50, 650);
        filled.setSize(150, 40);

        this.add(clear);
        this.add(erase);

        this.add(color);

        this.add(emptyrect);
        this.add(fillrect);

        this.add(emptyoval);
        this.add(filloval);

        this.add(line);
        this.add(freehand);

        this.add(dotted);
        this.add(filled);
        
         this.add(undo);
        this.add(save);
         this.add(image);
        

        addMouseListener(this);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        if (grid == null) {
            int w = this.getWidth();
            int h = this.getHeight();
            grid = (BufferedImage) (this.createImage(w, h));
            gc = grid.createGraphics();
            gc.setColor(Color.BLUE);
        }
        

        g2.drawImage(grid, null, 0, 0);
        
        check();
    }

    BufferedImage grid;
    Graphics2D gc;

    public void draw() {
        Graphics2D g = (Graphics2D) getGraphics();
        int w = xX2 - xX1;
        if (w < 0) {
            w = w * (-1);
        }

        int h = yY2 - yY1;
        if (h < 0) {
            h = h * (-1);
        }

        switch (choice) {
            case 1:
                check();
                gc.drawRect(xX1, yY1, w, h);
                repaint();
                break;

            case 2:
                check();
                gc.drawOval(xX1, yY1, w, h);
                repaint();
                break;

            case 3:
                check();
                gc.drawRect(xX1, yY1, w, h);
                gc.fillRect(xX1, yY1, w, h);
                repaint();
                break;

            case 4:
                check();
                gc.drawOval(xX1, yY1, w, h);
                gc.fillOval(xX1, yY1, w, h);
                repaint();
                break;

            case 5:

                if (stroke == 0) {
                    gc.setStroke(new BasicStroke(1));
                }
                if (stroke == 1) {
                    gc.setStroke(new BasicStroke(3));
                }
                if (stroke == 2) {
                    gc.setStroke(new BasicStroke(6));
                }
                gc.drawLine(xX1, yY1, xX2, yY2);
                repaint();
                break;

            case 6:
                repaint();
                Color temp = gc.getColor();
                gc.setColor(BACKGROUND_COLOR);
                gc.fillRect(0, 0, getWidth(), getHeight());
                gc.setColor(temp);
                repaint();
                break;

            case 7:

                if (eraser == 1) {
                    gc.clearRect(xX1, yY1, w, h);
                } else {

                }
                break;

            case 8:
                for (List<Point> path : points) {
                    Point from = null;
                    for (Point p : path) {
                        if (from != null) {
                            gc.drawLine(xX1, yY1, xX2, yY2);
                        }
                        from = p;
                    }
                }

        }
    }

    public void check() {
        if (xX1 > xX2) {
            int z = 0;
            z = xX1;
            xX1 = xX2;
            xX2 = z;
        }
        if (yY1 > yY2) {
            int z = 0;
            z = yY1;
            yY1 = yY2;
            yY2 = z;
        }
    }

    public void actionPerformed(ActionEvent e) {

        super.removeMouseMotionListener(this);

        if (e.getActionCommand().equals("Color")) {
            Color bgColor = JColorChooser.showDialog(this, "Choose Background Color", getBackground());
            if (bgColor != null) {
                gc.setColor(bgColor);
            }
        }

        if (e.getActionCommand().equals("Empty Rect")) {
            System.out.println("Empty Rectangle Has Been Selected!");
            choice = 1;

        }
        if (e.getActionCommand().equals("Filled Rect")) {
            System.out.println("Filled Rectangle Has Been Selected");
            choice = 3;
        }

        if (e.getActionCommand().equals("Empty oval")) {
            System.out.println("Empty Oval Has Been Selected!");
            choice = 2;
        }

        if (e.getActionCommand().equals("Filled oval")) {
            System.out.println("Filled Oval Has Been Selected");
            choice = 4;
        }

        if (e.getActionCommand().equals("Line")) {
            System.out.println("Draw Line Has Been Selected");
            choice = 5;
        }
        if (e.getActionCommand().equals("Free Hand")) {
            System.out.println("Hand Free Has Been Selected");
            choice = 8;
        }

        

        if (e.getActionCommand().equals("Clear")) {
            System.out.println("Clear All The Things!!!");
            choice = 6;
            draw();
        }
        
        if (e.getActionCommand().equals("Erase?")) {
            eraser = 1;
            choice = 7;

            super.addMouseMotionListener(this);
        }

    }

    public void mouseExited(MouseEvent evt) {
    }

    public void mouseEntered(MouseEvent evt) {
    }

    public void mouseClicked(MouseEvent evt) {
    }

    public void mousePressed(MouseEvent evt) {

        xX1 = evt.getX();
        yY1 = evt.getY();

        currentPath = new ArrayList<>(25);
        currentPath.add(evt.getPoint());
        points.add(currentPath);

    }

    public void mouseReleased(MouseEvent evt) {
        xX2 = evt.getX();
        yY2 = evt.getY();
        eraser = 0;
        currentPath = null;
        draw();

    }

    public void mouseDragged(MouseEvent me) {
        Color c = gc.getColor();
        gc.setColor(BACKGROUND_COLOR);
        gc.drawRect(me.getX(), me.getY(), eraserWidth, eraserHeight);
        gc.fillRect(me.getX(), me.getY(), eraserWidth, eraserHeight);
        Point dragPoint = me.getPoint();
        currentPath.add(dragPoint);
        gc.setColor(c);
        repaint();

    }

    public void mouseMoved(MouseEvent arg0) {
    }
}
