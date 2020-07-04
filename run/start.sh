#!/bin/sh

timestamp() {
  date +"%Y%m%d%H%M%S"
}

pid=`cat pid`
if [ $pid ]
then
  echo 'application already started!'
  exit 1
fi
for thing in $(ls -t | grep *.jar);
do
    #echo $thing;
    nohup java -jar $thing > nohup.log 2>&1 &
    break;
done
echo $! > pid

while ! netstat -lnp | grep 4445 > /dev/null;
do
  echo "wait while starting java application...$(timestamp)"
  sleep 1
done

echo "application started successful!"
exit 0

