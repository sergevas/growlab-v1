## Connected Garden application inspired by the [#Growlab](https://github.com/alexellis/growlab) contest, initiated by [Alex Ellis](https://twitter.com/alexellisuk)

This repo contains application, intended to be installed to the Raspberry Pi.

Also, a few dependencies should be installed before the application deployment:

### JDK 11  
The application runs fine with [Azul Zulu Build of OpenJDK](https://www.azul.com/downloads/?version=java-11-lts&os=linux&architecture=arm-32-bit-hf&package=jdk) for Linux ARM 32-bit.

### Pi4J

[Install](https://pi4j.com/1.4/install.html) Pi4J version 1.4

See [Maven Settings](https://pi4j.com/architecture/about-the-code/maven-settings/)

See [Raspberry Pi pinout](https://pi4j.com/getting-started/understanding-the-pins/)

### Mosquitto MQTT

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