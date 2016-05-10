package edu.wctc.ajs.ajsmidtermapp.service;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alyson
 */
@Service("emailService")
public class EmailService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired // required by Spring to inject object from Spring config file
    private MailSender mailSender;
    @Autowired
    private SimpleMailMessage templateMessage;

    public EmailService() {
    }

    
    
    public void setTemplateMessage(org.springframework.mail.SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }

    //@Override
    public void sendEmail(String userEmail) throws MailException, Exception {
        // Create a Base64 encode of the username
        byte[] encoded = Base64.encode(userEmail.getBytes());
        String base64Username = new String(encoded);

        // Create a thread safe "copy" of the template message and customize it
        // See Spring config in applicationContext.xml
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(userEmail);
        msg.setFrom("azimmel@azimmelportfolio.com");
        msg.setSubject("Web App Registration - One More Step Required");
        msg.setText("Thank you for registering with Nuglow, This email is "
                + "being sent to you to verify your intent to register with Nuglow. "
                + "To complete the registration process for user email: "
                + userEmail + ", please click on the link below."
                + "\n\nCAUTION: if you did not register with Nuglow, please "
                + "do not click on the link below unless you intend to confirm "
                + "your registraiton with Nuglow.\n\n"
                + "Here's the link to complete the registraiton process: \n\n"
                + "http://localhost:8080/ajsmidtermapp/Verify?id=" + base64Username); // change the URL to match your app

        try {
            mailSender.send(msg);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
    