import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInput extends JPanel implements ActionListener {

    JPanel Interface = new JPanel();
    Parameters param = new Parameters();
    FunctionWriter Writer = new FunctionWriter();
    FunctionDisplay Display = new FunctionDisplay();

    public void createPanel(){
        BorderLayout layout = new BorderLayout(10,10);
        Interface.setLayout(layout);
        Interface.setVisible(true);
        Interface.setMinimumSize(new Dimension(800,800));
        Interface.setBorder(BorderFactory.createBevelBorder(2,Color.GRAY,Color.LIGHT_GRAY));
    }

    public void createSubPanels(){

        Interface.add(param.GraphParameters,BorderLayout.WEST);
        Interface.add(Display.display,BorderLayout.CENTER);
        Interface.add(Writer.writer,BorderLayout.EAST);

    }

    public UserInput(){

        createPanel();
        createSubPanels();

    }

    public void actionPerformed(ActionEvent e) {

    }
}
