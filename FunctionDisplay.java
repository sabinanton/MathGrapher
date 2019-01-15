import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.lang.Integer.min;

public class FunctionDisplay extends JPanel  implements ActionListener{
    
    static ArrayList<JCheckBox> Functions= new ArrayList<>();
    static JPanel display = new JPanel();
    ArrayList<Function> funct = new ArrayList<>();


    public static void addFunctions(){
        BoxLayout layout = new BoxLayout(display,BoxLayout.X_AXIS);
        display.removeAll();
        for(int i=0;i<Functions.size();i++){
            try {
                display.add(Functions.get(i));//System.out.println("hhheeee");

            }catch( java.lang.NullPointerException e){
                e.printStackTrace();
            }
        }
    }

    public FunctionDisplay(){

        Timer timer = new Timer(30,this);
        timer.start();
        display.setBorder(BorderFactory.createTitledBorder("Function:"));
        display.setVisible(true);
        //addFunctions();

    }

    public static String Parse(String text){

        String rez = new String();
        rez = text.replaceAll(String.valueOf("\\*"),"");
        return rez;

    }

    public static void loadFunction(ArrayList<Function> functions){
        Functions.clear();
        for(int i=0;i<functions.size();i++){
           // System.out.println(functions.get(i).functionName+'('+functions.get(i).functionParameter+")="+functions.get(i).Function);
            JCheckBox button = new JCheckBox();
            button.setText(functions.get(i).functionName+'('+functions.get(i).functionParameter+")="+Parse(functions.get(i).Function));
            button.setFont(new Font("Arial",Font.PLAIN,40));
            button.setSize(40,40);
            button.setForeground(functions.get(i).functionColor);
            button.setBackground(new Color(min(functions.get(i).functionColor.getRed()+150,255),min(functions.get(i).functionColor.getGreen()+150,255),min(functions.get(i).functionColor.getBlue()+150,255)));
            button.setSelected(functions.get(i).visibility);
            Functions.add(button);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //System.out.println(Functions.size());
        if (Functions.size() > 0) {
            display.revalidate();
            display.repaint();
        }
        funct = ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).getFunctions();
        try {
            for (int i = 0; i < Functions.size(); i++) {
                if (Functions.get(i).isSelected()) {
                    funct.get(i).visibility = true;
                } else funct.get(i).visibility = false;
            }
            ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).changeFunctions(funct);
        } catch (java.lang.IndexOutOfBoundsException e2) {

        }
    }
}
