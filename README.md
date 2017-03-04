1. Create jar file
```bash
mvn clean install
```
2. Run timer script
```bash
bash ./runJavaMain.sh
```
3. Find process pid using jps utils
```bash
jps -lV
```
4. chmod bin folder and then cd jajsw/bin and execute with process pid
```bash
chmod -R 744 bin
cd bin
genConfig.sh <jar_pid>
```
5. Run app from console
```bash
./runConsole.sh
```
Some useful commands from documentation:

Execute your wrapped application as console application by calling

    runConsole.bat
    check that your application (for example tomcat) is running

To Install the application as service call

    installService.bat

To start the service:

    startService.bat

To stop the service:

    stopService.bat

To uninstall the service:

    uninstallService.bat