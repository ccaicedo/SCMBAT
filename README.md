# SCMBAT
The ** Spectrum Consumption Model Builder and Analysis Tool (SCMBAT) ** facilitates the construction of Spectrum Consumption Models (SCMs) and the analysis of compatibility between transmitters and receivers for which an SCM describes their boundaries of spectrum use. 

The structure of SCMs and the mechanisms to determine non-interfering use of spectrum when devices express their spectrum use boundaries via SCMs is defined in the IEEE Standard 1900.5.2 being elaborated by the IEEE 1900.5 Working Group on Policy Language and Architectures for Managing Cognitive Radio for Dynamic Spectrum Access Applications. http://grouper.ieee.org/groups/dyspan/5/index.htm

Overall, SCMBAT aims to incentivize the use of SCMs, uncover the potential benefits of their use, collect feedback for their improvement and contribute to the development of spectrum sharing techniques.

### Building the code: 

The code for the tool has been built in Java and uses scripts written in Octave (a numerical computation language with similarities to Matlab) and some shell scripts.


#### System requirements 

- OpenJDK 7 or 8. 

- Octave version 3.8 or higher
https://www.gnu.org/software/octave/

- Required libraries  (these are provided in the libraries folder):
commons-logging-1.1.1.jar
javaoctave-0.6.4.jar  (https://kenai.com/projects/javaoctave/pages/Home)

*Notes:*
- The tool has been tested in Ubuntu 16.04.
- Please note that the java file with the main( ) method is the Home.java file in the SCM_home folder.

### Code structure
- The *src* folder contains Java code components 
- The *Octave* folder contains scripts written in octave for SCMBAT operations
- The *Models* folder contains an example of a SCM for a transmitter and a receiver which can be loaded and explored in SCMBAT.
- The *libraries* folder contains jar libraries required by SCMBAT
- The MaxPow.sh and TotPow.sh files are required for adequate execution of compatibility computations in SCMBAT
- The HomeDirectoryPath.txt file. This file should be updated to contain the directory path of your installation of SCMBAT.

### Executing the code – environment setup
If you have compiled the Java code and want to execute it (i.e. an executable JAR file), please note that the following elements should be present in the same directory where you have your executable

- a sub-directory called “Octave” with all the octave scripts for the tool in it.
- The scripts MaxPow.sh and TotPow.sh
- (optional) a sub-directory containing the commons-logging and javaoctave jar libraries (depends on the options you used to compile the code)
- A “Models” directory where the spectrum consumption model directories and files will be created by default.
- A directory with the license files
- **IMPORTANT:** The *HomeDirectoryPath.txt* file must be edited to contain the directory path of your installation of SCMBAT.

Please note that once SCMBAT is started, it will create a "logs" directory to store its execution log. A "Reports" directory will be created the first time you perfrom a comptability test. This directory will store the compatibility test result reports. 

Also make sure your system meets the requirements mentioned previously

### Tool use – Notes/Observations
- Spectrum Consumption Models are saved as xml files compatible with the IEEE 1900.5.2	XML Schema for SCM. The XML file with the SCM information will be stored inside the *Models* folder.

- When performing compatibility calculations, a couple files are currently being generated inside the Octave directory:  SCM_receiver_java.txt and SCM_transmitter_java.txt. 
In future versions of the tool these files will be placed in a separate directory.

- When saving the data for a SCM, the application reconfirms that you are saving it into a Transmitter model (Tx) or a receive model file (Rx) depending on how you started building the model. Additionally, it provides a warning window indicating data that is missing (which may be intentional from a modelers perspective) or data that should be numeric and isn't (potential data entry error).

### Bug reports / Feature requests / Code contributions
Please look at the guidelines mentioned in Contributing.md


### Documentation
*Video tutorial* 

- The video tutorial for SCMBAT is available at:
[SCMBAT_video_tutorial](https://coursecast.ischool.syr.edu/Panopto/Pages/Viewer.aspx?id=a201b9c0-63fd-4ff4-b2b5-cf1d59f99a8f) 

A tutorial document is currently under preparation.

*Introduction to Spectrum Consumption Models:*

- J. Stine, C. Caicedo, “Enabling Spectrum Sharing via Spectrum Consumption Models”, IEEE Journal of Selected Areas in Communications (JSAC), Vol 33, No. 4, 2015
https://www.researchgate.net/publication/271964910_Enabling_Spectrum_Sharing_via_Spectrum_Consumption_Models?fulltextDialog=true


*Detailed explanations on SCMs and Model-Based Spectrum Managment can be found in:*

- Model-Based Spectrum Management Part 1: Modeling and Computation Manual Version 2.0
https://www.mitre.org/publications/technical-papers/model-based-spectrum-management-part-1-modeling-and-computation-manual



### Acknowledgements:
In addition to the contributors mentioned in Contributors.md the project originators would like to thank Google Inc. who provided initial support to start this project via a Google Faculty Award.

### Copyright and License
Code and documentation Copyright Syracuse University 2016. Code is released under a GPLv3 license 