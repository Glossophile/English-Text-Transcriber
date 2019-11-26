package convert17;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Window;
import java.util.*;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
public class HomographHandler extends JPanel {
    private String homograph;
    private Font script = new Font("Times New Roman", Font.PLAIN, 17);
    private String[] homographs;
    private homSel selector = new homSel();
    public Control accept = new Control("ACCEPT");
    public String selection;
    public boolean selected = false;
    private JLabel log;
    private String instruct;
    public class homSel {
        JComboBox select;
        private Choices choice = new Choices();
        public void assignSel (String[] opts, Color fore) {
            String[]hetphons = opts;
            select = new JComboBox(hetphons);
            select.setFont(script);
            select.addActionListener(choice);
        }
    }
    public class Control {
        public JButton cntr;
        public String cmnd;
        private ControlHandler Trace = new ControlHandler();
        public Control (String command) {
            cmnd = command;
            cntr = new JButton(cmnd);
            cntr.addActionListener(Trace);
            cntr.setEnabled(true);
            cntr.setFont(script);
        }
    }    
    public class Choices implements ActionListener {
        public void actionPerformed (ActionEvent choose) {
            JComboBox choice = (JComboBox)choose.getSource();
            selection = (String)choice.getSelectedItem();
        }
    }
    public class ControlHandler implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent event) {
            close();
        }
    }
    private void close() {
        Window ancestor = SwingUtilities.getWindowAncestor(this);
        selected = true;
        ancestor.dispose();
    }
    public HomographHandler (String form, List<String> options) {
        homograph = form;
        homographs = new String[options.size()+1];
        homographs = options.toArray(homographs);
        if (options.size() == 2) {
            homographs[options.size()] = "BOTH";
        }
        else {
            homographs[options.size()] = "ALL";
        }
        selection = options.get(0);
        instruct = "Please choose a pronunciation for the word "+homograph.toUpperCase()+".";
        log = new JLabel(instruct);
        selector.assignSel(homographs,Color.YELLOW);
        log.setFont(script);
        log.setOpaque(false);
        log.setForeground(Color.YELLOW);
        log.setHorizontalAlignment(JLabel.CENTER);
        setLayout(new BorderLayout());
        add(log,BorderLayout.CENTER);
        JPanel action = new JPanel();
        action.setOpaque(false);
        action.add(selector.select);
        action.add(accept.cntr);
        add(action,BorderLayout.SOUTH);
        setBackground(ConvGUI.bg);
    }
}
