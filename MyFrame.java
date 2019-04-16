import javax.swing.*;
import java.awt.*;

import static java.lang.Math.*;

public class MyFrame extends JFrame {

    MyPanel panel;
    double [] solutionsX = new double[5];
    double [] solutionsY = new double[5];


    public MyFrame(){
        setPreferredSize(new Dimension(1000,1000));

        panel = new MyPanel();
        add(panel);

        solutionsX[0] = cos(-3*PI/5);
        solutionsX[1] = cos(-PI / 5);
        solutionsX[2] = cos(PI / 5);
        solutionsX[3] = cos(3*PI/5);
        solutionsX[4] = -1;

        solutionsY[0] = sin(-3 * PI / 5);
        solutionsY[1] = sin(-PI / 5);
        solutionsY[2] = sin(PI / 5);
        solutionsY[3] = sin(3 * PI / 5);
        solutionsY[4] = 0;


        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Thread thread = new Thread(() -> count());
        thread.start();
    }

    public double g(double x, double y){
        return pow(x,5.0) - (10 * pow(x,3.0) * pow(y,2.0)) + (5 * x * pow(y,4.0)) + 1;
    }

    public double dgx(double x, double y){
        return (5 * pow(x,4.0)) - (30 * pow(x,2.0) * pow(y,2.0)) + (5 * pow(y,4.0));
    }

    public double dgy(double x, double y){
        return (-20 * pow(x,3.0) * y) + (20 * x * pow(y,3.0));
    }

    public double h(double x, double y){
        return (5 * pow(x,4.0) * y) - (10 * pow(x,2.0) * pow(y,3.0)) + pow(y,5.0);
    }

    public double dhx(double x, double y){
        return (20 * pow(x,3.0) * y) - (20 * x * pow(y,3.0));
    }

    public double dhy(double x, double y){
        return (5 * pow(x,4.0)) - (30 * pow(x,2.0) * pow(y,2.0)) + (5 * pow(y,4.0));
    }

    public double xn (double x, double y){
        return x - (((g(x,y)*dhy(x,y))-(h(x,y)*dgy(x,y)))/((dgx(x,y)*dhy(x,y))-(dhx(x,y)*dgy(x,y))));
    }

    public double yn (double x, double y){
        return y - (((dgx(x,y)*h(x,y))-(dhx(x,y)*g(x,y)))/((dgx(x,y)*dhy(x,y))-(dhx(x,y)*dgy(x,y))));
    }

    public void count() {
        final int maxn = 200;

        double x, y, prevx, prevy;

        for (int i=0;i<1000;i++) {

            for (int j = 1000; j > 0; j--) {
                x = (i-500)*0.002;
                y = (j-500)*0.002;
                int k = 0;
                while (k < maxn) {
                    prevx = x;
                    prevy = y;
                    x = xn(prevx,prevy);
                    y = yn(prevx, prevy);
                    k++;

                    if (abs(x-prevx) + abs(y-prevy) < 10e-5 && abs(pow(x+y,5)+1) < 10e-4) break;
                }

                int color=6;
                double difference = 1.0;
                for (int l = 0; l <5; l++) {
                    if (abs(x-solutionsX[l]) + abs(y-solutionsY[l]) < difference) {
                        color=l;
                        difference = abs(x-solutionsX[l]) + abs(y-solutionsY[l]);
                    }
                }
                if (difference < 1e-4) chooseColor(i,1000-j,color);
                else chooseColor(i,1000-j,6);
            }
        }
    }


    public void solve(int i,int j){

    }

    public void chooseColor(int x,int y, int l){
        Color c = Color.white;
        if (l==0) c = Color.BLUE;
        if (l==1) c = Color.RED;
        if (l==2) c = Color.GREEN;
        if (l==3) c = Color.YELLOW;
        if (l==4) c = Color.BLACK;
        if (l==5) c = Color.white;

        paintPixel(x,y,c);
    }

    public void paintPixel(int x, int y, Color c){
        Thread thread = new Thread(() -> panel.paintPixel(x,y,c) );
        thread.start();
    }
}
