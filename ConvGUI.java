package convert17;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.awt.Insets;
import javax.swing.BorderFactory;
public class ConvGUI extends JPanel {
    public static Color bg = new Color((float)0.055,(float)0.298,(float)0.047);
    public static Color fg = new Color ((float)0.980,(float)0.941,(float)0.6);
    public static Font script = new Font("Times New Roman", Font.PLAIN, 20);
    private JTextArea trad = new JTextArea(12, 63);
    private JScrollPane inpane = new JScrollPane(trad);
    public OutPane output = new OutPane(script,fg);
    private DeCapCheck decap = new DeCapCheck();
    public StressCheck stress = new StressCheck();
    private BackingCheck backcheck = new BackingCheck();
    public boolean stressed;
    private boolean decapit;
    private boolean backing;
    private boolean interact;
    //private String transat = "HB";
    private TextParser transcrip;
    public static Dialect uk = new Dialect("RP",false,true,true,false,false,false,false,false,true,false,true,false,false,true,false);
    public static Dialect us = new Dialect("GA",true,true,false,true,true,true,true,true,true,true,false,true,true,false,true);
    public static Dialect hb = new Dialect("HB",true,true,false,false,false,false,false,false,true,true,false,false,false,false,true);
    public static List<Dialect> vars = new ArrayList<>();
    private Key american = new Key("GA","USA.png","Transcribe according to General American, the national standard accent of the United States.");
    private Key british = new Key("RP","UK.png","Transcribe according to Received Pronunciation, the national standard accent of the United Kingom.");
    private Key hybrid = new Key("HB","Anglo.png","Transcribe according to a roughly neutral hybrid of General American and Received Pronunciation.");
    private String[] schemes = {"RLS", "PSS", "M-PSS", "SF", "KS", "IPA", "X-SAMPA", "IAST"};
    public static Map<String,Integer> schmind = new TreeMap<>();
    private schmSel selection = new schmSel();
    public String scheme = "RLS";
    public String instruct = "All possible transcriptions of TS homographs are presented between '|' (e.g. \"read\" = \"|ríd|red|\").\nWords for which no transcriptions could be determined are presented between '}{' (e.g. \"}jabberwocky{\").\nIAST, an auxiliary Romanization system for Indian languages, has been modified for English by Ken P.\nThe diagonally spliced half-British/half-American flag represents a proposed international standard that\ncompromises between the national broadcast accents of the United States and United Kingdom.\nChecking \"Keep back A\" will select UK /ɑː/ in favor of US /æ/ in the compromise form.";
    public JTextArea log = new JTextArea(instruct);
    private Control toggle = new Control("STOP",false,"Stop the transcription.");
    private Control cancel = new Control("CANCEL",true,"Cancel the transcription.");
    private InteractCheck intercheck = new InteractCheck();
    public boolean ready = false;
    public boolean paused = false;
    private Dialect var = hb;
    public void InitButtons () {
        stress.AssignCheck("Mark stress",Color.YELLOW,"Include stress-marking diacritics.");
        decap.AssignCheck("No capital I",Color.YELLOW,"Treat the first-person singular subject pronoun like any other word for capitalization purposes.");
        backcheck.AssignCheck("Keep back A",Color.YELLOW,"Adhere to the British rather than the American distribution of /ɑː/ versus /æ/ in the hybrid accent.");
        intercheck.AssignInter("Interactive",Color.YELLOW,"Ask for user input on homographs and unknown words.");
        selection.assignSel(schemes,"Choose reformed spelling system or auxiliary alphabet.");
        schmind.put("IPA",0);
        schmind.put("X-SAMPA",0);
        schmind.put("IAST",0);
        schmind.put("RLS",1);
        schmind.put("PSS",2);
        schmind.put("M-PSS",3);
        schmind.put("SF",4);
        schmind.put("KS",5);
        vars.add(uk);
        vars.add(us);
        vars.add(hb);
        TranRLS.initialize();
        TranPSS.initialize();
        TranMPSS.initialize();
        TranSF.initialize();
        TranKS.initialize();
        Lingua.initialize();
    }
    public class ToolTip implements MouseListener {
        String info;
        String backup;
        @Override
        public void mouseEntered (MouseEvent hover) {
            if (hover.getSource() == this) {
                backup = Convert17.report;
                //Convert17.report = info;
                log.setText(info);
            }
        }
        public void mouseExited (MouseEvent exit) {
            if (exit.getSource() == this) {
                //Convert17.report = backup;
                log.setText(backup);
            }
        }
        public void setInfo (String tip) {
            info = tip;
        }
        public void mousePressed (MouseEvent press) {
            //fac nil
        }
        public void mouseReleased (MouseEvent release) {
            //fac nil
        }
        public void mouseClicked (MouseEvent click) {
            //fac nil
        }
    }
    public class Choices implements ActionListener {
        public void actionPerformed (ActionEvent choose) {
            JComboBox choice = (JComboBox)choose.getSource();
            scheme = (String)choice.getSelectedItem();
        }
    }
    public class Updater implements EventListener {
        public void Update (OutputUpdate change) {
            String ceinj = (change.getSource()).toString();
            (output.out).setText(ceinj);
        }
    }
    public class StressCheckListener implements ItemListener {
        @Override
        public void itemStateChanged (ItemEvent chev) {
            JCheckBox check = (JCheckBox)chev.getItemSelectable();
            if (check == stress.box) {
                if (chev.getStateChange() == ItemEvent.SELECTED) {
                    stressed = true;
                }
                else if (chev.getStateChange() == ItemEvent.DESELECTED) {
                    stressed = false;
                }
            }
        }
    }
    public class DeCapCheckListener implements ItemListener {
        @Override
        public void itemStateChanged (ItemEvent chev) {
            JCheckBox check = (JCheckBox)chev.getItemSelectable();
            if (check == decap.box) {
                if (chev.getStateChange() == ItemEvent.SELECTED) {
                    decapit = true;
                }
                else if (chev.getStateChange() == ItemEvent.DESELECTED) {
                    decapit = false;
                }
            }
        }
    }
    public class BackingCheckListener implements ItemListener {
        @Override
        public void itemStateChanged (ItemEvent chev) {
            JCheckBox check = (JCheckBox)chev.getItemSelectable();
            if (check == backcheck.box) {
                if (chev.getStateChange() == ItemEvent.SELECTED) {
                    backing = true;
                }
                else if (chev.getStateChange() == ItemEvent.DESELECTED) {
                    backing = false;
                }
            }
        }
    }
    public class InteractCheckListener implements ItemListener {
        @Override
        public void itemStateChanged (ItemEvent chev) {
            JCheckBox check = (JCheckBox)chev.getItemSelectable();
            if (check == intercheck.box) {
                if (chev.getStateChange() == ItemEvent.SELECTED) {
                    interact = true;
                }
                else if (chev.getStateChange() == ItemEvent.DESELECTED) {
                    interact = false;
                }
            }
        }
    }
    public class schmSel {
        JComboBox select;
        private Choices choice = new Choices();
        private ToolTip guide = new ToolTip();
        public void assignSel (String[] opts, String inform) {
            String[]schemes = opts;
            select = new JComboBox(schemes);
            select.setFont(script);
            select.addActionListener(choice);
            guide.setInfo(inform);
            //select.setOpaque(false);
        }
    }    
    public class StressCheck {
        JCheckBox box;
        private StressCheckListener listen = new StressCheckListener();
        private ToolTip guide = new ToolTip();
        public void AssignCheck (String des, Color fore, String inform) {
            box = new JCheckBox(des);
            box.setFont(script);
            box.addItemListener(listen);
            box.setOpaque(false);
            box.setForeground(fore);
            guide.setInfo(inform);
        }
    }
    public class DeCapCheck {
        JCheckBox box;
        private DeCapCheckListener listen = new DeCapCheckListener();
        private ToolTip guide = new ToolTip();
        public void AssignCheck (String des, Color fore, String inform) {
            box = new JCheckBox(des);
            box.setFont(script);
            box.addItemListener(listen);
            box.setOpaque(false);
            box.setForeground(fore);
            guide.setInfo(inform);
        }
    }
    public class BackingCheck {
        JCheckBox box;
        private BackingCheckListener listen = new BackingCheckListener();
        private ToolTip guide = new ToolTip();
        public void AssignCheck (String des, Color fore, String inform) {
            box = new JCheckBox(des);
            box.setFont(script);
            box.addItemListener(listen);
            box.setOpaque(false); 
            box.setForeground(fore);
            guide.setInfo(inform);
        }
    }
    public class InteractCheck {
        JCheckBox box;
        private InteractCheckListener listen = new InteractCheckListener();
        private ToolTip guide = new ToolTip();
        public void AssignInter (String des, Color fore, String inform) {
            box = new JCheckBox(des);
            box.setHorizontalAlignment(JCheckBox.CENTER);
            box.setFont(script);
            box.addItemListener(listen);
            box.setOpaque(false);
            box.setForeground(fore);
            guide.setInfo(inform);
        }
    }
    public Dialect findVar (String id) {
        Dialect variety = hb;
        ListIterator<Dialect> varit = vars.listIterator();
        while (varit.hasNext()) {
            Dialect vrt = varit.next();
            if ((vrt.getName()).equals(id)) {
                variety = vrt;
                break;
            }
        }
        return variety;
    }
    private String enter;
    public class KeyHandler implements ActionListener {
        String lbl;
        public KeyHandler (String str) {
           lbl = str;
        }
        @Override
        public void actionPerformed (ActionEvent event) {
            ready = true;
            var = findVar(lbl);
            var.setBackA(backing);
            //transat = var.getName();
            enter = trad.getText();
            if (decapit) {
                Convert17.report = "Preparing text with temporary substitutions for easier parsing.";
            }
        }
    }
    public class ControlHandler implements ActionListener {
        boolean cease = false;
        public void setTerm (boolean stop) {
            cease = stop;
        }
        @Override
        public void actionPerformed (ActionEvent event) {
            paused = !paused;
            if (cease) {
                ready = false;
            }
        }
    }
    public class OutPane extends JPanel {
        public JTextArea out = new JTextArea(12, 63);
        public JScrollPane pane = new JScrollPane(out);
        public Updater update = new Updater();
        public OutPane(Font scrip, Color backgr) {
            out.setFont(scrip);
            out.setLineWrap(true);
            out.setWrapStyleWord(true);
            out.setBackground(backgr);
        }
    }
    public class Key {
        public JButton btn;
        public String symbol;
        private KeyHandler Track;
        private ToolTip guide = new ToolTip();
        public Key (String charac, String icon, String inform) {
            symbol = charac;
            Track = new ConvGUI.KeyHandler(symbol);
            String roll = icon.substring(0,icon.length()-4)+"2"+icon.substring(icon.length()-4);
            String press = icon.substring(0,icon.length()-4)+"3"+icon.substring(icon.length()-4);
            btn = new JButton(new ImageIcon(icon));
            btn.setBorder(BorderFactory.createEmptyBorder());
            btn.setContentAreaFilled(false);
            btn.setRolloverIcon(new ImageIcon(roll));
            btn.setPressedIcon(new ImageIcon(press));
            btn.setRolloverEnabled(true);
            btn.addActionListener(Track);
            btn.setFont(script);
            guide.setInfo(inform);
        }
    }
    public class Control {
        public JButton cntr;
        public String cmnd;
        private ControlHandler Trace = new ControlHandler();
        private ToolTip guide = new ToolTip();
        public Control (String command, boolean end, String inform) {
            cmnd = command;
            cntr = new JButton(cmnd);
            Trace.setTerm(end);
            guide.setInfo(inform);
            cntr.addActionListener(Trace);
            cntr.setEnabled(false);
            cntr.setFont(script);
        }
    }
    public void disableKeys () {
        (american.btn).setEnabled(false);
        (british.btn).setEnabled(false);
        (hybrid.btn).setEnabled(false);
    }
    public void enableKeys () {
        (american.btn).setEnabled(true);
        (british.btn).setEnabled(true);
        (hybrid.btn).setEnabled(true);
            
    }
    public void enableContr () {
        (toggle.cntr).setEnabled(true);
        (cancel.cntr).setEnabled(true);
    }
    public void disableContr () {
        (toggle.cntr).setEnabled(false);
        (cancel.cntr).setEnabled(false);
    }
    public void switchContr (boolean stopped) {
        if (stopped) {
            (toggle.cntr).setText("GO");
        }
        else {
            (toggle.cntr).setText("STOP");
        }
    }
    public ConvGUI () {
        InitButtons();
        trad.setFont(script);
        trad.setLineWrap(true);
        trad.setWrapStyleWord(true);
        trad.setBackground(fg);
        setLayout(new FlowLayout());
        log.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        log.setMargin(new Insets(10,10,10,10));
        log.setForeground(Color.WHITE);
        log.setBackground(Color.BLACK);
        log.setBorder(BorderFactory.createLineBorder(Color.yellow));
        //btns.add(cancel.cntr);
        add(inpane);
        JPanel console = new JPanel();
        console.setLayout(new FlowLayout());
        console.add(american.btn);
        console.add(british.btn);
        console.add(hybrid.btn);
        console.setSize(300,900);
        //add(toggle.cntr);
        console.add(cancel.cntr);
        console.add(stress.box);
        console.add(decap.box);
        console.add(backcheck.box);
        console.add(selection.select);
        add(console);
        output.setBackground(fg);
        add(output.pane);
        JPanel feed = new JPanel();
        feed.setLayout(new BorderLayout());
        feed.add(intercheck.box,BorderLayout.CENTER);
        feed.add(log,BorderLayout.SOUTH);
        add(feed);
        trad.setText("Transcribe me!");
        feed.setBackground(bg);
        console.setBackground(bg);
        setBackground(bg);
    }
    public TextParser setup () {
            Morphology.initialize();
            int code = schmind.get(scheme);
            transcrip = new TextParser(enter, var, stressed, code, decapit, interact);
            transcrip.setPriority(Thread.MIN_PRIORITY);
            return transcrip;
    }
}