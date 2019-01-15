import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FunctionWriter extends JPanel {

    JPanel writer = new JPanel();
    Button AddFunction = new Button("Add Function",null,true,new Font("Arial",Font.BOLD,20));
    Button Delete = new Button("Delete",null,true,new Font("Arial",Font.BOLD,20));
    Button Modify = new Button("Modify",null,true,new Font("Arial",Font.BOLD,20));
    FWindow fwindow[] = new FWindow[10];
    static boolean isModified = false;

    public void setButtons(){

        AddFunction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fwindow[1] = new FWindow();
            }
        });
        Modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Function> f;
                f = ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).getFunctions();
                System.out.println(f.size());
                for(int i=0;i<f.size();i++){
                    if(f.get(i).visibility){
                        fwindow[i] = new FWindow();
                        fwindow[i].function.setText(f.get(i).Function);
                        fwindow[i].colorSample.setBackground(f.get(i).functionColor);
                        fwindow[i].name.setText(f.get(i).functionName);
                        if(f.get(i).start<=-1000000000){
                            fwindow[i].min.setText("-OO");
                        }
                        else fwindow[i].min.setText(String.valueOf(f.get(i).start));
                        if(f.get(i).end>=1000000000){
                            fwindow[i].max.setText("+OO");
                        }
                        else fwindow[i].max.setText(String.valueOf(f.get(i).end));
                        f.remove(i);
                        i--;
                    }
                }
                ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).changeFunctions(f);
                FunctionDisplay.loadFunction(f);
                FunctionDisplay.addFunctions();
                MainWindow.graph.loadFunctions(ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).getFunctions());
                FunctionDisplay.display.repaint();
            }
        });
        Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Function> f = ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).getFunctions();
                for(int i=0;i<f.size();i++){
                    if(f.get(i).visibility){
                        f.remove(i);
                        FunctionDisplay.Functions.remove(i);
                        i--;
                    }
                }
                ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).changeFunctions(f);
                MainWindow.graph.loadFunctions(f);
                FunctionDisplay.loadFunction(f);
                FunctionDisplay.addFunctions();
                FunctionDisplay.display.repaint();
            }
        });

    }

    public void createPanel(){

        GridLayout layout = new GridLayout(3,1,20,20);
        writer.setLayout(layout);
        writer.setVisible(true);
        writer.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Function Writer"),new EmptyBorder(30,10,30,10)));
        writer.add(AddFunction);
        writer.add(Modify);
        writer.add(Delete);

    }

    public FunctionWriter(){

        setButtons();
        createPanel();

    }

}
