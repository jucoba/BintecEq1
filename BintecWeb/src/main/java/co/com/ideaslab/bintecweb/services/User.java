/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ideaslab.bintecweb.services;

import co.com.ideaslab.bintecweb.util.ApiClient;
import co.com.ideaslab.bintecweb.util.FBApiClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.Map;
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
        
        //String phone1 = fbApiClient.getCellphone(id);
        //String phone1 = "3999935214";
        String phone1 = "3195414070";
        String phone2 = "3004521687";

        apiClient = new ApiClient();
        gson = new Gson();
        JsonObject output = apiClient.validateClient(Long.parseLong(phone1), 0);

        JsonObject responseMessage = output.getAsJsonObject("ResponseMessage");
        JsonObject responseHeader = responseMessage.getAsJsonObject("ResponseHeader");
        JsonObject status = responseHeader.getAsJsonObject("Status");
        String success = status.get("StatusCode").getAsString();
        if (success.equalsIgnoreCase("0")) {
            //output = apiClient.transferservicesTransferPost(Long.parseLong(phone1), Long.parseLong(phone2), 2);
        }

        return Response.status(200).entity(gson.toJson(output)).build();
    }
}
