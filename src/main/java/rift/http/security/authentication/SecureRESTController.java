/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rift.http.security.authentication;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import rift.http.annotations.RequestMapping;
import rift.http.security.Role;
import rift.http.security.annotations.HasAnyRole;
import rift.http.security.authentication.basic.BasicAuthObject;
import rift.http.controller.RESTController;
import rift.http.handler.HttpRequest;
import rift.http.handler.HttpResponse;
import rift.http.session.Session;

/**
 *
 * @author Rift497
 */
public class SecureRESTController extends RESTController {

    Map<String, Method> requestMappings = new HashMap();

    public SecureRESTController(String path) {
        super(path);

    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        
        Session session = getServer().getSessionManager().getSession("" + response.getCookie("sessionid").getValue());
        
        try {
            URI uri = getSubURI(request.getFilteredUri());
            request.setFilteredUri(uri);
            Method method = findRequestMapping(request.getMethod(), request.getFilteredUri().getPath());
            

            if (method != null) {
                if (authorised(session, method)) {
                    response.setResponseCode(200);
                    method.invoke(this, new Object[]{request, response});
                } else {
                    if (session.getSessionObject() == null) {
                        onUnauthorised(request, response);
                    } else {
                        response.setResponseCode(403);
                    }
                }
            }

        } catch (URISyntaxException ex) {
            Logger.getLogger(SecureRESTController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SecureRESTController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(SecureRESTController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(SecureRESTController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean authorised(Session session, Method method) {
        if (method != null) {

            if (method.isAnnotationPresent(HasAnyRole.class)) {
                if (session.getSessionObject() != null) {
                    
                    List<Role> roles = ((BasicAuthObject) session.getSessionObject()).getRoles();
                    for (int i = 0; i < roles.size(); i++) {
                        String[] names = method.getAnnotation(HasAnyRole.class).names();
                        for (int j = 0; j < names.length; j++) {
                            if (roles.get(i).getRoleName().equals(names[j])) {
                                return true;
                            }
                        }
                    }
                }

            } else {
                return true;
            }
        }
        return false;
    }

    public void onUnauthorised(HttpRequest request, HttpResponse response) {
        response.setResponseCode(401);
    }
    
    
    
}
