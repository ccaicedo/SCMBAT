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

function [Spec_mask_new,Underlay_mask] = DSA_TotPow(report_directory, logging)

load SCM_transmitter_java.txt;
if(logging=="true")
  disp("SCM_transmitter_java.txt loaded successfully");
end
load SCM_receiver_java.txt;
if(logging=="true")
  disp("SCM_receiver_java.txt loaded successfully");
end

%Gets the min distance, elevation and azimuth angle
T=[Tx_Lat,Tx_Long,Tx_Alt];
R=[Rx_Lat,Rx_Long,Rx_Alt];

if(logging=="true")
  disp("Getting the the min distance, elevation and azimuth angle: ");
  disp("Calling function find_Compare");
end
[phi_Txo,theta_Txo,Min_Dist]=find_direction(T,R);
if(logging=="true")
  disp("Values returned from find_direction are: ");
  disp("phi_Txo: "), disp(phi_Txo), disp("theta_Txo: "), disp(theta_Txo), disp("Min_Dist: "), disp(Min_Dist);
end

%if the SCMs direction are operating in the same time duration
%if((Tx_Start<Rx_Start && Tx_End<Rx_Start) || (Tx_Start>Rx_End && Tx_End>Rx_End) )
%    disp('System is compatible')
%    break;
%    else
%end

%Compare frequency range
if(logging=="true")
  disp("Comparing Frequency range.");
end
if((Tx_SpecMask(1)<Rx_UnderlayMask(1) && Tx_SpecMask(end-1)<Rx_UnderlayMask(1)) || (Tx_SpecMask(1)>Rx_UnderlayMask(end-1) && Tx_SpecMask(end-1)>Rx_UnderlayMask(end-1)) )
    disp('System is compatible');
    return;
end

%Transmitter Power map attenuation to Total Power;
if(logging=="true")
  disp("Transmitter Power map attenuation to Total Power");
  disp("Calling function PowerMap_find for Transmitter model with values:");
  disp("phi_Txo: "), disp(phi_Txo), disp("theta_Txo: "), disp("Tx_PowerMap"), disp(Tx_PowerMap);
end
p=PowerMap_find(theta_Txo,phi_Txo,Tx_PowerMap);
if(logging=="true")
  disp("the p (gain) value for transmitter is : "), disp(p);
  disp("the total power value is: "), disp(Tx_TotPow);
end
Power=Tx_TotPow+p;
if(logging=="true")
  disp("the total power (Tx_TotPow + p) is : " ), disp(Power);
end
Power=Power+(10*log10(1e-3/Tx_ResBW)); % Bringing the Reference Bandwidth to 1 Khz;
if(logging=="true")
  disp("Bringing the Reference Bandwidth to 1 Khz");
  disp("the total power after adjustment (10*log10(1e-3/Tx_ResBW)) : " ), disp(Power);
end

%Attenuation due to Propagation Map
if(logging=="true")
  disp("Calling function PropMap_find_piece with values: ");
  disp("theta_Txo: "), disp(theta_Txo), disp("phi_Txo: "), disp(phi_Txo), disp("Min_Dist"), disp(Min_Dist);
end
[n0,d_break,n1]=PropMap_find_piece(theta_Txo,phi_Txo,Tx_PropMap,Min_Dist);
if(logging=="true")
  disp("the returned values for n0 is : " ), disp(n0), disp("for d_break: "), disp(d_break), disp("for n1: "), disp(n1);
  disp("Checking if(Min_Dist>d_break && d_break!=0.0)");
end
if(Min_Dist>d_break && d_break!=0.0)
  if(logging=="true")
    disp("condition is true, calculating 'Power = Power - (10*n0*log10(d_break)) - (10*n1*log10(Min_Dist/d_break))'");
  end
  Power = Power - (10*n0*log10(d_break)) - (10*n1*log10(Min_Dist/d_break));
else
  if(logging=="true")
    disp("Condition is false; calculating 'Power = Power - (10*n0*log(Min_Dist))'");
  end
  Power = Power - (10*n0*log(Min_Dist));
