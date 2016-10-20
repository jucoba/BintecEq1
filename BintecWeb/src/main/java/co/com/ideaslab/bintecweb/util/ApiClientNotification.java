/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ideaslab.bintecweb.util;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.google.gson.JsonObject;
import com.nequi.api.provider.BasicAWSCredentialsProvider;
import com.nequi.notification.api.client.NequiNotificationGateWayClient;

/**
 *
 * @author IDEASLAB
 */
public class ApiClientNotification {
    /**
     * Claves para consumo de API
     */
    private static final String SECRET_KEY = "GAlnQO3vZWncJf+d7yE79JLLAMe1x3JTXOdI8REe";
    private static final String _ACCESS_KEY = "AKIAISR34JAZBQFU4ARQ";
    private static final String _API_KEY = "xrQ6q19y5n3ToPQxYf5za6xZmaBkICfN5uPbcR9j";

    /**
     * Cliente del Api
     * 
     */
    private NequiNotificationGateWayClient apiClient;

    /**
     * Constructor que inicializa el cliente del API
     */
    public ApiClientNotification() {
        ApiClientFactory factory = new ApiClientFactory();
        AWSCredentialsProvider credenetialsProvider = new BasicAWSCredentialsProvider(
                _ACCESS_KEY, SECRET_KEY);
        factory.apiKey(_API_KEY);
        factory.credentialsProvider(credenetialsProvider);
        this.apiClient = factory.build(NequiNotificationGateWayClient.class);
        
    }

    /**
     * Envia una notificacion push a un celular teniendo como 
     * titulo y mensaje los valores que se le pasan al metodo
     * @param phone
     * @param subject
     * @param message
     * @return
     */
    public JsonObject sendPushNotification(Long phone, String subject,
            String message) {
        return apiClient.servicesPushNotificationPost(BodyUtilsNotification.getBodyNotification(phone, subject, message));
    }
    
    
    public void setApiClient(NequiNotificationGateWayClient apiClient) {
        this.apiClient = apiClient;
    }
}
