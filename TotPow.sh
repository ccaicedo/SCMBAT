#!/bin/bash

if [[ $# -eq 2 ]]; 

then

	cd $1/Octave
	#pwd
	octave --silent --eval "DSA_TotPow('$2');" 
	
else
	cd Octave
	#pwd
	octave --silent --eval "DSA_TotPow();"
		

fi

