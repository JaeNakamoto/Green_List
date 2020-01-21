package com.zero.greenlist.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
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
        Collections.addAll(boodschappenlijst, "product 1", "product 2", "product 3", "product 4", "product 5", "product 6", "product 7", "product 8", "product 9");
        boodschappenlijst.add("product 10");
        boodschappenlijst.add("product 11");
        boodschappenlijst.add("product 12");
        boodschappenlijst.add("product 13");
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
}
