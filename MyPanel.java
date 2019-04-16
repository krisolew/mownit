import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MyPanel extends JPanel {

    private BufferedImage canvas;

    public MyPanel(){
        setPreferredSize(new Dimension(1000,1000));
        canvas = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
        fillCanvas(Color.WHITE);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);
    }

    public void fillCanvas(Color c) {
        int color = c.getRGB();
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }

    public void paintPixel(int x, int y, Color c){
        canvas.setRGB(x, y, c.getRGB());
        repaint();
    }

}
