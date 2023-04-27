/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pidev.event.entities;

/**
 *
 * @author mizoj
 */
public class Tool {
    private int id ; 
    private String name;
    private boolean returned ;

    public Tool(int id, String name, boolean returned) {
        this.id = id;
        this.name = name;
        this.returned = returned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
    
    
    
    
}
