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
public class EMailMessageInfo
{
    private String recipient;
    private String subject;
    private String message;

    public EMailMessageInfo (String recipient, String subject, String message)
    {
	this.recipient = recipient;
	this.subject = subject;
	this.message = message;
    }

    public String getRecipient ()
    {
	return recipient;
    }

    public void setRecipient (String recipient)
    {
	this.recipient = recipient;
	
	return;
    }

    public String getSubject ()
    {
	return subject;
    }

    public void setSubject (String subject)
    {
	this.subject = subject;
	
	return;
    }

    public String getMessage ()
    {
	return message;
    }

    public void setMessage (String message)
    {
	this.message = message;
	
	return;
    }
    
    
}
