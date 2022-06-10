#!/bin/bash
mkdir -p fonts/
rm fonts/* -rf

atlasWidth=256
atlasHeight=256
sizes="12 14 16 18 20 22 24 26 28"

for size in $sizes
do
    java -jar target/*-jar-with-dependencies.jar \
        --input src/main/resources/TerminusTTF-Bold.ttf \
        --output fonts/Terminus-$size-Bold \
        --width $atlasWidth \
        --height $atlasHeight \
        --size $size
done

for size in $sizes
do
    java -jar target/*-jar-with-dependencies.jar \
        --input src/main/resources/TerminusTTF.ttf \
        --output fonts/Terminus-$size \
        --width $atlasWidth \
        --height $atlasHeight \
        --size $size
done

