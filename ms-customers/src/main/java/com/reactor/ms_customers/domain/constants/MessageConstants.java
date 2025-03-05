package com.reactor.ms_customers.domain.constants;

public class MessageConstants {

    public static final String INFORMACION_GUARDADA = "La información fué guardada con éxito";
    public static final String NOMBRE_CLIENTE_OBLIGATORIO = "El nombre del cliente es un campo obligatorio";
    public static final String PAIS_OBLIGATORIO = "El id del país es un campo obligatorio";

    public static final String RFC_EXISTENTE = "El RFC ya está registrado en la base de datos, por lo que no se puede crear un cliente con un RFC existente";

    private MessageConstants() {

    }
}
