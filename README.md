# CamClient  

Written by Matt Bertolini

## About CamClient

**NOTE:** This isn't really maintained anymore. I occasionally poke at the repo 
for fun. No artifacts are published.

CamClient is a pure Java implementation of the Cisco Clean Access Manager API. 
This library is meant to shield developers from having to deal with the HTTP 
interface of the Cisco API and provide a clean interface that integrates easily 
into your Java application. It allows a user to perform many different actions 
on the Clean Access Manager like adding, updating, and removing MAC addresses, 
subnets, and users. For more information on the Clean Access Manager API that 
this library is based on, read the Clean Access Manager Configuration guide for 
your version of Clean Access.

## License

CamClient is licensed under the "New BSD License" Please see the LICENSE file 
for full license information

## Minimum Requirements

CamClient requires Java 6 to run.

## Dependencies

CamClient has no runtime dependencies other than Java. If you are building the 
library from source, JUnit is required to build.

## Building CamClient

To build CamClient, make sure you have Apache Ant and Ivy installed and 
configured. Once configured, run ant on the build.xml file at the root of the 
project.

## Frequently Asked Questions

### What authentication methods does CamClient support?

The Cisco documentation lists two possible methods of authentication to the 
Clean Access Manager: session-based and function-based. Currently, CamClient 
only supports function-based authentication. This means the account credentials 
are given with every request to the Clean Access Manager. CamClient abstracts 
that work so you only provide the credentials to the library once. I will look 
to support session-based authentication at some point in the future.

### Is CamClient thread safe?

CamClient uses an HTTP connection behind the scenes so if multiple threads use 
the same CamConnection object, a new HTTP connection is opened for each caller. 
That being said, the code has not been examined for thread safety and should be 
assumed not thread safe. If you see a section of code that is not thread safe, 
file an issue and I will see about correcting it.

### I found a bug. What do I do?

If you find a bug or other issue with CamClient, please file an issue at 
https://github.com/mattbertolini/CamClient/issues

### Why aren't all of the operations listed in the CAM configuration guide supported in CamClient?

I wrote this API purely from the knowledge gained from working on a Clean Access 
system while I was in college and from the API documentation provided by Cisco. 
Unfortunately, the documentation that Cisco provides is not entirely clear and 
I do not have the tens of thousands of dollars to buy a Clean Access system. 
Rather than write a buggy API, I only implemented the operations that where the 
most clearly documented. 

If there is an operation that is not yet in the  CamClient and you would like 
it implemented, please file an issue. When filing, please provide as much 
detail as possible. Data like Clean Access version, parameter values, and 
return data strings are extremely helpful when implementing new operations.