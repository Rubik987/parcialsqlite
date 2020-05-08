package com.dorellano.parcialsqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mainActivity extends AppCompatActivity implements View.OnClickListener {
    Button addContact,listContact,exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        addContact = findViewById(R.id.btnAdd);
        listContact = findViewById(R.id.btnList);
        exit = findViewById(R.id.exit);
        addContact.setOnClickListener(this);
        listContact.setOnClickListener(this);
        exit.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnAdd:
                startActivity(new Intent(mainActivity.this, addPersonaActivity.class));
                //Toast.makeText(this, "Agregar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnList:
                startActivity(new Intent(mainActivity.this, listActivity.class));
                // Toast.makeText(this, "Listar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit:
                // Toast.makeText(this, "Salir", Toast.LENGTH_SHORT).show();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                break;
        }
    }
}
