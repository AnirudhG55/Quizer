package edu.purdue.cs.gupta396.quizer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private Button createNew;
    private Button deleteButton;
    private ListView stacksList;
    SharedPreferences settings;
    private ArrayList stacks = new ArrayList<>();
    private ArrayList<ArrayList<String>> stacksFromIntent = new ArrayList<ArrayList<String>>();
    private ArrayAdapter adapter;
    private int currentStackIndex = 0;
    private final static int SELECTOR_CONST_CREATE = 1;
    private final static int SELECTOR_CONST_FLASHLIST = 2;
    private  SharedPreferences.Editor editor;
    private ArrayList stackfromintent;
    private StringBuilder stacksToStore = new StringBuilder();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = getSharedPreferences("PREFS", 0);

        String temp = settings.getString("stacks",null);
        if(temp != null) {
            stacksToStore.append(settings.getString("stacks",null));
            String[] stacksFromSettings = temp.split("-");
            ArrayList stacksFromSettingArray = new ArrayList<>();
            for (int i = 0; i < stacksFromSettings.length; i++) {
                stacksFromSettingArray = new ArrayList();
                String[] individualStack = stacksFromSettings[i].split(",");

                ArrayList reFormatCards = new ArrayList();
                stacksFromSettingArray.add(individualStack[0]);
                stacks.add(individualStack[0]);
                for (int j = 1; j < individualStack.length; j++) {
                    reFormatCards.add(individualStack[j]);
                }
                stacksFromSettingArray.add(reFormatCards);
                stacksFromIntent.add(stacksFromSettingArray);
            }
        }
        createNew = findViewById(R.id.create_button);
        adapter = new ArrayAdapter<>(this, R.layout.stacklistview, stacks);
        stacksList = findViewById(R.id.stackList);
        stacksList.setAdapter(adapter);
        stacksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    ArrayList check = (ArrayList) stacksFromIntent.get(currentStackIndex);
                    ArrayList checkCards = (ArrayList) check.get(1);
                    if(checkCards.size() > 0) {
                        currentStackIndex = (int) l;
                        Intent myIntent = new Intent(MainActivity.this, Quiz.class);
                        ArrayList<String> stack2pass = stacksFromIntent.get(currentStackIndex);
                        myIntent.putExtra("Stack", stack2pass);
                        startActivityForResult(myIntent, SELECTOR_CONST_FLASHLIST);
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle("Can't Quiz!");
                        builder.setMessage("Sorry the stack you have chosen does not contain any cards. Make sure to create a stack " +
                                "with cards in order to quiz. ");
                        builder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        builder.show();
                    }
                }catch(Exception e ){
                    System.out.println("Error occurred");
                }
            }
        });
    }
    public void createNew(View view){
        try {
            Intent myIntent = new Intent(MainActivity.this, CreateName.class);
            startActivityForResult(myIntent, SELECTOR_CONST_CREATE);
        }catch(Exception e ){
            System.out.println("error has occurred");
            e.printStackTrace();
        }
    }
    public void deleteStack(View view) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Delete A Stack");
            builder.setMessage("Enter the name of the stack you wish to delete?");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton("Delete",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editor = settings.edit();
                            ArrayList temp = new ArrayList();
                            stacksToStore.append(settings.getString("stacks", null));
                            StringBuilder remove = new StringBuilder();
                            for (int i = 0; i < stacksFromIntent.size(); i++) {
                                temp = (ArrayList) stacksFromIntent.get(i);
                                String stackName = (String) temp.get(0);
                                stackName = stackName.toLowerCase();
                                if (stackName.equals((input.getText().toString()).toLowerCase())) {
                                    stacksFromIntent.remove(i);
                                    stacks.remove(i);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            for(int j = 0 ; j < stacksFromIntent.size(); j++){
                                ArrayList temp1 = new ArrayList();
                                temp1 = stacksFromIntent.get(j);
                                String stackName = temp1.get(0).toString();
                                remove.append(stackName);
                                remove.append(",");
                                ArrayList cards = (ArrayList)temp1.get(1);
                                String card;
                                for(int f = 0; f < cards.size(); f++){
                                    card = cards.get(f).toString();
                                    remove.append(card);
                                    remove.append(",");
                                }
                                remove.append("-");
                            }
                            editor.putString("stacks",remove.toString());
                            editor.commit();
                        }
                    });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problem in delete button method in main activity");
        }
    }
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        switch (requestCode) {
            case SELECTOR_CONST_CREATE:
                if (resultCode == RESULT_OK) {
                    stackfromintent = data.getExtras().getStringArrayList("NewStackWNoCards");
                    stacksFromIntent.add(stackfromintent);
                    stacks.add((stackfromintent.get(0).toString()));
                    adapter.notifyDataSetChanged();
                    editor = settings.edit();

                    for(int i = 0 ; i < stackfromintent.size(); i++){
                        if(i == 0){
                            stacksToStore.append(stackfromintent.get(i).toString());
                            stacksToStore.append(",");
                        }else if(i == 1){
                            ArrayList temp2 = new ArrayList<String>();
                            temp2 = (ArrayList) stackfromintent.get(1);
                            for(int f = 0; f < temp2.size(); f++ ){
                                stacksToStore.append(temp2.get(f).toString());
                                stacksToStore.append(",");
                            }
                            stacksToStore.append("-");
                        }
                    }
                    editor.putString("stacks",stacksToStore.toString());
                    editor.commit();
                }
                break;
            case SELECTOR_CONST_FLASHLIST:
                break;
        }
    }
}
