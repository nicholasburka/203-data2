JFLAGS = -g -Xlint
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Sequenced.java \
	Sequence.java \
	MultiSet.java \
	Branch.java \
	BranchSequence.java \
	Concatenation.java \
	Leaf.java \
	Tester.java 

default: classes

classes: $(CLASSES:.java=.class)

#javadoc: Javadoc MultiSet.java > ./javadoc/

clean:
	$(RM) *.class 
	rm -rfv ./javadoc/ && mkdir ./javadoc