JAVAC=/usr/bin/javac
.SUFFIXES: .java .class
BINDIR = bin
SRCDIR = src
JAVA = usr/bin/java
$(BINDIR)/%.class: $(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

classes= BinaryTreeNode.class BTQueueNode.class \
         BTQueue.class BinaryTree.class \
         AVLTree.class AVLTreeTest.class \
         Vaccine.class AVLExperiment.class
CLASS_FILES = $(classes:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/*.class

run: $(CLASS_FILES)
	java -cp $(BINDIR) AVLExperiment

docs:
	javadoc -d doc $(SRCDIR)/*.java
