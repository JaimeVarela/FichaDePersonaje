package com.example.pruebas;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;

public class FichaGeneral extends AppCompatActivity implements View.OnClickListener {

    private TextView nombre, raza, clase, pg, ca, vel, nv, exp;
    private TextView[] stats;
    private ArrayList<CheckBox> salvacion;
    private ArrayList<TextView> habilidades;
    private Button volver, editar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pruebas);

        asociar();
        iniciar();
        eventos();
    }

    private void asociar(){
        nombre = (TextView)findViewById(R.id.nombre);
        raza = (TextView)findViewById(R.id.raza);
        clase = (TextView)findViewById(R.id.clase);
        nv = (TextView)findViewById(R.id.NV);
        exp = (TextView)findViewById(R.id.EXP);
        pg = (TextView)findViewById(R.id.PG);
        ca = (TextView)findViewById(R.id.CA);
        vel = (TextView)findViewById(R.id.VEL);

        stats = new TextView[6];
        stats[0] = (TextView)findViewById(R.id.FUE);
        stats[1] = (TextView)findViewById(R.id.DES);
        stats[2] = (TextView)findViewById(R.id.CON);
        stats[3] = (TextView)findViewById(R.id.INT);
        stats[4] = (TextView)findViewById(R.id.SAB);
        stats[5] = (TextView)findViewById(R.id.CAR);

        salvacion = new ArrayList<CheckBox>();
        salvacion.add((CheckBox)findViewById(R.id.FUEcb));
        salvacion.add((CheckBox)findViewById(R.id.DEScb));
        salvacion.add((CheckBox)findViewById(R.id.CONcb));
        salvacion.add((CheckBox)findViewById(R.id.INTcb));
        salvacion.add((CheckBox)findViewById(R.id.SABcb));
        salvacion.add((CheckBox)findViewById(R.id.CARcb));

        habilidades = new ArrayList<TextView>();
        habilidades.add((TextView) findViewById(R.id.acrobacias));
        habilidades.add((TextView) findViewById(R.id.arcanos));
        habilidades.add((TextView) findViewById(R.id.atletismo));
        habilidades.add((TextView) findViewById(R.id.engañar));
        habilidades.add((TextView) findViewById(R.id.historia));
        habilidades.add((TextView) findViewById(R.id.interpretacion));
        habilidades.add((TextView) findViewById(R.id.intimidar));
        habilidades.add((TextView) findViewById(R.id.investigacion));
        habilidades.add((TextView) findViewById(R.id.juegoDeManos));
        habilidades.add((TextView) findViewById(R.id.medicina));
        habilidades.add((TextView) findViewById(R.id.naturaleza));
        habilidades.add((TextView) findViewById(R.id.percepcion));
        habilidades.add((TextView) findViewById(R.id.perspicacia));
        habilidades.add((TextView) findViewById(R.id.persuasion));
        habilidades.add((TextView) findViewById(R.id.religion));
        habilidades.add((TextView) findViewById(R.id.sigilo));
        habilidades.add((TextView) findViewById(R.id.supervivencia));
        habilidades.add((TextView) findViewById(R.id.tratoConAnimales));

        volver = (Button)findViewById(R.id.volver);
        editar = (Button)findViewById(R.id.editar);
    }

    private void iniciar(){

        Personaje pj = new Personaje
                (this, Personaje.DATABASE_NAME, null, Personaje.DATABASE_VERSION);

        //Creamos un objeto del tipo SQLiteDatabase y de nombre BaseDeDatos
        //El objeto lo crea y lo retorna la variable anterior admin
        SQLiteDatabase BaseDeDatos = pj.getReadableDatabase();
        try {
            //Lee el contenido del campo codigo
            String codigo = "0";
            if(getIntent().getStringExtra("codigo") != null)
                codigo = getIntent().getStringExtra("codigo");
            //Se realiza la consulta en la base datos
            //Obtiene los datos resultado de una consulta SELECT SQL
            //SELECT descripcion, precio
            //FROM articulos
            //WHERE codigo = codigo_tecleado
            Cursor fila = BaseDeDatos.rawQuery
                    ("select nombre, raza, clase, nv, exp, PG, CA, VEL, FUE, DES, CON, INT, SAB, CAR," +
                            " FUEsalv, DESsalv, CONsalv, INTsalv, SABsalv, CARsalv, " +
                            "acrobacias text, arcanos text, atletismo text, engañar text, historia text, interpretacion text," +
                            "intimidar text, investigacion text, juegoDeManos text, medicina text, naturaleza text," +
                            "percepcion text, perspicacia text, persuasion text, religion text, sigilo text, supervivencia text," +
                            "tratoConAnimales text " +
                            "from personajes where codigo = " + codigo, null);

            if (!fila.moveToFirst()) {
                toast("No existe ningún personaje");
                return;
            }

            //Se mueve al primer registro resultado
            fila.moveToFirst();
            //Rellenamos las cajas de texto con la información del personaje obtenida
            nombre.setText(fila.getString(0));
            raza.setText(fila.getString(1));
            clase.setText(fila.getString(2));
            nv.setText(fila.getString(3));
            exp.setText(fila.getString(4));
            pg.setText(fila.getString(5));
            ca.setText(fila.getString(6));
            vel.setText(fila.getString(7));

            int[] statsValue = new int[6];
            for(int i = 0; i < stats.length; i++){
                statsValue[i] = BonificadorStats(Integer.parseInt((fila.getString(i + 8))));
                if(statsValue[i] >= 0) //Añadir + delante del bonificador
                    stats[i].setText(String.format("%s (+%s)", fila.getString(i + 8), statsValue[i]));
                else
                    stats[i].setText(String.format("%s (%s)", fila.getString(i + 8), statsValue[i]));
            }

            for(int i = 0; i < salvacion.size(); i++){
                salvacion.get(i).setChecked(Boolean.parseBoolean(fila.getString(i + 14)));
            }

            for(int i = 0; i < habilidades.size(); i++){
                int valor = Habilidades.HabilidadStat(statsValue, i, Boolean.parseBoolean(fila.getString(i + 20)));
                if(valor >= 0)
                    habilidades.get(i).setText(String.format("+%s", String.valueOf(valor)));
                else
                    habilidades.get(i).setText(String.valueOf(valor));
            }
            //Cerramos la conexión
            BaseDeDatos.close();

        }catch (Exception ex){
            toast("Error al cargar los datos");
            //Cerramos la conexión
            BaseDeDatos.close();
        }
    }

    private void eventos(){
        volver.setOnClickListener(this);
        editar.setOnClickListener(this);
    }

    private int BonificadorStats(int valor){
         return ((valor / 2) - 5);
    }

    private void editar(){
        //Enlace con otra actividad
        String codigo = getIntent().getStringExtra("codigo");
        Intent otra = new Intent(this, MainActivity.class);
        otra.putExtra("codigo", codigo);
        otra.putExtra("nombre", nombre.getText().toString());
        otra.putExtra("raza", raza.getText().toString());
        otra.putExtra("clase", clase.getText().toString());
        otra.putExtra("nv", nv.getText().toString());
        otra.putExtra("exp", exp.getText().toString());
        otra.putExtra("pg", pg.getText().toString());
        otra.putExtra("ca", ca.getText().toString());
        otra.putExtra("vel", vel.getText().toString());

        String[] statsValue = new String[stats.length];
        for(int i = 0; i < statsValue.length; i++){
            statsValue[i] = stats[i].getText().toString();
        }
        otra.putExtra("stats", statsValue);
        String[] statsSalv = new String[stats.length];
        for(int i = 0; i < statsSalv.length; i++){
            statsSalv[i] = String.valueOf(salvacion.get(i).isChecked());
        }
        otra.putExtra("statsSalv", statsSalv);
        startActivity(otra);
    }

    private void volver(){
        Intent volver = new Intent(this, Perfiles.class);
        startActivity(volver);
    }

    private void toast(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.volver:
                volver();
                break;
            case R.id.editar:
                editar();
                break;
        }
    }
}
