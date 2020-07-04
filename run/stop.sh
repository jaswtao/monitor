#!/bin/sh
pid=`cat pid`
if [ $pid ]
then
  kill -9 $pid
  > pid
fi

