#!/bin/bash
counter=0
while :
do
         filename="/home/houssem/bigdata-docker-compose/nifi/stream/file$counter.csv"
	./flog -s 10s -n 1 >> "$filename"
	 counter=$((counter+1))
	sleep 1
done
