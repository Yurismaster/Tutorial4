package com.example.tutorial4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    MenuFragment menuFragment = new MenuFragment();
    NoteTakingFragment noteTakingFragment = new NoteTakingFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMenuFragment();
        MainActivityData mainActivityDataViewModel = new ViewModelProvider(this)
                .get(MainActivityData.class);
        mainActivityDataViewModel.clickedValue.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(mainActivityDataViewModel.getClickedValue()==0){
                    loadNotetakingFragment();
                }
                if(mainActivityDataViewModel.getClickedValue()==2){
                    loadMenuFragment();
                }
            }
        });
    }

    private void loadMenuFragment(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.menu_container);
        findViewById(R.id.notetaking_container).setVisibility(View.GONE);
        int screenOrientation = getResources().getConfiguration().orientation;
        if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            fm.beginTransaction().replace(R.id.menu_container, menuFragment).commit();
            findViewById(R.id.menu_container).setVisibility(View.VISIBLE);
        }
        else if(screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            findViewById(R.id.menu_container).setVisibility(View.VISIBLE);
            fm.beginTransaction().replace(R.id.menu_container, menuFragment).commit();
        }
    }
    private void loadNotetakingFragment(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.notetaking_container);
        int screenOrientation = getResources().getConfiguration().orientation;
        if(frag == null) {
            if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
                findViewById(R.id.menu_container).setVisibility(View.GONE);
                fm.beginTransaction().replace(R.id.notetaking_container, noteTakingFragment).commit();
                //findViewById(R.id.notetaking_container).setVisibility(View.VISIBLE);
            } else if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                fm.beginTransaction().add(R.id.notetaking_container, noteTakingFragment).commit();
            }
        }
        else{
            findViewById(R.id.menu_container).setVisibility(View.GONE);
            //findViewById(R.id.notetaking_container).setVisibility(View.VISIBLE);
        }
        findViewById(R.id.notetaking_container).setVisibility(View.VISIBLE);
    }
}