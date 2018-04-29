package bhumika.connect4game;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MainFragment mainFragment = (MainFragment)getFragmentManager().findFragmentById(R.id.titles_frag);



    }


}
