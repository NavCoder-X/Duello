import javax.swing.*;

public class LifeBar extends JProgressBar {

    LifeBar(int max){
        super(0,max);
        this.setValue(max);
        this.setStringPainted(true);
    }
    
}
