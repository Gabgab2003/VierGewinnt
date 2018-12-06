import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game implements ActionListener {
    private Team team = Team.ONE;
    private VierGewinnt vierGewinnt = new VierGewinnt();
    private ImageIcon red = new ImageIcon(this.getClass().getResource("assets/RED.png"));
    private ImageIcon blue = new ImageIcon(this.getClass().getResource("assets/BLUE.png"));

    private JFrame jFrame = new JFrame();
    private JLabel[][] fields;
    private JLabel bottomText;
    private JButton[] col_buttons;

    private ActionListener actionListener;

    private void switchTeam() {
        switch(team) {
            case ONE:
                team=Team.TWO;
                break;
            case TWO:
                team=Team.ONE;
                break;
        }
    }

    public void actionPerformed(ActionEvent actionEvent) {
        int num = Integer.parseInt(actionEvent.getActionCommand())-1;
        int pl = vierGewinnt.place(num, team);
        if(pl != -1) {
            switch(team) {
                case ONE:
                    fields[pl][num].setIcon(red);
                    break;
                case TWO:
                    fields[pl][num].setIcon(blue);
                    break;
            }
            if(vierGewinnt.win(num,pl)) {
                for(int i=0;i<7;i++) {
                    col_buttons[i].setEnabled(false);
                }
                switch(team) {
                    case ONE:
                        bottomText.setText("Red wins!");
                        break;
                    case TWO:
                        bottomText.setText("Blue wins!");
                        break;
                }
            } else {
                switch(team) {
                    case ONE:
                        bottomText.setText("Blue's turn");
                        break;
                    case TWO:
                        bottomText.setText("Red's turn");
                        break;
                }
            }
            switchTeam();
        }
    }

    public void initGui() {
        int cols = 7;
        int rows = 5;
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());

        JPanel board = new JPanel();
        board.setPreferredSize(new Dimension(500,300));
        board.setLayout(new GridLayout(rows, cols, 2,2));
        fields = new JLabel[rows][cols];
        for(int r=0;r<rows;r++) {
            for(int c=0;c<cols;c++) {
                fields[r][c] = new JLabel("",SwingConstants.CENTER);
                fields[r][c].setBorder(new LineBorder(Color.BLACK));
                board.add(fields[r][c]);
            }
        }
        board.setVisible(true);

        JPanel buttonRow = new JPanel();
        buttonRow.setPreferredSize(new Dimension(500,25));
        buttonRow.setLayout(new GridLayout(1, cols, 2, 0));
        col_buttons = new JButton[cols];
        for(int i=0;i<cols;i++) {
            col_buttons[i] = new JButton(""+(i+1));
            col_buttons[i].addActionListener(this);
            buttonRow.add(col_buttons[i]);
        }
        buttonRow.setVisible(true);

        bottomText = new JLabel("Red's turn", SwingConstants.CENTER);
        bottomText.setPreferredSize(new Dimension(500,25));

        jFrame.add(buttonRow, BorderLayout.NORTH);
        jFrame.add(board, BorderLayout.CENTER);
        jFrame.add(bottomText, BorderLayout.SOUTH);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
