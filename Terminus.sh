#!/bin/bash
mkdir -p fonts/terminus/
rm fonts/terminus/* -rf

sizes="12 14 16 18 20 22 24 28"

for size in $sizes
do
    java -jar target/*-jar-with-dependencies.jar \
        --input src/main/resources/TerminusTTF-Bold.ttf \
        --output fonts/terminus/Terminus-$size-Bold \
        --size $size 
done

for size in $sizes
do
    java -jar target/*-jar-with-dependencies.jar \
        --input src/main/resources/TerminusTTF.ttf \
        --output fonts/terminus/Terminus-$size \
        --size $size 
done

