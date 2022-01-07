package com.edensheiko.game;

import com.sun.security.jgss.GSSUtil;
import kotlin.reflect.jvm.internal.impl.util.collectionUtils.ScopeUtilsKt;
import com.edensheiko.game.grahics.Screen;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.security.spec.RSAOtherPrimeInfo;

public class Game extends Canvas implements Runnable{
    private static final long serialVersionUID = 1L; // אופצנאלי
    public static int width=300;
    public static int height =width / 16*9;
    public static int scale = 3;

    private  Thread thread;
    private JFrame frame;
    private boolean running = false;
    private Screen screen;
    private BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData(); // convert image to array of ints

    public Game(){
           Dimension size = new Dimension(width*scale,height*scale);
           setPreferredSize(size);
           screen=new Screen(width,height);

           frame = new JFrame();

    }
    // thread sub pros for game
    /*
    1)  a .join(); refers to the fact that the thread will close or die after it's function is over

    2)   There was an error that occurred w/ this syntax, and so they refer to it as an Exception.

    3) In order to bypass this, we must use  "catch", but catch always comes w/ a try loop. So we try to catch the Exception, and if we fail, that the syntax = "e.print stackTrace"
     */
    public synchronized void start()
    {
        running=true;
        thread=new Thread(this,"Display");
        thread.start();
    }
    public  synchronized  void stop()
    {
        running=false;
        try {
            thread.join();
        } catch (InterruptedException e ) {
            e.printStackTrace();
        }
        // to add some print ( if game will be crashed)
    }
    public void run()
    {
        while(running)
        {
            //  System.out.println("Running..."); //Debug
            tick();
            render();
        }

    }
    public void tick()
    {

    }
    public void render() // buffer
    {
        BufferStrategy bs = getBufferStrategy();
        if(bs==null)
        {
            createBufferStrategy(3); // 3 buffers -> more speed brr brr
            return;
        }
        Graphics g = bs.getDrawGraphics(); //link between Graphics and data
        g.setColor(Color.BLACK); // can be loaded with RGB

            // any Graphics need to be here.


        g.fillRect(0,0,getWidth(),getHeight());
        g.dispose();
        bs.show();
    }


    public static void main(String[] args){
        Game game = new Game();
        game.frame.setResizable(false); // if true might be lots of bugs
        game.frame.setTitle("Sun");
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        game.start();
    }
}
