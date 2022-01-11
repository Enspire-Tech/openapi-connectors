#!/usr/bin/env bash

# USAGE
# /Users/jacobmortelliti/IdeaProjects/openapi-connectors/src/main/resources/separate-connector.sh -o /Users/jacobmortelliti/boomi/openapi_connector_repos -t guru


# PREREQUISITES
# This script must be located in the resources directory of the project.
# Only tested on MacOS. The "sed" command may not work on other operating systems.

# A POSIX variable
OPTIND=1         # Reset in case getopts has been used previously in the shell.

# Fail on error
set -e

# Initialize our own variables:
out_dir=""
target=""
message=""

while getopts "t:o:m:" opt; do
  case "$opt" in
    o)  out_dir=$OPTARG
      ;;
    t)  target=$OPTARG
      ;;
    m)  message=$OPTARG
      ;;
    *)  echo "invalid option or argument $OPTARG"
      exit 1
      ;;

  esac
done

shift $((OPTIND-1))

[ "${1:-}" = "--" ] && shift

if [ -z "$target" ]
  then
    echo "No target supplied"
    exit 1
fi
if [ -z "$out_dir" ]
  then
    echo "No out directory supplied"
    exit 1
fi

script_dir="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
base_dir="$(dirname "$(dirname "$(dirname "$script_dir")")")"

echo "target='$target', output_dir='$out_dir', base_dir='$base_dir'"

#
# Remove old local dir
#
working_dir="$out_dir"/"$target"
echo Removing old directories if they exist: "$working_dir" lib and src
rm -Rf "$working_dir"/lib
rm -Rf "$working_dir"/src

#
# Copy files
#
echo Creating working directory: "$working_dir"
mkdir -p "$working_dir"

echo Copying lib: "$working_dir"/lib
cp -R "$base_dir"/lib "$working_dir"/lib



echo Copying assembly dir: "$working_dir"/src/assembly
mkdir -p "$working_dir"/src/main/java/com/boomi/connector/
cp -R "$base_dir"/src/assembly "$working_dir"/src/assembly

echo Copying source: "$working_dir"/src/main/java/com/boomi/connector/"$target"
cp -R "$base_dir"/src/main/java/com/boomi/connector/"$target" "$working_dir"/src/main/java/com/boomi/connector/"$target"

echo Copying resources: "$working_dir"/src/main/resources/
mkdir -p "$working_dir"/src/main/resources
cp -R "$base_dir"/src/main/resources/"$target"/ "$working_dir"/src/main/resources/"$target"
cp "$base_dir"/src/main/resources/com.boomi.Overrides "$working_dir"/src/main/resources
cp "$base_dir"/src/main/resources/bitbucket-pipelines.yml "$working_dir"

echo Copying test java: "$working_dir"/src/test/java/com/boomi/connector/"$target"
mkdir -p "$working_dir"/src/test/java/com/boomi/connector/
cp -R "$base_dir"/src/test/java/com/boomi/connector/"$target" "$working_dir"/src/test/java/com/boomi/connector/"$target"

echo Copying test resources: "$working_dir"/src/test/resources
cp -R "$base_dir"/src/test/resources "$working_dir"/src/test/

echo Copying root level files: .gitignore, pom.xml, LICENSE
cp "$base_dir"/.gitignore "$base_dir"/pom.xml "$base_dir"/LICENSE "$working_dir"

echo Moving readme to "$working_dir"/README.md
mv "$working_dir"/src/main/resources/"$target"/"$target"-readme.md "$working_dir"/README.md

#
# Substitute target name
#
echo Filling out pom.xml and assembly.xml with target name
sed -i '' "s/\${target_connector}/$target/g" "$working_dir"/src/assembly/assembly.xml "$working_dir"/pom.xml


#
# Commit to svn
#
pushd "$working_dir"

if [ -z "$message" ]
  then
    message="Automated update"
fi

git add .
git commit -m "$message"
git push origin master

popd