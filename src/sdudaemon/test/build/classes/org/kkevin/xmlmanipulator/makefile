JAVAC=javac
JAVADOC=javadoc
CLASSPATH="../../../../lib/jdom.jar:../../../../lib/webharvest_all_2.jar"

all: doc
	$(JAVAC) -classpath $(CLASSPATH) -d ../../../../lib/ *.java
doc:
	$(JAVADOC) -classpath $(CLASSPATH) -d ../../../../doc/XMLManipulator/ *.java
