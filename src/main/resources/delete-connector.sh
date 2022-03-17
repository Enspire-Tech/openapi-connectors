#!/bin/bash

CONNECTOR_NAME=$1

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
pushd $parent_path

# DELETE SRC JAVA

rm -r ../java/com/boomi/connector/${CONNECTOR_NAME}

# DELETE RESOURCES

rm -r ../resources/${CONNECTOR_NAME}

# REMOVE TESTS

rm -r ../../test/java/com/boomi/connector/${CONNECTOR_NAME}

popd


