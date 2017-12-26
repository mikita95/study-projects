import data.Feature;
import utils.ApplePencil;

import javax.swing.*;

/**
 * Created by sergeybp on 18.12.16.
 */
public class Draw extends JFrame {

    private ApplePencil panel;

    public Draw(){
        panel = new ApplePencil();
        this.add(panel);
        this.setSize(280,280);
    }

    public void showMe(){
        this.setVisible(true);
        this.show();
    }

    public Feature getFeature() {
        return panel.getFeature();
    }

}
