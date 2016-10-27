#!/bin/sh

fly -t demo-api login  --concourse-url http://192.168.100.4:8080;
fly -t demo-api sync;
fly sp -t demo-api -p demo-api -c concourse/pipeline.yml -n;
fly up -t demo-api -p demo-api;

cf dev destroy;
cf dev start;

gradle assemble;
cf login -a https://api.local.pcfdev.io --skip-ssl-validation -u user -p pass;

cf d book-producer-current -f -r;
cf d book-producer-new -f -r;
cf p -f book-producer/book-producer-v1_manifest.yml;
cf p -f book-producer/book-producer-v2_manifest.yml;
cf map-route book-producer-current local.pcfdev.io --hostname book;

cf d consumer-v1 -f -r;
cf p -f consumer/consumer-v1_manifest.yml;
cf map-route consumer-v1 local.pcfdev.io --hostname consumer;
