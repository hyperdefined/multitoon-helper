import getpass
import urllib
import sys
import os
import json
import time
import platform
import subprocess

class TTRQuickLauncher:
    login_url = 'https://www.toontownrewritten.com/api/login?format=json'

    username = sys.argv[1]
    password = sys.argv[2]
    def __init__(self):
        login = urllib.urlencode({'username': self.username, 'password': self.password})
        self.postRequest(login)

    def postRequest(self, request):
        post = urllib.urlopen(self.login_url, request)
        resp = json.loads(post.read())

        success = resp.get('success', 'false')
        
        if success == 'true':
            os.environ['TTR_PLAYCOOKIE'] = resp.get('cookie', 'CookieNotFound')
            os.environ['TTR_GAMESERVER'] = resp.get('gameserver', 'ServerNotFound')
            system = platform.system()
            if system == 'Windows':
                os.chdir("C:\\Program Files (x86)\\Toontown Rewritten\\")
                os.system("TTREngine.exe")
            elif system == 'Linux':
                os.system('./TTREngine')
            else:
                print 'Platform %s isn\' supported yet.' %system

        elif success == 'delayed':
            eta = resp.get('eta', '5')
            position = resp.get('position', '0')
            token = resp.get('queueToken', None)

            if token == None:
                print "No queue token was given. This is not supposed to be possible!"
            else:
                print "You're in %s in line. ETA: %s\n" % (position, eta)
                time.sleep(1)
                queueRequest = urllib.urlencode({'queueToken': token})
                self.postRequest(queueRequest)

        elif success == 'partial':
            banner = resp.get('banner', 'Please enter an authenticator token')
            authToken = resp.get('responseToken', None)

            if authToken == None:
                print "A auth token for response couldn't be found."
            else:
                appToken = raw_input(banner + '\n')

                authRequest = urllib.urlencode({'appToken': appToken, 'authToken': authToken})
                self.postRequest(authRequest)

        elif success == 'false':
            banner = resp.get('banner', "Login have failed, but the reason why was not given. Try again later.")
            print banner

        
TTRQuickLauncher = TTRQuickLauncher()