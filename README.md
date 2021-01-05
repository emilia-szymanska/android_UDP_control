# android\_UDP\_control

This repository contains an Android mobile app with which a connection via UDP can be made and used for a remote control of a Turtlebot Burger robot. 
Its ROS pipeline with simulated movements can be found [here](https://github.com/emilia-szymanska/turtlebot_control).

## Prerequisites

Make sure that the port with which you are supposed to connect is not blocked by your computer or any other softwares.
The code's first destination was Galaxy J5 (Android 7.1.1, screen 1280x720px). The code is supposed to deal with a screen 1920x1080px (not tested on a physical device yet). If you run the code on a screen with a different resolution, it is very important to change the source file of the background to the one with the corresponding resolution.
If you encounter some problems (e.g. app slows dow, not handled exception), do not hesitate do contact - any feedback and hints are welcome.

Clone this repo by running:
```
git clone https://github.com/emilia-szymanska/android_UDP_control
```

The image shown below presents examples of views of the app.

![Image of App Views](https://github.com/emilia-szymanska/android_UDP_control/blob/main/views_presentation.png)

## Contents    

The app contains:

- `MainActivity` java class: change input values into IP and port, init a connection with a server;
- `ArrowActivity` java class: every 50ms send a message (information about which button is pressed) to a UDP server;
- `UDPClient` java class: handle a connection with a server (sending and/or receiving messages);
- `UpdateTextsRunnable` java class: additional class helping with updating text views in `MainActivity`.

`MainActivity` consists of three buttons:
- button `CONNECT`, after being clicked, invokes functions which take the data from input text areas and try to init a connection with a UDP server,
- button `RETRY CONNECTING`, after being clicked, invokes an interruption of a connection and/or resets the values in input text areas,
- button `NEXT` is enabled after a confirmation from the server (message "OK") and allows to change the view to the one from `arrow_view.xml`.

## Credits
Images used in this project were taken from Google Creative Commons.
