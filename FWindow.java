import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FWindow extends JFrame {

    JFrame fwindow = new JFrame();
    JPanel panel  = new JPanel();
    JLabel parameter = new JLabel("(x)=");
    JLabel parameterInterval = new JLabel(" < x < ");
    JLabel colorSample = new JLabel();
    JTextField name = new JTextField(2);
    JTextField function = new JTextField(10);
    JTextField min = new JTextField(4);
    JTextField max = new JTextField(4);
    Button Done = new Button("Done",Color.white,true,new Font("Arial",Font.PLAIN,20));
    Button color = new Button("Choose color",Color.white,true,new Font("Arial",Font.PLAIN,20));
    Function f = new Function();
    Color Fcolor = Color.BLACK;
    boolean isDone = false;

    public void createPanel(){

        panel.setBorder(new CompoundBorder(BorderFactory.createBevelBorder(1,Color.GRAY,Color.LIGHT_GRAY),new EmptyBorder(40,30,40,30)));
        panel.setVisible(true);
        panel.setBackground(Color.LIGHT_GRAY);

    }

    public ArrayList<JLabel> loadFunction(ArrayList<Function> functions){
        ArrayList<JLabel> Functions = new ArrayList<>();
        for(int i=0;i<functions.size();i++){

            Functions.add(new JLabel("nulll"));
            Functions.get(i).setText(functions.get(i).functionName+'('+functions.get(i).functionParameter+")="+functions.get(i).Function);
            Functions.get(i).setBackground(functions.get(i).functionColor);


        }
        return Functions;
    }

    public void createContent(){

        parameter.setFont(new Font("Arial",Font.PLAIN,40));
        name.setFont(new Font("Arial",Font.PLAIN,40));
        function.setFont(new Font("Arial",Font.PLAIN,40));
        parameterInterval.setFont(new Font("Arial",Font.PLAIN,40));
        min.setFont(new Font("Arial",Font.PLAIN,30));
        min.setText("-OO");
        max.setText("+OO");
        min.setHorizontalAlignment(0);
        max.setHorizontalAlignment(0);
        max.setFont(new Font("Arial",Font.PLAIN,30));
        name.setHorizontalAlignment(0);
        function.setHorizontalAlignment(0);
        colorSample.setBackground(Fcolor);
        colorSample.setFont(new Font("Arial",Font.PLAIN,20));
        colorSample.setText("    ");
        colorSample.setOpaque(true);

        Done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Done.isEnabled()){
                    isDone = true;
                    f = new Function();
                    f.setParameterName("x");
                    f.setFunction(function.getText());
                    f.setColor(colorSample.getBackground());
                    f.visibility = true;
                    f.setName(name.getText());
                    try {
                        if (Float.valueOf(min.getText()) < Float.valueOf(max.getText()))
                            f.setBounds(Float.valueOf(min.getText()), Float.valueOf(max.getText()));
                        else f.setBounds(Float.valueOf(max.getText()), Float.valueOf(min.getText()));
                    }catch(java.lang.NumberFormatException e1){
                        double Min = 0;
                        double Max = 0;
                        if(min.getText().equals("-OO"))Min = -1000000000;
                        if(max.getText().equals("+OO"))Max = 1000000000;
                        f.setBounds(Min,Max);
                    }
                    ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).addFunction(f);
                    FunctionDisplay.loadFunction(ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).getFunctions());
                    FunctionDisplay.addFunctions();
                    System.out.println(ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).getFunctions().size());
                    MainWindow.graph.loadFunctions(ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).getFunctions());
                    fwindow.dispose();
                }
            }
        });
        color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(color.isEnabled()) {
                    JColorChooser colorChooser = new JColorChooser(colorSample.getBackground());
                    JFrame ColorFrame = new JFrame();
                    ColorFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    ColorFrame.setVisible(true);
                    ColorFrame.setSize(700,500);
                    ColorFrame.setLocationRelativeTo(null);
                    ColorFrame.add(colorChooser,BorderLayout.CENTER);
                    ColorSelectionModel model = colorChooser.getSelectionModel();
                    ChangeListener changeListener = new ChangeListener() {
                        public void stateChanged(ChangeEvent changeEvent) {
                            Color newForegroundColor = colorChooser.getColor();
                            colorSample.setBackground(newForegroundColor);
                        }
                    };
                    model.addChangeListener(changeListener);
                }
            }
        });
        panel.setLayout(new FlowLayout(FlowLayout.TRAILING,20,10));
        panel.add(name);
        panel.add(parameter);
        panel.add(function);
        panel.add(min);
        panel.add(parameterInterval);
        panel.add(max);
        panel.add(color);
        panel.add(colorSample);
        panel.add(Done);

    }

    public FWindow(){

        createPanel();
        createContent();
        fwindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        fwindow.setTitle("Function");
        fwindow.setResizable(true);
        fwindow.setSize(650,300);
        fwindow.setVisible(true);
        fwindow.setLocationRelativeTo(null);
        fwindow.add(panel);


    }

}
