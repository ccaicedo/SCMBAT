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
  DSA_TotPow.m
  Function to perform total power method compatibility computations
%}

function [Spec_mask_new,Underlay_mask] = DSA_TotPow(report_directory)

load SCM_transmitter_java.txt;
load SCM_receiver_java.txt;

%Gets the min distance, elevation and azimuth angle
T=[Tx_Lat,Tx_Long,Tx_Alt];
R=[Rx_Lat,Rx_Long,Rx_Alt];

[phi_Txo,theta_Txo,Min_Dist]=find_direction(T,R);

%Compare if the SCMs are operating in the same time duration
%if((Tx_Start<Rx_Start && Tx_End<Rx_Start) || (Tx_Start>Rx_End && Tx_End>Rx_End) )
%    disp('System is compatible')
%    break;
%    else
%end

%Compare frequency range
if((Tx_SpecMask(1)<Rx_UnderlayMask(1) && Tx_SpecMask(end-1)<Rx_UnderlayMask(1)) || (Tx_SpecMask(1)>Rx_UnderlayMask(end-1) && Tx_SpecMask(end-1)>Rx_UnderlayMask(end-1)) )
    disp('System is compatible')
    return;
end

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
Spec_freq_list=Spec_mask_new(1:2:end-1);

list_1=find(Underlay_freq_list(end)<Spec_freq_list);
if(isempty(list_1)==0)
  Spec_freq_list=[Spec_freq_list(1:list_1(1)-1),Underlay_freq_list(end)];
  p = find_power(Underlay_freq_list(end),Spec_mask_new);
  Spec_mask_new=[Spec_mask_new(1:2*list_1(1)-2),Underlay_freq_list(end),p];
end

list_2=find(Spec_freq_list<Underlay_freq_list(1));
if(isempty(list_2)==0)
  Spec_freq_list=[Underlay_freq_list(1),Spec_freq_list(list2(end)+1:end)];
  p2 = find_power(Underlay_freq_list(1),Spec_mask_new);
  Spec_mask_new=[Underlay_freq_list(end),p2,Spec_mask_new(2*list_2(end)+2:end)];
end

fig1 = figure;
plot(Tx_SpecMask(1:2:end-1),Tx_SpecMask(2:2:end),'b.-','LineWidth',2)
hold all
plot(Spec_mask_new(1:2:end-1),Spec_mask_new(2:2:end),'r.-','LineWidth',2)
grid on
xlabel('Frequency (MHz)');
ylabel('Power (dB)');
saveas(fig1, 'Analysis_Figure_1.png')
movefile('Analysis_Figure_1.png', report_directory)


fig2 = figure;
plot(Rx_UnderlayMask(1:2:end-1),Rx_UnderlayMask(2:2:end),'b.-','LineWidth',2)
hold all
plot(Spec_mask_new(1:2:end-1),Spec_mask_new(2:2:end),'r.-','LineWidth',2)
grid on
xlabel('Frequency (MHz)');
ylabel('Power (dB)');

saveas(fig2,'CompatAnalysis.png')
movefile('CompatAnalysis.png', report_directory)
	

%SCM compatibility
[p_Tx_new] = new_spectrum(Spec_mask_new,Underlay_mask);
[Power_Tx,Power_Tx_dB] = calculate_power(p_Tx_new,Rx_ResBW);
[p_Rx_new] = calculate_power_3dB(Underlay_mask,Rx_ResBW);
[Power_Rx,Power_Rx_dB] = calculate_power(p_Rx_new,Rx_ResBW);

%Power_Tx_dB
AllowablePower=Power_Rx_dB;
Power_Margin_Difference = AllowablePower-Power_Tx_dB;
if(AllowablePower>Power_Tx_dB)
    disp('System is compatible');
    disp(Power_Margin_Difference);
else
    disp('System is not compatible'); 
    disp(Power_Margin_Difference);
end


end