end
if(logging=="true")
  disp("After calculations the Power is : " ), disp(Power);
end

%Attenuation due to the Receiver Power Map
if(logging=="true")
  disp("Attenuation due to the Receiver Power Map");
end
phi_Rxo=-phi_Txo;
if(logging=="true")
  disp("phi_Rxo after phi_Rxo=-phi_Txo is : "), disp(phi_Rxo);
  disp("checking if(theta_Txo>180)");
end
if(theta_Txo>180)
    if(logging=="true")
      disp("Condition true calculating theta_Rxo=theta_Txo-180");
    end
    theta_Rxo=theta_Txo-180;
else
    if(logging=="true")
      disp("Condition true, calculating theta_Rxo=theta_Txo+180");
    end
    theta_Rxo=theta_Txo+180;
end
if(logging=="true")
  disp("Calling function PowerMap_find for Receiver model.");
end
p2=PowerMap_find(theta_Rxo,phi_Rxo,Rx_PowerMap);
if(logging=="true")
  disp("the p (gain) power value for receiver model is : " ), disp(p2);
end
%Power=Power+(10*log10(Rx_ResBW/1e-3));
Power=Power+(10*log10(1e-3/Rx_ResBW));
if(logging=="true")
  disp("Power after executing Power=Power+(10*log10(1e-3/Rx_ResBW)): "), disp(Power); 
end
Power=Power+p2;
if(logging=="true")
  disp("Power after Power=Power+p2 is : "), disp(Power);
end

Spec_mask_new=Tx_SpecMask;
if(logging=="true")
  disp("Spec_mask after executing 'Spec_mask_new=Tx_SpecMask' is : "), disp(Spec_mask_new);
end
Spec_mask_new(2:2:end)=Tx_SpecMask(2:2:end)+Power;
if(logging=="true")
  disp("Spec_mask_new(2:2:end) after calculating 'Spec_mask_new(2:2:end)=Tx_SpecMask(2:2:end)+Power' is: "), disp(Spec_mask_new(2:2:end));
end
Underlay_mask=Rx_UnderlayMask;
if(logging=="true")
  disp("Underlay_mask is: "), disp(Underlay_mask);
end

Underlay_freq_list=Underlay_mask(1:2:end-1);
if(logging=="true")
  disp("Underlay_freq_list after calculating 'Underlay_freq_list=Underlay_mask(1:2:end-1)' is "), disp(Underlay_freq_list);
end
Spec_freq_list=Spec_mask_new(1:2:end-1);
if(logging=="true")
  disp("Spec_freq_list @ 'Spec_freq_list=Spec_mask_new(1:2:end-1)' " );
end

list_1=find(Underlay_freq_list(end)<Spec_freq_list);
if(logging=="true")
  disp("list_1 is: "), disp(list_1);
end
if(isempty(list_1)==0)
  Spec_freq_list=[Spec_freq_list(1:list_1(1)-1),Underlay_freq_list(end)];
  if(logging=="true")
    disp("Spec_freq_list @ 'Spec_freq_list=[Spec_freq_list(1:list_1(1)-1),Underlay_freq_list(end)'] is: "), disp(Spec_freq_list);
    disp("calling find_power with the values: "), disp("Underlay_freq_list(end) :"), disp(Underlay_freq_list(end)), disp("Spec_mask_new"), disp(Spec_mask_new);
  end
  p = find_power(Underlay_freq_list(end),Spec_mask_new);
  if(logging=="true")
  disp("returning from find_power, the value of p is: "), disp(p);
  end
  Spec_mask_new=[Spec_mask_new(1:2*list_1(1)-2),Underlay_freq_list(end),p];
  if(logging=="true")
  disp("Spec_mask_new @ 'Spec_mask_new=[Spec_mask_new(1:2*list_1(1)-2),Underlay_freq_list(end),p]' is : "), disp(Spec_mask_new);
  end
end

