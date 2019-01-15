import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener {

    private Menu menu = new Menu();
    private UserInput user = new UserInput();
    static GraphicDisplay graph = new GraphicDisplay();
    private JFrame window = new JFrame();
    public MainWindow(){


        Timer timer = new Timer(30,this);
        timer.start();
        window.setBackground(Color.LIGHT_GRAY);
        window.add(menu.taskbar,BorderLayout.NORTH);
        window.add(user.Interface,BorderLayout.SOUTH);
        window.add(graph.graphic,BorderLayout.CENTER);
        ProjectDisplay pDisplay = new ProjectDisplay();
        window.add(pDisplay.display,BorderLayout.WEST);
        window.setSize(1920,1080);
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(true);
        window.setTitle("Function Grapher");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }
        graph.panelWidth = graph.graphic.getWidth();
        graph.panelHeight = graph.graphic.getHeight();

    }

    public static void main(String args[]){
        new MainWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(menu.isOpened){
            graph.loadFunctions(ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).getFunctions());
            FunctionDisplay.loadFunction(ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).getFunctions());
            FunctionDisplay.addFunctions();
            GraphicDisplay.Samples = ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).Samples;
            GraphicDisplay.MRangeX = ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).XRange;
            GraphicDisplay.MRangeY = ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).YRange;
            user.param.Samples.setText(String.valueOf(ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).Samples));
            user.param.MRangeX.setText(String.valueOf(ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).XRange));
            user.param.MRangeY.setText(String.valueOf(ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).YRange));
            user.param.Samples.repaint();
            user.param.MRangeX.repaint();
            user.param.MRangeY.repaint();
            System.out.println(ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).Samples);
            FunctionDisplay.display.repaint();
            menu.isOpened = false;

        }
    }
}
