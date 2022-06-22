#!/bin/bash
mkdir -p fonts/open-sans/
rm fonts/open-sans/* -rf

sizes="8 12 14 16 18 20 22 24 28"

for size in $sizes
do
    java -jar target/*-jar-with-dependencies.jar \
        --input src/main/resources/OpenSans-Regular.ttf \
        --output fonts/open-sans/OpenSans-$size-Regular \
        --size $size \
        --text-antialias-lcd-hrgb \
        --fractional-metrics-on \
        --spacing-x 10 \
        --spacing-y 10
done

