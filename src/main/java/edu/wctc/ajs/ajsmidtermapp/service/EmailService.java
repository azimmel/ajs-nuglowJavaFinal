package edu.wctc.ajs.ajsmidtermapp.service;

import edu.wctc.ajs.ajsmidtermapp.entity.Authorities;
import edu.wctc.ajs.ajsmidtermapp.entity.User;
import edu.wctc.ajs.ajsmidtermapp.repository.UserRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alyson
 */
@Repository
@Stateless
public class EmailService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired // required by Spring to inject object from Spring config file
    private MailSender mailSender;
    @Autowired
    private SimpleMailMessage templateMessage;

    public void setTemplateMessage(org.springframework.mail.SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }

    //@Override
    public void sendEmail(String userEmail, Object data) throws MailException {
        // Create a Base64 encode of the username
        byte[] encoded = Base64.encode(userEmail.getBytes());
        String base64Username = new String(encoded);

        // Create a thread safe "copy" of the template message and customize it
        // See Spring config in applicationContext.xml
        SimpleMailMessage msg = new SimpleMailMessage(templateMessage);
        msg.setTo(userEmail);

        msg.setText("Thank you for registering with bitBay(tm), the unique "
                + "auction site that benefits WCTC's IT student club. This email is "
                + "being sent to you to verify your intent to join bitBay. "
                + "To complete the registration process for user email ["
                + userEmail + "], please click on the link below."
                + "\n\nCAUTION: if you did not register with bitBay, somebody "
                + "else is trying use your email to scam the system. Please "
                + "do not click on the link below unless you intend to confirm "
                + "your registraiton with bitBay.\n\n"
                + "Here's the link to complete the registraiton process: \n\n"
                + "http://localhost:8080/ajsmidtermapp/regVerify?id=" + base64Username); // change the URL to match your app

        try {
            mailSender.send(msg);
        } catch (NullPointerException npe) {
            throw new MailSendException(
                    "Email send error from EmailVerificationSender");
        }
    }

   
}
