#!/usr/bin/env bash

JAVA_HOME=/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home mvn clean install -Dtarget_connector=$1
