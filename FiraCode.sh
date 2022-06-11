#!/bin/bash
mkdir -p fonts/fira-code/
rm fonts/fira-code/* -rf

sizes="8 12 14 16 18 20 22 24 28"

for size in $sizes
do
    java -jar target/*-jar-with-dependencies.jar \
        --input src/main/resources/FiraCode-Regular.ttf \
        --output fonts/fira-code/FiraCode-$size-Regular \
        --size $size \
        --text-antialias-lcd-hrgb \
        --fractional-metrics-on
done

for size in $sizes
do
    java -jar target/*-jar-with-dependencies.jar \
        --input src/main/resources/FiraCode-Medium.ttf \
        --output fonts/fira-code/FiraCode-$size-Medium \
        --size $size \
        --text-antialias-lcd-hrgb \
        --fractional-metrics-on
done

for size in $sizes
do
    java -jar target/*-jar-with-dependencies.jar \
        --input src/main/resources/FiraCode-Bold.ttf \
        --output fonts/fira-code/FiraCode-$size-Bold \
        --size $size \
        --text-antialias-lcd-hrgb \
        --fractional-metrics-on
done
