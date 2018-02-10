/* <applet code = "StopWatch.class" height="300" width="300"></applet> */

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by matt on 2/8/2018.
 */
public class StopWatch extends Applet implements Runnable, ActionListener {
    int theta;//var to draw lines around clock face
    double r;//var for center point of clock face
    double sec, min, hr;//var for clock hands
    int x1, y1, x2, y2; //coordinates for lines to be drawn

    Thread clockThread;
    boolean running = false;//bool to start/stop clock

    public void run() {
        while (running) {
            try {
                clockThread.sleep(1000);//sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sec += 6;//move 6 degrees per second
            min += 6.0 / 60;//move minute 6 degrees per 60 seconds
            hr += 30.0 / 3600;//move 30 degrees per 3600 seconds
            repaint();
        }
    }

    Button start, stop, reset;

    public void init() {
        //start all hands at 0
        sec = 270;
        min = 270;
        hr = 270;

        //add buttons to control clock
        Button start = new Button("Start");
        add(start);
        start.addActionListener(this);

        Button stop = new Button("Stop");
        add(stop);
        stop.addActionListener(this);

        Button reset = new Button("Reset");
        add(reset);
        reset.addActionListener(this);

    }

    public void destroy() {
        running = false;//stop running
        clockThread = null;//end thread
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String sString = e.getActionCommand();//determines which button was pressed
        if (sString.equals("Start")) {
            //prevents user from increasing movement speed with repeated start clicks
            if (running) {
                return;
            }
            //create thread and start clock
            clockThread = new Thread(this);
            running = true;
            clockThread.start();
        } else if(sString.equals("Reset")){
            //reset the hands to 0
            sec = 270;
            min = 270;
            hr = 270;
            repaint();
        } else if (sString.equals("Stop")) {
            destroy();//destroy thread
        }
    }

    public void paint(Graphics g) {
        g.drawOval(50, 50, 200, 200);//draw circle for clock
        theta = 0;

        //draw each hour marker on watch face
        while (theta <= 360) {
            r = Math.toRadians(theta);
            x1 = (int) (100 * Math.cos(r));
            y1 = (int) (100 * Math.sin(r));
            x2 = (int) (80 * Math.cos(r));
            y2 = (int) (80 * Math.sin(r));

            g.drawLine(150 + x1, 150 + y1, 150 + x2, 150 + y2);
            theta += 30;
        }

        theta = 0;//reset val to draw minute markers

        //draw each minute marker on watch face
        while (theta <= 360) {
            r = Math.toRadians(theta);
            x1 = (int) (100 * Math.cos(r));
            y1 = (int) (100 * Math.sin(r));
            x2 = (int) (95 * Math.cos(r));
            y2 = (int) (95 * Math.sin(r));

            g.drawLine(150 + x1, 150 + y1, 150 + x2, 150 + y2);
            theta += 6;
        }

        //draw the hour, minute and second hands
        g.drawLine(150, 150, 150 + (int) (85 * Math.cos(Math.toRadians(sec))), 150 +
                (int) (85 * Math.sin(Math.toRadians(sec))));
        g.drawLine(150, 150, 150 + (int) (75 * Math.cos(Math.toRadians(min))), 150 +
                (int) (75 * Math.sin(Math.toRadians(min))));
        g.drawLine(150, 150, 150 + (int) (65 * Math.cos(Math.toRadians(hr))), 150 +
                (int) (65 * Math.sin(Math.toRadians(hr))));
    }

}