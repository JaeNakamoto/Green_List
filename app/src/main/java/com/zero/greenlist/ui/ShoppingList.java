package com.zero.greenlist.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        Collections.addAll(boodschappenlijst, "product 1", "product 2", "Product 3");
        boodschappenlijst.add("product 4");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, boodschappenlijst);
        lv = (ListView)  findViewById(R.id.listView);
        lv.setAdapter(adapter);
    }
}
