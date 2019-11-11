package com.example.pruebas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Personaje extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "personajes";

    public Personaje(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //metodo que se ejecuta la primera vez que se crea un objeto de la clase
    //AdminDB con new. Podemos crear tablas y relaciones.
    //El parametro db del tipo SQLiteDataBase, es un objeto para acceder y manipular
    //la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table personajes(codigo int primary key, nombre text, raza text, clase text," +
                "nv int, exp int, PG int, CA int, VEL int, FUE int, DES int, CON int, INT int, SAB int, CAR int," +
                "salvBonus int, FUEsalv text, DESsalv text, CONsalv text, INTsalv text, SABsalv text, CARsalv text, " +
                "habBonus int, acrobacias text, arcanos text, atletismo text, engañar text, historia text, interpretacion text," +
                "intimidar text, investigacion text, juegoDeManos text, medicina text, naturaleza text," +
                "percepcion text, perspicacia text, persuasion text, religion text, sigilo text, supervivencia text, " +
                "tratoConAnimales text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists personajes");
        onCreate(db);
    }
}
