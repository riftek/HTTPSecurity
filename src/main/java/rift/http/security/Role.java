/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rift.http.security;

import java.io.Serializable;

/**
 *
 * @author Rift497
 */
public class Role implements Serializable{
    String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
    
    
}
