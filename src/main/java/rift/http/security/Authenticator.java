/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rift.http.security;

import java.util.Map;

/**
 *
 * @author Rift497
 */
public abstract class Authenticator<T> {
    public abstract T authenticate(Map parameters);
    
}
