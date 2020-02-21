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
  TxDuty_FreqList.m
  Function to perform compatibility computation if the underlay mask is
  duty cycle rated and the spectrum mask is frequency listed 
%}

function [Spec_mask_new,p_Tx_new,compatDutyList] = TxDuty_FreqList()

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
FreqList=0;

for i=1:length(Tx_FreqList)

  if((Tx_SpecMask(1)+Tx_FreqList(i)<=Rx_UnderlayMask(1) && Tx_SpecMask(end-1)+Tx_FreqList(i)<=Rx_UnderlayMask(1)) || (Tx_SpecMask(1)+Tx_FreqList(i)>=Rx_UnderlayMask(end-1) && Tx_SpecMask(end-1)+Tx_FreqList(i)>=Rx_UnderlayMask(end-1)) )
    
  else
    FreqList(i0)=Tx_FreqList(i);
    i0=i0+1;
  end

end

if(FreqList==0)
    disp(strcat('result: ', 'System is compatible'));
    return;
end

DutyCylceList = Rx_DutyList(1:3:end-2);
DwellList = Rx_DutyList(2:3:end-1);
PowerAdj = Rx_DutyList(3:3:end);

Center_UnderlayFreq=(Underlay_freq_list(end)+Underlay_freq_list(1))/2;
diff=abs(Center_UnderlayFreq-FreqList);
ind = find(diff==min(diff));
Center_UnderlayFreq
FreqList(ind(1))
Spec_mask_new(1:2:end-1)=Spec_mask_new(1:2:end-1)+FreqList(ind(1));

Spec_freq_list=Spec_mask_new(1:2:end-1);
Spec_power_list=Spec_mask_new(2:2:end);
Spec_cent_freq=(Spec_freq_list(1)+Spec_freq_list(end))/2;
Spec_BW=Spec_mask_new(end-1)-Spec_mask_new(1);
Spec_MaxPower=max(Spec_power_list);
Spec_DutyCycle=Tx_DwellTime/Tx_RevisitPeriod;

% Execute total power method with each Duty cycle mask.

compatDutyList=0;
i1=1;
p_Tx_new=0;


for i=1:length(DutyCylceList)

if(Spec_DutyCycle<DutyCylceList(i) && Tx_DwellTime<(DwellList(i)*1000))

DutyUnderlayMask = Underlay_mask;
DutyUnderlayMask(2:2:end)=Underlay_mask(2:2:end)+PowerAdj(i);

%SCM compatibility
[p_Tx_new] = new_spectrum(Spec_mask_new,DutyUnderlayMask);
plot(p_Tx_new(1:2:end-1),p_Tx_new(2:2:end),'LineWidth',2);
hold all
[Power_Tx,Power_Tx_dB] = calculate_power(p_Tx_new,Rx_ResBW);
[p_Rx_new] = calculate_power_3dB(DutyUnderlayMask,Rx_ResBW);
[Power_Rx,Power_Rx_dB] = calculate_power(p_Rx_new,Rx_ResBW);
%Power_Tx_dB
AllowablePower=Power_Rx_dB;
Power_Margin_Difference = AllowablePower-Power_Tx_dB
if(AllowablePower>Power_Tx_dB)
    compatDutyList(i1)=DutyCylceList(i);
    i1=i1+1;
else
end

else
end

end



if(compatDutyList==0)
    disp(strcat('result: ', 'System is not at all compatible'));
    return;
else
    disp(strcat('result: ', 'System compatible with: '));
    disp(strcat('result: ', num2str(compatDutyList)));
    return;
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
%plot(ExtSpecMask(1:2:end-1),ExtSpecMask(2:2:end),'r.-','LineWidth',2)
%grid on
%xlabel('Frequency (MHz)');
%ylabel('Power (dB)');
%saveas(fig1,'BTPRatedFreqList.png')

end
