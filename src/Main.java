import javax.swing.*;
import java.awt.*;

public class Main {
    private static JFrame jFrame = new JFrame();
    public static void main(String[] args) {
        initGui();
    }

    public static void initGui() {
        GridLayout frameLayout = new GridLayout(2,1);
        jFrame.setLayout(frameLayout);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
