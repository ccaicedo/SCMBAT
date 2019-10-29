%{
Copyright (C) 2016 Syracuse University

This file is part of the Spectrum Consumption Model Builder and
Analysis Tool

This program is free software; you can redistribute it and/or modify it
under the terms of the GNU General Public License as published by the
Free Software Foundation; either version 3 of the License, or (at your
option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received a copy of the GNU General Public License
along with program.  If not, see <http://www.gnu.org/licenses/>.

%}

%{
  OctaveLauncher.m
  Launcher file to handle all the calculations and processing related to Octave code.
  Instead of interacting direct with the specific Octave files, the Java code will inreract with
  this file and from here, all the required code will be launched.
    execPattern - defines the pattern in which bandwidth and frequency components have to be executed in case of BTP and DC
%}

function [retVal1, retVal2, retVal3, retVal4] = Coupler(report_directory, method, logging, specTag, numOfTransmitterString, execPattern)

disp('displaying from the coupler file.');
disp(report_directory), disp(method), disp(logging), disp(numOfTransmitterString);

%converting string to decimal, so that we may loop over it.
numOfTransmitter = str2num(numOfTransmitterString)

switch (method)

	case 'TotalPower'
        rename('SCM_transmitter_java1.txt', 'SCM_transmitter_java.txt')
        [Spec_mask_new,Underlay_mask] = DSA_TotPow(report_directory);
		return;


    case 'MaxPower'
        rename('SCM_transmitter_java1.txt', 'SCM_transmitter_java.txt')
        DSA_MaxPow(report_directory);
		return;


	case 'PlotBWRated'
		disp('inside bandwidth switch case');
        fig1=figure;
        retval = plotBWRated();
        saveas(fig1, 'Analysis_Figure_1.png');
        movefile('Analysis_Figure_1.png', report_directory);
        disp('inside RatedBW');

		%initializing the return values
		retVal1 = []
        retVal2 = []
        retVal3 = []
        retVal4 = []

		specTagIndex = 1
		
        for i  = 1:numOfTransmitter
            %changing the name of the file
            fileToBeReplaced = strcat('SCM_transmitter_java', num2str(i), '.txt')
			disp('file to be replaced is: '), disp(fileToBeReplaced);
            rename(fileToBeReplaced, 'SCM_transmitter_java.txt')
			disp('rename done');
			
			disp('spec Tag is : '), disp(specTag);
			%fetch the specTagName from the specTagList
			%get the length of next name
			disp('trying to fetch length of specNameTag');
			len = str2num(substr(specTag, specTagIndex, 2));
			disp('len is '), disp(len);
			specTagName = substr(specTag, specTagIndex+2, len);
			disp('specTagName is: '), disp(specTagName);
			
			%update the specTagIndex to point to next name
			specTagIndex = specTagIndex+2+len

            [SpecMask,PSD,BW,compatBWList] = TxMPSD();
            plot(SpecMask(1:2:end-1),SpecMask(2:2:end),'r.-','LineWidth',2);
            xpoint=(SpecMask(length(SpecMask)/2-1)+SpecMask(length(SpecMask)/2+1))/2;
            minSpecPow=min(SpecMask(2:2:end));
            text(xpoint,minSpecPow-1,specTagName);
            #setting all the values to standard return value variable names
            retVal1 = [retVal1, SpecMask]
            retVal2 = [retVal2, PSD]
            retVal3 = [retVal3, BW]
            retVal4 = [retVal4, compatBWList]
        endfor

        saveas(fig1,'BWRatedAnalysis.png');
        movefile('BWRatedAnalysis.png', report_directory);
		return;


	case 'PlotBTPRated'
		disp('inside BTP switch case');

        fig2=figure;
        retval = plotBTPRated();
        saveas(fig2, 'Analysis_Figure_1.png');
        movefile('Analysis_Figure_1.png', report_directory);

		%initializing the return values
		retVal1 = []
        retVal2 = []
        retVal3 = []
        retVal4 = []
		%initialize specTagName index
		specTagIndex = 1

        for i  = 1:numOfTransmitter
            %changing the name of the file
            fileToBeReplaced = strcat('SCM_transmitter_java', num2str(i), '.txt')
			disp('file to be replaced is: '), disp(fileToBeReplaced);
            rename(fileToBeReplaced, 'SCM_transmitter_java.txt')
			
			disp('spec Tag is : '), disp(specTag);
			%fetch the specTagName from the specTagList
			%get the length of next name
			disp('trying to fetch length of specNameTag');
			len = str2num(substr(specTag, specTagIndex, 2));
			disp('len is '), disp(len);
			specTagName = substr(specTag, specTagIndex+2, len);
			disp('specTagName is: '), disp(specTagName);
			
			%update the specTagIndex to point to next name
			specTagIndex = specTagIndex+2+len

			%fetching whether it's frequency based or bandwidth based system
            ch = substr(execPattern, i, 1)
			disp('the char is:'), disp(ch);

            %in case of freq
            if(ch == "f")
                [Spec_BTP,ExtSpecMask,compatBTPList] = TxHop_FreqList();
                plot(ExtSpecMask(1:2:end-1),ExtSpecMask(2:2:end),'r.-','LineWidth',2);
                xpoint=ExtSpecMask(1);
                minSpecPow=min(ExtSpecMask(2:2:end));
                text(xpoint,minSpecPow-1,specTagName);
                #setting all the values to standard return value variable names
                retVal1 = [retVal1, Spec_BTP]
                retVal2 = [retVal2, ExtSpecMask]
                retVal3 = [retVal3, compatBTPList]
            endif

            %in case of bandwidth
            if(ch == "b")
                [Spec_BTP,NewBandList,Spec_MaxPower,compatBTPList] = TxHop_BandList();
                plot(NewBandList,Spec_MaxPower*ones(1,length(NewBandList)),'r.-','LineWidth',2);
                xpoint=NewBandList(1);
                minSpecPow=Spec_MaxPower;
                text(xpoint,minSpecPow-1,specTagName);
                #setting all the values to standard return value variable names
                retVal1 = [retVal1, Spec_BTP]
                retVal2 = [retVal2, NewBandList]
                retVal3 = [retVal3, Spec_MaxPower]
                retVal4 = [retVal4, compatBTPList]
            endif
        endfor
        saveas(fig2,'BTPRatedAnalysis.png');
        movefile('BTPRatedAnalysis.png', report_directory);
		return;


	case 'PlotDCRated'
		disp('inside PlotDCRated');
		fig3=figure;

		%initializing the return values
		retVal1 = []
        retVal2 = []
        retVal3 = []
        retVal4 = []

        for i  = 1:numOfTransmitter
            % changing the name of the file
            fileToBeReplaced = strcat('SCM_transmitter_java', num2str(i), '.txt')
			disp('file to be replaced is: '), disp(fileToBeReplaced);
            rename(fileToBeReplaced, 'SCM_transmitter_java.txt')
			
			%fetching whether it's frequency based or bandwidth based system
            ch = substr(execPattern, i, 1)
			disp('the char is:'), disp(ch);

            %in case of freq
            if(ch == "f")
                [Spec_mask_new,p_Tx_new,compatDutyList] = TxDuty_FreqList();
                #setting all the values to standard return value variable names
                retVal1 = [retVal1, Spec_mask_new]
                retVal2 = [retVal2, p_Tx_new]
                retVal3 = [retVal3, compatDutyList]
            endif

            %in case of bandwidth
            if(ch == "b")
                [Spec_mask_new,p_Tx_new,compatDutyList] = TxDuty_BandList();
                #setting all the values to standard return value variable names
                retVal1 = [retVal1, Spec_mask_new]
                retVal2 = [retVal2, p_Tx_new]
                retVal3 = [retVal3, compatDutyList]
            endif
        endfor

        saveas(fig3,'DCRatedAnalysis.png');
        movefile('DCRatedAnalysis.png', report_directory);
		return;

	otherwise
		%nothing to do!! We don't have a default case!!
		return;
endswitch

