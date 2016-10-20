/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ideaslab.bintecweb.util;

import com.amazonaws.util.Base64;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nequi.api.utils.NequiEncrypter;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author IDEASLAB
 */
public class BodyUtils {
    
    private static Gson gson = new Gson();
    
    /**
     * Canal entregado por el equipo de Nequi
     */
    public static final String CHANNEL = "MF-001";
    
    
    /**
     * Construye a partir del celular y valor de recarga el cuerpo de la peticion para
     * consumir el servicio de validate client.
     * @param phone
     * @param value
     * @return
     */
    public static JsonObject getBodyValidateClient(Long phone, Integer value) {
        Date d = new Date();
        String miliseconds = Long.toString(System.currentTimeMillis());
        String date = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format (d);        
        String input = 
                "{\"RequestMessage\":"
                + "{\"RequestHeader\":{"
                    + "\"Channel\":\""+CHANNEL+"\","
                    + "\"RequestDate\":\""+date+"\","
                    + "\"MessageID\":\""+miliseconds.substring(miliseconds.length()-9)+"\","
                    + "\"ClientID\":\"" + phone + "\""
                    + ",\"Address\": {"
                    +    "\"DeviceAddress\": \"1.1.1.1\""
                    +    ", \"NetworkAddress\": \"1.1.1.1\""
                    + "}"                   
                    + ", \"Destination\": {"
                    +    "\"ServiceName\": \"RechargeService\""
                    +    ", \"ServiceOperation\": \"validateClient\""
                    +    ", \"ServiceRegion\": \"C001\""
                    +    ", \"ServiceVersion\": \"1.0.0\""
                    + "}"
                + "},"
                + "\"RequestBody\":{"
                    + "\"any\":{"
                        + "\"validateClientRQ\":{"
                                + "\"phoneNumber\":\""+ phone + "\","                                
                                + "\"value\":\"" + value + "\""
                                + "}"
                            + "}"
                   + "}"
                + "}}";
        System.out.println("VALIDATE ****************");
        System.out.println(input);
        return gson.fromJson(input, JsonObject.class);
    } 
    
    /**
     * Construye a partir del celular y monto a recargar el mensaje de entrada
     * del servicio de cash in
     * 
     * @param phone
     * @param mount
     * @return
     */
    public static JsonObject getBodyCashIn(Long phone, Integer mount) {
        Date d = new Date();
        String date = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format (d);
        String miliseconds = Long.toString(System.currentTimeMillis());
        String input = 
                "{\"RequestMessage\":"
                + "{\"RequestHeader\":{"
                    + "\"Channel\":\""+CHANNEL+"\","
                    + "\"RequestDate\":\""+date+"\","
                    + "\"MessageID\":\""+miliseconds.substring(miliseconds.length()-9)+"\","
                    + "\"ClientID\":\"" + phone + "\""
                    + ", \"Destination\": {"
                        +    "\"ServiceName\": \"CashInService\""
                        +    ", \"ServiceOperation\": \"cashIn\""
                        +    ", \"ServiceRegion\": \"C001\""
                        +    ", \"ServiceVersion\": \"1.0.0\""
                    + "}"
                + "},"
                + "\"RequestBody\":{"
                    + "\"any\":{"
                        + "\"cashInRQ\":{"
                                + "\"phoneNumber\":\""+ phone + "\","
                                + "\"code\":\"1\","
                                + "\"value\":\"" + mount + "\""
                                + "}"
                            + "}"
                   + "}"
                + "}}";
        return gson.fromJson(input, JsonObject.class);
    }

    /**
     * Construye a partir del celular y valor para generar un codigo de pago el
     * mensaje de entrada del servicio de generacion de QR
     * 
     * @param phone
     * @param mount
     * @return
     */
    public static JsonObject getBodyGenerateQR(Long phone, Integer value) {
        Date d = new Date();
        String date = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format(d);
        String miliseconds = Long.toString(System.currentTimeMillis());
        String input = 
                "{\"RequestMessage\":{"
                        + "\"RequestHeader\":{"
                            + "\"Channel\":\""+CHANNEL+"\","
                            + "\"RequestDate\":\""+date+"\","
                            + "\"MessageID\":\""+miliseconds.substring(miliseconds.length()-9)+"\","
                            + "\"ClientID\":\""+ phone+ "\""
                            + ", \"Destination\": {"                                        
                            +    "\"ServiceName\": \"PaymentsService\""
                                    +    ", \"ServiceOperation\": \"generateCodeQR\""
                                    +    ", \"ServiceRegion\": \"C001\""
                                    +    ", \"ServiceVersion\": \"1.0.0\""
                            + "}"
                        + "},"
                        + "\"RequestBody\":{"
                            + "\"any\":{"
                                + "\"generateCodeQRRQ\":{"
                                    + "\"phoneNumber\":\""+ phone + "\","
                                    + "\"value\":\"" + value + "\""
                                    + "}"
                               + "}"
                         + "}"
                 + "}}";
        return gson.fromJson(input, JsonObject.class);
    }

