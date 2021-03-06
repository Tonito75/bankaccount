# bankaccount
Android app of Antoine L, IOS2

1. The user starting the app must enter a key in order to get his informations. This key is 123456 (in a real context, this key should be more complex and maybe given 
by email). This is a simple one in this case, just in order to be easier to write for you to test. This key is stored locally, so the authentification can even be offline.

2. I saved user data on txt files, with a custom personnal encryption algorythm. The data of the user is stored like this for example :
X|d_5!-'#]^^jphpI]j`#5 >p`_dr@]n`!=^_jpls '#\ljoju61+--j]_i5#NB14-2/,,5-+5-,1/3+311.2)^pmmbj^u#5=bebhf\oBjig[o#x)v
the original is :
"[{"id":"1","accountName":"Credit Card Account","amount":602,"iban":"SE6301400620821039065013","currency":"Bahamian Dollar"}]"...
This algorithm has been made with C++.

3. The API URL is stored in the C++ file, since this is almost impossible to decompile C++ clearly. The URL is then stored encrypted in an other txt file. This is how the url looks
in the txt file once encrypted :
euokn6*.2-,1.a,11].,a.+1,5+3+,-6)jk_gbld*dj-k_]c]`ie+b`_jpiup

ScreenShots :

Offline :
https://github.com/Tonito75/bankaccount/issues/1#issue-823626614

