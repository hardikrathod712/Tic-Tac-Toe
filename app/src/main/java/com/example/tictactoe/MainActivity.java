package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.String.format;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    private Button[][] buttons = new Button[3][3];
    private boolean player1turn = true;
    private int roundCount;
    private int player1points;
    private int player2points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for(int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                String buttonID = format("button_%d%d", i, j);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(!((Button) view).getText().toString().equals("")) {
            return;
        }

        if(player1turn){
            ((Button)view).setText("X");
        } else {
            ((Button)view).setText("O");
        }

        roundCount++;

        if(checkForWin()){
            if(player1turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if(roundCount == 9){
            draw();
        } else {
            player1turn = !player1turn;
        }
    }
    
    // Logic for checking the winning combinations     
    private boolean checkForWin(){
        int i,j;
        String[][] field = new String[3][3];

        for( i = 0; i < 3; i++){
            for ( j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for ( i = 0; i < 3; i++){
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                return true;
            }
        }

        for ( i = 0; i < 3; i++){
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    @SuppressLint("DefaultLocale")
    private void player1Wins(){
        player1points++;
        Toast.makeText(this,"Player 1 Wins!!", Toast.LENGTH_SHORT).show();
        textViewPlayer1.setText(format("Player 1: %d",player1points));
        textViewPlayer2.setText(format("Player 2: %d",player2points));
        resetBoard();
    }

    @SuppressLint("DefaultLocale")
    private void player2Wins(){
        player2points++;
        Toast.makeText(this,"Player 2 Wins!!", Toast.LENGTH_SHORT).show();
        textViewPlayer1.setText(format("Player 1: %d",player1points));
        textViewPlayer2.setText(format("Player 2: %d",player2points));
        resetBoard();
    }

    private void draw(){
        Toast.makeText(this,"Draw!!",Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1turn = true;
    }

    @SuppressLint("DefaultLocale")
    private void resetGame(){
        player1points = 0;
        player2points = 0;
        textViewPlayer1.setText(format("Player 1: %d",player1points));
        textViewPlayer2.setText(format("Player 2: %d",player2points));
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount",roundCount);
        outState.putInt("player1points",player1points);
        outState.putInt("player2points",player2points);
        outState.putBoolean("player1turn",player1turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1points = savedInstanceState.getInt("player1points");
        player2points = savedInstanceState.getInt("player2points");
        player1turn = savedInstanceState.getBoolean("player1turn");
    }
}
