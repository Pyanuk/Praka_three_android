package com.example.tema_belaja_chernaja;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity3 extends AppCompatActivity {

    private boolean xTurn = true;
    private int[][] board = new int[3][3];
    private boolean isDarkMode = true;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        preferences = getSharedPreferences("ticTacToePrefs", MODE_PRIVATE);
        loadGameState();

        ConstraintLayout layout = findViewById(R.id.main);
        ImageButton imageButton = findViewById(R.id.imageButtonId);


        applyTheme(layout);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDarkMode = !isDarkMode;
                applyTheme(layout);
                saveGameState();
            }
        });

        setupBoard();
        updateTurnDisplay();
    }

    private void setupBoard() {
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            final int row = i / 3;
            final int col = i % 3;
            Button button = (Button) gridLayout.getChildAt(i);
            button.setText(board[row][col] == 1 ? "X" : (board[row][col] == 2 ? "O" : ""));
            button.setEnabled(board[row][col] == 0);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (board[row][col] == 0) {
                        board[row][col] = xTurn ? 1 : 2;
                        ((Button) v).setText(xTurn ? "X" : "O");
                        if (checkWin()) {
                            showResult(xTurn ? "X Wins!" : "O Wins!");
                        } else if (isBoardFull()) {
                            showResult("Draw!");
                        } else {
                            xTurn = !xTurn;
                            updateTurnDisplay();
                            saveGameState();
                        }
                    }
                }
            });
        }
    }

    private boolean checkWin() {

        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != 0) {
                return true;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != 0) {
                return true;
            }
        }

        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != 0) {
            return true;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != 0) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 0) return false;
            }
        }
        return true;
    }

    private void showResult(String result) {
        TextView resultView = findViewById(R.id.textViewResult);
        resultView.setText(result);
        disableBoard();
    }

    private void disableBoard() {
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            gridLayout.getChildAt(i).setEnabled(false);
        }
    }

    private void updateTurnDisplay() {
        TextView resultView = findViewById(R.id.textViewResult);
        resultView.setText(xTurn ? "X's Turn" : "O's Turn");
    }


    private void applyTheme(ConstraintLayout layout) {
        layout.setBackgroundColor(isDarkMode ? Color.DKGRAY : Color.WHITE);
    }


    private void saveGameState() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isDarkMode", isDarkMode);
        editor.putBoolean("xTurn", xTurn);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                editor.putInt("board_" + i + "_" + j, board[i][j]);
            }
        }
        editor.apply();
    }

    private void loadGameState() {
        isDarkMode = preferences.getBoolean("isDarkMode", true);
        xTurn = preferences.getBoolean("xTurn", true);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = preferences.getInt("board_" + i + "_" + j, 0);
            }
        }
    }
}
