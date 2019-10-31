package com.example.pruebas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private EditText nombre, pg, ca, vel, nv, exp;
    private Spinner raza, clase;
    private ArrayList<EditText> stats;
    private TextView dados;
    private ArrayList<CheckBox> salvacion;
    private ArrayList<CheckBox> habilidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        asociar();
        iniciar();
    }

    private void asociar(){
        nombre = (EditText)findViewById(R.id.nombre);

        pg = (EditText)findViewById(R.id.PG);
        ca = (EditText)findViewById(R.id.CA);
        vel = (EditText)findViewById(R.id.VEL);
        nv = (EditText)findViewById(R.id.NV);
        exp = (EditText)findViewById(R.id.EXP);

        stats = new ArrayList<EditText>();
        stats.add((EditText)findViewById(R.id.FUEvalor));
        stats.add((EditText)findViewById(R.id.DESvalor));
        stats.add((EditText)findViewById(R.id.CONvalor));
        stats.add((EditText)findViewById(R.id.INTvalor));
        stats.add((EditText)findViewById(R.id.SABvalor));
        stats.add((EditText)findViewById(R.id.CARvalor));

        dados = (TextView)findViewById(R.id.dados);

        salvacion = new ArrayList<CheckBox>();
        salvacion.add((CheckBox)findViewById(R.id.FUEcb));
        salvacion.add((CheckBox)findViewById(R.id.DEScb));
        salvacion.add((CheckBox)findViewById(R.id.CONcb));
        salvacion.add((CheckBox)findViewById(R.id.INTcb));
        salvacion.add((CheckBox)findViewById(R.id.SABcb));
        salvacion.add((CheckBox)findViewById(R.id.CARcb));

        habilidades = new ArrayList<CheckBox>();
        habilidades.add((CheckBox)findViewById(R.id.acrobaciasCb));
        habilidades.add((CheckBox)findViewById(R.id.arcanosCb));
        habilidades.add((CheckBox)findViewById(R.id.atletismoCb));
        habilidades.add((CheckBox)findViewById(R.id.engañarCb));
        habilidades.add((CheckBox)findViewById(R.id.historiaCb));
        habilidades.add((CheckBox)findViewById(R.id.interpretacionCb));
        habilidades.add((CheckBox)findViewById(R.id.intimidarCb));
        habilidades.add((CheckBox)findViewById(R.id.investigacionCb));
        habilidades.add((CheckBox)findViewById(R.id.juegoDeManosCb));
        habilidades.add((CheckBox)findViewById(R.id.medicinaCb));
        habilidades.add((CheckBox)findViewById(R.id.naturalezaCb));
        habilidades.add((CheckBox)findViewById(R.id.percepcionCb));
        habilidades.add((CheckBox)findViewById(R.id.perspicaciaCb));
        habilidades.add((CheckBox)findViewById(R.id.persuasionCb));
        habilidades.add((CheckBox)findViewById(R.id.religionCb));
        habilidades.add((CheckBox)findViewById(R.id.sigiloCb));
        habilidades.add((CheckBox)findViewById(R.id.supervivenciaCb));
        habilidades.add((CheckBox)findViewById(R.id.tratoConAnimalesCb));
    }

    private void iniciar(){
        //Asociación de mi spn con el control spn1 en la vista
        raza = (Spinner)findViewById(R.id.spn1);
        //Ahora hay que crear un adaptador entre el array de opciones
        //y el control Spinner
        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, BD.razas);
        raza.setAdapter(adaptador);

        //Asociación de mi spn con el control spn1 en la vista
        clase = (Spinner)findViewById(R.id.spn2);
        //Ahora hay que crear un adaptador entre el array de opciones
        //y el control Spinner
        ArrayAdapter<String> adaptador2 =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, BD.clases);
        clase.setAdapter(adaptador2);

        mostrar_datos();
    }

    private void mostrar_datos(){

        nombre.setText(getIntent().getStringExtra("nombre"));

        for(int i=0; i < BD.razas.length; i++){
            if(BD.razas[i].equals(getIntent().getStringExtra("raza"))){
                raza.setSelection(i);
                break;
            }
        }

        for(int i=0; i < BD.clases.length; i++){
            if(BD.clases[i].equals(getIntent().getStringExtra("clase"))){
                clase.setSelection(i);
                break;
            }
        }

        if(getIntent().getStringArrayExtra("stats") != null){
            String[] statsValue = getIntent().getStringArrayExtra("stats");
            for(int i=0; i<statsValue.length; i++){
                stats.get(i).setText(statsValue[i]);
            }
        }

        nv.setText(getIntent().getStringExtra("nv"));
        exp.setText(getIntent().getStringExtra("exp"));
        pg.setText(getIntent().getStringExtra("pg"));
        ca.setText(getIntent().getStringExtra("ca"));
        vel.setText(getIntent().getStringExtra("vel"));

        if(getIntent().getStringArrayExtra("stats") != null){
            String[] statsSalv = getIntent().getStringArrayExtra("statsSalv");
            for(int i = 0; i < statsSalv.length; i++){
                salvacion.get(i).setChecked(Boolean.parseBoolean(statsSalv[i]));
            }
        }
    }

    public void calcular(View vista){

        int[] resultados = new int[6];
        for(int i=0; i<stats.size(); i++){

            resultados[i] = calcular_dados();
        }
        Arrays.sort(resultados);
        String resultado = "";
        for(int i = 0; i < resultados.length; i++){
            resultado += resultados[i] + "  ";
        }
        dados.setText(resultado);
    }

    private int calcular_dados(){

        int dado[] = new int[4]; //Los dados d6 que se utilizarán para calcular los stats

        //Calcular los valores de los 4 dados
        for(int j=0; j<dado.length; j++){
            dado[j] = ((int) (Math.random() * (7 - 1))) + 1;
        }

        //Quedarse con los 3 valores más altos
        Arrays.sort(dado); //El primero es el más bajo
        int resultado = 0;
        for(int j=1; j<dado.length; j++){
            resultado += dado[j];
        }

        //Mostrar resultado de cada dado
        /*
        StringBuilder builder = new StringBuilder();
        for(int i : dado)
        {
            builder.append("" + i + " ");
        }

        Toast.makeText(this, builder, Toast.LENGTH_SHORT).show();
        */

        return resultado;
    }

    private ContentValues registro(){
        //Guardar en BD los datos introducidos
        ContentValues fila = new ContentValues();
        fila.put("codigo", getIntent().getStringExtra("codigo"));
        fila.put("nombre", nombre.getText().toString());
        fila.put("raza", raza.getSelectedItem().toString());
        fila.put("clase", clase.getSelectedItem().toString());
        fila.put("nv", nv.getText().toString());
        fila.put("exp", exp.getText().toString());
        fila.put("PG", pg.getText().toString());
        fila.put("CA", ca.getText().toString());
        fila.put("VEL", vel.getText().toString());
        fila.put("FUE", stats.get(0).getText().toString());
        fila.put("DES", stats.get(1).getText().toString());
        fila.put("CON", stats.get(2).getText().toString());
        fila.put("INT", stats.get(3).getText().toString());
        fila.put("SAB", stats.get(4).getText().toString());
        fila.put("CAR", stats.get(5).getText().toString());
        fila.put("FUEsalv", String.valueOf(salvacion.get(0).isChecked()));
        fila.put("DESsalv", String.valueOf(salvacion.get(1).isChecked()));
        fila.put("CONsalv", String.valueOf(salvacion.get(2).isChecked()));
        fila.put("INTsalv", String.valueOf(salvacion.get(3).isChecked()));
        fila.put("SABsalv", String.valueOf(salvacion.get(4).isChecked()));
        fila.put("CARsalv", String.valueOf(salvacion.get(5).isChecked()));
        fila.put("acrobacias", String.valueOf(habilidades.get(0).isChecked()));
        fila.put("arcanos", String.valueOf(habilidades.get(1).isChecked()));
        fila.put("atletismo", String.valueOf(habilidades.get(2).isChecked()));
        fila.put("engañar", String.valueOf(habilidades.get(3).isChecked()));
        fila.put("historia", String.valueOf(habilidades.get(4).isChecked()));
        fila.put("interpretacion", String.valueOf(habilidades.get(5).isChecked()));
        fila.put("intimidar", String.valueOf(habilidades.get(6).isChecked()));
        fila.put("investigacion", String.valueOf(habilidades.get(7).isChecked()));
        fila.put("juegoDeManos", String.valueOf(habilidades.get(8).isChecked()));
        fila.put("medicina", String.valueOf(habilidades.get(9).isChecked()));
        fila.put("naturaleza", String.valueOf(habilidades.get(10).isChecked()));
        fila.put("percepcion", String.valueOf(habilidades.get(11).isChecked()));
        fila.put("perspicacia", String.valueOf(habilidades.get(12).isChecked()));
        fila.put("persuasion", String.valueOf(habilidades.get(13).isChecked()));
        fila.put("religion", String.valueOf(habilidades.get(14).isChecked()));
        fila.put("sigilo", String.valueOf(habilidades.get(15).isChecked()));
        fila.put("supervivencia", String.valueOf(habilidades.get(16).isChecked()));
        fila.put("tratoConAnimales", String.valueOf(habilidades.get(17).isChecked()));
        return fila;
    }

    public void guardar(View vista){

        Personaje pj = new Personaje
                (this, Personaje.DATABASE_NAME, null, Personaje.DATABASE_VERSION);

        SQLiteDatabase BaseDeDatos = pj.getWritableDatabase();
        try{

            int filas_afectadas =
                    BaseDeDatos.update("personajes", registro(),
                            "codigo=" + getIntent().getStringExtra("codigo"), null);
            if (filas_afectadas == 1) {
                toast("Personaje modificado correctamente");
            } else {
                toast("No existe el personaje. Creando un nuevo registro");
                BaseDeDatos.insert("personajes", null, registro());
            }

            BaseDeDatos.close();
        }catch (Exception ex){
            toast("Error al guardar los datos");
            BaseDeDatos.close();
        }
    }

    public void finalizar(View vista){

        //Enlace con otra actividad
        Intent otra = new Intent(this, FichaGeneral.class);
        otra.putExtra("codigo", getIntent().getStringExtra("codigo"));
        /*
        otra.putExtra("nombre", nombre.getText().toString());
        otra.putExtra("raza", raza.getSelectedItem().toString());
        otra.putExtra("clase", clase.getSelectedItem().toString());

        String[] statsGuardar = new String[stats.size()];
        for(int i=0; i<statsGuardar.length; i++){
            statsGuardar[i] = stats.get(i).getText().toString();
        }
        otra.putExtra("stats", statsGuardar);
        */
        startActivity(otra);
    }

    private void toast(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }
}