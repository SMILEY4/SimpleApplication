## What is SimpleApplication ?

"SimpleApplication" is a Java framework for creating complex but flexible desktop applications. At its core stands a simple plugin-system that allows developers to easily modularize and structure the code and quickly expand the application later with independent plugins. "SimpleApplication" uses the JavaFX-Library for the user interface, but provides an additional abstraction layer inspired by "React" simplifying and streamlining the process of creating interactive UIs.

An example application can be checked out at https://github.com/SMILEY4/JXClipboard



## Getting Started

```xml
<dependency>
    <groupId>de.ruegnerlukas.simpleapplication</groupId>
    <artifactId>simpleapplication</artifactId>
    <version>${simpleapplication.version}</version>
</dependency>
```

The maven project is currently hosted on "Github Packages". To be able to pull maven-packages from GitHub, add the following lines to your maven settings file (in ".m2/settings.xml").

```xml
<settings xmlns=...>

    ...
    
    <servers>
      <server>
        <id>github</id>
        <username>YOUR_GITHUB_NAME</username>
        <password>YOUR_GITHUB_TOKEN</password>
      </server>
    </servers>

    ...
    
</settings>
```

**The settings.xml file now contains private information. DO NOT SHARE settings.xml with anybody!**

Also add the folling block to your project's pom.xml:

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/smiley4/simpleapplication</url>
    </repository>
</repositories>
```

Maven is then ready to use packages from Github and the dependency can be added to the pom.