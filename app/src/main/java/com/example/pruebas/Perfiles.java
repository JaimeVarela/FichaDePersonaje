package com.example.pruebas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Perfiles extends AppCompatActivity {

    public ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfiles);
        asociar();
        iniciar();
        eventos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lista_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.m_salir:
                salir();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void asociar(){
        lv = (ListView)findViewById(R.id.lv);
    }

    private void iniciar(){
        Personaje pj = new Personaje
                (this, Personaje.DATABASE_NAME, null, Personaje.DATABASE_VERSION);

        //Creamos un objeto del tipo SQLiteDatabase y de nombre BaseDeDatos
        //El objeto lo crea y lo retorna la variable anterior admin
        SQLiteDatabase BaseDeDatos = pj.getReadableDatabase();

        ArrayList<String> nombres = new ArrayList<String>();
        try {
            //Se realiza la consulta en la base datos
            //Obtiene los datos resultado de una consulta SELECT SQL
            //SELECT descripcion, precio
            //FROM articulos
            //WHERE codigo = codigo_tecleado
            Cursor fila = BaseDeDatos.rawQuery
                    ("select nombre from personajes", null);

            if (!fila.moveToFirst()) {
                toast("No existe ningún personaje");
            }
            else{
                fila.moveToFirst();
                nombres.add(fila.getString(0));
                for(int i = 1; i < fila.getCount(); i++){
                    fila.moveToNext();
                    nombres.add(fila.getString(0));
                }
            }
            nombres.add("Crear nuevo personaje");

        }catch (Exception ex){
            toast(ex.getMessage());
        }

        //Mostrar los nombres de los personajes
        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nombres);
        lv.setAdapter(adaptador);
    }

    //Crea los listener de eventos y sus acciones
    private void eventos(){
        //Hacemos un evento Listener para el Item seleccionado
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cambiar(position);
            }
        });
    }

    private void cambiar(int i){
        Intent actividad = new Intent(this, FichaGeneral.class);
        actividad.putExtra("codigo", String.valueOf(i));
        startActivity(actividad);
    }

    private void salir(){
        finish();
    }

    public void toast(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }
}
