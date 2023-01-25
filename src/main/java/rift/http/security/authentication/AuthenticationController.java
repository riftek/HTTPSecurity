/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rift.http.security.authentication;

import rift.http.security.Authenticator;
import rift.http.controller.RESTController;
import rift.http.handler.HttpRequest;
import rift.http.handler.HttpResponse;
import rift.http.session.Session;

/**
 *
 * @author Rift497
 */
public class AuthenticationController extends RESTController {

    Authenticator authenticator;

    public AuthenticationController(String path) {
        super(path);
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        System.out.println("cp1: " + response.getCookie("sessionid"));
        Session session = this.getRestHandler().getServer().getSessionManager().getSession("" + response.getCookie("sessionid").getValue());
        Object object = authenticator.authenticate(request.getBodyParameters());

        session.setSessionObject(object);
        if (object != null) {
            response.setResponseCode(200);
            authorised(session, request, response);

//            response.getHeaders().add("Access-Control-Allow-Origin", "*");
//            response.getHeaders().add("Access-Control-Allow-Headers", "Content-Type");
//            response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        } else {
            response.setResponseCode(401);
            unauthorised(session, request, response);
        }

    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    public void authorised(Session session, HttpRequest request, HttpResponse response) {

    }

    public void unauthorised(Session session, HttpRequest request, HttpResponse response) {

    }

}
