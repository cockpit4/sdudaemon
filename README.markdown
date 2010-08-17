Description
===========
This is a Webscraping project. Other solutions found lags features this project combines.

- Scraping Framework  : contains everything necessary to download picture and text information.
- Data adaption       : scripting engine adapting data to fit in a relational database
- Database management : updates data in the database

Main purpose of this project is the harvesting of textual information from websites. There is an example project showing the the advances and the 

Use case(s)
===========
- you want to copy lots of search results of a given query
- you want to update this kind of data periodicaly
- you want to map fields (or parts of them) of a HTML-Website to SQL datatypes (in order to build special querys)

Requirements
============
The project in its current state needs some additional jars added to classpath in order to work properly.
A Database server is needed as well.
For now just PostgreSQL is supported. (more may follow)

- JDOM (http://www.jdom.org/)
- jaxen (usualy shipped with JDOM)
- PostGreSQL Connector for JDBC (http://jdbc.postgresql.org/download.html)
- PostGreSQL Database Server (http://www.postgressql.org/)
- Beanshell (http://www.beanshell.org/)
- WebHarvest (http://web-harvest.sourceforge.net/)

Limits
======
The early version of this project has some limitations

- no data updating application simply drops tables and recreates them
- limited multithreding (multiple projects run simultanous, not restricted to computer resources)

Known Issues
============
- nasty memory leak crashing the JVM in combination with a project plugin
- huge memory usage while adapting data. Caused by the JDOM XML Framework
