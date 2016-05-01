/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.service.newsletter;

/**
 *
 * @author webbaser
 */
public class EMailSessionInfo
{
    private String username;
    private String password;
    
    private String hostURL;
    private int hostPort;

    public EMailSessionInfo (String username, String password, String hostURL, int hostPort)
    {
	this.username = username;
	this.password = password;
	this.hostURL = hostURL;
	this.hostPort = hostPort;
    }

    public String getUsername ()
    {
	return username;
    }

    public void setUsername (String username)
    {
	this.username = username;
	
	return;
    }

    public String getPassword ()
    {
	return password;
    }

    public void setPassword (String password)
    {
	this.password = password;
	
	return;
    }

    public String getHostURL ()
    {
	return hostURL;
    }

    public void setHostURL (String hostURL)
    {
	this.hostURL = hostURL;
	
	return;
    }

    public int getHostPort ()
    {
	return hostPort;
    }

    public void setHostPort (int hostPort)
    {
	this.hostPort = hostPort;
	
	return;
    }
    
    
}
