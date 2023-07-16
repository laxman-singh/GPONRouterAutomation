# GPONRouterAutomation
Selenium Java-based project for automating the router reboot using its web console. It also uses GraalVM to compile byte code into native binary.

## Setup
This project uses `gradle` as build tool. Run the below command to build this project:
```groovy
gradle clean build
```

It will create a standalone jar file inside `build/libs` directory.

Also check GraalVM native image generation config files are created or not. Look for the below files:

- build
  - classes
    - main
      - com
      - META-INF
        - com.thinqerr
          - hint
            - dynamic-proxy-config.json
            - native-image.properties
            - resource-config.json
            - reflect-config.json

  
Create native image:
> **Note**
> GraalVM JDK is needed to create the native executable. Follow these instructions to setup and 
installation of [GraaalVM](https://www.graalvm.org/latest/docs/getting-started/).

```shell
native-image -jar GPONRouterAutomation-1.0-SNAPSHOT.jar 
```

The above command will create a native executable binary, which you can run directly:

```shell
./router-automation
```

Output:
```log
Executing GPON Router Automation Task..........!!
1689536363934	geckodriver	INFO	Listening on 127.0.0.1:18505
*** You are running in headless mode.
console.warn: services.settings: Ignoring preference override of remote settings server
console.warn: services.settings: Allow by setting MOZ_REMOTE_SETTINGS_DEVTOOLS=1 in the environment
WebDriver BiDi listening on ws://127.0.0.1:22578
Read port: 39021
console.error: ({})
DevTools listening on ws://127.0.0.1:22578/devtools/browser/e71d16e6-544c-4561-9210-6e49b8f6b247
console.warn: LoginRecipes: "Falling back to a synchronous message for: http://192.168.1.1."
console.warn: LoginRecipes: "Falling back to a synchronous message for: http://192.168.1.1."
console.warn: services.settings: Ignoring preference override of remote settings server
console.warn: services.settings: Allow by setting MOZ_REMOTE_SETTINGS_DEVTOOLS=1 in the environment
console.error: ({})
Jul 17, 2023 1:09:32 AM org.openqa.selenium.devtools.CdpVersionFinder findNearestMatch
WARNING: Unable to find CDP implementation matching 85
Hurry, mission accomplished ..........!!

```