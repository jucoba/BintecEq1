/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ideaslab.bintecweb.config;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author ideaslabsas
 */
@ApplicationPath("/bintec")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
      /*  register( new GZipEncoder() );
        register( JacksonFeature.class );
        register(MultiPartFeature.class);
        register(CORSResponseFilter.class);*/
        //register(AutenticationRequestFilter.class);
        packages("co.com.ideaslab.bintecweb.services");
    }

}
