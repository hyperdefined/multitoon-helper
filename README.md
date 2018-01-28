# Multitoon Helper
Multitoon Helper is a program aimed at users who have a lot of TTR accounts. This program can track the accounts and allow for more easier time logging into them. This program also comes with the famous Multicontroller (the program downloads it from the offical website). 

![screenshot1](https://raw.githubusercontent.com/hyperdefined/images/master/github/multitoon-helper/screenshot1.png)
# Prerequisites 
Before you use this program, you are going to need [Panda3D-1.9.0](https://www.panda3d.org/download.php?sdk&version=1.9.0). Make sure it installs in the default directory.
# Setting Up
First, open the `config/accounts.json` file and add your accounts. Make sure the numbers are going in order starting from zero. You can add more accounts, just make sure the JSON is formatted correctly and the numbers are in order.
# Usage
After you added your account, restart the program. If the JSON file is correct, then your username(s) will be listed. Double click one to login. The game will now launch with that account. You can launch as many accounts as you wish.
# Download
The download can be found on the [release](https://github.com/hyperdefined/multitoon-helper/releases) page. Make a new folder and extract the contents of the zip file. Run Multitoon-Helper.jar to use.
# Troubleshooting
### Nothing happens when I run Multitoon-Helper.jar!
There was some error. Run the .jar from command prompt like this: `java -jar Multitoon-Helper.jar`. That will show an error message. Feel free to create an issue.
### My accounts.json is not correct?
This means that your accounts.json is not a valid json file. Check the formatting.
### My accounts.json can't be found.
Make sure it is named correctly and it's in the `config` folder.
# Libraries Used
* [Apache Commons IO](https://commons.apache.org/proper/commons-io/)
* [JSON in Java](https://mvnrepository.com/artifact/org.json/json/20140107)
# Credits
* [Stack Overflow](https://stackoverflow.com/): Many code examples and help.
* [Loonatic](https://pastebin.com/Az7qgHKq): For the TTR Python launcher (the login.py file).
