



import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;

public class Menu extends JPanel implements ActionListener {

    JMenuBar taskbar = new JMenuBar();
    private JMenu File = new JMenu("File");
    private JMenu Edit = new JMenu("Edit");
    private JMenu Window = new JMenu("Window");
    private JMenu Tasks = new JMenu("Tasks");
    private JMenuItem New = new JMenuItem("New");
    private JMenuItem Open = new JMenuItem("Open");
    private JMenuItem SaveAs = new JMenuItem("Save as");
    private JMenuItem Limit = new JMenuItem("Limit");
    private JCheckBoxMenuItem ShowCoordinates = new JCheckBoxMenuItem("Show Coordinates");
    static boolean isOpened = false;
    private int newCount = 0;

    private void createContent(){

        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEADING);
        taskbar.setLayout(layout);
        taskbar.add(File);
        taskbar.add(Edit);
        taskbar.add(Window);
        taskbar.add(Tasks);
        File.setFont(new Font("Arial",Font.PLAIN,20));
        File.add(New);
        File.addSeparator();
        File.add(Open);
        File.addSeparator();
        File.add(SaveAs);
        New.setFont(new Font("Arial",Font.PLAIN,20));
        New.setPreferredSize(new Dimension(200,40));
        New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
        Open.setFont(new Font("Arial",Font.PLAIN,20));
        Open.setPreferredSize(new Dimension(200,40));
        Open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        SaveAs.setFont(new Font("Arial",Font.PLAIN,20));
        SaveAs.setPreferredSize(new Dimension(200,40));
        SaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        Window.setFont(new Font("Arial",Font.PLAIN,20));
        Edit.setFont(new Font("Arial",Font.PLAIN,20));
        Tasks.setFont(new Font("Arial",Font.PLAIN,20));
        Tasks.add(Limit);
        Limit.setFont(new Font("Arial",Font.PLAIN,20));
        Limit.setPreferredSize(new Dimension(200,40));
        Tasks.add(ShowCoordinates);
        ShowCoordinates.setFont(new Font("Arial",Font.PLAIN,20));
        ShowCoordinates.setPreferredSize(new Dimension(250,40));
    }

    private void createFileMenu(){
        SaveAs.addActionListener(e -> {
            if(SaveAs.isArmed()){
                JFileChooser fc = new JFileChooser();
                fc.setPreferredSize(new Dimension(900,600));
                fc.setDialogTitle("Save file:");
                fc.setFont(new Font("Arial",Font.PLAIN,20));
                fc.setDialogType(JFileChooser.SAVE_DIALOG);
                fc.setDragEnabled(true);
                fc.setAcceptAllFileFilterUsed(false);
                fc.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        if(f.isDirectory())return true;
                        try{
                            String extension =f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf("."));
                            if(extension!=null){
                                return extension.equals(".asv");
                            }
                        }catch(StringIndexOutOfBoundsException ignored){

                        }
                        return false;
                    }

                    @Override
                    public String getDescription() {
                        return "ASV files";
                    }
                });

                int returnVal = fc.showSaveDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                   Save(ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex),fc.getSelectedFile().getAbsolutePath()+".asv");
                }
            }
        });
        Open.addActionListener(e -> {
            if(Open.isArmed()){
                JFileChooser fc = new JFileChooser();
                fc.setPreferredSize(new Dimension(900,600));
                fc.setDialogTitle("Open file:");
                fc.setFont(new Font("Arial",Font.PLAIN,20));
                fc.setDialogType(JFileChooser.OPEN_DIALOG);
                fc.setDragEnabled(true);
                fc.setAcceptAllFileFilterUsed(false);
                fc.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        if(f.isDirectory())return true;
                        try{
                            String extension =f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf("."));
                            if(extension.equals(".asv"))return true;
                            else return false;
                        }catch(StringIndexOutOfBoundsException ignored){

                        }
                        return false;
                    }

                    @Override
                    public String getDescription() {
                        return "ASV files";
                    }
                });

                int returnVal = fc.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    ProjectDisplay.openedProjects.add(Open(fc.getSelectedFile().getAbsolutePath()));
                    ProjectDisplay.isAdded = true;
                    isOpened = true;
                }

            }
        });
        New.addActionListener(e -> {
            if(New.isArmed()){
                Project p = new Project();
                newCount++;
                p.name = "Untitled project"+String.valueOf(newCount);
                ProjectDisplay.openedProjects.add(p);
                ProjectDisplay.isAdded = true;
                isOpened = true;

            }
        });
    }

    private void createTasks(){
        Limit.addActionListener(e -> {
            if(Limit.isArmed()){
               new LWindow();
            }
        });
        ShowCoordinates.addActionListener(e -> {
            if(ShowCoordinates.isEnabled()){
                GraphicDisplay.showCoordinates = !GraphicDisplay.showCoordinates;
            }
        });
    }

    private void CreateTaskBar(){
        taskbar.setVisible(true);
        /* taskbar.setBorder(BorderFactory.createTitledBorder("Task Bar")); */
        taskbar.setBackground(Color.WHITE);
        taskbar.setName("TaskBar");
        createContent();
        createFileMenu();
        createTasks();
    }

    private void Save(Project project, String Address){
        try {
            FileOutputStream f = new FileOutputStream(Address);
            ObjectOutputStream o = new ObjectOutputStream(f);
            project.setParameters(GraphicDisplay.Samples,GraphicDisplay.MRangeX,GraphicDisplay.MRangeY);
            project.name = Address.substring(Address.lastIndexOf("\\")).substring(1,Address.substring(Address.lastIndexOf("\\")).indexOf("."));
            System.out.println(project.name);
            o.writeObject(project);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Project Open(String Address){
        try {
            FileInputStream f = new FileInputStream(Address);
            ObjectInputStream o = new ObjectInputStream(f);
            return (Project) o.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    Menu(){
        CreateTaskBar();
        Timer timer = new Timer(30,this);
        timer.start();

    }

    public void actionPerformed(ActionEvent e) {



    }
}
