package com.marcosledesma.ej03tattooedu.configuraciones;

import java.text.SimpleDateFormat;

public class Configuraciones {
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");;
    // Mejor así
    static{
        //simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }
}
