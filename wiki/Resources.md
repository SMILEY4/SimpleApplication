

## Resources

An abstraction layer addressing files inside and outside of the packaged jar.



### 1. Internal Files

For when the file is packaged inside the jar. The path is then usually relative to the maven "resource" directory.

```
MyProject
- src
  - main
    - java
      - ...
    - resources
      - images
        - testImage.png
  - test
    ...
```

```java
Resource resource = Resource.internal("images/testImage.png");
resource.asURL();            // returns the resource as an URL
resource.asInputStream();    // return the file this resource points to as a stream
resource.getPath();          // returns the path to the file. "images/testImage.png" in this example
```



### 2. External Files

For when the file is outside the jar.

```
MyProject
- src
  - main
    - java
      - ...
    - resources
      - images
        - testImage.png
  - test
    ...
```

```java
Resource resource = Resource.external("D:/.../MyProject/src/main/resources/images/testImage.png");
Resource resource = Resource.externalRelative("src/main/resources/images/testImage.png");
resource.asURL();   // returns the resource as an URL
resource.asFile();  // return the file this resource points to
resource.getPath(); // returns the absolute path to the file.
```

