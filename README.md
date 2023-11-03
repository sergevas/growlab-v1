# !!! THIS PROJECT IS WORK IN PROGRESS !!!

# The connected Garden application

### Inspired by the [#Growlab](https://github.com/alexellis/growlab) contest, initiated by [Alex Ellis](https://twitter.com/alexellisuk)

This repo contains an application, intended to be installed on the Raspberry Pi.

Also, a few dependencies should be installed before the application deployment:

## JDK 11

The application runs fine
with [Azul Zulu Build of OpenJDK](https://www.azul.com/downloads/?version=java-11-lts&os=linux&architecture=arm-32-bit-hf&package=jdk)
for Linux ARM 32-bit.

## Pi4J

_Note: the current implementation depends on [Pi4J Version 2.0](https://github.com/Pi4J/pi4j-v2)_

[Install](https://pi4j.com/1.4/install.html) Pi4J version 1.4

See [Maven Settings](https://pi4j.com/architecture/about-the-code/maven-settings/)

See [Raspberry Pi pinout](https://pi4j.com/getting-started/understanding-the-pins/)

## Mosquitto MQTT

_Note: current implementation doesn't use Mosquitto_

Mosquitto server can be installed from the Debian repository.

See [Mosquitto Debian repository](https://mosquitto.org/blog/2013/01/mosquitto-debian-repository/)

```bash 
# On the Raspberry Pi
wget http://repo.mosquitto.org/debian/mosquitto-repo.gpg.key
sudo apt-key add mosquitto-repo.gpg.key
cd /etc/apt/sources.list.d/
# For Raspbian GNU/Linux 10 (buster)
sudo wget http://repo.mosquitto.org/debian/mosquitto-buster.list

sudo apt update
sudo apt install mosquitto mosquitto-clients
```

Mosquitto starts on boot after the installation.

"mosquitto" system service status check:

```bash
sudo systemctl status mosquitto
```

Add config file:

```bash
sudo nano /etc/mosquitto/conf.d/rpi.conf
```

with the following content:

```text
connection_messages true
```

Enable Username and Password Authentication:

```bash
cd /etc/mosquitto
sudo mosquitto_passwd -c passwds garden
#Enter password for garden user
```

Update config file /etc/mosquitto/conf.d/rpi.conf
with the following content:

```text
allow_anonymous false
password_file /etc/mosquitto/passwds
```

Restart "mosquitto" system service:

```bash
sudo systemctl restart mosquitto
```

## Deployment

The process implemented with [Wagon Maven Plugin](http://www.mojohaus.org/wagon-maven-plugin/)

The application jar and [Helidon](https://helidon.io/#/) ``libs`` directory content are copied into the device using the
plugin:

``scp://${rpi.ip}/home/pi/growlabv1/app/helidon``

See the application's [pom.xml](https://github.com/sergevas/growlab-v1/blob/main/pom.xml) for the details.

The device authentication profile has to be added into ``servers`` element of Maven's ``settings.xml`` file

## Linux service for Java application

#### Describes how to configure Linux service, running the Java application deployed as a jar file.

The device OS has to be prepared to run the application.
See discussion on [Pi4J v2](https://github.com/Pi4J/pi4j-v2) issue [#60](https://github.com/Pi4J/pi4j-v2/issues/60)

Main points:

- Pi4J native libs have a dependency on [pigpio](http://abyz.me.uk/rpi/pigpio/download.html) library.
  The installation procedure can be found here: [Download & Install](http://abyz.me.uk/rpi/pigpio/download.html)

  I ran through the **Download and install the latest version** section without any issues:
  ```bash
  wget https://github.com/joan2937/pigpio/archive/master.zip
  unzip master.zip
  cd pigpio-master
  make
  sudo make install
  ```
- the default [pigpio](http://abyz.me.uk/rpi/pigpio/faq.html#Cant_initialise_pigpio_library) daemon should be stopped
  ```bash
  sudo killall pigpiod
  sudo systemctl disable pigpiod
  ```
- the application has to be run using ``sudo``

To run the application as ``systemctl`` Linux service:

- put [growlabv1.service](https://github.com/sergevas/growlab-v1/blob/main/src/main/resources/system/growlabv1.service)
  into ``/etc/systemd/system``
- reload systemd manager configuration
  ```bash
  systemctl daemon-reload
  ```
- start growlabv1.service
  ```bash
  systemctl start growlabv1.service
  ```
- enable growlabv1.service
  ```bash
  systemctl enable growlabv1.service
  ```
- see the application logs
  ```bash
  journalctl --unit=growlabv1.service -f
  ```
