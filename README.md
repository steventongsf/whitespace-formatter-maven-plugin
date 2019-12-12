
# whitespace-formatter-maven-plugin
2
Initial purpose to replace leading tab characters with spaces from a Maven plugin. This plugin was created to supplement the formatter-maven-plugin which does not support:
3
* converting leading tabs to spaces (no upper limit to number of spaces per tab. the default is 4 spaces)

* provide a way to add more formatting options in the future
* Remove trailing whitespace and tabs

A sample client has been provided to illustrate how to use the plugin under client/pom.xml

# Releases

## Release 1.0.0 
* replace leading tabs

## Release 1.1.0
* Bad release.  Do not use

## Release 1.2.0
* replace trailing spaces and tabs
