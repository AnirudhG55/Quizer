package edu.purdue.cs.gupta396.quizer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Quiz extends AppCompatActivity {
        private Button next_Button;
        private View flipView;
        private TextView factsView;
        private TextView nameOfStackView;
        private Button flipButton;
        private ArrayList stack = new ArrayList();
        private Intent doneQuizing;
        private int i = 0;
        private ArrayList cards;

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_quiz);
            next_Button = findViewById(R.id.next_button);
            factsView = findViewById(R.id.facts_view);
            nameOfStackView = findViewById(R.id.nameOfStackView);
            flipButton = findViewById(R.id.flip_button);
            stack = getIntent().getStringArrayListExtra("Stack");
            nameOfStackView.setText(stack.get(0).toString());
            cards = (ArrayList) stack.get(1);
            factsView.setText(cards.get(i).toString());
            flipButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    try{
                        i++;
                        factsView.setText(cards.get(i).toString());
                    }catch(Exception e){
                        System.out.println("Error in flipbutton onClick in Quiz.java");
                        e.printStackTrace();
                    }
                }
            });

            next_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        i++;
                        if(i >= (cards.size() - 1)){
                            AlertDialog.Builder builder = new AlertDialog.Builder(Quiz.this);
                            builder.setCancelable(true);
                            builder.setTitle("You've Reached the end of the Stack!");
                            builder.setMessage("Do you want to return to the Main Menu or Beginning of the Stack?");
                            builder.setPositiveButton("Main Menu",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent myIntent = new Intent(Quiz.this, MainActivity.class);
                                            startActivity(myIntent);
                                        }
                                    });
                            builder.setNegativeButton("Beginning of Stack", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    i = 0;
                                    factsView.setText(cards.get(i).toString());
                                }
                            });
                            builder.show();
                            //pop up return to menu
                        }else {
                            if (i % 2 == 1) {
                                i++;
                                factsView.setText(cards.get(i).toString());
                            } else if (i % 2 == 0) {
                                factsView.setText(cards.get(i).toString());
                            }
                        }
                    }catch(Exception e){
                        System.out.println("Error in nextbutton onClick in Quiz.java");
                        e.printStackTrace();
                    }
                }
            });
        }
    }
