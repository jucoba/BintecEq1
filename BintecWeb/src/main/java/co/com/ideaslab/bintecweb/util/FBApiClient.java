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
import java.util.Map;

/**
 *
 * @author IDEASLAB
 */
public class FBApiClient {
    
    String phone = "";
    
    public FBApiClient(){
    }
    
    public String getCellphone(final String id){
        
        
    
        Firebase myFirebaseRef = new Firebase("https://bintec-e5dd4.firebaseio.com/students");
        
        Query query =myFirebaseRef.orderByChild("id_carnet").equalTo(id);
        
        
        query.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot ds, String string) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                Map<String, Object> newPost = (Map<String, Object>) ds.getValue();
                System.out.println("cellphone: " + newPost.get("cellphone"));
//                return newPost.get("cellphone");
                
            }

            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onChildRemoved(DataSnapshot ds) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        //Query query =myFirebaseRef.equalTo("id_carnet", id);
       
        //Query query =myFirebaseRef.equalTo("id_carnet", id);
        
        /*query.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot ds, String string) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                Map<String, Object> newPost = (Map<String, Object>) ds.getValue();
                System.out.println("cellphone: " + newPost.get("cellphone"));
            }

            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onChildRemoved(DataSnapshot ds) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });*/
        /*Query query = myFirebaseRef.orderByChild("Author").equalTo("Author1", "Author");
        
        
        myFirebaseRef.
        
        
        myFirebaseRef=myFirebaseRef.child("students");
        
        myFirebaseRef.*/
        
        
        /*myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    for (DataSnapshot single : child.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) single.getValue();
                        String b = (String) map.get("id_carnet");
                        if(b.equalsIgnoreCase(id)){
                            phone = (String) map.get("cellphone");
                            
                            myFirebaseRef.push().setValue(map, phone);
                        }
                        
                        System.out.println("Cellphone: " + phone + "Carnet: " + b);
                    }
                }
            }
            
            @Override
            public void onCancelled() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });*/
        
        
        //myFirebaseRef.
        
        return phone;
    }
    
}
