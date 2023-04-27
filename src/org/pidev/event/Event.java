/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pidev.event;

import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author mizoj
 */
public class Event {
    private int id ;
    private String nom , type ;
    private Date date_event;

    public Event(int id, String nom, String type, Date date) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.date_event = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date_event;
    }

    public void setDate(Date date) {
        this.date_event = date;
    }

    
    
}
