import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

 class GraphicDisplay extends JScrollPane implements ActionListener, MouseWheelListener {

    JPanel graphic = new JPanel(new BorderLayout());
    private JScrollBar hbar = new JScrollBar();
    private JScrollBar vbar = new JScrollBar();
     int panelWidth = graphic.getWidth();
     int panelHeight = graphic.getHeight();
     int StartX = this.getWidth()/2;
     private int StartY = this.getHeight()/2;
     private int Yhovered;
     int Xclicked = panelWidth/2;
     int Yclicked = panelHeight/2;
     int copyXclicked;
     int copyYclicked;
     static int Samples = 1000;
     static double MRangeX = 10;
     static double MRangeY = 10;
     static double RangeX = 5;
     static double RangeY = 5;
     double functionVector[][] = new double[100][1000000];
     double functionStart[] = new double[100];
     double functionEnd[] = new double[100];
     ArrayList<Function> functions = new ArrayList<>();
     Timer timer = new Timer(30,this);
    static boolean showCoordinates = false;
    private boolean MousePressed = false;
    private boolean begin = true;
    MyGraphics mg = new MyGraphics();

     void createPanel(){
        graphic.setBorder(BorderFactory.createBevelBorder(1,Color.LIGHT_GRAY,Color.GRAY));
        graphic.setBackground(Color.LIGHT_GRAY);
        graphic.setVisible(true);
        graphic.add(mg);
        //pane = new JScrollPane(graphic);
        //pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //pane.setVisible(true);
        //pane.setBorder(BorderFactory.createBevelBorder(1,Color.LIGHT_GRAY,Color.GRAY));
        graphic.add(hbar,BorderLayout.SOUTH);
        hbar.setOrientation(Adjustable.HORIZONTAL);
        hbar.setVisibleAmount(2);
        hbar.setValue(hbar.getMaximum()/2);
        hbar.setToolTipText("Adjusts X-Axis range");
        hbar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                RangeX = MRangeX*hbar.getValue()/hbar.getMaximum();
                loadFunctionVectors();
            }
        });
        graphic.addMouseWheelListener(this);
        graphic.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                Xclicked = e.getX();
                Yclicked = e.getY();
                MousePressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                MousePressed = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        graphic.add(vbar,BorderLayout.EAST);
        vbar.setOrientation(Adjustable.VERTICAL);
        vbar.setVisibleAmount(2);
        vbar.setValue(vbar.getMaximum()/2);
        vbar.setToolTipText("Adjusts Y-Axis range");
        vbar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                RangeY = MRangeY*vbar.getValue()/vbar.getMaximum();
            }
        });
       // graphic.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    }

     void startTimer(){
        timer.start();
    }

     void loadFunctions(ArrayList<Function> f){
        functions=f;
        loadFunctionVectors();
        //System.out.println(f.size());
    }

     GraphicDisplay(){
        createPanel();
        startTimer();
    }

     public void actionPerformed(ActionEvent e) {

        graphic.repaint();
        graphic.revalidate();
        //System.out.println(panelWidth+" "+panelHeight);
        //System.out.println(functions.size());

    }

     void loadFunctionVectors(){
         System.out.println(RangeX);
        for(int ind = 0; ind<functions.size();ind++) {
                for (int i = 0; i <= Samples; i++) {
                    if (2 * i * RangeX / Samples - RangeX > functions.get(ind).start && 2 * i * RangeX / Samples - RangeX < functions.get(ind).end)
                        functionVector[ind][i] = (double) functions.get(ind).getResult( -(StartX-panelWidth/2)*2*RangeX/panelWidth + 2 * i * RangeX / Samples - RangeX);
                    //System.out.println(2*i*RangeX/Samples-RangeX+" "+(double)functions.get(ind).getResult(2*i*RangeX/Samples-RangeX));
                }
                functionStart[ind] = functions.get(ind).start;
                functionEnd[ind] = functions.get(ind).end;
        }
    }

     int convertX(double number){
        return (int)(number*panelWidth/2/RangeX+panelWidth/2);
    }

     int convertY(double number){
        return (int)(number*panelHeight/2/RangeY+panelHeight/2);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        if(e.getWheelRotation()>0){
            hbar.setValue(hbar.getValue()+e.getScrollAmount());
            vbar.setValue(vbar.getValue()+e.getScrollAmount());
        }
        else{
            hbar.setValue(hbar.getValue()-e.getScrollAmount());System.out.println("spinn");
            vbar.setValue(vbar.getValue()-e.getScrollAmount());
        }
    }

    private class MyGraphics extends JComponent{

         MyGraphics(){
            GraphicDisplay.this.setPreferredSize(new Dimension(panelWidth,panelHeight));
            StartX = panelWidth/2;
            StartY = panelHeight/2;
        }

         void drawAxes(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            //System.out.println(StartX);
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.BLACK);
            //System.out.println(RangeX);
            g2.drawLine((int) StartX,0, (int) StartX,panelHeight);
            g2.drawLine(0, (int) StartY,panelWidth, (int) StartY);
            Font f = new Font("Arial",Font.PLAIN,25);
            FontMetrics m = g2.getFontMetrics(f);
            g2.setFont(f);
            DecimalFormat frm = new DecimalFormat("0.00");
            g2.drawString(String.valueOf(frm.format((RangeX - (StartX - panelWidth / 2) * 2 * RangeX / panelWidth))),panelWidth-m.stringWidth(String.valueOf(frm.format((RangeX - (StartX - panelWidth / 2) * 2 * RangeX / panelWidth))))-10,StartY+m.getHeight());
            g2.drawString(String.valueOf(frm.format(-RangeX-(float)(StartX-panelWidth/2)*2*RangeX/panelWidth)),0, (float) (StartY+m.getHeight()));
            g2.drawString(String.valueOf(frm.format(RangeY + (StartY-panelHeight/2)*2*RangeY/panelHeight)), (float) (StartX+5),m.getHeight());
            g2.drawString(String.valueOf(frm.format(-RangeY + (StartY-panelHeight/2)*2*RangeY/panelHeight)), (float) (StartX+5),panelHeight-2*m.getHeight()-10);
        }

         void showCoordinateX(Graphics g,int xcoord, int ycoord){
            Graphics2D g2 = (Graphics2D) g;
            float dash[] = {10.0f};
            g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f,dash,0.0f));
            g2.drawLine(xcoord,ycoord,xcoord,StartY);
            Font f = new Font("Arial",Font.BOLD,15);
            g2.setFont(f);
            g2.drawString(String.valueOf(new DecimalFormat("0.00").format(((double)xcoord-StartX)*RangeX/panelWidth*2)),xcoord+10,StartY+15);
        }

         void showCoordinateY(Graphics g, int xcoord, int ycoord){
            Graphics2D g2 = (Graphics2D) g;
            float dash[] = {10.0f};
            g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f,dash,0.0f));
            g2.drawLine(xcoord,ycoord, (int) StartX,ycoord);
            Font f = new Font("Arial",Font.BOLD,15);
            g2.setFont(f);
            g2.drawString(String.valueOf(new DecimalFormat("0.00").format(-((double)ycoord - StartY)*RangeY/panelHeight*2)), (float) (StartX+10),ycoord+15);
        }

         void DisplayFunction(Graphics g, double function[],Color functionColor,double start, double end){
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(functionColor);
            g2.setStroke(new BasicStroke(2,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
            for(int i = 0;i<Samples;i++){
                if(Math.abs(function[i]-function[i+1])<RangeY&&2*i*RangeX/Samples-RangeX>start && 2*(i+1)*RangeX/Samples-RangeX<end)g2.drawLine(convertX(2*i*RangeX/Samples-RangeX),StartY + panelHeight/2-convertY(function[i]),convertX(2*(i+1)*RangeX/Samples-RangeX),StartY + panelHeight/2-convertY(function[i+1]));
            }

        }

        @Override
        protected void paintComponent(Graphics g){

            for(int i=0;i< functions.size();i++){
                if(functions.get(i).visibility)DisplayFunction(g,functionVector[i],functions.get(i).functionColor,functions.get(i).start,functions.get(i).end);
            }
            if(begin){
                StartX = panelWidth/2;
                StartY = panelHeight/2;
                begin = false;
            }
            try{
                int xhovered = graphic.getMousePosition().x;
                Yhovered = graphic.getMousePosition().y;
                if(MousePressed) {

                    StartX += (xhovered -copyXclicked);
                    StartY += (Yhovered-copyYclicked);
                    loadFunctionVectors();

                    //MousePressed = false;
                }
                copyXclicked = xhovered;
                copyYclicked = Yhovered;
                if(showCoordinates==true) {
                    showCoordinateX(g, xhovered, Yhovered);
                    showCoordinateY(g, xhovered, Yhovered);
                }
            }catch(java.lang.NullPointerException e){

            }
            drawAxes(g);
            super.paintComponent(g);
        }

    }
}
