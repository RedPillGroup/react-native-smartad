################################################################################################################
#
#   Sonar Scanner Build.
#
#   This build script is used to build and scan the SDK using sonar and to run the unit tests using Slather
#   to compute coverage.
#   All results will automatically be uploaded on SonarCloud.
#
#   Requirement:
#    - Cocapods & git properly must be properly setup on your computer
#    - 'Sonar build wrapper' and 'Sonar scanner' must be installed on your computer and available in the PATH
#      (this script will try to 'source' .zshrc or .bashrc to load the relevant PATH)
#
#   Variables:
#    - All variables from BuildUniversal.sh
#
################################################################################################################

# Deployment always start with a clean universal build (without documentation build because it is not deployed anyway)
BUILD_DOCUMENTATION=false source "${SCRIPT_PATH}/BuildUniversal.sh"


######################
# Importing local environment
######################

load_local_environment


######################
# Global variables
######################

SONAR_REPOSITORY_DIRECTORY="${OUTPUT_DIR}/sonar-build-wrapper-output"
BIN_BUILD_WRAPPER=build-wrapper-macosx-x86
BIN_SONAR_SCANNER=sonar-scanner


######################
# Build using sonar build wrapper
######################

echo "Build using sonar build wrapper…"

${BIN_BUILD_WRAPPER} --out-dir ${SONAR_REPOSITORY_DIRECTORY} \
  xcodebuild -workspace ${BUILD_SCHEME}.xcworkspace -scheme ${BUILD_SCHEME} clean test 2>&1 OBJROOT="${OBJROOT}/DependentBuilds"

if [ $? -eq 0 ]; then
  echo -e "> SDK built successfully"
else
  echo -e "> unable to build the SDK!"
  exit 1
fi


######################
# Unit tests with Slather
######################

echo "Unit tests with Slather…"

slather coverage --llvm-cov --workspace ${BUILD_SCHEME}.xcworkspace --output-directory ${SONAR_REPOSITORY_DIRECTORY}

if [ $? -eq 0 ]; then
  echo -e "> Unit tests are successful"
else
  echo -e "> unit tests unsuccessful! Fix all the tests before any Sonar analysis."
  exit 1
fi


######################
# Sonar scanner & automatic upload to SonarCloud
######################

echo "Sonar scanner & automatic upload to SonarCloud…"

${BIN_SONAR_SCANNER}

if [ $? -eq 0 ]; then
  echo -e "> Unit tests are successful"
else
  echo -e "> unit tests unsuccessful! Fix all the tests before any Sonar analysis."
  exit 1
fi