    /**
     * Construye a partir de un codigo de pago o qr el mensaje de entrada del
     * servicio de validacion de pago
     * 
     * @param codeQR
     * @return
     */
    public static JsonObject getBodyValidatePayment(Long phone_number, String codeQR) {
        Date d = new Date();
        String date = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format(d);
        String miliseconds = Long.toString(System.currentTimeMillis());
        String input = 
                "{\"RequestMessage\":{"
                    + "\"RequestHeader\":{"
                        + "\"Channel\":\""+CHANNEL+"\","
                        + "\"RequestDate\":\""+date+"\","
                        + "\"MessageID\":\""+miliseconds.substring(miliseconds.length()-9)+"\","
                        + "\"ClientID\":\""+phone_number+ "\""
                        + ", \"Destination\": {"                                        
                                + "\"ServiceName\": \"PaymentsService\""
                                + ", \"ServiceOperation\": \"getStatusPayment\""
                                + ", \"ServiceRegion\": \"C001\""
                                + ", \"ServiceVersion\": \"1.0.0\""
                        + "}"

                     + "},"
                     + "\"RequestBody\":{"
                         + "\"any\":{"
                             + "\"getStatusPaymentRQ\":{"
                                 + "\"codeQR\":\"" + codeQR + "\""
                             + "}"
                         + "}"
                     + "}"
                + "}}";
        return gson.fromJson(input, JsonObject.class);
    }

    /**
     * Construye a partir del celular y monto a recargar el mensaje de entrada
     * del servicio de cash in
     * 
     * @param phone
     * @param mount             
     * @return
     * @throws Exception 
     */
    public static JsonObject getBodyCashOut(Long phone, Integer value,
            String reference, String token, String publicKey) throws Exception {
        Date d = new Date();
        String date = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format (d);
        String miliseconds = Long.toString(System.currentTimeMillis());
        String tokenEncrypted = encryptToken(token, publicKey);       
        String input = 
                "{\"RequestMessage\":"
                + "{\"RequestHeader\":{"
                    + "\"Channel\":\""+CHANNEL+"\","
                    + "\"RequestDate\":\""+date+"\","
                    + "\"MessageID\":\""+miliseconds.substring(miliseconds.length()-9)+"\","
                    + "\"ClientID\":\"" + phone + "\""
                        + ", \"Destination\": {"
                        +    "\"ServiceName\": \"CashOutServices\""
                        +    ", \"ServiceOperation\": \"cashOut\""
                        +    ", \"ServiceRegion\": \"C001\""
                        +    ", \"ServiceVersion\": \"1.0.0\""
                    + "}"
                + "},"
                + "\"RequestBody\":{"
                    + "\"any\":{"
                        + "\"cashOutRQ\":{"
                                + "\"phoneNumber\":\""+ phone + "\","
                                + "\"code\":\"1\","
                                + "\"value\":\"" + value + "\","
                                + "\"reference\":\""+ reference + "\","
                                + "\"token\":\""+ tokenEncrypted + "\","
                                + "\"externalData\":{\"tranPart\":\"5644564654\",\"idTerminal\":\"123\"}}"
                            + "}"
                   + "}"
                + "}}";       
        return gson.fromJson(input, JsonObject.class);
    }
    
    /**
     * Construye a partir del numero de celular rl cuerpo para consultar un cashout pendiente
     * @param phone
     * @return
     */
    public static JsonObject getBodyCashOutConsult(Long phone) {
        Date d = new Date();
        String date = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format (d);
        String miliseconds = Long.toString(System.currentTimeMillis());
        String input = 
                "{\"RequestMessage\":"
                + "{\"RequestHeader\":{"
                    + "\"Channel\":\""+CHANNEL+"\","
                    + "\"RequestDate\":\""+date+"\","
                    + "\"MessageID\":\""+miliseconds.substring(miliseconds.length()-9)+"\","
                    + "\"ClientID\":\"" + phone + "\""
                            + ", \"Destination\": {"
                            +    "\"ServiceName\": \"CashOutServices\""
                            +    ", \"ServiceOperation\": \"cashOutConsult\""
                            +    ", \"ServiceRegion\": \"C001\""
                            +    ", \"ServiceVersion\": \"1.0.0\""
                        + "}"
                    + "},"
                + "\"RequestBody\":{"
                    + "\"any\":{"
                        + "\"cashOutConsultRQ\":{"
                                + "\"phoneNumber\":\""+ phone + "\""                               
                                + "}"
                            + "}"
                   + "}"
                + "}}";  
        
        return gson.fromJson(input, JsonObject.class);
    }
    
    /**
     * Construye a partir del numero de celular rl cuerpo para consultar un cashout pendiente
     * @param phoneNumberOrigin
     * @param phoneNumberDestiny
     * @param value
     * @return
     */
    public static JsonObject getBodyTransferservicesTransferPost(Long phoneNumberOrigin, Long phoneNumberDestiny, Integer value) {
        Date d = new Date();
        String date = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format (d);
        String miliseconds = Long.toString(System.currentTimeMillis());
        String input = 
                "{\"RequestMessage\":{\"RequestHeader\":{\"Channel\":\""+CHANNEL+"\",\"RequestDate\":\""+date+"\",\"MessageID\":\""+miliseconds.substring(miliseconds.length()-9)+"\",\"ClientID\":\""+phoneNumberOrigin+"\"},\"RequestBody\":{\"any\":{\"transferRQ\":{\"phoneNumberOrigin\":\""+phoneNumberOrigin+"\",\"phoneNumberDestiny\":\""+phoneNumberDestiny+"\",\"description\":\"STRING\",\"pocket\":{},\"value\":\""+value+"\"}}}}}";       
        System.out.println("TRANSFERSERVICES ****************");
        System.out.println(input);
        return gson.fromJson(input, JsonObject.class);
    }
    

    /**
     * Crea a partir del string de la llave publica el objeto PublicKey que recibe el método
     * de encripcion ofrecido por el API y encripta el token.
     * @param token
     * @return
     * @throws Exception
     */
    private static String encryptToken(String token, String publicKeyString) throws Exception {
        String pubKey = publicKeyString.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "");
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.decode(pubKey));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(spec);
        String tokenEncrypted = NequiEncrypter.encryptRSA(token, publicKey);
        return tokenEncrypted;
    }     
}
