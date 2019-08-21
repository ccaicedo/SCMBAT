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
disp("SCM_transmitter_java.txt loaded successfully");

load SCM_receiver_java.txt;
disp("SCM_receiver_java.txt loaded successfully");


%Gets the min distance, elevation and azimuth angle
T=[Tx_Lat,Tx_Long,Tx_Alt];
R=[Rx_Lat,Rx_Long,Rx_Alt];


disp("Getting the the min distance, elevation and azimuth angle: ");
disp("Calling function find_Compare");
[phi_Txo,theta_Txo,Min_Dist]=find_direction(T,R);
disp("Values returned from find_direction are: ");
disp("phi_Txo: "), disp(phi_Txo), disp("theta_Txo: "), disp(theta_Txo), disp("Min_Dist: "), disp(Min_Dist);


%Compare frequency range
disp("Comparing Frequency range.");
if((Tx_SpecMask(1)<Rx_UnderlayMask(1) && Tx_SpecMask(end-1)<Rx_UnderlayMask(1)) || (Tx_SpecMask(1)>Rx_UnderlayMask(end-1) && Tx_SpecMask(end-1)>Rx_UnderlayMask(end-1)) )
    disp(strcat('result:', 'System is compatible'));
    return;
end


%Transmitter Power map attenuation to Total Power;
disp("Transmitter Power map attenuation to Total Power");
disp("Calling function PowerMap_find for Transmitter model with values:");
disp("phi_Txo: "), disp(phi_Txo), disp("theta_Txo: "), disp(theta_Txo), disp("Tx_PowerMap"), disp(Tx_PowerMap);


p=PowerMap_find(theta_Txo,phi_Txo,Tx_PowerMap);
disp("the p (gain) value for transmitter is : "), disp(p);
disp("the total power value is: "), disp(Tx_TotPow);


Power=Tx_TotPow+p;
disp("the total power @ 'Power=Tx_TotPow+p' is : " ), disp(Power);


Power=Power+(10*log10(1e-3/Tx_ResBW)); % Bringing the Reference Bandwidth to 1 Khz;
disp("Bringing the Reference Bandwidth to 1 Khz");
disp("the total power after adjustment (10*log10(1e-3/Tx_ResBW)) : " ), disp(Power);


%Attenuation due to Propagation Map
disp("Calling function PropMap_find_piece with values: ");


disp("theta_Txo: "), disp(theta_Txo), disp("phi_Txo: "), disp(phi_Txo), disp("Min_Dist"), disp(Min_Dist);
[n0,d_break,n1]=PropMap_find_piece(theta_Txo,phi_Txo,Tx_PropMap,Min_Dist);
disp("the returned values for n0 is : " ), disp(n0), disp("for d_break: "), disp(d_break), disp("for n1: "), disp(n1);


disp("Checking if(Min_Dist>d_break && d_break!=0.0)");
if(Min_Dist>d_break && d_break!=0.0)
  disp("condition is true, calculating 'Power = Power - (10*n0*log10(d_break)) - (10*n1*log10(Min_Dist/d_break))'");
  Power = Power - (10*n0*log10(d_break)) - (10*n1*log10(Min_Dist/d_break));
else
  disp("Condition is false; calculating 'Power = Power - (10*n0*log(Min_Dist))'");
  Power = Power - (10*n0*log(Min_Dist));
end
disp("After calculations the Power is : " ), disp(Power);


Spec_mask_new=Tx_SpecMask;
disp("Spec_mask @ 'Spec_mask_new=Tx_SpecMask' is : "), disp(Spec_mask_new);
Spec_mask_new(2:2:end)=Tx_SpecMask(2:2:end)+Power;
disp("Spec_mask_new(2:2:end) @ 'Spec_mask_new(2:2:end)=Tx_SpecMask(2:2:end)+Power' is: "), disp(Spec_mask_new(2:2:end));


%%---- Computations for the Receiver Model -------

rx_PowAdj = Rx_TotPow;


%Attenuation due to the Receiver Power Map
disp("Attenuation due to the Receiver Power Map");

phi_Rxo=-phi_Txo;
disp("phi_Rxo after phi_Rxo=-phi_Txo is : "), disp(phi_Rxo);


disp("checking if(theta_Txo>180)");
if(theta_Txo>180)
    disp("Condition true calculating 'theta_Rxo=theta_Txo-180'");
    theta_Rxo=theta_Txo-180;
else
    disp("Condition true, calculating theta_Rxo=theta_Txo+180");
    theta_Rxo=theta_Txo+180;
end


disp("Calling function PowerMap_find for Receiver model.");
p2=PowerMap_find(theta_Rxo,phi_Rxo,Rx_PowerMap);
disp("the p (gain) power value for receiver model is : " ), disp(p2);


%Power=Power+(10*log10(Rx_ResBW/1e-3));
rx_PowAdj=rx_PowAdj+(10*log10(1e-3/Rx_ResBW));
%disp("Power @ Power=Power+(10*log10(Rx_ResBW/1e-3)): "), disp(Power); 
disp("Power @ Power=Power+(10*log10(1e-3/Rx_ResBW)): "), disp(Power); 


