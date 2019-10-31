package com.example.pruebas;

public class Habilidades {

    public static int valorBonus = 2;
    public static String[] habilidadesBonus = {
            "DES", //Acrobacias
            "INT", //Arcanos
            "FUE", //Atletismo
            "CAR", //Engañar
            "INT", //Historia
            "CAR", //Interpretacion
            "CAR", //Intimidar
            "INT", //Investigacion
            "DES", //Juego de manos
            "SAB", //Medicina
            "INT", //Naturaleza
            "SAB", //Percepcion
            "SAB", //Perspicacia
            "CAR", //Persuasion
            "INT", //Religión
            "DES", //Sigilo
            "SAB", //Supervivencia
            "SAB"  //Trato con animales
    };

    public static int HabilidadStat(int[] statsValue, int i, boolean bonus){
        int valor = 0;
        switch (habilidadesBonus[i]){
            case "FUE":
                valor += statsValue[0];
                break;
            case "DES":
                valor += statsValue[1];
                break;
            case "CON":
                valor += statsValue[2];
                break;
            case "INT":
                valor += statsValue[3];
                break;
            case "SAB":
                valor += statsValue[4];
                break;
            case "CAR":
                valor += statsValue[5];
        }

        if(bonus)
            valor += valorBonus;

        return valor;
    }

}
