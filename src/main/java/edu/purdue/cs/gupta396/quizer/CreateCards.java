package edu.purdue.cs.gupta396.quizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CreateCards extends AppCompatActivity{
    private EditText side1text;
    private EditText side2Text;
    private Button anotherButton;
    private Button doneButton;
    private ArrayList cards = new ArrayList();
    Intent newFacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_cards);
        side1text = findViewById(R.id.side1Text);
        side2Text = findViewById(R.id.side2Text);
        anotherButton = findViewById(R.id.addanother_button);
        doneButton = findViewById(R.id.done_buttoncc);
        anotherButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try {
                    cards.add(side1text.getText().toString());
                    cards.add(side2Text.getText().toString());
                    side2Text.setText(" ");
                    side1text.setText(" ");
                }catch(Exception e){
                    System.out.println("Error occurred in onClick of CreateCards.java");
                    e.printStackTrace();
                }
            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cards.add(side1text.getText().toString());
                    cards.add(side2Text.getText().toString());
                    newFacts = new Intent();
                    newFacts.putExtra("NewCardsName", cards);
                    setResult(RESULT_OK, newFacts);
                    finish();
                }catch(Exception e){
                    System.out.println("Error occurred in onClick of doneButton of CreateCards.java");
                    e.printStackTrace();
                }
            }
        });
    }
}
