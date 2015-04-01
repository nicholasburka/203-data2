JFLAGS = -g -Xlint
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	MultiSet.java \
	Branch.java \
	#Leaf.java \
	#Tester.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class