/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ideaslab.bintecweb.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author IDEASLAB
 */
public class BodyUtilsNotification {
    private static Gson gson = new Gson();

    /**
     * Canal entregado por el equipo de Nequi
     */
    public static final String CHANNEL = "MF-001";
    
  /**
   * Construye a partir del celular, titulo y mensaje el cuerpo de la peticion para una
   * notificacion push   
   * @param phone
   * @param subject
   * @param message
   * @return
   */
    public static JsonObject getBodyNotification(Long phone, String subject, String message) {
            //Fecha de la petición
            Date d = new Date();
            String date = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format(d);        
            String input = 
                            "{\"RequestMessage\":"
                            + "{\"RequestHeader\":{"
                                    //Canal
                                    + "\"Channel\":\""+CHANNEL+"\","
                                    //Fecha de la petición
                                    + "\"RequestDate\":\""+date+"\","
                                    //Identificador único de la transacción
                                    + "\"MessageID\":\""+UUID.randomUUID().toString()+"\","
                                    // Número identificador del cajero ecológico
                                    + "\"ClientID\":\"" + phone
                            + "\"},"
                            + "\"RequestBody\":{"
                                    + "\"any\":{"
                                            + "\"notificationRQ\":{"
                                                            + "\"subject\":\""+ subject + "\","
                                                            + "\"message\":\"" + message + "\""
                                                            + "}"
                                                    + "}"
                               + "}"
                            + "}}";
            System.out.println("NOTIFICACIÓN ****************");
            System.out.println(input);
            return gson.fromJson(input, JsonObject.class);
    }
}
