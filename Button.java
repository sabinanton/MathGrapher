import javax.swing.*;
import java.awt.*;

class Button extends JButton {

     Button(String name, Color Background, boolean BorderPainted, Font font){
        this.setText(name);
        this.setBackground(Background);
        this.setBorderPainted(BorderPainted);
        this.setFont(font);
    }

}
