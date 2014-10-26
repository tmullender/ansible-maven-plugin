# Ansible Maven Plugin 

[![Build Status](https://travis-ci.org/tmullender/ansible-maven-plugin.svg?branch=master)](https://travis-ci.org/tmullender/ansible-maven-plugin)

A [Maven plugin](http://maven.apache.org/plugins/index.html) to simplify the running 
of [Ansible](http://docs.ansible.com/) [playbooks](http://docs.ansible.com/playbooks.html) from within a Maven Project.
 
Available from the [Central Repository](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22ansible-maven-plugin%22)

* [Overview](#overview)
* [Usage](#usage)
* [Goals](#goals)
* [Release Notes](#release-notes)

## Overview

* __ansible:help__ displays the available goals and parameters
* [ansible:ansible](#ansible) executes an ansible module, using the ansible executable
* [ansible:playbook](#playbook) runs an ansible playbook, using the ansible-playbook executable

## Usage

* [ansible](#ansible)
* [playbook](#playbook)

See the [integration tests](src/it) for examples of using the plugin within a project

### ansible

To execute the ansible __ping__ module with the host as __localhost__

```
      <plugin>
        <groupId>co.escapeideas.maven</groupId>
        <artifactId>ansible-maven-plugin</artifactId>
        <version>1.0.1</version>
        <executions>
          <execution>
            <id>ansible</id>
            <goals>
              <goal>ansible</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
```

From the command line

```
  mvn co.escapeideas.maven:ansible-maven-plugin:ansible -Dansible.moduleName=ping -Dansible.hosts=localhost
``` 
 
 or in short form
 
```
  mvn ansible:ansible -Dansible.moduleName=ping -Dansible.hosts=localhost
```  

### playbook

To run the playbook __playbook.yml__

```
      <plugin>
        <groupId>co.escapeideas.maven</groupId>
        <artifactId>ansible-maven-plugin</artifactId>
        <version>1.0.1</version>
        <executions>
          <execution>
            <id>ansible-playbook</id>
            <goals>
              <goal>playbook</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
```

From the command line

```
  mvn co.escapeideas.maven:ansible-maven-plugin:playbook -Dansible.playbook=playbook.yml 
```  

or in short form

```
  mvn ansible:playbook -Dplaybook=ansible.playbook.yml 
```  

## Goals

* [ansible](#ansible-1)
* [playbook](#playbook-1)

### ansible 

Binds by default to the [lifecycle phase](http://maven.apache.org/ref/current/maven-core/lifecycles.html): _pre-integration-test_

####Parameters
  Name | Type | Description | Required
  :----|:----:|:------------|:-------:
  background|Integer|Run asynchronously, failing after this number of seconds | No
  connection|String|Connection type to use | No
  executable|String|The executable to use for this execution, __defaults__ to _ansible_ | __Yes__
  forks|Integer|The number of parallel processes to use | No
  hosts|String|Pattern for matching hosts to run the module against, __defaults__ to _localhost_ | __Yes__
  inventory|File|The inventory host file | No
  limit|String|Limit selected hosts to an additional pattern | No
  moduleArgs|String|Module arguments | No
  moduleName|String|Module name to execute, __defaults__ to _ping_ | __Yes__
  modulePath|File|The path to the ansible module library | No
  pollInterval|Integer|The poll interval if using background | No
  privateKey|File|Use this file to authenticate the connection | No
  remoteUser|String|Connect as this user | No
  timeout|Integer|Override the SSH timeout in seconds | No
  vaultPasswordFile|File|Vault password file | No
  workingDirectory|File|The directory in which to run, __defaults__ to _project.build.directory_ or the java tmp directory if it does not exist | __Yes__
  
To use a parameter on the command line, prefix it with __ansible.__
  
### playbook

Binds by default to the [lifecycle phase](http://maven.apache.org/ref/current/maven-core/lifecycles.html): _pre-integration-test_

####Parameters
  Name | Type | Description | Required
  :----|:----:|:------------|:-------:
  connection|String|Connection type to use | No
  executable|String|The executable to use for this execution, __defaults__ to _ansible-playbook_ | __Yes__
  extraVars|String|Additional variables as key=value or YAML/JSON | No
  forks|Integer|The number of parallel processes to use | No
  inventory|File|The inventory host file | No
  limit|String|Limit selected hosts to an additional pattern | No
  modulePath|File|The path to the ansible module library | No
  playbook|File|The playbook to run, __defaults__ to _playbook.yml_ | __Yes__
  pollInterval|Integer|The poll interval if using background | No
  privateKey|File|Use this file to authenticate the connection | No
  skipTags|String|Only run plays and tasks whose tags do not match these values | No
  startAtTask|String|Start the playbook at the task matching this name | No
  remoteUser|String|Connect as this user | No
  tags|String|Only run plays and tasks tagged with these values | No
  timeout|Integer|Override the SSH timeout in seconds | No
  vaultPasswordFile|File|Vault password file | No
  workingDirectory|File|The directory in which to run, __defaults__ to _project.build.directory_ or the java tmp directory if it does not exist | __Yes__

To use a parameter on the command line, prefix it with __ansible.__

## Release Notes

### 1.0.0 Initial release 
  Release date: 24-10-2014
### 1.0.1 Bug fix release
  Release date: 26-10-2014
  
  * [Issue#1](issues/1) Command line execution fails outside of project directories

