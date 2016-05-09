/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.service.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author webbaser
 */
public class GMailAuthenticator extends Authenticator
{

    private String user;
    private String password;

    public GMailAuthenticator (String username, String password)
    {
	super ();
	this.user = username;
	this.password = password;
    }

    public PasswordAuthentication getPasswordAuthentication ()
    {
	PasswordAuthentication pa = new PasswordAuthentication (user, password);

	return pa;
    }
}
