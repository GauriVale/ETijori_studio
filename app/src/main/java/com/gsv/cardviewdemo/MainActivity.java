package com.gsv.cardviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gsv.etijori.R;

public class MainActivity extends Activity {

    public static final String TAG = "CARD_DEMO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mBtnStart = (Button) findViewById(R.id.btnStart);
        mBtnStart.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "Start button is clicked.");
                //findViewById(R.id.container).setVisibility(View.VISIBLE);
                getFragmentManager().beginTransaction()
                        .add(getContainer(), CardViewFragment.newInstance())
                        .commit();
            }
        });
    }

    private int getContainer() {
        return R.id.container;
    }
}
