package com.zero.greenlist.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.zero.greenlist.R;

public class CreateList extends AppCompatActivity {

    public static final String STRING_NAAM = "com.zero.greenlist.greenlist.extra.NAAM";
    private EditText mNaamEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        mNaamEditText = findViewById(R.id.new_list_name);
    }

    public void launchListActivity(View view) {
        Intent intent = new Intent(this, ShoppingList.class);
        String listNaam = mNaamEditText.getText().toString();
        intent.putExtra(STRING_NAAM, listNaam);
        startActivity(intent);
    }

}
