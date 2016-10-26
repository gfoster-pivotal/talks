FROM ubuntu:latest
MAINTAINER George Foster <gfoster@pivotal.io>
RUN apt-get update;
RUN apt-get -y install wget;
RUN apt-get -y install apt-transport-https;
RUN wget -q -O - https://packages.cloudfoundry.org/debian/cli.cloudfoundry.org.key | apt-key add -;
RUN echo "deb http://packages.cloudfoundry.org/debian stable main" | tee /etc/apt/sources.list.d/cloudfoundry-cli.list;
RUN apt-get update;
RUN apt-get -y install cf-cli;
RUN apt-get -y install gradle;
RUN apt-get -y install git;
