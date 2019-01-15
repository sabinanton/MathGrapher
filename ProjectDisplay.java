import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ProjectDisplay implements ActionListener {

    public JPanel display = new JPanel();
    public static ArrayList<Project> openedProjects = new ArrayList<>();
    public ArrayList<JRadioButton> OpenedProjects = new ArrayList<>();
    ButtonGroup group = new ButtonGroup();
    static boolean isAdded = false;
    boolean isChosen[] = new boolean[101];
    boolean copyIsChosen[] = new boolean[101];
    public static int currentProjectIndex;

    public void createPanel(){
        display.setBorder(BorderFactory.createTitledBorder("Project List"));
        display.setLayout(new GridLayout(20,1));
        display.setPreferredSize(new Dimension(200,400));
        OpenedProjects.clear();
        openedProjects.add(new Project());
        currentProjectIndex = 0;
        isAdded = true;
    }

    public ProjectDisplay(){
        Timer timer = new Timer(30,this);
        timer.start();
        createPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(isAdded){
            display.repaint();
            display.removeAll();
            display.revalidate();
            OpenedProjects.clear();
            for(int i=0;i<openedProjects.size();i++){
                OpenedProjects.add(new JRadioButton(openedProjects.get(i).name));
                OpenedProjects.get(i).setFont(new Font("Arial",Font.PLAIN,20));
                display.add(OpenedProjects.get(i));
                group.add(OpenedProjects.get(i));
            }
            OpenedProjects.get(OpenedProjects.size()-1).setSelected(true);
            isAdded = false;
        }
        for(int i=0;i<OpenedProjects.size();i++){
            if(OpenedProjects.get(i).isSelected()){
                isChosen[i] = true;
            }
            else isChosen[i] = false;
            if(isChosen[i]!= copyIsChosen[i] && isChosen[i] == true){
                currentProjectIndex = i;
                Menu.isOpened = true;
            }
            copyIsChosen[i] = isChosen[i];
        }
    }
}
