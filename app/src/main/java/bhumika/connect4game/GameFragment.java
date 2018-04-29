package bhumika.connect4game;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import static android.os.ParcelFileDescriptor.MODE_APPEND;
import static android.os.ParcelFileDescriptor.MODE_READ_ONLY;

/**
 * Created by bhumi on 11/7/2017.
 */

public class GameFragment extends Fragment{

    private ImageView[][] cells;
    private View mGameView;
    private LinearLayout boardView;
    GameClass loadedgame = new GameClass();
    GameClass gameclass = new GameClass();
    boolean game_check;
    boolean pause_flag = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mGameView = inflater.inflate(R.layout.activity_game_screen, null);
        //mGameView = (ImageView) mGameView.findViewById(R.id.image);
        //mGameView.setDrawingCacheEnabled(false);

        boardView = mGameView.findViewById(R.id.game_board_front);
        buildCells();

        boardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_POINTER_UP:
                    case MotionEvent.ACTION_UP: {
                        int col = colAtX(motionEvent.getX());
                        if (col != -1) {
                            game_check = drop(col);
                            if(game_check==true){
                                //gameclass.save_game();
                                //view.setOnTouchListener(null);
                                //pause();
                                pause_flag=false;
                                return pause_flag;
                            }
                        }
                    }
                }
                return pause_flag;
            }
        });


        return mGameView;
    }

    private void pause(){
        reset_now();

    }

    private boolean drop(int column){
        int low = gameclass.occupy(column);
        if(low>=0) {
            ViewGroup row = (ViewGroup) ((ViewGroup) boardView).getChildAt(low);
            row.setClipChildren(false);

            ImageView imageView = (ImageView) row.getChildAt(column);
            if (gameclass.turn == false) {
                imageView.setImageResource(R.drawable.red);
                //imageView.setImageResource(R.drawable.empty_frame);

                //TODO: dynamically allocate layers to a Drawable object.
//                Resources r = getResources();
//                Drawable[] layers = new Drawable[2];
//                layers[0] = r.getDrawable(R.drawable.empty);
//                layers[1] = r.getDrawable(R.drawable.red);
//                LayerDrawable layerDrawable = new LayerDrawable(layers);
//                imageView.setImageDrawable(layerDrawable);
            } else {
                imageView.setImageResource(R.drawable.yellow);
            }
            cells[low][column] = imageView;

            int win = gameclass.check_for_win();

            TextView winner_text = mGameView.findViewById(R.id.winner_text);

            if(win == 1) {
                winner_text.setText("Yellow Wins!");

                update_model();
                return true;
            }
            else if(win==2){
                winner_text.setText("Red Wins!");
                update_model();
                return true;

            }
            gameclass.toggle();

            if(gameclass.board_full()==true){
                winner_text.setText("Game Over! It's a draw!");
                update_model();
                return true;
            }
        }
        update_model();
        return false;
    }


    private void buildCells() {
        cells = new ImageView[6][7];
        for (int r = 0; r < 6; r++) {
            ViewGroup row = (ViewGroup) ((ViewGroup) boardView).getChildAt(r);
            row.setClipChildren(false);
            for (int c = 0; c < 7; c++) {
                ImageView imageView = (ImageView) row.getChildAt(c);
                imageView.setImageResource(R.drawable.empty);
                cells[r][c] = imageView;
            }
        }

    }



    public void reset_now(){


        pause_flag=true;
        GameClass new_obj = new GameClass();
        gameclass = new_obj;
        //GameFragment fragment = new GameFragment();
        //getFragmentManager().beginTransaction().replace(R.id.game_frag, fragment).commit();
        //mGameView = null;

        TextView winner_text = mGameView.findViewById(R.id.winner_text);

        winner_text.setText("");


        for(int i=0; i<6; ++i){
            for(int j=0; j<7; ++j){
                ViewGroup row = (ViewGroup) ((ViewGroup) boardView).getChildAt(i);
                //row.setClipChildren(false);
                ImageView imageView = (ImageView) row.getChildAt(j);
                imageView.setImageResource(R.drawable.empty);


            }
        }

    }




    public void update_model(){




        Log.d(gameclass.board[1][3]+"", "this is a test");
        try {
            FileOutputStream file_out = getActivity().openFileOutput("new_file", MODE_APPEND);
            ObjectOutputStream os = new ObjectOutputStream(file_out);
            os.writeObject(gameclass);
            Log.d(gameclass.turn+"","second test");
            os.close();
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //label.setText("File not found");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            //label.setText("IOException");
            e.printStackTrace();
        }

    }

    public void load_game() {

        //loadedgame = gameclass.load_from_model();


        try {
            FileInputStream fis = getActivity().openFileInput("new_file");
            //ObjectInputStream ois = new ObjectInputStream(fis);
            ObjectInputStream is = new ObjectInputStream(fis);
            loadedgame = (GameClass) is.readObject(); // read object
            Log.d(gameclass.turn+"","third test");
            //loadedgame = (GameClass) objectInputStream.readObject();

            //label.setText("Show name: " + p.name + " Show age: " + p.age);
            is.close();

        }
        catch (StreamCorruptedException e) {
            //label.setText("Stream corrupted");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            //label.setText("File not found 2");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            //label.setText("IOException");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            //label.setText("Class not found");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        fill_board_from_old_game();
    }

    public void fill_board_from_old_game(){
        for(int i=0; i<6; ++i){
            for(int j=0; j<7; ++j){

                ViewGroup row = (ViewGroup) ((ViewGroup) boardView).getChildAt(i);
                //row.setClipChildren(false);
                ImageView imageView = (ImageView) row.getChildAt(j);
                if(loadedgame.board[i][j]==1){
                    imageView.setImageResource(R.drawable.yellow);
                }
                else if(loadedgame.board[i][j]==2){
                    imageView.setImageResource(R.drawable.red);
                }
                else
                    imageView.setImageResource(R.drawable.empty);

            }
        }
        gameclass = loadedgame;

    }

    private int colAtX(float x) {
        float colWidth = cells[0][0].getWidth();
        int col = (int) x / (int) colWidth;
        if (col < 0 || col > 6)
            return -1;
        return col;
    }

}
