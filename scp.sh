#!/bin/sh
ip=`sh /Users/chengx/bin/my/get_ip.sh`
scp target/firstblood.war root@$ip:/home/admin/webapps/temp
