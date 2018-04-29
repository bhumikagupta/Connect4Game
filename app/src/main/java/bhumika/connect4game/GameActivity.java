package bhumika.connect4game;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by bhumi on 11/7/2017.
 */

public class GameActivity extends Activity {

    //GameClass unicorn = new GameClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(null);

        setContentView(R.layout.fragment_2);
        GameFragment gameFragment = (GameFragment)getFragmentManager().findFragmentById(R.id.game_frag);
        //((GameFragment) getActivity().getFragmentManager().findFragmentById(R.id.game_frag)).reset_now();
        String s = getIntent().getStringExtra("SESSION_ID");
        if(s.equals("resetting")) {
            gameFragment.reset_now();
        }
        else {
            gameFragment.load_game();
        }
    }


}


