package edu.wctc.ajs.ajsmidtermapp.entity;

import edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User class for Users in database to log in.
 * @author Alyson
 * @version 1.1
 */
@Entity
@Table(name = "users")
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "username")
    private String username;
    @Size(max = 255)
    @Column(name = "password")
    private String password;
    @Column(name = "enabled")
    private Boolean enabled;
    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Column(name = "version")
    private Integer version;

    /**
     * Empty constructor
     */
    public User() {
    }

    /**
     * User constructor to set username.
     * @param username name of the user
     */
    public User(String username) {
        this.username = username;
    }

    /**
     * Gets the users username.
     * @return the user's name is returned.
     */
    public final String getUsername() {
        return username;
    }

    /**
     * Sets the users username.
     * @param username the users name.
     */
    public final void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the users password.
     * @return users password.
     */
    public final String getPassword() {
        return password;
    }

    /**
     * Sets the users password
     * @param password the users password.
     */
    public final void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the boolean value for if a users account is 
     * enabled or disabled.
     * @return True- enables. False- disables.
     */
    public final Boolean getEnabled() {
        return enabled;
    }

    /**
     * This method enables and disables the users account.
     * 
     * @param enabled True- enables. False- disables.
     */
    public final void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get the date of the last time the account was updated.
     * @return date of the last time the account was updated.
     */
    public final Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the date for the last time the users account was updated.
     * @param lastUpdate Date for the last time the account was updated.
     */
    public final void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets the version for the account.
     * @return version number
     */
    public final Integer getVersion() {
        return version;
    }

    /**
     * Sets the version for the account.
     * @param version version number.
     */
    public final void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.wctc.ajs.ajsmidtermapp.model.Users[ username=" + username + " ]";
    }
    
}
