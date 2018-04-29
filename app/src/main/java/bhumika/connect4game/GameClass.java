package bhumika.connect4game;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

import static android.os.ParcelFileDescriptor.MODE_APPEND;

/**
 * Created by bhumi on 10/29/2017.
 */

public class GameClass implements Serializable {

    int[][] board = new int[6][7];
    boolean turn;
    int value;

    void toggle(){
        if(turn==true)
            turn=false;
        else
            turn=true;

    }

    int occupy(int col){
        int low = lowest_empty_row(col);
        if(low>=0) {
            board[low][col] = turn == true ? 1 : 2;
        }
        return low;

    }

    void reset_board(){
        for(int i=0; i<6; ++i){
            for(int j=0; j<7; ++j) {
                board[i][j] = 0;
            }

        }
    }

    int lowest_empty_row(int col){
        int i;
        for(i=5; i>=0; --i){
            if(board[i][col]==0)
                break;
        }
        //TODO: check for full column
        if(i<0){
            return -1;
        }
        return i;
    }

    public int check_for_win() {
        if (Diagonal_1() || Diagonal_2() || Horizontal() || Vertical()) {
            return value;
        }
        return -1;
    }



    public boolean board_full(){

        //TODO
        for(int i = 0; i<6; ++i){
            for(int j = 0; j<7; ++j){
                if(board[i][j]==0){
                    return false;
                }
            }
        }
        return true;
    }



    public boolean Horizontal(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                value = board[i][j];
                if (value != 0 && board[i][j + 1] == value && board[i][j + 2] == value && board[i][j + 3] == value) {
                    return true;
                }}}
        return false;
    }



    public boolean Vertical(){
        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < 3; i++) {
                value = board[i][j];
                if (value != 0 && board[i+1][j] ==
                        value && board[i+2][j] ==
                        value && board[i+3][j] == value) {
                    return true;
                }}}
        return false;
    }

    public boolean Diagonal_1(){
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j <4; j++) {
                value = board[i][j];
                if (value != 0 && board[i-1][j+1] ==
                        value && board[i-2][j+2] ==
                        value && board[i-3][j+3] == value) {
                    return true;
                }}
        }
        return false;
    }

    public boolean Diagonal_2(){
        for (int i = 3; i < 6; i++) {
            for (int j = 3; j <7; j++) {
                value = board[i][j];
                if (value != 0 && board[i-1][j-1] ==
                        value && board[i-2][j-2] ==
                        value && board[i-3][j-3] == value) {
                    return true;
                }}
        }
        return false;
    }
}
