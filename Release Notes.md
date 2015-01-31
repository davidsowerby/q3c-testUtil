### Release Notes for q3c-testUtil 0.7.3

This version removes the IDEA project files from Git, and moves a couple of classes in from the krail core

#### Change log

-   [1](https://github.com/davidsowerby/q3c-testUtil/issues/1): Remove IDEA project files from git
-   [2](https://github.com/davidsowerby/q3c-testUtil/issues/2): Bring in test utilities from krail core
-   [3](https://github.com/davidsowerby/q3c-testUtil/issues/3): Group incorrect


#### Dependency changes

   compile dependency version changed to: krail:0.7.9

#### Detail

Release notes and version.properties generated

---
*Merge branch 'master' into develop*

Conflicts:
	.idea/.name


---
*Set version information*


---
*Updated README files to conform to q3c-gradle format.  See davidsowerby/krail-master#315.*


---
*Javadoc*


---
*Fix [3](https://github.com/davidsowerby/q3c-testUtil/issues/3). group now set by this project's build file*

Overrides the default 'uk.q3c.krail' set by the master build, with 'uk.q3c'


---
*Fix [2](https://github.com/davidsowerby/q3c-testUtil/issues/2). Test utilities moved in from krail*

FileTestUtil and TesUtils copied in.  Will still need to be deleted from krail, see [krail 314](https://github.com/davidsowerby/krail/issues/314)


---
*See [krail 313](https://github.com/davidsowerby/krail/issues/313).  ExampleUtil moved into this project*


---
*Fix [1](https://github.com/davidsowerby/q3c-testUtil/issues/1) Removed IDEA project files from Git*


---
