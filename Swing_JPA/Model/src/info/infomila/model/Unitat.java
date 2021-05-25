/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author averd
 */
@Entity
public class Unitat implements Serializable {
    @Id
    private long codi;
    @Basic(optional = false)
    private String nom;
    
    protected Unitat(){}

    public long getCodi() {
        return codi;
    }

    public void setCodi(long codi) {
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (int) (this.codi ^ (this.codi >>> 32));
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
        final Unitat other = (Unitat) obj;
        if (this.codi != other.codi) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Unitat{" + "codi=" + codi + ", nom=" + nom + '}';
    }
    
    
}
