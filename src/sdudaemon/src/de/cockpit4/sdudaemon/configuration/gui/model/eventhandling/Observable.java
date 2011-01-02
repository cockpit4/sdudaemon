/*
Copyright (c) 2010 cockpit4, Kevin Krüger

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

package de.cockpit4.sdudaemon.configuration.gui.model.eventhandling;

/**To watch part of the observer actor model
 */
public interface Observable {

	/**This method is the callback to notify all registered observers about a change made to an model object, what ever ...
	 */
	public void fireChangeEvent(); //add a event class to resolve changes
	/**Adds a listener to the notify list
	 * @param listener
	 */
	public void addListener(ModelChangeListener listener);
	/** Removes a listener from the notify list
	 * @param listener
	 */
	public void removeListener(ModelChangeListener listener);
}
