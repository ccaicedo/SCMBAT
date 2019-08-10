#!/bin/bash
# - $1 provides the path to the directory where Octave directory is present
# - $2 provides the path to the file where reporting is to be done.
# - $3 provides Boolean for Octave Logging.

if [[ $# -eq 3 ]]; 
then
    echo "arg 3 is : $3";
    if ($3 == 'true')
    then
	cd $1/Octave
	#pwd
	octave --silent --eval "DSA_TotPow('$2', '$3');"
    else
	cd $1/Octave
	#pwd
	octave --silent --eval "DSA_TotPow('$2', 0);"
    fi

else
	cd Octave
	#pwd
	octave --silent --eval "DSA_TotPow();"
		

fi

