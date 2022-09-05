# FritzTR064

Java-library to communicate with the AVM FritzBox by using the TR-064 protocol.

## Dependencies

Managed by maven

## Quickstart

### compile and package

mvn compile assembly:single

This command will produce a startable jar containing all dependencies.

### start and query
```bash
java -jar target/FritzTR064-<x.x.x>-SNAPSHOT-jar-with-dependencies.jar fb-ip fb-password fb-user tr064-service service-action [paramName=paramValue]*

```
The above query feature is intended only for testing / toying. 

## Examples
Get all the possible Actions:

```java
FritzConnection fc = new FritzConnection("192.168.1.1","<username>","<password>");
fc.init();
fc.printInfo();
```
The next Example shows how you can get the number of connected Wlan Devices:
```java
FritzConnection fc = new FritzConnection("192.168.1.1","<username>","<password>");
fc.init();
Service service = fc.getService("WLANConfiguration:1");
Action action = service.getAction("GetTotalAssociations");
Response response = action.execute();
int deviceCount = response.getValueAsInteger("NewTotalAssociations");

```
For more examples see: [The Example Folder](src/main/java/de/bausdorf/avm/tr064/examples)

## Resorces
* [AVM API Description](http://avm.de/service/schnittstellen/) (German)
* [Examples](src/main/java/de/bausdorf/avm/tr064/examples)

## Thanks

To Marin Pollmann<pollmann.m@gmail.com> for fiddling out the SOAP stuff.
