#!/bin/bash
YELLOW='\e[1;33m';
RESET='\e[0m';

mvn process-sources $*
echo -e ${YELLOW} "KEA summarization source code headers updated" ${RESET}
