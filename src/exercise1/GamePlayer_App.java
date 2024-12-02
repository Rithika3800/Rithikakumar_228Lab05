package exercise1;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GamePlayer_App extends JFrame
{

    public final Game_Player op;
    public JComboBox<Integer> playerIdComboBox;
    public JTextField FName, LName, GameTitle, Score, PlayingDate;
    public JTable table;
    public DefaultTableModel Model;

    public GamePlayer_App() {
        op = new Game_Player(DBConnection.getConnection());

        setTitle("GamePlayer App");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));

        inputPanel.add(new JLabel("Player ID:"));
        playerIdComboBox = new JComboBox<>();
        op.loadPlayerIds(playerIdComboBox);
        inputPanel.add(playerIdComboBox);

        inputPanel.add(new JLabel("First Name:"));
        FName = new JTextField();
        inputPanel.add(FName);

        inputPanel.add(new JLabel("Last Name:"));
        LName = new JTextField();
        inputPanel.add(LName);

        inputPanel.add(new JLabel("Game Title:"));
        GameTitle = new JTextField();
        inputPanel.add(GameTitle);

        inputPanel.add(new JLabel("Score :"));
        Score = new JTextField();
        inputPanel.add(Score);

        inputPanel.add(new JLabel("Playing Date (YYYY-MM-DD):"));
        PlayingDate = new JTextField();
        inputPanel.add(PlayingDate);

        add(inputPanel, BorderLayout.NORTH);

        JPanel button= new JPanel();

        JButton btnInsert = new JButton("Insert data");
        btnInsert.addActionListener(e -> op.insertPlayerAndGame(FName.getText(), LName.getText(), GameTitle.getText(), PlayingDate.getText(),
                Integer.parseInt(Score.getText())
        ));
        button.add(btnInsert);

        JButton buttonUpdate = new JButton("Update data");
        buttonUpdate.addActionListener(e -> op.updatePlayerInfo(
                (Integer) playerIdComboBox.getSelectedItem(),
                FName.getText(),
                LName.getText()
        ));
        button.add(buttonUpdate);

        JButton btnDisplay = new JButton("Display Reports");
        btnDisplay.addActionListener(e -> op.displayReports(Model));
        button.add(btnDisplay);

        add(button, BorderLayout.CENTER);

        Model = new DefaultTableModel(new String[]{"Player ID", "First Name", "Last Name", "Game Title", "Playing Date", "Score"}, 0);
        table = new JTable(Model);
        JScrollPane scroll= new JScrollPane(table);
        add(scroll, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GamePlayer_App app = new GamePlayer_App();
            app.setVisible(true);
        });
    }
}

