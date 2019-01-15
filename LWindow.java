import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

class LWindow extends JFrame implements ActionListener {

    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JPanel display = new JPanel();
    private JPanel function = new JPanel();
    private JPanel limit = new JPanel();
    private JPanel parameters = new JPanel();
    private JLabel Display = new JLabel();
    private String dis = "lim(...) = ....";
    private String param = "...->...";
    private String[] Signs = {"<",">"};

    private JTextField parameter = new JTextField(5);
    private JTextField result = new JTextField(2);
    private JTextField expression = new JTextField(10);
    private JTextField constrain = new JTextField(3);
    private JComboBox<String> Function = new JComboBox<>();
    private JComboBox<String> sign = new JComboBox<>(Signs);
    private Button calculate = new Button("Calculate",null,true,new Font("Arial",Font.PLAIN,25));

    private void createPanel(){
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setTitle("Limit calculator");
        frame.setVisible(true);
        frame.add(panel);
        panel.setBackground(Color.LIGHT_GRAY);
        display.setBorder(BorderFactory.createTitledBorder("Display"));
        Display.setText(dis);
        Display.setFont(new Font("Arial",Font.PLAIN,50));
        display.add(Display);

        panel.setLayout(new GridLayout(2,1));
        panel.add(display);
        panel.add(parameters);
        parameters.setLayout(new GridLayout(1,2));
        parameters.add(function);
        parameters.add(limit);
    }

    private void createExpression(){
        function.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Expression:"),new EmptyBorder(30,40,30,40)));
        function.setLayout(new GridLayout(2,2,5,10));
        function.setFont(new Font("Arial",Font.PLAIN,30));
        function.add(new JLabel("Expression:"));
        function.add(expression);
        expression.setHorizontalAlignment(0);
        ArrayList<Function> f;
        f = ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).getFunctions();
        for (Function aF : f) {
            Function.addItem(aF.functionName);
        }
        expression.setFont(new Font("Arial",Font.PLAIN,30));
        Function.setFont(new Font("Arial",Font.PLAIN,30));
        function.add(new JLabel("Choose function:"));
        Function.addActionListener(this);
        function.add(Function);
    }

    private void createParameter(){
        limit.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Limit:"),new EmptyBorder(10,55,30,55)));
        limit.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
        JLabel label = new JLabel();
        label.setFont(new Font("Arial",Font.PLAIN,30));
        label.setText("x->");
        limit.add(label);
        parameter.setFont(new Font("Arial",Font.PLAIN,30));
        parameter.addActionListener(this);
        limit.add(parameter);
        JLabel label2 = new JLabel();
        label2.setFont(new Font("Arial",Font.PLAIN,30));
        label2.setText("x");
        limit.add(label2);
        sign.setFont(new Font("Arial",Font.PLAIN,30));
        constrain.setFont(new Font("Arial",Font.PLAIN,30));
        constrain.setEditable(false);
        limit.add(sign);
        limit.add(constrain);
        constrain.setHorizontalAlignment(0);
        constrain.addActionListener(this);
        parameter.setHorizontalAlignment(0);
        calculate.setPreferredSize(new Dimension(300,30));
        limit.add(calculate);
        calculate.addActionListener(this);
    }

    LWindow(){

        Timer timer = new Timer(30,this);
        timer.start();
        createPanel();
        createExpression();
        createParameter();
    }

    private String calculateLimit(){
        Function SelectedFunction = new Function();
        double limit;
        double result;
        if(expression.getText().length()>0){
            SelectedFunction.setFunction((String)expression.getText());
            SelectedFunction.setParameterName("x");
        }
        else{
            String name = (String) Function.getSelectedItem();
            ArrayList<Function> f = new ArrayList<>();
            f =  ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).getFunctions();

            for (Function aF : f) {
                assert name != null;
                if (name.equals(aF.functionName)) {
                    SelectedFunction = aF;
                }
            }
        }
        switch (parameter.getText()) {
            case "+OO":
                limit = 1000000000;
                break;
            case "-OO":
                limit = -1000000000;
                break;
            default:
                limit = SelectedFunction.eval(parameter.getText());
                if (Objects.equals(sign.getSelectedItem(), ">")) {
                    limit += 0.000000000000001;
                } else {
                    limit -= 0.000000000000001;
                }
                break;
        }
        result = SelectedFunction.getResult(limit);
        System.out.println(result);
        if(String.valueOf(result).equals("NaN")){
            return "lim("+SelectedFunction.Function+")=NaN";
        }
        if(result<0.00000001&&result>=0)result = 0;
        if(result>-0.00000001&&result<=0)result = 0;

        if(result>10E7){

            return "lim("+SelectedFunction.Function+")=+OO";
        }
        if((int)result<-10E7){
            return "lim("+SelectedFunction.Function+")=-OO";
        }

        result = Math.round(result*10000.0)/10000.0;
        return "lim("+SelectedFunction.Function+")="+result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(calculate.isEnabled()){
            dis = calculateLimit();
            Display.setText(dis);
        }
        constrain.setText(parameter.getText());
    }
}
