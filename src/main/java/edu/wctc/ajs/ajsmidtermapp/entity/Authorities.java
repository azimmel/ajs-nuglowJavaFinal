package edu.wctc.ajs.ajsmidtermapp.entity;

import edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alyson
 */
@Entity
@Table(name = "authorities")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Authorities.findAll", query = "SELECT a FROM Authorities a"),
    @NamedQuery(name = "Authorities.findByAuthoritiesId", query = "SELECT a FROM Authorities a WHERE a.authoritiesId = :authoritiesId"),
    @NamedQuery(name = "Authorities.findByUsername", query = "SELECT a FROM Authorities a WHERE a.username = :username"),
    @NamedQuery(name = "Authorities.findByAuthority", query = "SELECT a FROM Authorities a WHERE a.authority = :authority")})
public class Authorities implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "authorities_id")
    private Integer authoritiesId;
    @Size(max = 50)
    @Column(name = "username")
    private String username;
    @Size(max = 50)
    @Column(name = "authority")
    private String authority;

    /**
     * Empty Constructor.
     */
    public Authorities() {
    }

    /**
     * Constructor that passes in the authorities id.
     * @param authoritiesId the id of the authorities record.
     */
    public Authorities(Integer authoritiesId) {
        this.authoritiesId = authoritiesId;
    }

    /**
     * Gets the id for the authorities record.
     * @return the id.
     */
    public final Integer getAuthoritiesId() {
        return authoritiesId;
    }

    /**
     * Sets the id for the authorities record.
     * @param authoritiesId The id.
     */
    public final void setAuthoritiesId(Integer authoritiesId) {
        this.authoritiesId = authoritiesId;
    }

    /**
     * Gets the username of the user.
     * @return The users username.
     */
    public final String getUsername() {
        return username;
    }

    /**
     * Sets the username that the role is assigned to.
     * @param username The users username.
     */
    public final void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the authority for the user.
     * @return the role for the user.
     */
    public final String getAuthority() {
        return authority;
    }

    /**
     * Set the authority for the user.
     * This is the roll for the username. Ex: 'ROLE_MGR' or 'ROLE_USER'
     * @param authority roll for the username.
     */
    public final void setAuthority(String authority) {
        
        this.authority = authority;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (authoritiesId != null ? authoritiesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Authorities)) {
            return false;
        }
        Authorities other = (Authorities) object;
        if ((this.authoritiesId == null && other.authoritiesId != null) || (this.authoritiesId != null && !this.authoritiesId.equals(other.authoritiesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.wctc.ajs.ajsmidtermapp.model.Authorities[ authoritiesId=" + authoritiesId + " ]";
    }
    
}
