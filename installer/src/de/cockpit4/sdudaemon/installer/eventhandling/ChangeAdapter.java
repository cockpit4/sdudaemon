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

package de.cockpit4.sdudaemon.installer.eventhandling;

import java.util.ArrayList;

/**
 *
 * @author kneo
 */
public class ChangeAdapter implements Observable {
    private ArrayList<ChangeListener> listeners = new ArrayList<ChangeListener>();
    private ArrayList<FinishedListener> flisteners = new ArrayList<FinishedListener>();
    public void addChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        listeners.remove(listener);
    }

    public void fireChangeEvent(ChangeEvent evt) {
        for(ChangeListener l : listeners){
            l.onChange(evt);
        }
    }

    public void addFinishListener(FinishedListener listener) {
        flisteners.add(listener);
    }

    public void removeFinishListener(FinishedListener listener) {
        flisteners.remove(listener);
    }

    public void fireFinishEvent() {
        for(FinishedListener l : flisteners){
            l.onFinish();
        }
    }
}
