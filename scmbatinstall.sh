#! /bin/bash

#Copyright (C) 2018 Syracuse University
#This file is part of the Spectrum Consumption Model Builder andAnalysis Tool
#This program is free software; you can redistribute it and/or modify it
#under the terms of the GNU General Public License as published by the
#Free Software Foundation; either version 3 of the License, or (at your
#option) any later version.
#This program is distributed in the hope that it will be useful, but WITHOUT
#ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
#FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
#for more details.
#You should have received a copy of the GNU General Public License
#along with program.  If not, see <http://www.gnu.org/licenses/>.

#This script automates the installation and execution of SCMBAT in an Ubuntu Environment

clear

echo "****************** SCMBAT installation script ******************" 
echo "*******STARTING"
echo 
sleep 1s

install_java(){
	echo "Installing java......................................."
	sudo apt-get-update	
	sudo apt-get install openjdk-8-jdk
	
}

install_octave(){
	echo "Adding the stable octave build into repository............"
	sudo apt-add-repository ppa:octave/stable
	echo "Installing octave........................"
	sudo apt-get update
	sudo apt-get install octave

}

install_epstool(){
	echo "Installing epstool......................................."
	sudo apt-get update
	sudo apt-get install epstool

}

install_fig2dev(){
	echo "Installing transfig......................................."
	sudo apt-get update
	sudo apt-get install transfig
}


#Uncompress the tar file

echo "************The script will try to uncompress the SCMBAT.tar.gz file*************"

#Check if the tar file exists
if [[ -f './SCMBAT.tar.gz' ]]; then
	echo "SCMBAT tar file exists"
else
	echo "ERROR: The SCMBAT.tar.gz is not present in the current directory. Please save it in the current directory and run the script again"
	echo "You can download the SCMBAT.tar.gz file for which this script was designed"
	echo "at https://github.com/ccaicedo/SCMBAT/releases"
	exit 1
fi

echo "Uncompressing the SCMBAT tar file............................"

#Java setup
echo "Checking if java is installed or not.........................................."

if type -p java; then
    echo "java executable found in PATH"
    echo
    cur_java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
    echo "java executable found in JAVA_HOME"     
    cur_java="$JAVA_HOME/bin/java"
else
    echo "java is not installed in the system"
    echo
    echo "The script will try to install java........"
    install_java   
fi

if [[ "$cur_java" ]]; then
	echo "Checking if the current installed version of java is greater than or equal to 7.........."
	echo
	version = java -version 2>&1 | sed -n 's/.* version "\(.*\)\.\(.*\)\..*"/\1\2/p;'
	if [[ "$version ge 17" ]]; then
		echo "The installed java version is greater than or equal to 7"
		echo
	else
		echo "The installed java version is lower than 7" 
		echo "Trying to install java................................................."
		install_java
	fi
fi

echo "******************Finished verification of installed java******************"

#Octave setup
echo "Checking if Octave is installed or not......................................"
echo
if type -p octave; then
	echo "Octave is installed in the system"
else
	echo "Octave is not installed in the system"
	echo
	echo "Trying to install Octave..............................."
	install_octave
	echo "****************Successfully finished the installation of Octave*************"
fi

#epstool setup
echo "Checking if epstool is installed or not......................................"
echo
if type -p epstool; then
	echo "epstool is installed in the system"
else
	echo "epstool is not installed in the system"
	echo
	echo "Trying to install epstool..............................."
	install_epstool
	echo "****************Successfully finished the installation of epstool*************"
fi

#fig2dev setup
echo "Checking if fig2dev is installed or not......................................"
echo
if type -p fig2dev; then
	echo "fig2dev is installed in the system"
else
	echo "fig2dev is not installed in the system"
	echo
	echo "Trying to install fig2dev..............................."
	install_fig2dev
	echo "****************Successfully finished the installation of fig2dev*************"
fi

#Proceed to complete SCMBAT setup

#Check if the SCMBAT folder is already uncompressed and is present in the current directory

if [[ -f './SCMBAT' ]]; then
	echo 
else
	mkdir SCMBAT
	tar -xzf ./SCMBAT.tar.gz -C ./SCMBAT
	echo
	echo "************ Successfully uncompressed the SCMBAT tar file *************"
	echo
fi

#Clean the current folder by deleting the tar file
#rm -f ./SCMBAT.tar.gz


#Navigate to the SCMBAT directory
cd ./SCMBAT

#Write the current directory path into the HomeDirectoryPath.txt file

echo "Writing the current directory path into the HomeDirectoryPath.txt file............................"

#First clear the current contents of the file
: > HomeDirectoryPath.txt

#Add the correct path into the file
currDir=`pwd`

echo "HOME_DIR=$currDir/" > HomeDirectoryPath.txt

echo "SCMBAT has been installed"

while true; do
    read -p "Do you wish to run SCMBAT now? (y/n) " yn
    case $yn in
        [Yy]* ) java -jar SCMBAT.jar &
		break
		;;
        [Nn]* ) echo -e "Use the command sequence \n\njava -jar SCMBAT.jar \n\ninside the SCMBAT directory to run the SCMBAT tool"
		 break
		;;
        * ) echo "Please answer yes or no";;
    esac
done

echo
echo "**************Exiting the SCMBAT installation script*************"
