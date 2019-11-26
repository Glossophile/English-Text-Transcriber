package convert17;
import java.awt.Font;
import java.awt.Window;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
public class CapLine extends JPanel {
    private Font script = new Font("Times New Roman", Font.PLAIN, 17);
    private JLabel instruct;
    public boolean capt = false;
    private Control yes = new Control("YES",true);
    private Control no = new Control("NO",false);
    public boolean ready = false;
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
    private void close() {
        Window ancestor = SwingUtilities.getWindowAncestor(this);
        ancestor.dispose();
    }
    public class ControlHandler implements ActionListener {
        private boolean proc;
        public ControlHandler (boolean affirm) {
            proc = affirm;
        }
        @Override
        public void actionPerformed (ActionEvent event) {
            if (proc) {
                capt = true;
            }
            else {
                capt = false;
            }
            ready = true;
            close();
        }
    }
    public CapLine () {
        instruct = new JLabel("Should every line begin with a capital letter?");
        setLayout(new BorderLayout());
        instruct.setFont(script);
        instruct.setOpaque(false);
        instruct.setForeground(Color.YELLOW);
        instruct.setHorizontalAlignment(JLabel.CENTER);
        add(instruct,BorderLayout.CENTER);
        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.add(yes.cntr);
        buttons.add(no.cntr);
        add(buttons,BorderLayout.SOUTH);
        setBackground(ConvGUI.bg);
    }
}
