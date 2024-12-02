package exercise1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Game_Player {

    public final Connection connect;

    public Game_Player(Connection connection) {
        this.connect = connection;
    }

    public void loadPlayerIds(JComboBox<Integer> playerIdComboBox) {
        try {
            String query = "Select player_id FROM Player";
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            playerIdComboBox.removeAllItems();
            while (resultSet.next()) {
                playerIdComboBox.addItem(resultSet.getInt("player_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load player IDs: " + e.getMessage());
        }
    }

    public void insertPlayerAndGame(String firstName, String lastName, String gameTitle, String playingDate, int score) {
        try {
            String PlayerOperation = "INSERT INTO Player (player_id, first_name, last_name) VALUES (seq_player_id.NEXTVAL, ?, ?)";
            String GameOperation = "INSERT INTO Game (game_id, game_title) VALUES (seq_game_id.NEXTVAL, ?)";
            String PlayerGameOperation = "INSERT INTO PlayerAndGame (player_game_id, player_id, game_id, playing_date, score)VALUES (seq_pg_id.NEXTVAL, seq_player_id.CURRVAL, seq_game_id.CURRVAL, ?, ?)";

            PreparedStatement playerStmt = connect.prepareStatement(PlayerOperation);
            PreparedStatement gameStmt = connect.prepareStatement(GameOperation);
            PreparedStatement playerGameStmt = connect.prepareStatement(PlayerGameOperation);

            playerStmt.setString(1, firstName);
            playerStmt.setString(2, lastName);
            gameStmt.setString(1, gameTitle);
            playerGameStmt.setString(1, playingDate);
            playerGameStmt.setInt(2, score);

            playerStmt.executeUpdate();
            gameStmt.executeUpdate();
            playerGameStmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Player and game information successfully inserted !");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to insert info: " + e.getMessage());
        }
    }
    int playerId;
    String firstName;
    String lastName;

    public void updatePlayerInfo(int playerId,String firstName,String lastName) {
        try {
            String updateOperation = "UPDATE Player SET first_name = ?" +
                    ", last_name = ? WHERE player_id = ?";
            PreparedStatement updateStmt = connect.prepareStatement(updateOperation);

            updateStmt.setString(1, firstName);
            updateStmt.setString(2, lastName);
            updateStmt.setInt(3, playerId);

            updateStmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Player information successfully updated!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to update player info: " + e.getMessage());
        }
    }

    public void displayReports(DefaultTableModel tableModel) {
        try {
            String Operation = "SELECT pl.player_id, pl.first_name, pl.last_name, ga.game_title, plga.playing_date, plga.score FROM Player pl JOIN PlayerAndGame plga ON pl.player_id = plga.player_id JOIN Game ga ON ga.game_id = plga.game_id ";

            PreparedStatement statement = connect.prepareStatement(Operation);
            ResultSet rset = statement.executeQuery();

            tableModel.setRowCount(0);
            while (rset.next()) {
                tableModel.addRow(new Object[]{
                        rset.getInt("player_id"),
                        rset.getString("first_name"),
                        rset.getString("last_name"),
                        rset.getString("game_title"),
                        rset.getDate("playing_date"),
                        rset.getInt("score")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to display reports: " + e.getMessage());
        }
    }
}

