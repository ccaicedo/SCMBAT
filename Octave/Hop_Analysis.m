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
  Hop_Analysis.m
  Function to perform BTP analysis for spectrum hopping systems. 
%}

function [Hop_Result] = Hop_Analysis(Tx_Spec_mask,Rx_Spec_mask,Rx_BTP,Tx_FreqList,Tx_RevisitPeriod,Tx_DwellTime)

BTP_Data=Rx_BTP(1:2:end-1);
Tx_BW=Tx_Spec_mask(end-1)-Tx_Spec_mask(1);
Rx_BW=Rx_Spec_mask(end-1)-Rx_Spec_mask(1);

Tx_minFreq=Tx_FreqList(find(Tx_FreqList>Rx_Spec_mask(1)));

if(isempty(Tx_minFreq)==1)
  Hop_Result=1;
else
  Tx_NewFreqList=Tx_minFreq;

  Tx_maxFreq=Tx_FreqList(find(Tx_NewFreqList<Rx_Spec_mask(end-1)));
  
  if(isempty(Tx_maxFreq)==1)
    Hop_Result=1;
  else
    Tx_NewFreqList=Tx_maxFreq;
    n=length(Tx_NewFreqList);
    Tx_BTP = n*Tx_BW*Tx_DwellTime*(1/Tx_RevisitPeriod);
    BTP_index=find(BTP_Data>Tx_BTP);
    Hop_Result=zeros(1,2*length(BTP_index));
    Hop_Result(1:2:end-1)=Rx_BTP((2*BTP_index)-1);
    Hop_Result(2:2:end)=Rx_BTP(2*BTP_index);  
  end

end

end