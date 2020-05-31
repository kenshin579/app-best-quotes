#!/bin/bash

# upgrade java
sudo yum install -y java-1.8.0-openjdk-devel.x86_64
sudo /usr/sbin/alternatives --config java
sudo yum remove java-1.7.0-openjdk
java -version


# change timezone
sudo rm /etc/localtime
sudo ln -s /usr/share/zoneinfo/Asia/Seoul /etc/localtime
date

# change Hostname
sudo vim /etc/sysconfig/network
sudo reboot
sudo vim /etc/hosts
