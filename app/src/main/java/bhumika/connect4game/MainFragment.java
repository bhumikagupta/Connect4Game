package bhumika.connect4game;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by bhumi on 11/7/2017.
 */

public class MainFragment extends Fragment {

    private View mMainView;
    GameClass game_class = new GameClass();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.activity_main, null);
        final ImageView imageView = (ImageView) mMainView.findViewById(R.id.image);
        mMainView.setDrawingCacheEnabled(false);

        Button newButton = mMainView.findViewById(R.id.new_button);

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((getResources().getConfiguration().screenLayout &
                        Configuration.SCREENLAYOUT_SIZE_MASK) !=
                        Configuration.SCREENLAYOUT_SIZE_LARGE) {
                    // on a small screen device
                    Intent intent = new Intent(getActivity(), GameActivity.class);
                    String ID = "resetting";
                    intent.putExtra("SESSION_ID", ID);
                    startActivity(intent);

                }
                else{
                    //TODO: reset the the board
                    //notify controller to reset
                    //notify();
                    //game_class.reset_board();
                    //((MainActivity)getActivity()).checkFragment();
                    //resetBoard();


                    ((GameFragment) getActivity().getFragmentManager().findFragmentById(R.id.game_frag)).reset_now();



                }

            }

        });

        Button loadButton = mMainView.findViewById(R.id.load_button);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                //startActivity(intent);

                if ((getResources().getConfiguration().screenLayout &
                        Configuration.SCREENLAYOUT_SIZE_MASK) !=
                        Configuration.SCREENLAYOUT_SIZE_LARGE) {
                    // on a small screen device
                    Intent intent = new Intent(getActivity(), GameActivity.class);
                    String ID = "loading";
                    intent.putExtra("SESSION_ID", ID);
                    startActivity(intent);

                }
                else {
                    ((GameFragment) getActivity().getFragmentManager().findFragmentById(R.id.game_frag)).load_game();
                }
            }
        });



        return mMainView;
    }


}
