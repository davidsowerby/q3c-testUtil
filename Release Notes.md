### Release Notes for q3c-testUtil 0.8.1

Finally got the Travis and Coveralls (Jacoco coverage) working

#### Change log

-   [17](https://github.com/davidsowerby/q3c-testUtil/issues/17): Build #34 fails
-   [18](https://github.com/davidsowerby/q3c-testUtil/issues/18): Remove Guice from build



#### Dependency changes


#### Detail

*Updated version information*


---
*Fix [18](https://github.com/davidsowerby/q3c-testUtil/issues/18) Removed Guice*

Added cglib-nodeps as required by Spock


---

---
*Fix [17](https://github.com/davidsowerby/q3c-testUtil/issues/17) Tests failed on Travis*

Caused by absolute file comparison.  Modified tests to take account of running on different platform



---
