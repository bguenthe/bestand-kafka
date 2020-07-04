#Kafka
Was für ein Mist, ich habe ewig gebraucht, damit Kafka es läuft!

1.) Virtual Box: Freigabe der Ports 2181 und 9092 and localhost.
2.) Unbedingt den KAFKA_ADVERTISED_HOST_NAME setzen. Diese wird auf localhost gesetzt. 
Durch die Portfreigabe findet der Windows Client dann auch den Broker. 
Dies wird nicht gehen, wenn ich von einem anderen Rechner zugreifen will. 
Dann muss ich die Ports auch für IP-Adresse der VirtualBox freigeben. 
Diese wird per DHCP gesetzt. Sie ist für die vm in dev-setum: 172.28.128.3. Ob dies denn klappt habe ich noch nicht propiert

3.) Den Kafka einfach mit dem docker-compose starten

# Einrichten node.js
https://docs.microsoft.com/de-de/windows/nodejs/setup-on-wsl2

# IM Bestandstream Verzeichnis
## Einrichten Angular
https://cli.angular.io/

## Packages installieren  
npm install

## ng build

#Vagrant
**Plugin zum Rezizen der Disk**

vagrant plugin install vagrant-disksize

**Einbauen mit**

Vagrant.configure('2') do |config|
  config.vm.box = 'ubuntu/xenial64'
  config.disksize.size = '50GB'
end

#Docker

Alle stopped Container löschen
docker rm $(docker ps -aq)

Alle images löschen
docker rmi $(docker images -q)

#DNS
Zum testen
in  c:\windows\system32\drivers\etc\hosts folgende Zeilen eintragen
    127.0.0.1       kafka
	127.0.0.1       zookeeper
	127.0.0.1       postgres	
