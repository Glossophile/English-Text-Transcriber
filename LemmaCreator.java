package convert17;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Window;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
public class LemmaCreator extends JPanel {
    private JLabel instruct = new JLabel("This word could not be found or parsed.");
    private Font script = new Font("Times New Roman", Font.PLAIN, 17);
    private JLabel lab1 = new JLabel("Spelling");
    public JTextField written = new JTextField(12);
    private JLabel lab2 = new JLabel("Pronunciation (UK)");
    private JTextField britprn = new JTextField(12);;
    private JLabel lab3 = new JLabel("Pronunciation (US)");
    private JTextField amprn = new JTextField(12);;
    private JLabel lab4 = new JLabel("Grammatical Category");
    private String[] pos = {"noun", "verb", "adjective", "adverb","substantive"};
    private JLabel lab5 = new JLabel("Irregularity");
    private String[] alts = {"NONE","Voicing Back-Assimilation", "Laxing/R-Stem","Penultimate Clipping","Inflectional Ablaut"};
    //private JTextField irreg = new JTextField(2);
    private IrregSel marker = new IrregSel(alts);
    private String part = "noun";
    private int morphirr = 0;
    private PartSel selector = new PartSel(pos);
    private JLabel lab6 = new JLabel("Compound Head");
    public JTextField thyg1 = new JTextField(8);;
    private JLabel lab7 = new JLabel("Compound Non-Head");
    public JTextField thyg2 = new JTextField(8);;
    public boolean full = false;
    public Lemma addition;
    private Control accept = new Control("OKAY",true);
    private Control deny = new Control("SKIP",false);
    private SaveCheck saver = new SaveCheck("Save to external lexicon",Color.YELLOW);
    private ExemptCheck exemptor = new ExemptCheck("Exempt",Color.YELLOW);
    public boolean save = false;
    public boolean done = false;
    public boolean exempt = false;
    public String help = "Please input pronunciations in X-SAMPA or IPA with syllable boundaries and stress marks.\nIf UK and US forms are identical, one need only fill the UK field.\nIf the word is not a compound, leave compound fields blank.\nIRREGULARITIES:\nVoicing Back-Assimilation: (e.g. house/houses, wolf/wolves)\nLaxing/R-Stem: (e.g. child/children)\nPenultimate Clipping: (e.g. \"secretary\" with silent 'a')\nInflectional Ablaut: (e.g. man/men, foot/feet)";
    //private JTextArea guide = new JTextArea(help);
    /*1 = voicing assimilation (affix)
    2 = /ɪ/-epenthesis (affix)
    3 = backwards voicing assimilation
    4 = /g/-addition (affix)
    5 = final /iː/-laxing (affix)
    6 = British /ɪə˞/  American /ə˞/
    7 = /ə/-syncope (affix)
    8 = British /ɒ/  American /ʌ/
    9 = British /iː/  American /iː/
    10 = British /ɑː/  American /æ/
    11 = epenthetic /ə/ (affix)
    12 = plural laxing /aɪ/  /ɪ/
    13 = American /jə˞/  British /ə/
    14 = British /ɛ/  American /ɪ/
    15 = root syncope/clipping
    16 = inflectional ablaut*/
    public static String transXSAMPA (String samp) {
        String inphal = samp;
        inphal = inphal.replaceAll("g","ɡ");
        inphal = inphal.replaceAll("r\\\\","ɹ");
        inphal = inphal.replaceAll("A`","ɑ˞");
        inphal = inphal.replaceAll("3`","ɜ˞");
        inphal = inphal.replaceAll("O`","ɔ˞");
        inphal = inphal.replaceAll("@`","ə˞");
        inphal = inphal.replaceAll("tS","t͡ʃ");
        inphal = inphal.replaceAll("dZ","d͡ʒ");
        inphal = inphal.replaceAll("V","ʌ");
        inphal = inphal.replaceAll("E","ɛ");
        inphal = inphal.replaceAll("I","ɪ");
        inphal = inphal.replaceAll("Q","ɒ");
        inphal = inphal.replaceAll("U","ʊ");
        inphal = inphal.replaceAll("\\{","æ");
        inphal = inphal.replaceAll("@","ə");
        inphal = inphal.replaceAll("A","ɑ");
        inphal = inphal.replaceAll("3","ɜː");
        inphal = inphal.replaceAll("O","ɔ");
        inphal = inphal.replaceAll("S","ʃ");
        inphal = inphal.replaceAll("Z","ʒ");
        inphal = inphal.replaceAll("T","θ");
        inphal = inphal.replaceAll("D","ð");
        inphal = inphal.replaceAll(":","ː");
        inphal = inphal.replaceAll("\"","ˈ");
        inphal = inphal.replaceAll("\'","ˌ");
        return inphal;
    }
    public class PartSel {
        JComboBox select;
        private Parts part = new Parts();
        public PartSel (String[] opts) {
            select = new JComboBox(opts);
            select.setFont(script);
            select.addActionListener(part);
        }
    }
    public class Parts implements ActionListener {
        public void actionPerformed (ActionEvent choose) {
            JComboBox choice = (JComboBox)choose.getSource();
            part = (String)choice.getSelectedItem();
        }
    }
    public class IrregSel {
        JComboBox sel;
        private Irregs irr = new Irregs();
        public IrregSel (String[] opts) {
            sel = new JComboBox(opts);
            sel.setFont(script);
            sel.addActionListener(irr);
        }
    }
    public class Irregs implements ActionListener {
        public void actionPerformed (ActionEvent choose) {
            JComboBox choice = (JComboBox)choose.getSource();
            String desc = (String)choice.getSelectedItem();
            if (desc.equals("Voicing Back-Assimilation")) {
                morphirr = 3;
            }
            else if (desc.equals("Laxing/R-Stem")) {
                morphirr = 12;
            }
            else if (desc.equals("Penultimate Clipping")) {
                morphirr = 15;
            }
            else if (desc.equals("Inflectional Ablaut")) {
                morphirr = 16;
            }
            else {
                morphirr = 0;
            }
        }
    }
    public class Control {
        public JButton cntr;
        public String cmnd;
        public Control (String command, boolean go) {
            cmnd = command;
            cntr = new JButton(cmnd);
            cntr.addActionListener(new ControlHandler(go));
            cntr.setEnabled(true);
            cntr.setFont(script);
        }
    }
    public class SaveCheck {
        JCheckBox box;
        private SaveCheckListener listen = new SaveCheckListener();
        public SaveCheck (String des, Color fore) {
            box = new JCheckBox(des);
            box.setHorizontalAlignment(JCheckBox.CENTER);
            box.setFont(script);
            box.addItemListener(listen);
            box.setOpaque(false);
            box.setForeground(fore);
        }
    }
    public class ExemptCheck {
        JCheckBox box;
        private ExemptCheckListener listen = new ExemptCheckListener();
        public ExemptCheck (String des, Color fore) {
            box = new JCheckBox(des);
            box.setHorizontalAlignment(JCheckBox.CENTER);
            box.setFont(script);
            box.addItemListener(listen);
            box.setOpaque(false);
            box.setForeground(fore);
        }
    }
    public class ControlHandler implements ActionListener {
        private boolean proc;
        public ControlHandler (boolean go) {
            proc = go;
        }
        @Override
        public void actionPerformed (ActionEvent event) {
            if (proc) {
                String phono1 = transXSAMPA(britprn.getText());
                String phono2 = transXSAMPA(amprn.getText());
                britprn.setText(phono1);
                if (phono2.equals("")) {
                    amprn.setText(phono1);
                }
                else {
                    amprn.setText(phono2);
                }
                String heading = (written.getText()).replaceAll("\'","~");
                heading = heading.toLowerCase();
                addition = new Lemma(heading,phono1,amprn.getText(),Lemma.encode(part),morphirr);
                full = true;
            }
            else {
                full = false;
            }
            done = true;
            close();
        }
    }
    public class SaveCheckListener implements ItemListener {
        @Override
        public void itemStateChanged (ItemEvent chev) {
            JCheckBox check = (JCheckBox)chev.getItemSelectable();
            if (check == saver.box) {
                if (chev.getStateChange() == ItemEvent.SELECTED) {
                    save = true;
                }
                else if (chev.getStateChange() == ItemEvent.DESELECTED) {
                    save = false;
                }
            }
        }
    }
    public class ExemptCheckListener implements ItemListener {
        @Override
        public void itemStateChanged (ItemEvent chev) {
            JCheckBox check = (JCheckBox)chev.getItemSelectable();
            if (check == exemptor.box) {
                if (chev.getStateChange() == ItemEvent.SELECTED) {
                    exempt = true;
                }
                else if (chev.getStateChange() == ItemEvent.DESELECTED) {
                    exempt = false;
                }
            }
        }
    }
    private void close() {
        Window ancestor = SwingUtilities.getWindowAncestor(this);
        ancestor.dispose();
    }
    public LemmaCreator(String unknown) {
        instruct.setFont(script);
        instruct.setForeground(Color.YELLOW);
        instruct.setOpaque(false);
        written.setText(unknown);
        written.setFont(script);
        britprn.setFont(script);
        amprn.setFont(script);
        /*irreg.setText("0");
        irreg.setFont(script);*/
        thyg1.setFont(script);
        thyg2.setFont(script);
        lab1.setFont(script);
        lab1.setForeground(Color.YELLOW);
        lab1.setOpaque(false);
        lab2.setFont(script);
        lab2.setForeground(Color.YELLOW);
        lab2.setOpaque(false);
        lab3.setFont(script);
        lab3.setForeground(Color.YELLOW);
        lab3.setOpaque(false);
        lab4.setFont(script);
        lab4.setForeground(Color.YELLOW);
        lab4.setOpaque(false);
        lab5.setFont(script);
        lab5.setForeground(Color.YELLOW);
        lab5.setOpaque(false);
        lab6.setFont(script);
        lab6.setForeground(Color.YELLOW);
        lab6.setOpaque(false);
        lab7.setFont(script);
        lab7.setForeground(Color.YELLOW);
        lab7.setOpaque(false);
        //guide.setFont(script);
        JPanel writ = new JPanel();
        writ.add(lab1);
        writ.add(written);
        writ.setOpaque(false);
        JPanel proUK = new JPanel();
        proUK.add(lab2);
        proUK.add(britprn);
        proUK.setOpaque(false);
        JPanel proUS = new JPanel();
        proUS.add(lab3);
        proUS.add(amprn);
        proUS.setOpaque(false);
        JPanel posch = new JPanel();
        posch.add(lab4);
        posch.add(selector.select);
        posch.setOpaque(false);
        JPanel alt = new JPanel();
        alt.add(lab5);
        //alt.add(irreg);
        alt.add(marker.sel);
        alt.setOpaque(false);
        JPanel hija1 = new JPanel();
        hija1.add(lab6);
        hija1.add(thyg1);
        hija1.setOpaque(false);
        JPanel hija2 = new JPanel();
        hija2.add(lab7);
        hija2.add(thyg2);
        hija2.setOpaque(false);
        JPanel cont = new JPanel();
        JPanel checks = new JPanel();
        cont.setLayout(new FlowLayout());
        cont.setOpaque(false);
        checks.setLayout(new FlowLayout());
        cont.add(accept.cntr);
        cont.add(deny.cntr);
        checks.add(saver.box);
        checks.add(exemptor.box);
        checks.setOpaque(false);
        setLayout(new FlowLayout());
        add(instruct);
        add(writ);
        add(proUK);
        add(proUS);
        add(posch);
        add(alt);
        add(hija2);
        add(hija1);
        add(cont);
        add(checks);
        this.setBackground(ConvGUI.bg);
        //add(guide);
    }
}
