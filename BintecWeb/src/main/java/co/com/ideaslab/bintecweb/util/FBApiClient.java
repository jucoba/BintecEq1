/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ideaslab.bintecweb.util;


import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 *
 * @author IDEASLAB
 */
public class FBApiClient {
    
    String phone = "";
    
    public FBApiClient(){
    }
    
    public String getCellphone(final String id) throws InterruptedException{
        
        // create a java.util.concurrent.Semaphore with 0 initial permits
        
    
        Firebase myFirebaseRef = new Firebase("https://bintec-e5dd4.firebaseio.com/students");
        
        Query query =myFirebaseRef.orderByChild("id_carnet").equalTo(id);
        
        final Semaphore semaphore = new Semaphore(0);
        
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot ds) {
                 Map<String, Object> newPost = (Map<String, Object>) ds.getValue();
                 
                 Set<Entry<String, Object>> data=newPost.entrySet();
                 for (Iterator<Entry<String, Object>> iterator = data.iterator(); iterator.hasNext();) {
                    Entry<String, Object> next = iterator.next();
                    HashMap map=(HashMap)next.getValue();
                    System.out.println("cellphone: " + map.get("cellphone"));
                    phone=map.get("cellphone").toString();
                    
                }
                 semaphore.release();
                
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        /*query.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot ds, String string) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               
                
            }

            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                System.out.println("cellphone: ");
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onChildRemoved(DataSnapshot ds) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });*/
        // wait until the onDataChange callback has released the semaphore
        semaphore.acquire();
        
        
        return phone;
    }
    
}
