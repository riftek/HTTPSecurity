/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rift.http.security.authentication.basic;

import java.io.Serializable;
import java.util.List;
import rift.http.security.Role;

/**
 *
 * @author Rift497
 */
public class BasicAuthObject implements Serializable{
    String id;
    String username;
    
    List<Role> roles ;

    public BasicAuthObject(String id, String username, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }
    
    
}
