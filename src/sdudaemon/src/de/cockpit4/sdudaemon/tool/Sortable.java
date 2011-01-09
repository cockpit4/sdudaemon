/*
Copyright (c) 2011 cockpit4, Kevin Krüger

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */

package de.cockpit4.sdudaemon.tool;

/** Interface recommended for general Datasorting. Implement this Interface and define the behavor in the class implementing this interface.
 * So you are able to sort more complex data types than numeric arrays
 * @author kneo
 */
public interface Sortable {
    /**Supposed to return true if two Sortables are equal
     * @param t comparing Sortable
     * @return true if your defnition of equality fits
     */
    public boolean equalTo(Sortable t);
    /**Supposed to return true if two Sortables are not equal
     * @param t comparing Sortable
     * @return true if your defnition of inequality fits
     */
    public boolean inequalTo(Sortable t);
    /**Supposed to return true if one of two Sortables is lesser than the other
     * @param t comparing Sortable
     * @return true if your defnition of lesser than fits
     */
    public boolean lesserThan(Sortable t);
    /**Supposed to return true if one of two Sortables is greater than the other
     * @param t comparing Sortable
     * @return true if your defnition of greater than fits
     */
    public boolean greaterThan(Sortable t);
    /**Supposed to return true if one of two Sortables is lesser than or equal to the other
     * @param t comparing Sortable
     * @return true if your defnition of lesser or equal than fits
     */
    public boolean lesserOrEqualTo(Sortable t);
    /**Supposed to return true if one of two Sortables is greater than or equal to the other
     * @param t comparing Sortable
     * @return true if your defnition of greater or equal than fits
     */
    public boolean greaterOrEqualTo(Sortable t);
    /**according to several sorting algoritms there meight be a requirement to swap elements.
     * Override this function to swap the structure data as needed, allowing you to implement generalized sorting algorithms
     */
    public void swap(Sortable t);
}
