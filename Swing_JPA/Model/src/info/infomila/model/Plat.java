/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author averd
 */
@Entity
public class Plat implements Serializable {
     @Id
     private long codi;
     @Basic(optional = false)
     private String nom;
     @Basic(optional = false)  
     private String descripcioMD;
     @Basic(optional = false)
     private float preu;
     @Basic(optional = false)
     private int disponible;
     @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
     @JoinColumn(name="categoria")
     private Categoria categoria;
     @OneToMany(mappedBy="plat")
     private List<LiniaEscandall> escandall;
     
     protected Plat(){}

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

    
    
    public String getDescripcioMD() {
        return descripcioMD;
    }

    public void setDescripcioMD(String descripcioMD) {
        this.descripcioMD = descripcioMD;
    }

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) {
        this.preu = preu;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<LiniaEscandall> getEscandall() {
        return escandall;
    }

    public void setEscandall(List<LiniaEscandall> escandall) {
        this.escandall = escandall;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (int) (this.codi ^ (this.codi >>> 32));
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
        final Plat other = (Plat) obj;
        if (this.codi != other.codi) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Plat{" + "codi=" + codi + ", descripcioMD=" + descripcioMD + 
                ", preu=" + preu + ", disponible=" + disponible + ", categoria=" + categoria + '}';
    }
     
     
     
     
     
}
