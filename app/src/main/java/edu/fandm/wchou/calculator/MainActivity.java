package edu.fandm.wchou.calculator;

import static edu.fandm.wchou.calculator.ShuntingYard.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final String TAG = "MAIN";
    private String[] keypad = new String[]{
            "1", "2", "3", "+",
            "4", "5", "6", "-",
            "7", "8", "9", "*",
            "0", ".", ":)", "/",
            "(", ")", "DEL", "^",
    };

    private String operation = "";
    private String[] historyList;

    List<String> list = new ArrayList<String>(Arrays.asList(keypad));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView  keyBoard = (GridView)findViewById(R.id.grid);

        TextView field = findViewById(R.id.field_tv);

        keyBoard.setAdapter(new ArrayAdapter<String>(this, R.layout.keypad, list));
        keyBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selected = (String) (keyBoard.getItemAtPosition(position));

                if (selected == "DEL"){
                    if(operation.length()==0) return;
                    operation = operation.substring(0, operation.length()-1);
                }
                else if(selected == ":)"){
                    return;
                }


                else {
                    operation += selected;
                }
                //some logic here to start a new operation if the screen contains '='
                field.setText(operation);


            }
        });

        Button clear = (Button)findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation = "";
                field.setText(operation);
            }
        });

        Button enter = (Button)findViewById(R.id.enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ans = String.valueOf(calculate(convertToPostFix(operation)));

                operation += "=" + ans;
                field.setText(operation);

            }
        });

        Button historyBT = (Button)findViewById(R.id.history_bt);
        historyBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), History.class);
                i.putExtra("History", historyList);
                startActivity(i);
            }
        });



    }
}