list_2=find(Spec_freq_list<Underlay_freq_list(1));
if(logging=="true")
  disp("list_2 is: "), disp(list_2);
end
if(isempty(list_2)==0)
  Spec_freq_list=[Underlay_freq_list(1),Spec_freq_list(list2(end)+1:end)];
  if(logging=="true")
    disp("Spec_freq_list @ 'Spec_freq_list=[Underlay_freq_list(1),Spec_freq_list(list2(end)+1:end)]"), disp(Spec_freq_list);
    disp("calling find_power with the values: "), disp("Underlay_freq_list(1) :"), disp(Underlay_freq_list(1)), disp("Spec_mask_new"), disp(Spec_mask_new);
  end
  p2 = find_power(Underlay_freq_list(1),Spec_mask_new);
  if(logging=="true")
    disp("returning from find_power, the value of p is: "), disp(p);
  end
  Spec_mask_new=[Underlay_freq_list(end),p2,Spec_mask_new(2*list_2(end)+2:end)];
  if(logging=="true")
    disp("Spec_mask_new @ 'Spec_mask_new=[Underlay_freq_list(end),p2,Spec_mask_new(2*list_2(end)+2:end)]' is : "), disp(Spec_mask_new);
  end
end

if(logging=="true")
  disp("Plotting Analysis_Figure_1.png");
end
fig1 = figure ();
plot(Tx_SpecMask(1:2:end-1),Tx_SpecMask(2:2:end),'b.-','LineWidth',2)
hold all
plot(Spec_mask_new(1:2:end-1),Spec_mask_new(2:2:end),'r.-','LineWidth',2)
grid on
xlabel('Frequency (MHz)');
ylabel('Power (dB)');
title("Analysis Figure 1");
saveas(fig1, 'Analysis_Figure_1.png');
movefile('Analysis_Figure_1.png', report_directory);

%this pause value is used to allow the octave code to flush the first figure and create the second figure, in the absense of this pause, the second figure might not get generated.
%this pause value might need to be increased for the slower systems. (initially set to .4 seconds)
pause (.4);

if(logging=="true")
  disp("Plotting CompatAnalysis.png");
end
fig2 = figure ();
plot(Rx_UnderlayMask(1:2:end-1),Rx_UnderlayMask(2:2:end),'b.-','LineWidth',2)
hold all
plot(Spec_mask_new(1:2:end-1),Spec_mask_new(2:2:end),'r.-','LineWidth',2)
grid on
xlabel('Frequency (MHz)');
ylabel('Power (dB)');
title("Compat Analysis");
saveas(fig2,'CompatAnalysis.png');
movefile('CompatAnalysis.png', report_directory);
	

%SCM compatibility
[p_Tx_new] = new_spectrum(Spec_mask_new,Underlay_mask);
if(logging=="true")
  disp("[p_Tx_new] @ '[p_Tx_new] = new_spectrum(Spec_mask_new,Underlay_mask)' is : "), disp([p_Tx_new]);
end
[Power_Tx,Power_Tx_dB] = calculate_power(p_Tx_new,Rx_ResBW);
if(logging=="true")
  disp("[Power_Tx,Power_Tx_dB] @ [Power_Tx,Power_Tx_dB] = calculate_power(p_Tx_new,Rx_ResBW) is: "), disp([Power_Tx,Power_Tx_dB]);
end
[p_Rx_new] = calculate_power_3dB(Underlay_mask,Rx_ResBW);
if(logging=="true")
  disp("[p_Rx_new] @ [p_Rx_new] = calculate_power_3dB(Underlay_mask,Rx_ResBW)' is : "), disp([p_Rx_new]);
end
[Power_Rx,Power_Rx_dB] = calculate_power(p_Rx_new,Rx_ResBW);
if(logging=="true")
  disp("[Power_Rx,Power_Rx_dB] @ '[Power_Rx,Power_Rx_dB] = calculate_power(p_Rx_new,Rx_ResBW)' is : "), disp([Power_Rx,Power_Rx_dB]);
end

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
