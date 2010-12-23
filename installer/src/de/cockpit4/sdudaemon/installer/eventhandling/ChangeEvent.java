/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.installer.eventhandling;

/**
 *
 * @author kneo
 */
public class ChangeEvent {
    private int value;

    public ChangeEvent(int value){
        this.value=value;
    }

    public int getValue(){
        return value;
    }
}
