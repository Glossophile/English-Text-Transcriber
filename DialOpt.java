package convert17;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.*;
import java.util.EventListener;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
public class DialOpt extends JPanel{
    public boolean selected = false;
    private Dialect accent = new Dialect("Default",true,true,true,false,false,false,false,false,true,true,false,false,false,false,false);;
    private JButton select = new JButton("SELECT");
    private static Map<String,Integer> features = new TreeMap<>();
    private Font script = new Font("Times New Roman", Font.PLAIN, 20);
    public class OptionCheck {
        private JCheckBox box;
        private OptionCheckListener listen;
        public OptionCheck (String des) {
            box = new JCheckBox(des);
            box.setFont(script);
            listen = new OptionCheckListener();
            box.addItemListener(listen);
        }
    }
    public class OptionCheckListener implements ItemListener {
        @Override
        public void itemStateChanged (ItemEvent chev) {
            JCheckBox check = (JCheckBox)chev.getItemSelectable();
            String indic = check.getText();
            if (chev.getStateChange() == ItemEvent.SELECTED) {
                collect(features.get(indic),true);
            }
            else if (chev.getStateChange() == ItemEvent.DESELECTED) {
                collect(features.get(indic),true);
            }
            
        }
    }
    public class BtnHandler implements ActionListener {
        String lbl;
        @Override
        public void actionPerformed (ActionEvent event) {
            selected = true;
        }
    }
    public void collect (int idnm, boolean set) {
        switch (idnm) {
            case 1: accent.setRhotic(set);
            case 2: accent.setSyncope(set);
            case 3: accent.setClip(set);
            case 4: accent.setMergeFB(set);
            case 5: accent.setMergeCC(set);
            case 6: accent.setMergeHF(set);
            case 7: accent.setMergeMM(set);
            case 8: accent.setRaiseAsh(set);
            case 9: accent.setDropYod(set);
            case 10: accent.setDropYur(set);
            case 11: accent.setWhine(set);
            case 12: accent.setWunt(set);
            case 13: accent.setGlide(set);
            case 14: accent.setBackA(set);
            case 15: accent.setFlap(set);
        }
    }
    public DialOpt () {
        setLayout(new FlowLayout());
        add(rhotic.box);
        add(syncope.box);
        add(clip.box);
        add(mergeFB.box);
        add(mergeCC.box);
        add(mergeHF.box);
        add(mergeMM.box);
        add(raiseAsh.box);
        add(dropYod.box);
        add(dropYur.box);
        add(wHine.box);
        add(wunt.box);
        add(glide.box);
        add(backA.box);
        add(flapping.box);
        features.put("Rhotic",1);
        features.put("Syncope",2);
        features.put("Clipping",3);
        features.put("Father-Bother Merger",4);
        features.put("Cot-Caught Merger",5);
        features.put("Hurry-Furry Merger",6);
        features.put("Mary-Merry Merger",7);
        features.put("Marry-Merry Merger",8);
        features.put("Wine-Whine Merger",9);
        features.put("Post-W Unrounding",10);
        features.put("Yod-Dropping",11);
        features.put("Figure-Digger Merger",12);
        features.put("Gliding",13);
        features.put("Keep Back A",14);
        features.put("Flapping",15);
        select.addActionListener(new BtnHandler());
        add(select);
    }
    private OptionCheck rhotic = new OptionCheck("Rhotic");
    private OptionCheck syncope = new OptionCheck("Syncope");
    private OptionCheck clip = new OptionCheck("Clipping");
    private OptionCheck mergeFB = new OptionCheck("Father-Bother Merger");
    private OptionCheck mergeCC = new OptionCheck("Cot-Caught Merger");
    private OptionCheck mergeHF = new OptionCheck("Hurry-Furry Merger");
    private OptionCheck mergeMM = new OptionCheck("Mary-Merry Merger");
    private OptionCheck raiseAsh = new OptionCheck("Marry-Merry Merger");
    private OptionCheck wHine = new OptionCheck("Wine-Whine Merger");
    private OptionCheck wunt = new OptionCheck("Post-W Unrounding");
    private OptionCheck dropYod = new OptionCheck("Yod-Dropping");
    private OptionCheck dropYur = new OptionCheck("Figure-Digger Merger");
    private OptionCheck glide = new OptionCheck("Gliding");
    private OptionCheck backA = new OptionCheck("Keep Back A");
    private OptionCheck flapping = new OptionCheck("Flapping");
    public Dialect getDialect() {
        //System.out.println(accent.printFtrs());
        return accent;
    }
}