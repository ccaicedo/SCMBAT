#!/bin/bash

if [[ $# -eq 2 ]]; 

then

	cd $1/Octave
	#pwd
	octave --silent --eval "DSA_MaxPow('$2');" 
	
else
	cd Octave
	#pwd
	octave --silent --eval "DSA_MaxPow();"
		

fi


