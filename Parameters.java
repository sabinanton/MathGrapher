import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Parameters extends JPanel implements ActionListener {

    JPanel GraphParameters = new JPanel();
    JLabel rangeX = new JLabel("X-Range:");
    JLabel rangeY = new JLabel("Y-Range:");
    JLabel samples = new JLabel("Samples:");
    JTextField MRangeX = new JTextField(2);
    JTextField MRangeY = new JTextField(2);
    JTextField Samples = new JTextField(2);
    String copyMRangeX = "10.0";
    String copyMRangeY = "10.0";
    String copySamples = "100";

    void createPanel(){

        Timer timer = new Timer(30,this);
        timer.start();
        GridLayout grid = new GridLayout(3,3,10,10);
        rangeX.setFont(new Font("Arial",Font.PLAIN,20));
        MRangeX.setFont(new Font("Arial",Font.PLAIN,20));
        MRangeX.setText("10.0");
        copyMRangeX = "10.0";
        rangeY.setFont(new Font("Arial",Font.PLAIN,20));
        MRangeY.setFont(new Font("Arial",Font.PLAIN,20));
        MRangeY.setText("10.0");
        copyMRangeY = "10.0";
        samples.setFont(new Font("Arial",Font.PLAIN,20));
        Samples.setFont(new Font("Arial",Font.PLAIN,20));
        Samples.setText("100");
        copySamples = "100";
        MRangeX.setHorizontalAlignment(0);
        MRangeY.setHorizontalAlignment(0);
        Samples.setHorizontalAlignment(0);
        GraphParameters.setLayout(grid);
        GraphParameters.add(rangeX);
        GraphParameters.add(MRangeX);
        GraphParameters.add(rangeY);
        GraphParameters.add(MRangeY);
        GraphParameters.add(samples);
        GraphParameters.add(Samples);
        GraphParameters.setVisible(true);
        GraphParameters.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Display  Parameters:"),new EmptyBorder(30,40,30,40)));

    }

    public Parameters(){

        createPanel();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            GraphicDisplay.MRangeX = Float.valueOf(MRangeX.getText());
            GraphicDisplay.MRangeY = Float.valueOf(MRangeY.getText());
            GraphicDisplay.Samples = Integer.valueOf(Samples.getText());
            if (!MRangeX.getText().equals(copyMRangeX) || !MRangeY.getText().equals(copyMRangeY) || !Samples.getText().equals(copySamples)) {
                MainWindow.graph.loadFunctions(ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).getFunctions());
                ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).setParameters(GraphicDisplay.Samples,GraphicDisplay.MRangeX,GraphicDisplay.MRangeY);
                System.out.println(MRangeX.getText() + " " + copyMRangeX);
            }
        }catch( java.lang.NumberFormatException e1){

        }
        copyMRangeX = MRangeX.getText();
        copyMRangeY = MRangeY.getText();
        copySamples = Samples.getText();
    }
}
