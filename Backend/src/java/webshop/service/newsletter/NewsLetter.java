/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.service.newsletter;

import javax.ejb.Remote;

/**
 *
 * @author webbaser
 */
@Remote
public interface NewsLetter
{
    void sendNewsLetter (EMailMessageInfo eMailMessageInfo);
}
