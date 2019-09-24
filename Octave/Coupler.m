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
%}

function [retVal1, retVal2, retVal3, retVal4] = Coupler(report_directory, method, logging, specTag)

disp('displaying from the coupler file.');
disp(report_directory), disp(method), disp(logging);

switch (method)

    case 'TotalPower'
        [Spec_mask_new,Underlay_mask] = DSA_TotPow(report_directory);
		return;


    case 'MaxPower'
        DSA_MaxPow(report_directory);
		return;


	case {'RatedBW', 'PlotBWRated', 'mvBWRatedAnalysis'}
		disp('inside bandwidth switch case');
		if(method == 'RatedBW')
			disp('inside RatedBW');
			[SpecMask,PSD,BW,compatBWList] = TxMPSD();
			plot(SpecMask(1:2:end-1),SpecMask(2:2:end),'r.-','LineWidth',2);
			xpoint=(SpecMask(length(SpecMask)/2-1)+SpecMask(length(SpecMask)/2+1))/2;
			minSpecPow=min(SpecMask(2:2:end));
			text(xpoint,minSpecPow-1,specTag);
			#setting all the values to standard return value variable names
			[retVal1, retVal2, retVal3, retVal4] = [SpecMask,PSD,BW,compatBWList];
			return;

		elseif(method == 'PlotBWRated')
			disp('inside PlotBWRated');
			fig1=figure;
			retval = plotBWRated();
			saveas(fig1, 'Analysis_Figure_1.png');
			movefile('Analysis_Figure_1.png', report_directory);
			return;

		elseif(method == 'mvBWRatedAnalysis')
			disp('inside mvBWRatedAnalysis');
			saveas(fig1,'BWRatedAnalysis.png');
			movefile('BWRatedAnalysis.png', report_directory);
			return;
		endif
		break;


	case {'PlotBTPRated', 'RatedBTPFreq', 'RatedBTPBand', 'mvBTPRatedAnalysis'}
		disp('inside BTP switch case');
		if(method == 'RatedBTPFreq')
			disp('inside RatedBTPFreq');
			[Spec_BTP,ExtSpecMask,compatBTPList] = TxHop_FreqList();
			plot(ExtSpecMask(1:2:end-1),ExtSpecMask(2:2:end),'r.-','LineWidth',2);
			xpoint=ExtSpecMask(1);
			minSpecPow=min(ExtSpecMask(2:2:end));
			text(xpoint,minSpecPow-1,specTag);
			#setting all the values to standard return value variable names
			[retVal1, retVal2, retVal3, retVal4] = [Spec_BTP,ExtSpecMask,compatBTPList, []];	
			return;

		elseif(method == 'RatedBTPBand')
			[Spec_BTP,NewBandList,Spec_MaxPower,compatBTPList] = TxHop_BandList();
			plot(NewBandList,Spec_MaxPower*ones(1,length(NewBandList)),'r.-','LineWidth',2);
			xpoint=NewBandList(1);
			minSpecPow=Spec_MaxPower;
			text(xpoint,minSpecPow-1,specTag);
			#setting all the values to standard return value variable names
			[retVal1, retVal2, retVal3, retVal4] = [Spec_BTP,NewBandList,Spec_MaxPower,compatBTPList]
			return;

		elseif(method == 'PlotBTPRated')
			disp('inside PlotBTPRated');
			fig2=figure;
			retval = plotBTPRated();
			saveas(fig2, 'Analysis_Figure_1.png');
			movefile('Analysis_Figure_1.png', report_directory);
			return;

		elseif(method == 'mvBTPRatedAnalysis')
			disp('inside mvBWRatedAnalysis');
			saveas(fig2,'BTPRatedAnalysis.png');
			movefile('BTPRatedAnalysis.png', report_directory);
			return;
		endif
		break;


	case {'PlotDCRated','DCRatedListBand', 'mvDCRatedAnalysis'}
		if(method == 'PlotDCRated')
			disp('inside PlotDCRated');
			octaveDuty.eval("fig3=figure");
			return;
	
		elseif(method == 'DCRatedListFreq')
			[Spec_mask_new,p_Tx_new,compatDutyList] = TxDuty_FreqList();
			#setting all the values to standard return value variable names
			[retVal1, retVal2, retVal3, retVal4] = [Spec_mask_new,p_Tx_new,compatDutyList, []]
			return;

		elseif(method == 'DCRatedListBand')
			[Spec_mask_new,p_Tx_new,compatDutyList] = TxDuty_BandList();
			#setting all the values to standard return value variable names
			[retVal1, retVal2, retVal3, retVal4] =  [Spec_mask_new,p_Tx_new,compatDutyList, []]
			return;
		
		elseif(method == 'mvDCRatedAnalysis')
			saveas(fig3,'DCRatedAnalysis.png');
			movefile('DCRatedAnalysis.png','" + compatTestDirectory + "');
			return;

		endif
		break;

	otherwise
		%nothing to do!! We don't have a default case!!
		break;
endswitch

