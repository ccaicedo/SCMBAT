#!/bin/bash

if [ -z $1 ] 

then

	cd Octave
	#pwd
	octave --silent --eval "DSA_TotPow();"

else
	cd $1/Octave
	#pwd
	octave --silent --eval "DSA_TotPow();" 	

fi

