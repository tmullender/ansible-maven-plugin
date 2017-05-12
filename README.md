# Ansible Maven Plugin 

[![Codeship Status for tmullender/ansible-maven-plugin](https://codeship.com/projects/b6180bd0-4127-0132-ff07-7a81632181b7/status?branch=master)](https://codeship.com/projects/44111)
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
* [ansible:pull](#pull) set up a remote copy of ansible, using the ansible-pull executable

## Usage

* [ansible](#ansible)
* [playbook](#playbook)
* [pull](#pull)

See the [integration tests](src/it) for examples of using the plugin within a project

### ansible

To execute the ansible __ping__ module with the host as __localhost__

```
      <plugin>
        <groupId>co.escapeideas.maven</groupId>
        <artifactId>ansible-maven-plugin</artifactId>
        <version>1.3.0</version>
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
        <version>1.3.0</version>
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
  mvn ansible:playbook -Dansible.playbook=playbook.yml 
```  

### pull

To pull the repo __git://example.com/repo__

```
      <plugin>
        <groupId>co.escapeideas.maven</groupId>
        <artifactId>ansible-maven-plugin</artifactId>
        <version>1.3.0</version>
        <executions>
          <execution>
            <id>ansible-pull</id>
            <goals>
              <goal>pull</goal>
            </goals>
          </execution>
          <configuration>
            <directory>somewhere</directory>
            <url>git://example.com/repo<url>
          </configuration>
        </executions>
      </plugin>
```

From the command line

```
  mvn co.escapeideas.maven:ansible-maven-plugin:pull -Dansible.url='git://example.com/repo' -Dansible.directory=somewhere 
```  

or in short form

```
  mvn ansible:pull -Dansible.url='git://example.com/repo' -Dansible.directory=somewhere 
```  


## Goals

* [ansible](#ansible-1)
* [playbook](#playbook-1)
* [pull](#pull-1)

### ansible 

Binds by default to the [lifecycle phase](http://maven.apache.org/ref/current/maven-core/lifecycles.html): _pre-integration-test_

#### Parameters
  Name | Type | Description | Required
  :----|:----:|:------------|:-------:
  background|Integer|Run asynchronously, failing after this number of seconds | No
  connection|String|Connection type to use | No
  executable|String|The executable to use for this execution, __defaults__ to _ansible_ | __Yes__
  failOnAnsibleError|boolean|If true, the build will fail when the ansible command returns an error(a non zero exit status), __defaults__ to _false_ | No
  forks|Integer|The number of parallel processes to use | No
  hosts|String|Pattern for matching hosts to run the module against, __defaults__ to _localhost_ | __Yes__
  inventory|File|The inventory host file | No
  limit|String|Limit selected hosts to an additional pattern | No
  logDirectory|File|If present the plugin will log the output of the execution to files in this directory | No
  moduleArgs|String|Module arguments | No
  moduleName|String|Module name to execute, __defaults__ to _ping_ | __Yes__
  modulePath|File|The path to the ansible module library | No
  options|List|Additional options to be included in the command | No
  pollInterval|Integer|The poll interval if using background | No
  privateKey|File|Use this file to authenticate the connection | No
  promoteDebugAsInfo|boolean|Output messages will be promoted from debug messages to info messages, __defaults__ to _false_| No
  remoteUser|String|Connect as this user | No
  timeout|Integer|Override the SSH timeout in seconds | No
  vaultPasswordFile|File|Vault password file | No
  workingDirectory|File|The directory in which to run, __defaults__ to _project.build.directory_ or the java tmp directory if it does not exist | __Yes__
  
To use a parameter on the command line, prefix it with __ansible.__
  
### playbook

Binds by default to the [lifecycle phase](http://maven.apache.org/ref/current/maven-core/lifecycles.html): _pre-integration-test_

#### Parameters
  Name | Type | Description | Required
  :----|:----:|:------------|:-------:
  connection|String|Connection type to use | No
  executable|String|The executable to use for this execution, __defaults__ to _ansible-playbook_ | __Yes__
  extraVars|List|Additional variables as key=value or YAML/JSON | No
  failOnAnsibleError|boolean|If true, the build will fail when the ansible command returns an error(a non zero exit status), __defaults__ to _false_ | No
  forks|Integer|The number of parallel processes to use | No
  inventory|File|The inventory host file | No
  limit|String|Limit selected hosts to an additional pattern | No
  logDirectory|File|If present the plugin will log the output of the execution to files in this directory | No
  modulePath|File|The path to the ansible module library | No
  options|List|Additional options to be included in the command | No
  playbook|File|The playbook to run, __defaults__ to _playbook.yml_ | __Yes__
  pollInterval|Integer|The poll interval if using background | No
  privateKey|File|Use this file to authenticate the connection | No
  promoteDebugAsInfo|boolean|Output messages will be promoted from debug messages to info messages, __defaults__ to _false_| No
  skipTags|String|Only run plays and tasks whose tags do not match these values | No
  startAtTask|String|Start the playbook at the task matching this name | No
  syntaxCheck|boolean|Perform a syntax check on the playbook, but do not execute it, __defaults__ to _false_ | No
  remoteUser|String|Connect as this user | No
  tags|String|Only run plays and tasks tagged with these values | No
  timeout|Integer|Override the SSH timeout in seconds | No
  vaultPasswordFile|File|Vault password file | No
  workingDirectory|File|The directory in which to run, __defaults__ to _project.build.directory_ or the java tmp directory if it does not exist | __Yes__

To use a parameter on the command line, prefix it with __ansible.__

### pull

Binds by default to the [lifecycle phase](http://maven.apache.org/ref/current/maven-core/lifecycles.html): _pre-integration-test_

#### Parameters
  Name | Type | Description | Required
  :----|:----:|:------------|:-------:
  checkout|String|The branch, tag or commit to checkout | No
  executable|String|The executable to use for this execution, __defaults__ to _ansible-pull_ | __Yes__
  extraVars|String|Additional variables as key=value or YAML/JSON | No
  failOnAnsibleError|boolean|If true, the build will fail when the ansible command returns an error(a non zero exit status), __defaults__ to _false_ | No
  force|boolean|Run the playbook even if the repository could not be updated, __defaults__ to _false_ | No
  inventory|File|The inventory host file | No
  logDirectory|File|If present the plugin will log the output of the execution to files in this directory | No
  moduleName|String|Module name used to check out repository | No
  onlyIfChanged|boolean|Only run the playbook if the repository has been updated, __defaults__ to _false_ | No
  options|List|Additional options to be included in the command | No
  playbook|File|The playbook to run | No
  purge|boolean|Purge checkout after playbook run, __defaults__ to _false_ | No
  promoteDebugAsInfo|boolean|Output messages will be promoted from debug messages to info messages, __defaults__ to _false_| No
  sleep|Integer|Sleep for a random interval upto this number of seconds | No
  url|String|URL of the playbook repository | No
  vaultPasswordFile|File|Vault password file | No
  workingDirectory|File|The directory in which to run, __defaults__ to _project.build.directory_ or the java tmp directory if it does not exist | __Yes__

To use a parameter on the command line, prefix it with __ansible.__

## Release Notes

### 1.0.0 Initial release 
  Release date: 24-10-2014
  
### 1.0.1 Bug fix release
  Release date: 26-10-2014
  
  * [Issue#1](https://github.com/tmullender/ansible-maven-plugin/issues/1) Command line execution fails outside of project directories
  
### 1.1.0 Update release
  Release date: 02-12-2014
  
  * [Issue#2](https://github.com/tmullender/ansible-maven-plugin/issues/2) Improve error handling
  * [Issue#3](https://github.com/tmullender/ansible-maven-plugin/issues/3) Add ansible-pull support

### 1.1.1 Bug fix release
  Release date: 08-12-2014
  
  * [Issue#4](https://github.com/tmullender/ansible-maven-plugin/issues/4) Output is not logged until the process has completed
 
### 1.1.2 Bug fix release
  Release date: 04-01-2015
  
  * [Issue#5](https://github.com/tmullender/ansible-maven-plugin/issues/5) Specify multiple extraVars
  * [Issue#6](https://github.com/tmullender/ansible-maven-plugin/issues/6) Configure buffer size

### 1.2.0 Update release
  Release date: 31-03-2015

  * [Issue#8](https://github.com/tmullender/ansible-maven-plugin/issues/8) Display output depending on a settings

### 1.3.0 Update release
  Release date: 14-08-16

  * [Issue#10](https://github.com/tmullender/ansible-maven-plugin/pull/10) Adding syntaxCheck option for ansible-playbook
