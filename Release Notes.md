### Release Notes for q3c-testUtil 0.7.4

This version modified the TestResource class to be more robust. Adds gitattributes file

#### Change log

-   [1](https://github.com/davidsowerby/q3c-testUtil/issues/1): Remove IDEA project files from git
-   [4](https://github.com/davidsowerby/q3c-testUtil/issues/4): Inconsistent test paths


#### Dependency changes

   compile dependency version changed to: krail:0.8.0

#### Detail

*Fix [4](https://github.com/davidsowerby/q3c-testUtil/issues/1) TestResource reworked*

The method for identifying the project root is changed to try and accommodate IDEA, Eclipse and Gradle, though unable to test for Eclipse.


---
*Update version information*


---
*gitattributes added*

To overcome Linux/Windows line ending issues


---
