Description
===========
This is a Webscraping project. Other solutions found lag features this project combines.

- Scraping Framework  : contains everything necessary to download picture and text information
- Data adaption       : scripting engine adapting data to fit in a relational database
- Database management : updates data in the database

Main purpose of this project is harvesting of textual information from websites.
There is an example project showing the features this project provides.

Use case(s)
===========
- you want to copy lots of search results of a given query
- you want to update this kind of data periodicaly
- you want to map fields (or parts of them) of a HTML-Website to SQL datatypes (in order to build special querys)

Requirements
============
The project in its current state needs some additional jars added to classpath in order to work properly.
A database server is needed as well.
For now just PostgreSQL is supported. (more may follow)

- [JDOM]: http://www.jdom.org/
- jaxen (usualy shipped with JDOM)
- [PostGreSQL Connector for JDBC]: http://jdbc.postgresql.org/download.html
- [PostGreSQL Database Server]: http://www.postgressql.org/
- [Beanshell]: http://www.beanshell.org/
- [WebHarvest]: http://web-harvest.sourceforge.net/

Limits
======
The early (pre pre pre alpha) version of this project has some limitations.

- no data updating application simply drops tables and recreates them yet
- limited multi threading (multiple projects run simultanous, not restricted to computer resources)

Known Issues
============
- nasty memory leak crashing the JVM in combination with a project plugin
- huge memory usage while adapting data. Caused by the JDOM XML Framework
- No XML-Validers
- XML-Dispatching (lot of work to do here)
- Lots of runtime errors

Next Steps
==========
- fix all bugs
- XML valider
- better database management

License
=======
Copyright (c) 2010 Kevin Krueger, cockpit4 GmbH

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
