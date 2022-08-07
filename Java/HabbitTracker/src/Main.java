import javax.swing.*;

public class Main {



    public static void main(String[] args){

        JFrame frame = new JFrame();


        JButton b = new JButton("Click Me");
        b.setBounds(150, 150,150,40);

        frame.add(b);

        frame.setSize(500, 500);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
