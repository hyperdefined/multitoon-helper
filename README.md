# Multitoon Helper
Multitoon Helper is a program aimed at users who have a lot of TTR accounts. This program can track the accounts and allow for more easier time logging into them. This program also comes with the famous Multicontroller (the program downloads it from the offical website). 

![screenshot](https://raw.githubusercontent.com/hyperdefined/images/master/github/multitoon-helper/screenshot.png)
# Prerequisites 
Before you use this program, you are going to need [Panda3D-1.9.0](https://www.panda3d.org/download.php?sdk&version=1.9.0). Make sure it installs in the default directory.
# Setting Up
First, open the `config/accounts.json` file and add your accounts. To add your accounts, change the username# and password# fields. To add more accounts, simply just copy and paste a field. Make sure the numbers are in order starting from zero.

<a href="http://www.youtube.com/watch?feature=player_embedded&v=XNp6H3KPMAM
" target="_blank"><img src="http://img.youtube.com/vi/XNp6H3KPMAM/0.jpg" 
alt="video" width="240" height="180" border="10" /></a>
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
# FAQ
### Is this program allowed?
Yes. TTR allows third-party launchers. They don't allow custom game engines.
### Does this support 2FA and Toonguard?
Yes. When you double click an account, it will ask your for the code on the prompt.
# Libraries Used
* [Apache Commons IO](https://commons.apache.org/proper/commons-io/)
* [JSON in Java](https://mvnrepository.com/artifact/org.json/json/20140107)
# Credits
* [Stack Overflow](https://stackoverflow.com/): Many code examples and help.
* [Loonatic](https://pastebin.com/Az7qgHKq): For the TTR Python launcher (the login.py file).
