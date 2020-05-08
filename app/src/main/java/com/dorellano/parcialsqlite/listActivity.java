package com.dorellano.parcialsqlite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.util.Log;

import com.dorellano.parcialsqlite.helpers.person;
import com.dorellano.parcialsqlite.helpers.personContract;
import com.dorellano.parcialsqlite.helpers.personDBHelper;

import java.util.ArrayList;

public class listActivity extends AppCompatActivity {
    personDBHelper con;
    SQLiteDatabase db;
    RecyclerView recyclerView;
    recyclerViewAdapter adapter;
    private static final String TAG = "listActivity";
    private ArrayList<person> people = new ArrayList<>();


    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView");
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new recyclerViewAdapter(people, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 122:
                String id = String.valueOf(people.get(adapter.getPosition()).getId());
                Intent intent = new Intent(listActivity.this, editActivity.class);
                intent.putExtra("ID", id);
                this.finish();
                startActivity(intent);

                break;
            case 123:
                int idEliminar = people.get(adapter.getPosition()).getId();
                Log.e("ELIMINAREL:", people.get(adapter.getPosition()).getNombre()+"CON ID"+ people.get(adapter.getPosition()).getId());
                deleteSql(idEliminar);
                //Toast.makeText(this, "Eliminar", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void consultarSql() {
        con = new personDBHelper(this, "bd_personas", null, 1);
        try {
            SQLiteDatabase db = con.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + personContract.TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
//                    int id = cursor.getInt(cursor.getColumnIndex(personContract.ID));
                    int id = cursor.getInt(0);
//                    String nombre = cursor.getString(cursor.getColumnIndex(personContract.NOMBRE));
                    String nombre = cursor.getString(1);
                    String cedula = cursor.getString(cursor.getColumnIndex(personContract.CEDULA));
                    String Estrato = cursor.getString(cursor.getColumnIndex(personContract.ESTRATO));
                    String Salario = cursor.getString(cursor.getColumnIndex(personContract.SALARIO));
                    String NivelE = cursor.getString(cursor.getColumnIndex(personContract.NIVELE));
                    cursor.moveToNext();
                    //String nombre, String cedula, String salario, String estrato, String nivelEdu
                    people.add(new person(id, nombre,cedula,Salario,Estrato,NivelE));
                    Log.e("PERSONAAAAAA", new person(id, nombre,cedula,Salario,Estrato,NivelE).toString());
                }
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.d("INFOOOOOOOOOOOOOO", "ERROR SQL");
            Log.d("INFOOOOOOOOOOOOOO", e.toString());
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void deleteSql(int ID) {
        con = new personDBHelper(this, "bd_personas", null, 1);
        SQLiteDatabase db = con.getWritableDatabase();
        long idResultado = db.delete(personContract.TABLE_NAME, "id = ?", new String[]{String.valueOf(ID)});
        Toast.makeText(this, "Persona Eliminada:" + idResultado, Toast.LENGTH_SHORT).show();
        db.close();
        this.finish();
        startActivity(new Intent(this, mainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //Toast.makeText(listActivity.this, "Text Changed", Toast.LENGTH_SHORT).show();
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Log.d(TAG, "Created");
        consultarSql();
        initRecyclerView();
        registerForContextMenu(recyclerView);

    }
}
