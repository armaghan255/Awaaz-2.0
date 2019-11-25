package com.example.awaaz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class OnBoardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PaperOnboardingPage page = new PaperOnboardingPage("DONATE BLOOD","Donate Blood , Donate Life", R.color.green,R.drawable.settings,R.drawable.settings);
        PaperOnboardingPage page1 = new PaperOnboardingPage("HELP WHO NEEDS","A life may depend on a gesture from you, a bottle of Blood.!", Color.parseColor("#BF1F2D"),R.drawable.upload,R.drawable.upload);
        PaperOnboardingPage page2 = new PaperOnboardingPage("REQUEST BLOOD","A bottle of blood saved my life, was it yours.?", R.color.WhiteBg,R.drawable.video,R.drawable.video);
        ArrayList<PaperOnboardingPage> arrayList = new ArrayList<>();
        arrayList.add(page);
        arrayList.add(page1);
        arrayList.add(page2);
        PaperOnboardingFragment onboardingFragment = PaperOnboardingFragment.newInstance(arrayList);
        FragmentTransaction ft;
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.con,onboardingFragment);
        ft.commit();
        onboardingFragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
