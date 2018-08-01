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

public class CreateName extends AppCompatActivity{
    private EditText stackNameView;
    private Button createNameButton;
    private Button createFlashCards;
    Intent newStackName;
    Intent newCardsName;
    ArrayList cards = new ArrayList();
    ArrayList stack = new ArrayList<>();
    String stackName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        createNameButton = findViewById(R.id.done_buttoncn);
        createFlashCards = findViewById(R.id.createCards_button);
        stackNameView = findViewById(R.id.stackNameText);
        newStackName = new Intent();
        createNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    stackName = stackNameView.getText().toString();
                    stack.add(stackName);
                    stack.add(cards);
                    newStackName.putExtra("NewStackWNoCards", stack);
                    setResult(RESULT_OK, newStackName);
                    finish();
                } catch (Exception e) {
                    System.out.println("Error occurred");
                    e.printStackTrace();
                }
            }
        });
    }

    public void onCreateNewCards (View view){
        try {
            newCardsName = new Intent(CreateName.this, CreateCards.class);
            startActivityForResult(newCardsName, 3);
        } catch (Exception e) {
            System.out.println("Error occurred");
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 3:
                if (resultCode == RESULT_OK) {
                    cards = (data.getExtras().getStringArrayList("NewCardsName"));
                    stackName = stackNameView.getText().toString();
                }
                break;
        }
    }
}