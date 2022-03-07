# Framework YY

## Requirements:

[comment]: <> (* Install appium server &#40;For Linux you can use the image **Appium-linux-1.18.3.AppImage** from https://github.com/appium/appium-desktop/releases or install via npm **"npm install -g appium"**&#41;)

[comment]: <> (* Install adb manager &#40;to emulate devices&#41;, you can have it with android studio)

[comment]: <> (    * Command for linux to see the list names of devices **emulator -list-avds**)

[comment]: <> (    * Command for linux to start the device **emulator -avd device_name**)
* Install Java 11
* Install Maven
* Create environment variable (JAVA_HOME and MAVEN_HOME) 

[comment]: <> (    * **You must point to this path so you can work with the devices:**  )

[comment]: <> (        * export ANDROID_SDK_HOME=/user.dir/Android/Sdk)

[comment]: <> (        * export PATH=${PATH}:$ANDROID_SDK_HOME/emulator:$ANDROID_SDK_HOME/tools:$PATH**)


## Running the stories
This will run the build and (after a minute or so) will open the application in the chrome navigator and execute the test:

```shell
 mvn clean test
```

  This will run a single story by tag

    mvn clean test -Dcucumber.filter.tags="@login"

This will run a suite based on the tags in the story files:

    mvn clean test -Dcucumber.filter.tags="@regression"

### Debugging the stories

Just as a normal run, but replacing 'mvn' with 'mvnDebug'

    mvnDebug clean test

This execution will stop and wait until a debugger connects to the port

    8000

You can accomplish this by creating a Debug Configuration in your IDEA or running the class **RunTest.java** with debug mode. 

## Viewing the results

* The framework uses the **Extent Report Plugin** to generate the report view for each executed test.
* In a **test-output/** directory, a page called **Spark.html** has been generated.
* If you open it in any browser you can see the stories that have been run and their running status.
* There should be a row for each story.  You can click on the story reports via links in the left column. You also have a dashboard and a menu with more information about the run.

# Framework YY description

## Structure

The framework follows the *Page Object Model*. The structure of the framework is:

- **PageObjects**, *contains the objects* that will be used in the Page classes, this is used in the *initialization* of the *Page classes* by the
- **pages**, contains *the pages themselves*, these classes define *all the selenium web driver code needed to work within a web page application*, this is the correspondence to reality.
- **runner**: contains the cucumber runner class so far.
- **steps**: contains the *classes that work with the Gerkin language* defined in the steps for the scenarios in the *features file*...
- **utils**: contains the utility or *help classes for some functionality of the project*, e.g. *DataGenerator* and *RestAssuredExtension*.
- **resources**, contains the *resources of the project in general*, e.g. *application properties*, *feature files*, *cumber properties*; in other projects it could also be the webview templates.

## How to create a new page and their Step Def

1. Define a new Page class that inherit the BasePage class.
2. Define a PageObject type.
3. Define a constructor that pass the driver class to be injected, in this constructor defines PageFactory to create the page using the PageObject.
4. Create the methods that verify, execute something with the *web elements.*
5. Associate it with a *Step def class* to verify *your feature and their scenarios*.






