package com.dorellano.parcialsqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dorellano.parcialsqlite.helpers.person;
import com.dorellano.parcialsqlite.helpers.personContract;
import com.dorellano.parcialsqlite.helpers.personDBHelper;

import java.util.ArrayList;

public class editActivity extends AppCompatActivity {

    personDBHelper con;
    private ArrayList<person> people = new ArrayList<>();
    EditText etNombre, etCedula, etSalrio;
    Button updateButton;
    Spinner estratoSpinner,nivelESpinner;

    public void writeInformation() {
        try {
            etNombre.setText(people.get(0).getNombre());
            etCedula.setText(people.get(0).getCedula());
            etSalrio.setText(people.get(0).getSalario());
            etCedula.setFocusable(false);
            etCedula.setEnabled(false);
            etCedula.setCursorVisible(false);
            etCedula.setKeyListener(null);
            etCedula.setBackgroundColor(Color.TRANSPARENT);
        }catch (Exception e){
            Log.e("ERROR",e.getMessage());
        }
    }

    private void updateSql(int ID) {
        if (TextUtils.isEmpty(etNombre.getText().toString())) {
            etNombre.setError("No hay nombre");
            return;
        }
        if (TextUtils.isEmpty(etCedula.getText().toString())) {
            etCedula.setError("No hay Sistolico");
            return;
        }
        if (TextUtils.isEmpty(etSalrio.getText().toString())) {
            etSalrio.setError("No hay Distolico");
            return;
        }
        con = new personDBHelper(this, "bd_personas", null, 1);
        SQLiteDatabase db = con.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(personContract.NOMBRE, etNombre.getText().toString());
        values.put(personContract.CEDULA, etCedula.getText().toString());
        values.put(personContract.SALARIO, etSalrio.getText().toString());
        values.put(personContract.NIVELE, nivelESpinner.getSelectedItem().toString());
        long idResultado = db.update(personContract.TABLE_NAME, values, "id = ?", new String[]{String.valueOf(ID)});
        Toast.makeText(this, "Registro Actualizado:"+idResultado , Toast.LENGTH_SHORT).show();
        db.close();
        this.finish();
        startActivity(new Intent(editActivity.this, mainActivity.class));
    }
    private void consultarSql(int ID) {
        con = new personDBHelper(this, "bd_personas", null, 1);
        try {
            SQLiteDatabase db = con.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + personContract.TABLE_NAME+" WHERE id =" + ID, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String id = cursor.getString(0);
//                    String nombre = cursor.getString(cursor.getColumnIndex(personContract.NOMBRE));
                    String nombre = cursor.getString(1);
                    String cedula = cursor.getString(cursor.getColumnIndex(personContract.CEDULA));
                    String Estrato = cursor.getString(cursor.getColumnIndex(personContract.ESTRATO));
                    String Salario = cursor.getString(cursor.getColumnIndex(personContract.SALARIO));
                    String NivelE = cursor.getString(cursor.getColumnIndex(personContract.NIVELE));
                    cursor.moveToNext();
                    people.add(new person(Integer.parseInt(id), nombre,cedula,Salario,Estrato,NivelE));
                    //String nombre, String cedula, String salario, String estrato, String nivelEdu
                }
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.d("Error", e.toString());
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        final int ID = Integer.valueOf(getIntent().getStringExtra("ID"));
        setContentView(R.layout.activity_add_contact);
        estratoSpinner = findViewById(R.id.gruopEstrato);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.estrato, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estratoSpinner.setAdapter(adapter);
        nivelESpinner = findViewById(R.id.groupNivelE);
        adapter = ArrayAdapter.createFromResource(this, R.array.nivel_educate, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nivelESpinner.setAdapter(adapter);
        etNombre = findViewById(R.id.etNombre);
        etCedula = findViewById(R.id.etCedula);
        etSalrio = findViewById(R.id.etSalario);
        updateButton = findViewById(R.id.ingresarButton);
        consultarSql(ID);
        writeInformation();
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSql(ID);
            }
        });
    }
}
