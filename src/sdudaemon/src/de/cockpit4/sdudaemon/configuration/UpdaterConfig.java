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

package de.cockpit4.sdudaemon.configuration;

/**
  * Database Loader loads data of an XMLDataTable into a PostGRES Database;
  *
  * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
  * @version 1.0
  *
  *  Copyright (c) 2010 cockpit4 GmbH
  *  sdudeamon is released under the MIT license
  */
public class UpdaterConfig {
	public int id;
	public String host;
	public String port;
	public String user;
	public String pass;
	public String databaseRepository;
	public String db;
	public String table;

	public boolean finished;
	public boolean active;
	public boolean error;

	public UpdaterConfig(int id,String host, String port, String user, String pass, String inputFile,String db,String table,boolean act, boolean fin, boolean err){
		this.host      = host;
		this.port      = port;
		this.user      = user;
		this.pass      = pass;
		this.databaseRepository = inputFile;
		this.db        = db;
		this.table     = table;
		error          = err;
		active         = act;
		finished       = fin;
	}
}
