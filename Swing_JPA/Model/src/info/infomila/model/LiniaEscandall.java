/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author averd
 */
@Entity
@IdClass(LiniaEscandallId.class)
public class LiniaEscandall implements Serializable {
    @Id
    @Column(name="plat")
    private long platId;
    
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "plat", insertable = false, updatable = false)
    private Plat plat;
    @Id
    private int num;
    @Basic(optional = false)
    private int quantitat;
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    @JoinColumn(name="unitat")
    private Unitat unitat;
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    @JoinColumn(name="ingredient")
    private Ingredient ingredient;
    
    protected LiniaEscandall(){}

    public Plat getPlat() {
        return plat;
    }

    public void setPlat(Plat plat) {
        this.plat = plat;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    public Unitat getUnitat() {
        return unitat;
    }

    public void setUnitat(Unitat unitat) {
        this.unitat = unitat;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public long getPlatId() {
        return platId;
    }

    public void setPlatId(long platId) {
        this.platId = platId;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.plat);
        hash = 89 * hash + this.num;
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
        final LiniaEscandall other = (LiniaEscandall) obj;
        if (this.num != other.num) {
            return false;
        }
        if (!Objects.equals(this.plat, other.plat)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LiniaEscandall{" + "plat=" + plat
                + ", num=" + num + ", quantitat=" + quantitat + ", unitat=" + unitat + ", ingredient=" + ingredient + '}';
    }
    
    
}
