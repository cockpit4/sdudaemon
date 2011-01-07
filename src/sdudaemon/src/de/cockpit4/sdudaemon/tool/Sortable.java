/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.tool;

/**
 *
 * @author kneo
 */
public interface Sortable {
    public boolean equalTo(Sortable t);
    public boolean inequalTo(Sortable t);
    public boolean lesserThan(Sortable t);
    public boolean greaterThan(Sortable t);
    public boolean lesserOrEqualTo(Sortable t);
    public boolean greaterOrEqualTo(Sortable t);
    public void swap(Sortable t);
}
