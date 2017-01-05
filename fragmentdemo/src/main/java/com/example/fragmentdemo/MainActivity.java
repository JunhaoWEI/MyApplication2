package com.example.fragmentdemo;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener, PlusOneFragment.OnFragmentInteractionListener {


    private TextView textView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView2);

        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content, BlankFragment.newInstance("1", "2"), "blankFragment");
//        fragmentTransaction.addToBackStack("blank");
        fragmentTransaction.commit();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().add(R.id.content, PlusOneFragment.newInstance("", ""), "PlusOneFragment")
                .commit();
//                textView.setText(fragmentManager.getFragments() == null ? "null" : fragmentManager.getFragments().size() + "");
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("blankFragment")).commit();
                if (fragmentManager.getFragments() != null) {
                    textView.setText(getFragmentName());
                }

            }
        });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(MainActivity.this, uri + "", Toast.LENGTH_SHORT).show();
        if (fragmentManager.getFragments() != null) {
            textView.setText(getFragmentName());
        }
    }

    private String getFragmentName() {
        StringBuilder sb = new StringBuilder();
//        sb.append(textView.getText());
        sb.append(fragmentManager.getFragments().size() + "");
        for (Fragment fragment : fragmentManager.getFragments()) {
            sb.append(fragment.getTag() + "\n");
        }
        return sb.toString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (fragmentManager.getFragments() != null) {
            textView.setText(getFragmentName());
        }
    }
}
