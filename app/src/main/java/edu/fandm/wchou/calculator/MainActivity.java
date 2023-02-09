package edu.fandm.wchou.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GridView gv;
    String[] keypad = new String[]{
            "1", "2", "3", "+",
            "4", "5", "6", "-",
            "7", "8", "9", "*",
            "0", ".", ":)", "/",
            "(", ")", "DEL", "^",
    };
    List<String> list = new ArrayList<String>(Arrays.asList(keypad));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gv = (GridView)findViewById(R.id.grid);
        gv.setAdapter(new ArrayAdapter<String>(this, R.layout.keypad, list));

    }
}