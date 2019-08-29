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
  TxMPSD.m
  Function to calculate the maximum power spectral density for narrow band 
  spectrum masks
%}

function [Spec_mask_new,MaxPSD,Spec_BW,compatBWRatedList] = TxMPSD ()

load SCM_transmitter_java.txt;
load SCM_receiver_java.txt;

status=0;

%Gets the min distance, elevation and azimuth angle
T=[Tx_Lat,Tx_Long,Tx_Alt];
R=[Rx_Lat,Rx_Long,Rx_Alt];

[phi_Txo,theta_Txo,Min_Dist]=find_direction(T,R);
disp('orientation');
[phi_Txo,theta_Txo,Min_Dist]

%Compare frequency range
if((Tx_SpecMask(1)<Rx_UnderlayMask(1) && Tx_SpecMask(end-1)<Rx_UnderlayMask(1)) || (Tx_SpecMask(1)>Rx_UnderlayMask(end-1) && Tx_UnderlayMask(end-1)>Rx_SpecMask(end-1)) )
    disp('System is compatible');
    break;
    else
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
Underlay_mask=Rx_UnderlayMask
Underlay_freq_list=Underlay_mask(1:2:end-1);
Underlay_power_list=Underlay_mask(2:2:end);
Underlay_min_pow_freq=Underlay_freq_list( find(Underlay_power_list==min(Underlay_power_list)) );


BWRated_BW_List=Rx_BWRatedList(1:2:end-1);
BWRated_Power_List=Rx_BWRatedList(2:2:end);

Spec_freq_list=Spec_mask_new(1:2:end-1);
Spec_power_list=Spec_mask_new(2:2:end);
Spec_cent_freq=(Spec_freq_list(1)+Spec_freq_list(end))/2;
Spec_BW=calcNarrowBW(Spec_mask_new); %Spec_freq_list(end)-Spec_freq_list(1);
Spec_MaxPower=max(Spec_power_list);

Compat_BWRated_Masks1=[];
Compat_BWRated_Masks2=[];

i0=1;
i1=1;


for i=1:length(BWRated_BW_List)
  
    BWRatedMask=Underlay_mask;
    BWRatedMask(2:2:end)=BWRatedMask(2:2:end)+BWRated_Power_List(i);

    MaxPSD=Spec_MaxPower;
    MaxPSD

    Underlay_pow_cent=find_power(Spec_cent_freq,BWRatedMask);

    if(Spec_MaxPower<Underlay_pow_cent && Spec_BW<=BWRated_BW_List(i))
      
        if(Spec_cent_freq<Underlay_min_pow_freq(1) || Spec_cent_freq>Underlay_min_pow_freq(end))
          Compat_BWRated_Masks1((2*i0)-1:(2*i0))=[BWRated_BW_List(i),BWRated_Power_List(i)];
          MaxPSD=min(BWRatedMask(2:2:end))-(Underlay_pow_cent-Spec_MaxPower);
          i0=i0+1;
        else
          Compat_BWRated_Masks2((2*i1)-1:(2*i1))=[BWRated_BW_List(i),BWRated_Power_List(i)];
          MaxPSD=Spec_MaxPower;
          i1=i1+1;
        end
    
    else
    end


end 

MaxPSD=min(MaxPSD);


if(length(Compat_BWRated_Masks1)==length(Rx_BWRatedList) || length(Compat_BWRated_Masks2)==length(Rx_BWRatedList))
    compatBWRatedList=1;
    disp('1');
    disp(MaxPSD);
    disp(Spec_BW);
    disp(Spec_mask_new);
else

if(isempty(Compat_BWRated_Masks1)==0)
 compatBWRatedList=Compat_BWRated_Masks1; 
 disp(Compat_BWRated_Masks1);
 disp(MaxPSD);
 disp(Spec_BW);
 disp(Spec_mask_new);
else
  if(isempty(Compat_BWRated_Masks2)==0)
    compatBWRatedList=Compat_BWRated_Masks2;
    disp(Compat_BWRated_Masks2);
    disp(MaxPSD);
    disp(Spec_BW);
    disp(Spec_mask_new);
  else
    compatBWRatedList=0;
    disp('0');
    disp(MaxPSD);
    disp(Spec_BW);
    disp(Spec_mask_new);
  end
end

end
disp('Spec BW')
Spec_BW
MaxPSD
end
