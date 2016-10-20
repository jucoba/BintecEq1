/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ideaslab.bintecweb.util;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.google.gson.JsonObject;
import com.nequi.api.client.NequiGatewayClient;
import com.nequi.api.provider.BasicAWSCredentialsProvider;

/**
 *
 * @author IDEASLAB
 */
public class ApiClient {
    /**
     * Claves para consumo de API
     */
    private static final String SECRET_KEY = "GAlnQO3vZWncJf+d7yE79JLLAMe1x3JTXOdI8REe";
    //private static final String SECRET_KEY = "1xMnGGGjaXaFRUsE4tKit6t5E9F6hNK64esu65e3";
    private static final String _ACCESS_KEY = "AKIAISR34JAZBQFU4ARQ";
    private static final String _API_KEY = "xrQ6q19y5n3ToPQxYf5za6xZmaBkICfN5uPbcR9j";

    /**
     * Cliente del Api
     * 
     */
    private NequiGatewayClient apiClient;

    /**
     * Constructor que inicializa el cliente del API
     */
    public ApiClient() {
        ApiClientFactory factory = new ApiClientFactory();
        AWSCredentialsProvider credenetialsProvider = new BasicAWSCredentialsProvider(
                _ACCESS_KEY, SECRET_KEY);
        factory.apiKey(_API_KEY);
        factory.credentialsProvider(credenetialsProvider);
        this.apiClient = factory.build(NequiGatewayClient.class);
        
    }
    
    /**
     * A partir de un celular y valor a recargar (puede ser 0) consulta si un cliente nequi existe
     * @param phone
     * @param value
     * @return
     */
    public JsonObject validateClient(Long phone, Integer value) {
        return apiClient.servicesClientserviceValidateclientPost(BodyUtils.getBodyValidateClient(phone, value));
    }  

    /**
     * Apartir del celular y monto llama al servicio de cashIn del API
     * 
     * @param phone
     * @param mount
     * @return
     */
    public JsonObject cashIn(Long phone, Integer mount) {
        return apiClient.servicesCashinserviceCashinPost(BodyUtils.getBodyCashIn(phone,
                mount));
    }
    
    /**
     * A partir del celular, valor, referencia y token llama al servicio de cashout del API
     * @param phone
     * @param value
     * @param reference
     * @param token
     * @return
     * @throws Exception 
     */
    public JsonObject cashOut(Long phone, Integer value, String reference,
            String token) throws Exception {
        String publicKey = apiClient.servicesKeysserviceGetpublicPost();            
        return apiClient.servicesCashoutserviceCashoutPost(BodyUtils.getBodyCashOut(phone,
                value, reference, token, publicKey));
    }
    
    
    /**
     * A partir de un codigo de pago o qr verifica su estado
     * @param codeQR
     * @return
     */
    public JsonObject validatePayment(Long phone, String codeQR) {
        return apiClient.servicesPaymentserviceGetstatuspaymentPost(BodyUtils.getBodyValidatePayment(phone, codeQR));
    }
    
    /**
     * Apartir del celular de un merchant y un valor llama al servicio de generacion de QR del API
     * 
     * @param phone
     * @param mount
     * @return
     */
    public JsonObject generateQR(Long phone, Integer value) {
        return apiClient.servicesPaymentserviceGeneratecodeqrPost(BodyUtils.getBodyGenerateQR(phone, value));
    }
    
    /**
     * A partir del celular consume el servicio de consulta de cashout pendiente para un cliente nequi del API
     * @param phone
     * @return
     */
    public JsonObject cashOutConsult(Long phone) {
        return apiClient.servicesCashoutserviceCashoutconsultPost(BodyUtils.getBodyCashOutConsult(phone));
    } 

    public void setApiClient(NequiGatewayClient apiClient) {
        this.apiClient = apiClient;
    }
    
    public JsonObject transferservicesTransferPost(Long phoneNumberOrigin, Long phoneNumberDestiny, Integer value){
        return apiClient.servicesTransferservicesTransferPost(BodyUtils.getBodyTransferservicesTransferPost(phoneNumberOrigin, phoneNumberDestiny, value));
    }
}
