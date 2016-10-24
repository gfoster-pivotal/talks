#!/bin/sh

cd ..;
gradle assemble;
cf login -a https://api.local.pcfdev.io --skip-ssl-validation -u user -p pass;

cf d book-producer-v1 -f -r;
cf d book-producer-v2 -f -r;
cf d consumer-v1 -f -r;

cf p -f book-producer/book-producer-v1_manifest.yml;
cf p -f book-producer/book-producer-v2_manifest.yml;
cf map-route book-producer-v1 local.pcfdev.io --hostname book;

cf p -f consumer/consumer-v1_manifest.yml;
cf map-route consumer-v1 local.pcfdev.io --hostname consumer;
