package com.example.fragmentexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private int currentStep = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar

//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
        //FLAG_FULLSCREEN is deprecated on ver API 30
        // Instead of: window.insetsController.hide(WindowInsets.Type.statusBars());
        //WindowInsetsController.BEHAVIOR_SHOW_BARS_BY_SWIPE //Hay ne?
//        WindowInsetsController.hide(android.view.WindowInsets.Type.STATUS_BARS);
        getWindow().getInsetsController().hide(WindowInsets.Type.statusBars());

        setContentView(R.layout.activity_main);

        initFragment();

        @SuppressLint("CutPasteId") Button btn_next = findViewById(R.id.btn_next);
        @SuppressLint("CutPasteId") Button btn_back = findViewById(R.id.btn_back);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                replaceFrament(new Fragment2());
                Fragment newFragment;
                switch (currentStep){
                    case 1:
                        Log.d(TAG, "onClick: currentStep = 1");
                        newFragment = new Fragment2();
                        break;
                    case 2:
                        newFragment = new Fragment3();
                        break;
                    default:
                        newFragment = null;
                        break;
                }
                addFragmentToBackStack(newFragment);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.onBackPressed();
            }
        });
    }

    private void addFragmentToBackStack(Fragment fragment) {
        String tag = "fragment" + Integer.toString(currentStep+1);
        Log.d(TAG, "addFragmentToBackStack: " + tag);
        if (fragment != null){
            if (null == getSupportFragmentManager().findFragmentByTag(tag)) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, fragment, tag)
                        .addToBackStack("list")
                        .commit();
                currentStep++;
                Log.d(TAG, "addFragmentToBackStack: currentStep: " + Integer.toString(currentStep));
                Log.d(TAG, "addFragmentToBackStack: addToBackStack Completed");
            }
        }
    }

    private void replaceFrament(Fragment2 fragment2) {
        if (fragment2 != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment2);
            fragmentTransaction.commit();
        }
    }

    private void initFragment() {
        currentStep = 1;
        String tag = "fragment" + Integer.toString(currentStep);
        Fragment1 fragment1 = new Fragment1();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment1, tag);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (currentStep > 0){
            currentStep--;
        };
        super.onBackPressed();
    }
}