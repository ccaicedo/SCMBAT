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
  TxHop_BandList.m
  Function to perform compatibility if the underlay mask is BTP rated, 
  and the spectrum mask is Band listed. 
%}

function [Spec_BTP,NewBandList,Spec_MaxPower,compatBWList] = TxHop_BandList()

load SCM_transmitter_java.txt;
load SCM_receiver_java.txt;

status=0;

%Gets the min distance, elevation and azimuth angle
T=[Tx_Lat,Tx_Long,Tx_Alt];
R=[Rx_Lat,Rx_Long,Rx_Alt];

[phi_Txo,theta_Txo,Min_Dist]=find_direction(T,R);

%Transmitter Power map attenuation to Total Power;
p=PowerMap_find(theta_Txo,phi_Txo,Tx_PowerMap);
Power=Tx_TotPow+p;
Power=Power+(10*log10(1e-3/Tx_ResBW)); % Bringing the Reference Bandwidth to 1 Khz;
%Attenuation due to Propagation Map
[n0,d_break,n1]=PropMap_find_piece(theta_Txo,phi_Txo,Tx_PropMap,Min_Dist);
if(Min_Dist>d_break && d_break!=0.0)
Power = Power - (10*n0*log10(d_break)) - (10*n1*log10(Min_Dist/d_break));
else
Power = Power - (10*n0*log(Min_Dist));
end
%Attenuation due to the Receiver Power Map
phi_Rxo=-phi_Txo;
if(theta_Txo>180)
    theta_Rxo=theta_Txo-180;
else
    theta_Rxo=theta_Txo+180;
end
p2=PowerMap_find(theta_Rxo,phi_Rxo,Rx_PowerMap);
Power=Power+(10*log10(Rx_ResBW/1e-3));
Power=Power+p2;


Spec_mask_new=Tx_SpecMask;
Spec_mask_new(2:2:end)=Tx_SpecMask(2:2:end)+Power;
Underlay_mask=Rx_UnderlayMask;

Underlay_freq_list=Underlay_mask(1:2:end-1);
Underlay_power_list=Underlay_mask(2:2:end);
Underlay_min_pow_freq=Underlay_freq_list( find(Underlay_power_list==min(Underlay_power_list)) );

%--- Start BTP and compatibility Analysis --- 

%Compare if the SCMs are operating in the same time duration
%if((Tx_Start<Rx_Start && Tx_End<Rx_Start) || (Tx_Start>Rx_End && Tx_End>Rx_End) )
%    disp('System is compatible')
%    break;
%    else
%end

%Compare frequency range
i0=1;
ExtSpecMask=zeros(1,length(Spec_mask_new));
NewBandList=sort([Tx_BandList,Underlay_freq_list(1),Underlay_freq_list(end)]);
ind0=find(NewBandList==Underlay_freq_list(1));
ind1=find(NewBandList==Underlay_freq_list(end));
NewBandList=NewBandList(ind0:ind1);

BTP_BW_List=Rx_BTPRatedList(1:2:end-1);
BTP_Power_List=Rx_BTPRatedList(2:2:end);
Underlay_BW = Underlay_freq_list(end)-Underlay_freq_list(1);

Spec_freq_list=Spec_mask_new(1:2:end-1);
Spec_power_list=Spec_mask_new(2:2:end);
Spec_cent_freq=(Spec_freq_list(1)+Spec_freq_list(end))/2;
Spec_BW=Spec_mask_new(end-1)-Spec_mask_new(1);
Spec_MaxPower=max(Spec_power_list);

Band_high=Tx_BandList(2:2:end);
Band_low=Tx_BandList(1:2:end-1);
BW_total=sum(Band_high-Band_low);

NewBand_high=NewBandList(2:2:end);
NewBand_low=NewBandList(1:2:end-1);
NewBW_total=sum(NewBand_high-NewBand_low);

Spec_BTP=NewBW_total*Spec_BW*Tx_DwellTime*1e+6/(BW_total*Tx_RevisitPeriod);

compatBWList=0;
i2=1;
for i=1:length(BTP_BW_List)

BTPMask=Underlay_mask;
BTPMask(2:2:end)=Underlay_mask(2:2:end)+BTP_Power_List(i);
minBTPMask=min(BTPMask(2:2:end));

if(Spec_MaxPower<minBTPMask && Spec_BTP<BTP_BW_List(i))
compatBWList(i2)=BTP_BW_List(i);
i2=i2+1;
else
end

end

if(compatBWList==0)
disp('System not at all compatible')
else
disp('System compatible with: ')
disp(compatBWList);
end

%figure
%plot(Tx_SpecMask(1:2:end-1),Tx_SpecMask(2:2:end),'b.-','LineWidth',2)
%hold all
%plot(Spec_mask_new(1:2:end-1),Spec_mask_new(2:2:end),'r.-','LineWidth',2)
%grid on
%xlabel('Frequency (MHz)');
%ylabel('Power (dB)');

%fig1=figure;
%for i=1:length(BTP_BW_List)
%plot(Rx_UnderlayMask(1:2:end-1),Rx_UnderlayMask(2:2:end)+BTP_Power_List(i),'b.-','LineWidth',2)
%hold all
%end
%plot(NewBandList,Spec_MaxPower*ones(1,length(NewBandList)),'r.-','LineWidth',2)
%grid on
%xlabel('Frequency (MHz)');
%ylabel('Power (dB)');
%saveas(fig1,'BTPRatedFreqList.png')



end

