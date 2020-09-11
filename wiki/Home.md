## What is SimpleApplication ?

"SimpleApplication" is a java framework for creating complex but flexible desktop applications. At its core stands a 
simple plugin-system that allows developers to easily modularize and structure the code and quickly expand the
application later with independent plugins. "SimpleApplication" uses the JavaFX-Library for the user interface, but
provides an additional abstraction layer inspired by "React" simplifying and streamlining the process of creating
interactive UIs.

## Overview

- Getting Started
- 2.
- 3.







# Getting Started

- getting the maven dependency from github packages
- setting up a base application

```xml
<dependency>
    <groupId>de.ruegnerlukas.simpleapplication</groupId>
    <artifactId>simpleapplication</artifactId>
    <version>0.5</version>
</dependency>
```



# Documentation

- The provider system
- the event system
- the plugin system
- the extension system ?
- misc / utils (validation, resources)

- SimpleUI
  - Overview / Lifecycle
    - basic setup
    - mutation process, ids
    - state management
  - element injection
  - elements
  - properties
    - ...
    - item properties
    - mutation behaviour + in depth
    - ...
  - creating custom elements and properties
  - sui streams
