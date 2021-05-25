/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.model;

import java.io.Serializable;

/**
 *
 * @author averd
 */
public class LiniaEscandallId implements Serializable {
    private long platId;
    private int num;
    
    protected LiniaEscandallId(){}
    
    public LiniaEscandallId(long platId, int num)
    {
        this.platId = platId;
        this.num = num;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (int) (this.platId ^ (this.platId >>> 32));
        hash = 71 * hash + this.num;
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
        final LiniaEscandallId other = (LiniaEscandallId) obj;
        if (this.platId != other.platId) {
            return false;
        }
        if (this.num != other.num) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LiniaEscandallId{" + "platId=" + platId + ", num=" + num + '}';
    }
    
    
}
