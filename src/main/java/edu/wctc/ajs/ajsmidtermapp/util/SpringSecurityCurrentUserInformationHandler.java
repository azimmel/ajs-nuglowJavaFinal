
package edu.wctc.ajs.ajsmidtermapp.util;

import java.util.Objects;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
/**
 *
 * @author Alyson
 */
public class SpringSecurityCurrentUserInformationHandler {
    private String username;


    public SpringSecurityCurrentUserInformationHandler() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.username = user.getUsername(); //get logged in username
    }

    public final String getUsername() {
        return username;
    }

    public final void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.username);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SpringSecurityCurrentUserInformationHandler other = (SpringSecurityCurrentUserInformationHandler) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return true;
    }
    
}
