/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ideaslab.bintecweb.services;

import co.com.ideaslab.bintecweb.util.ApiClient;
import co.com.ideaslab.bintecweb.util.ApiClientNotification;
import co.com.ideaslab.bintecweb.util.FBApiClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author IDEASLAB
 */
@Path("user")
public class User {

    ApiClient apiClient;
    Gson gson;
    FBApiClient fbApiClient;
    ApiClientNotification apiClientNotif;

    private static Integer VALUE = 2000;

    /*
     * @param documentType
     * @param documentNumber
     * @param password
     * @return
     */
    @GET
    @Path("/pay/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pay(@PathParam("id") String id) throws IOException {

        fbApiClient = new FBApiClient();
        apiClientNotif = new ApiClientNotification();

        String phone1;
        try {
            phone1 = fbApiClient.getCellphone(id);
            //3195414070
            //String phone2 = "3004521687";
            String phone2 = "3103478606";

            apiClient = new ApiClient();
            gson = new Gson();
            JsonObject output = apiClient.validateClient(Long.parseLong(phone1), 0);

            JsonObject responseMessage = output.getAsJsonObject("ResponseMessage");
            JsonObject responseHeader = responseMessage.getAsJsonObject("ResponseHeader");
            JsonObject status = responseHeader.getAsJsonObject("Status");
            String success = status.get("StatusCode").getAsString();
            if (success.equalsIgnoreCase("0")) {
                output = apiClient.transferservicesTransferPost(Long.parseLong(phone1), Long.parseLong(phone2), VALUE);
                responseMessage = output.getAsJsonObject("ResponseMessage");
                responseHeader = responseMessage.getAsJsonObject("ResponseHeader");
                status = responseHeader.getAsJsonObject("Status");
                success = status.get("StatusCode").getAsString();
                if (success.equalsIgnoreCase("0")) {
                    output = apiClientNotif.sendPushNotification(Long.parseLong(57+phone1), "Pago parqueadero", "Se ha pagado " + VALUE + " mil pesos por parqueo.");
                }
            }

            return Response.status(200).entity(gson.toJson(output)).build();
        } catch (InterruptedException ex) {
            //Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        //String phone1 = "3999935214";
        //String phone1 = "3195414070";
        return Response.serverError().build();
    }
}
