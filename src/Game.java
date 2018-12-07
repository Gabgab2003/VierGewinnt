import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

class Game extends KeyAdapter implements ActionListener, KeyListener {
    private Team team = Team.ONE;
    private VierGewinnt vierGewinnt = new VierGewinnt();
    private final ImageIcon red = new ImageIcon(this.getClass().getResource("assets/RED.png"));
    private final ImageIcon blue = new ImageIcon(this.getClass().getResource("assets/BLUE.png"));


    private final JFrame jFrame = new JFrame();
    private JLabel[][] fields;
    private JLabel bottomText;
    private JButton[] col_buttons;

    private void switchTeam() {
        switch (team) {
            case ONE:
                team = Team.TWO;
                break;
            case TWO:
                team = Team.ONE;
                break;
        }
    }

    private void resetDialog() {
        int resp = JOptionPane.showConfirmDialog(jFrame, "Do you want to play another round?", "Another round?", JOptionPane.YES_NO_OPTION);
        if (resp == 0) {
            resetBoard();
        } else {
            System.exit(0);
        }
    }

    private void resetBoard() {
        vierGewinnt = new VierGewinnt();
        bottomText.setText("Red's turn");
        team = Team.TWO;

        for (int r = 0; r < vierGewinnt.getRows(); r++) {
            for (int c = 0; c < vierGewinnt.getColumns(); c++) {
                fields[r][c].setIcon(new ImageIcon());
            }
        }
        for (int i = 0; i < 7; i++) {
            col_buttons[i].setEnabled(true);
        }
    }

    public void actionPerformed(ActionEvent actionEvent) {
        place(Integer.parseInt(actionEvent.getActionCommand()) - 1);
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        int num = -1;
        try {
            num = Integer.parseInt(Character.toString(e.getKeyChar())) - 1;
        } catch (NumberFormatException ignore) {
        }
        if (num >= 0 && num <= vierGewinnt.getColumns() - 1) {
            col_buttons[num].doClick(111);
        }
    }


    public void keyTyped(KeyEvent e) {
    }

    private void place(int num) {
        int pl = vierGewinnt.place(num, team);
        if (pl != -1) {
            switch (team) {
                case ONE:
                    fields[pl][num].setIcon(red);
                    break;
                case TWO:
                    fields[pl][num].setIcon(blue);
                    break;
            }
            if (vierGewinnt.win(num, pl)) {
                for (int i = 0; i < 7; i++) {
                    col_buttons[i].setEnabled(false);
                }
                switch (team) {
                    case ONE:
                        JOptionPane.showConfirmDialog(jFrame, "Red wins!", "Winner", JOptionPane.DEFAULT_OPTION);
                        break;
                    case TWO:
                        JOptionPane.showConfirmDialog(jFrame, "Blue wins!", "Winner", JOptionPane.DEFAULT_OPTION);
                        break;
                }
                resetDialog();
            } else if (vierGewinnt.isDraw()) {
                JOptionPane.showConfirmDialog(jFrame, "It's a draw!", "Draw", JOptionPane.DEFAULT_OPTION);
                resetDialog();
            } else {
                switch (team) {
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

    void initGui() {
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());
        jFrame.addKeyListener(this);
        jFrame.setFocusable(true);

        JPanel board = new JPanel();
        board.addKeyListener(this);
        board.setPreferredSize(new Dimension(500, 300));
        board.setLayout(new GridLayout(vierGewinnt.getRows(), vierGewinnt.getColumns(), 2, 2));
        fields = new JLabel[vierGewinnt.getRows()][vierGewinnt.getColumns()];
        for (int r = 0; r < vierGewinnt.getRows(); r++) {
            for (int c = 0; c < vierGewinnt.getColumns(); c++) {
                fields[r][c] = new JLabel("", SwingConstants.CENTER);
                fields[r][c].setBorder(new LineBorder(Color.BLACK));
                board.add(fields[r][c]);
            }
        }
        board.setVisible(true);

        JPanel buttonRow = new JPanel();
        buttonRow.addKeyListener(this);
        buttonRow.setPreferredSize(new Dimension(500, 25));
        buttonRow.setLayout(new GridLayout(1, vierGewinnt.getColumns(), 2, 0));
        col_buttons = new JButton[vierGewinnt.getColumns()];
        for (int i = 0; i < vierGewinnt.getColumns(); i++) {
            col_buttons[i] = new JButton("" + (i + 1));
            col_buttons[i].addActionListener(this);
            col_buttons[i].addKeyListener(this);
            buttonRow.add(col_buttons[i]);
        }
        buttonRow.setVisible(true);

        bottomText = new JLabel("Red's turn", SwingConstants.CENTER);
        bottomText.setPreferredSize(new Dimension(500, 25));
        bottomText.setFocusable(false);

        jFrame.add(buttonRow, BorderLayout.NORTH);
        jFrame.add(board, BorderLayout.CENTER);
        jFrame.add(bottomText, BorderLayout.SOUTH);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
