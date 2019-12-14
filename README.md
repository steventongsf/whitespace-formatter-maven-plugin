
# whitespace-formatter-maven-plugin
After using the formatter-maven-plugin, I found that doesn't 
* substitute leading tabs with spaces
* it doesn't remove trailing tabs and spaces

This plugin has been created to address these needs:
* converting leading tabs to spaces (no upper limit to number of spaces per tab. the default is 4 spaces)
* Remove trailing whitespace and tabs

A sample client has been provided to illustrate how to use the plugin under client/pom.xml

# Releases

## Release 1.0.0 
* replace leading tabs

## Release 1.1.0
* Bad release.  Do not use

## Release 1.2.0
* replace trailing spaces and tabs

## Release 1.2.1
* Add some default values to the configuration
