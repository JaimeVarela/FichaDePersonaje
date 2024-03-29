package com.example.pruebas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;

public class FichaGeneral extends AppCompatActivity{

    private TextView nombre, raza, clase, pg, ca, vel, nv, exp;
    private int pgValue, pgMaxValue;
    private TextView[] stats;
    private ArrayList<TextView> salvacion;
    private int salvBonus;
    private String[] salvacionCb;
    private ArrayList<TextView> habilidades;
    private int habBonus;
    private String[] habilidadesCb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_general);

        asociar();
        iniciar();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.m_editar:
                editar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

        salvacion = new ArrayList<TextView>();
        salvacion.add((TextView)findViewById(R.id.FUEsalv));
        salvacion.add((TextView)findViewById(R.id.DESsalv));
        salvacion.add((TextView)findViewById(R.id.CONsalv));
        salvacion.add((TextView)findViewById(R.id.INTsalv));
        salvacion.add((TextView)findViewById(R.id.SABsalv));
        salvacion.add((TextView)findViewById(R.id.CARsalv));

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
                    ("select nombre, raza, clase, nv, exp, PG, PGmax, CA, VEL, FUE, DES, CON, INT, SAB, CAR," +
                            " salvBonus, FUEsalv, DESsalv, CONsalv, INTsalv, SABsalv, CARsalv, habBonus, " +
                            "acrobacias text, arcanos text, atletismo text, engañar text, historia text, interpretacion text," +
                            "intimidar text, investigacion text, juegoDeManos text, medicina text, naturaleza text," +
                            "percepcion text, perspicacia text, persuasion text, religion text, sigilo text, supervivencia text," +
                            "tratoConAnimales text " +
                            "from personajes where codigo = " + codigo, null);

            if (!fila.moveToFirst()) {
                toast("No existe ningún personaje");
                salvBonus = 0; //Por defecto
                habBonus = 0; //Por defecto
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
            pgValue = Integer.parseInt(fila.getString(5));
            pgMaxValue = Integer.parseInt(fila.getString(6));
            pg.setText(String.format("%s / %s", fila.getString(5), fila.getString(6)));
            ca.setText(fila.getString(7));
            vel.setText(fila.getString(8));

            int[] statsValue = new int[6];
            for(int i = 0; i < stats.length; i++){
                statsValue[i] = bonificadorStats(Integer.parseInt((fila.getString(i + 9))));
                if(statsValue[i] >= 0) //Añadir + delante del bonificador
                    stats[i].setText(String.format("%s\n   (+%s)", fila.getString(i + 9), statsValue[i]));
                else
                    stats[i].setText(String.format("%s\n   (%s)", fila.getString(i + 9), statsValue[i]));
            }


            salvBonus = Integer.parseInt(fila.getString(15));

            salvacionCb = new String[salvacion.size()];
            for(int i = 0; i < salvacion.size(); i++){
                salvacionCb[i] = fila.getString(i + 16);
                int valor = statsValue[i];
                if(Boolean.parseBoolean(salvacionCb[i])){
                    valor += salvBonus;
                    salvacion.get(i).setBackgroundResource(R.drawable.back);
                }
                if(valor >= 0)
                    salvacion.get(i).setText(String.format("+%s", String.valueOf(valor)));
                else
                    salvacion.get(i).setText(String.valueOf(valor));
            }

            habBonus = Integer.parseInt(fila.getString(22));

            habilidadesCb = new String[habilidades.size()];
            for(int i = 0; i < habilidades.size(); i++){
                habilidadesCb[i] = fila.getString(i + 23);
                int valor = Habilidades.HabilidadStat(statsValue, i, habBonus, Boolean.parseBoolean(habilidadesCb[i]));
                if(valor >= 0)
                    habilidades.get(i).setText(String.format("+%s", String.valueOf(valor)));
                else
                    habilidades.get(i).setText(String.valueOf(valor));
                if(Boolean.parseBoolean(habilidadesCb[i]))
                    habilidades.get(i).setBackgroundResource(R.drawable.back);
            }
            //Cerramos la conexión
            BaseDeDatos.close();

        }catch (Exception ex){
            toast("Error al cargar los datos " + ex.getMessage());
            //Cerramos la conexión
            BaseDeDatos.close();
        }
    }

    private int bonificadorStats(int valor){
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
        otra.putExtra("pg", String.valueOf(pgValue));
        otra.putExtra("pgMax", String.valueOf(pgMaxValue));
        otra.putExtra("ca", ca.getText().toString());
        otra.putExtra("vel", vel.getText().toString());

        String[] statsValue = new String[stats.length];
        for(int i = 0; i < statsValue.length; i++){
            statsValue[i] = stats[i].getText().toString();
        }
        otra.putExtra("stats", statsValue);

        otra.putExtra("salvBonus", String.valueOf(salvBonus));

        otra.putExtra("statsSalv", salvacionCb);

        otra.putExtra("habBonus", String.valueOf(habBonus));

        otra.putExtra("habilidadesCb", habilidadesCb);

        startActivity(otra);
    }

    private void toast(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() { //Se utiliza al volver con el finish() del Editar
        super.onStart();
        iniciar();
    }
}
