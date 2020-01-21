package com.zero.greenlist.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.zero.greenlist.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingList extends AppCompatActivity {

    ArrayList<String> boodschappenlijst = null;
    ArrayAdapter<String> adapter = null;
    ListView lv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        Intent intent = getIntent();
        String listNaam = intent.getStringExtra(CreateList.STRING_NAAM);
        TextView textView = findViewById(R.id.list_titel);
        textView.setText(listNaam);

        boodschappenlijst = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, boodschappenlijst);
        lv = (ListView)  findViewById(R.id.listView);
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shopping_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_sort) {
            Collections.sort(boodschappenlijst);
            lv.setAdapter(adapter);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Geeft een pop-up om een nieuwe list item toe te voegen
    public void addNewItem(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Item");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, which) -> {
            boodschappenlijst.add(prefferedCase(input.getText().toString()));
            Collections.sort(boodschappenlijst);
            lv.setAdapter(adapter);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    //Zet de eerste letter van een string om naar een hoofdletter
    public static String prefferedCase(String original) {

        if(original.isEmpty()) {
            return original;
        }

        return original.substring(0, 1).toUpperCase() + original.substring(1).toLowerCase();
    }
}
