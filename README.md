# Ansible Maven Plugin 

[![Build Status](https://travis-ci.org/tmullender/ansible-maven-plugin.svg?branch=master)](https://travis-ci.org/tmullender/ansible-maven-plugin)

A [maven plugin](http://maven.apache.org/plugins/index.html) to simplify the running 
of [Ansible](http://docs.ansible.com/) [playbooks](http://docs.ansible.com/playbooks.html) from within Maven. 
Available from the [Central Repository](http://search.maven.org/)

## Goals Overview

* __ansible:help__ displays the available goals and parameters
* [ansible:ansible](#ansible) executes an ansible module, using the ansible executable
* [ansible:playbook](#playbook) runs an ansible playbook, using the ansible-playbook executable

## Usage

See the [integration tests](src/it) for examples of using the plugin within a project

### ansible
```
      <plugin>
        <groupId>co.escapeideas.maven</groupId>
        <artifactId>ansible-maven-plugin</artifactId>
        <version>1.0.0</version>
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

```
  mvn co.escapeideas.maven:ansible-maven-plugin:ansible -DmoduleName=ping -Dhosts=localhost
``` 
 
 or in short form
 
```
  mvn ansible:ansible -DmoduleName=ping -Dhosts=localhost
```  

Executes the ansible __ping__ module with the host as __localhost__

### playbook
```
      <plugin>
        <groupId>co.escapeideas.maven</groupId>
        <artifactId>ansible-maven-plugin</artifactId>
        <version>1.0.0</version>
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

```
  mvn co.escapeideas.maven:ansible-maven-plugin:playbook -Dplaybook=playbook.yml 
```  

or in short form

```
  mvn ansible:playbook -Dplaybook=playbook.yml 
```  

Runs the playbook __playbook.yml__

## Goals
<a id="ansible"></a>
### ansible

Binds by default to the [lifecycle phase](http://maven.apache.org/ref/current/maven-core/lifecycles.html): _pre-integration-test_

####Parameters
  Name | Type | Description | Required
  :----|:----:|:------------|:-------:
  background|Integer|Run asynchronously, failing after this number of seconds | No
  connection|String|Connection type to use | No
  executable|String|The executable to use for this execution, defaults to _ansible_ | __Yes__
  forks|Integer|The number of parallel processes to use | No
  hosts|String|Pattern for matching hosts to run the module against, defaults to _localhost_ | __Yes__
  inventory|File|The inventory host file | No
  limit|String|Limit selected hosts to an additional pattern | No
  moduleArgs|String|Module arguments | No
  moduleName|String|Module name to execute, defaults to _ping_ | __Yes__
  modulePath|File|The path to the ansible module library | No
  pollInterval|Integer|The poll interval if using background | No
  privateKey|File|Use this file to authenticate the connection | No
  remoteUser|String|Connect as this user | No
  timeout|Integer|Override the SSH timeout in seconds | No
  vaultPasswordFile|File|Vault password file | No
  workingDirectory|File|The directory in which to run, defaults to _basedir_ | __Yes__
  
<a id="playbook"></a>
### playbook
Binds by default to the [lifecycle phase](http://maven.apache.org/ref/current/maven-core/lifecycles.html): _pre-integration-test_

####Parameters
  Name | Type | Description | Required
  :----|:----:|:------------|:-------:
  connection|String|Connection type to use | No
  executable|String|The executable to use for this execution, defaults to _ansible-playbook_ | __Yes__
  extraVars|String|Additional variables as key=value or YAML/JSON | No
  forks|Integer|The number of parallel processes to use | No
  inventory|File|The inventory host file | No
  limit|String|Limit selected hosts to an additional pattern | No
  modulePath|File|The path to the ansible module library | No
  playbook|File|The playbook to run, defaults to _playbook.yml_ | __Yes__
  pollInterval|Integer|The poll interval if using background | No
  privateKey|File|Use this file to authenticate the connection | No
  skipTags|String|Only run plays and tasks whose tags do not match these values | No
  startAtTask|String|Start the playbook at the task matching this name | No
  remoteUser|String|Connect as this user | No
  tags|String|Only run plays and tasks tagged with these values | No
  timeout|Integer|Override the SSH timeout in seconds | No
  vaultPasswordFile|File|Vault password file | No
  workingDirectory|File|The directory in which to run, defaults to _basedir_ | __Yes__

## Release Notes

### 1.0.0 Initial release 24-10-2014