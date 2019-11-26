package convert17;
import java.io.*;
import java.net.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.JFrame;
public class Monitor extends Thread{
    public static ConvGUI newwin = new ConvGUI();
    public boolean going = false;
    public boolean active = false;
    public void run() {
        newwin.setVisible(true);
        JFrame mainwin = new JFrame("Transcribe from TS to RLS, PSS, SF, KS, IPA, X-SAMPA, or IAST");
        mainwin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainwin.setSize(846, 820);
        mainwin.setContentPane(newwin);
        mainwin.setVisible(true);
        mainwin.setResizable(false);
        //editor.showEditor(new JFrame())
        try {
            while (true) {
                track(100);
            }
        }
        catch (InterruptedException interrupt) {
            //do nothing
        }
    }
    public void track (int res) throws InterruptedException {
        this.sleep(res);
        if (newwin.ready && !active) {
            //System.out.println("Testing...");
            //Convert17.begin();
        }
        going = newwin.ready && !newwin.paused;
        System.out.println(going);
    }
    public void start() {
        this.run();
    }
}
