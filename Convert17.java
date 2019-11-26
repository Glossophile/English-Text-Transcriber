package convert17;
import java.util.*;
import java.awt.Color;
import javax.swing.JFrame;
public class Convert17 extends Thread {
    //public static String org;
    public static ConvGUI newwin = new ConvGUI();
    //public static DialOpt options = new DialOpt();
    private static boolean going = false;
    private static boolean active = false;
    private static boolean status = false;
    private static boolean selstat  = false;
    public static String converted = "";
    public static String report = newwin.instruct;
    public TextParser conversion;
    public boolean strsschm = false;
    public boolean stresson = false;
    public static Color bg = new Color((float)0.031373,(float)0.454902,(float)0.101961);
    public static Color fg = new Color ((float)0.941176,(float)0.929412,(float)0.615686);
    public static void main (String args[]) {
        newwin.setVisible(true);
        //options.setVisible(true);
        JFrame mainwin = new JFrame("Transcribe from TS to RLS, PSS, MPSS, SF, KS, IPA, X-SAMPA, or IAST");
        mainwin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainwin.setSize(920, 900);
        mainwin.setContentPane(newwin);
        mainwin.setVisible(true);
        mainwin.setResizable(false);
        Convert17 converter = new Convert17();
        converter.setPriority(Thread.MAX_PRIORITY);
        converter.start();
        //showOptions();
    }
    /*public static void showOptions () {
        JFrame optwin = new JFrame("Dialect Options");
        optwin.setSize(423, 205);
        optwin.setContentPane(options);
        optwin.setVisible(true);
        optwin.setResizable(false);
    }*/
    public void run () {
    //public static void main (String args[]) {
        Lexicon dictionary = new Lexicon(true);
        List<Lemma> lexic = dictionary.getLex(); 
        Morphology.initialize();
        Morphology.dict = dictionary;
        Morphology.dictionary = lexic;
        Phonology.initXSMap();
        Phonology.initIASTMap();
        Phonology.initRecMap();
        Phonology.initRecLongMap();
        /*newmon.setPriority(Thread.MAX_PRIORITY);
        newmon.start();*/
        try {
            while (true) {
                track(30);
            }
        }
        catch (InterruptedException interrupt) {
            //do nothing
        }
    }
    public void track (int res) throws InterruptedException {
        this.sleep(res);
        going = newwin.ready && !newwin.paused;
        if (newwin.ready && !active) {
            active = true;
            conversion = newwin.setup();
            conversion.start();
            newwin.disableKeys();
            newwin.enableContr();
        }
        if (going || !going && status) {
            (newwin.log).setText(report);
            ((newwin.output).out).setText(converted);
        }
        if (going != status) {
            status = going;
            newwin.switchContr(!going);
            conversion.paused = newwin.paused;
            (newwin.log).setText(report);
            ((newwin.output).out).setText(converted);
        }
        if (!newwin.ready && active) {
            active = false;
            conversion = null;
            newwin.disableContr();
            newwin.enableKeys();
        }
        stresson = newwin.stressed;
        if ((newwin.scheme).equals("M-PSS") && !strsschm) {
            ((newwin.stress).box).setSelected(true);
            newwin.stressed = true;
            stresson = true;
            strsschm = true;
        }
        else if (!(newwin.scheme).equals("M-PSS") && strsschm) {
            strsschm = false;
        }
        /*if (options.selected && !selstat) {
            selstat = true;
            Dialect variety = options.getDialect();
            options.selected = false;
        }*/
    }
    public static void finish (String transcrip1, String transcrip2) {
        //disableButtons();
        String spelling = "";
        if (newwin.scheme.equals("IPA")) {
            spelling = "/"+Phonology.prepIPA(transcrip1, newwin.stressed)+"/";
        }
        else if (newwin.scheme.equals("X-SAMPA")) {
            String trans = Phonology.prepIPA(transcrip1, newwin.stressed);
            trans = (trans.replaceAll("d͡ʒ","dʒ")).replaceAll("t͡ʃ", "tʃ");
            spelling = "/"+Util.convert(trans, Phonology.sampa)+"/";
        }
        else if (newwin.scheme.equals("IAST")) {
            String trans = Phonology.prepIPA(transcrip1, newwin.stressed);
            trans = Phonology.prepIAST(trans);
            spelling = "/"+Util.convert(trans, Phonology.iast)+"/";
        }
        else {
            spelling = transcrip2;
        }
        active = false;
        newwin.ready = false;
        newwin.paused = false;
        converted = spelling;
        report = newwin.instruct;
        newwin.disableContr();
        newwin.enableKeys();
    }
}