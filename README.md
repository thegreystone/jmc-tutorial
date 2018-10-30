# JDK Mission Control Tutorial

This tutorial provides plenty of examples and material to help you learn JDK Mission Control (7+).

** This is work in progress, I will not upload the lab instructions before I am done submitting my various projects. Do not use this repo until I have removed this warning! **

## Preparations
For the HoL at CodeOne, this material is usually pre-packaged with everything you need, such as an Eclipse with the eclipse.ini file already modified. This is not practical at GitHub, so there are some preparations required before starting the HoL.

### Setting up the JDK
You will need to have a JDK 11 or later to do this tutorial. You can either use the [Oracle JDK](http://java.oracle.com) or any OpenJDK build, for example the one provided by [Oracle](http://jdk.java.net/11/).

You will need to ensure that `java` for your JDK is on your path, and you should also make sure that your JAVA_HOME variable is set to the parent folder of the `bin` folder containing your `java` binary.

### Setting up Eclipse
The tutorial will be easier to run if you have an Eclipse installed. You will need an Eclipse Oxygen 4.8.0 or later. You will also need to add some VM arguments.

For example:

```ini
-vmargs
-Djdk.attach.allowAttachSelf=true
--add-exports=java.xml/com.sun.org.apache.xerces.internal.parsers=ALL-UNNAMED
--add-exports=jdk.internal.jvmstat/sun.jvmstat.monitor=ALL-UNNAMED
--add-exports=java.management/sun.management=ALL-UNNAMED
--add-exports=java.management/sun.management.counter.perf=ALL-UNNAMED
--add-exports=jdk.management.agent/jdk.internal.agent=ALL-UNNAMED
--add-exports=jdk.attach/sun.tools.attach=ALL-UNNAMED
--add-opens=java.base/java.net=ALL-UNNAMED
--add-opens=jdk.attach/sun.tools.attach=ALL-UNNAMED
```

Depending on your platform you will also need to add one final export.

If running on Windows, also add:

```ini
--add-exports=java.desktop/sun.awt.windows=ALL-UNNAMED
```

If running on Mac OS, also add:

```ini
--add-exports=java.desktop/sun.lwawt.macosx=ALL-UNNAMED
```

If running on Linux, also add:

```ini
--add-exports=java.desktop/sun.awt.X11=ALL-UNNAMED
```

You may also want to ensure that your newly setup JDK is being used for running Eclipse. This can be enforced by using the -vm option in the eclipse.ini file. Don't forget that the -vmargs option must be last in the file. For example:

```ini
-vm
/Library/Java/JavaVirtualMachines/jdk-11.jdk/Contents/Home/bin
-vmargs
-Djdk.attach.allowAttachSelf=true
...
```

#### Adding the Eclipse plug-ins

Next you will want to add the JMC plug-ins. Once JMC is released, this will be as simple as going to the Eclipse market place. For now, you will need to build them yourself. Install mercurial (if you don't already have it) and run the following command in the folder you wish to clone the JMC source:

```bash
hg clone http://hg.openjdk.java.net/jmc/jmc/
```

Follow the instructions in the README.md found in the root of the JMC repository on how to create and access the update sites for Eclipse.

#### Importing the projects
To import the projects into Eclipse, create a new Workspace and simply import all the projects available in the projects folder.

## Running the Tutorial
There is a PDF in the root of this project explaining in detail how to run the JMC labs. If running the labs from within Eclipse, first ensure that you have set up an Eclipse properly, added the plug-in version of JMC, and imported the projects.

## About
This tutorial is for learning how to use the JDK Mission Contol. It is provided under GPLv3 as is. If you find a problem, please open a ticket or feel free to provide a pull request.