rx_PowAdj=rx_PowAdj+p2;
disp("Power @ 'Power=Power+p2' is : "), disp(Power);


% Adjusted Underlay Mask
Underlay_mask=Rx_UnderlayMask;
disp("Underlay_mask is: "), disp(Underlay_mask);

Underlay_mask(2:2:end) = Rx_UnderlayMask(2:2:end) + rx_PowAdj;
disp("Adjusted Underlay_mask is: "), disp(Underlay_mask(2:2:end));

Underlay_freq_list=Underlay_mask(1:2:end-1);
disp("Underlay_freq_list @ 'Underlay_freq_list=Underlay_mask(1:2:end-1)' is "), disp(Underlay_freq_list);

Spec_freq_list=Spec_mask_new(1:2:end-1);
disp("Spec_freq_list @ 'Spec_freq_list=Spec_mask_new(1:2:end-1)' " ), disp(Spec_freq_list);


list_1=find(Underlay_freq_list(end)<Spec_freq_list);
disp("list_1 @ 'list_1=find(Underlay_freq_list(end)<Spec_freq_list)'is: "), disp(list_1);


if(isempty(list_1)==0)
  Spec_freq_list=[Spec_freq_list(1:list_1(1)-1),Underlay_freq_list(end)];
  disp("Spec_freq_list @ 'Spec_freq_list=[Spec_freq_list(1:list_1(1)-1),Underlay_freq_list(end)'] is: "), disp(Spec_freq_list);
  disp("calling find_power with the values: "), disp("Underlay_freq_list(end) :"), disp(Underlay_freq_list(end)), disp("Spec_mask_new"), disp(Spec_mask_new);
  p = find_power(Underlay_freq_list(end),Spec_mask_new);
  disp("returning from find_power, the value of p is: "), disp(p);
  Spec_mask_new=[Spec_mask_new(1:2*list_1(1)-2),Underlay_freq_list(end),p];
  disp("Spec_mask_new @ 'Spec_mask_new=[Spec_mask_new(1:2*list_1(1)-2),Underlay_freq_list(end),p]' is : "), disp(Spec_mask_new);
end


list_2=find(Spec_freq_list<Underlay_freq_list(1));
disp("list_2 @ 'list_2=find(Spec_freq_list<Underlay_freq_list(1))' is: "), disp(list_2);
if(isempty(list_2)==0)
  Spec_freq_list=[Underlay_freq_list(1),Spec_freq_list(list_2(end)+1:end)];
  disp("Spec_freq_list @ 'Spec_freq_list=[Underlay_freq_list(1),Spec_freq_list(list_2(end)+1:end)]"), disp(Spec_freq_list);
  disp("calling find_power with the values: "), disp("Underlay_freq_list(1) :"), disp(Underlay_freq_list(1)), disp("Spec_mask_new"), disp(Spec_mask_new);
  p2 = find_power(Underlay_freq_list(1),Spec_mask_new);
  disp("returning from find_power, the value of p is: "), disp(p2);
  Spec_mask_new=[Underlay_freq_list(1),p2,Spec_mask_new(2*list_2(end)+1:end)];
  disp("Spec_mask_new @ 'Spec_mask_new=[Underlay_freq_list(1),p2,Spec_mask_new(2*list_2(end)+1:end)]' is : "), disp(Spec_mask_new);
end


disp("Plotting Analysis_Figure_1.png");
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


disp("Plotting CompatAnalysis.png");
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
disp("[p_Tx_new] @ '[p_Tx_new] = new_spectrum(Spec_mask_new,Underlay_mask)' is : "), disp([p_Tx_new]);
[Power_Tx,Power_Tx_dB] = calculate_power(p_Tx_new,Rx_ResBW);
disp("[Power_Tx,Power_Tx_dB] @ [Power_Tx,Power_Tx_dB] = calculate_power(p_Tx_new,Rx_ResBW) is: "), disp([Power_Tx,Power_Tx_dB]);
[p_Rx_new] = calculate_power_3dB(Underlay_mask,Rx_ResBW);
disp("[p_Rx_new] @ [p_Rx_new] = calculate_power_3dB(Underlay_mask,Rx_ResBW)' is : "), disp([p_Rx_new]);
[Power_Rx,Power_Rx_dB] = calculate_power(p_Rx_new,Rx_ResBW);
disp("[Power_Rx,Power_Rx_dB] @ '[Power_Rx,Power_Rx_dB] = calculate_power(p_Rx_new,Rx_ResBW)' is : "), disp([Power_Rx,Power_Rx_dB]);


%Power_Tx_dB
AllowablePower=Power_Rx_dB;
Power_Margin_Difference = AllowablePower-Power_Tx_dB;
if(AllowablePower>Power_Tx_dB)
    disp(strcat('result: ', 'System is compatible'));
    disp(strcat('result: ', num2str(Power_Margin_Difference)));
else
    disp(strcat('result: ', 'System is not compatible')); 
    disp(strcat('result: ', num2str(Power_Margin_Difference)));
end


end
