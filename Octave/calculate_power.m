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
  calculate_power.m
  Function to calculate power under a spectrum/underlay mask
  P_net: power under the spectrum/underlay mask,
  P_net_dB: power under the spectrum/underlay mask in dB,   
  p0: spectrum/underlay mask representation: [f0,p0,f1,p1....fn,pn],
  RBW: Resolution Bandwidth
%}
function [P_net,P_net_dB] = calculate_power(p0,RBW)
%disp("************* Inside calculate_power function **************");
RBW = RBW*1e+6;			% adjusting RBW to Hertz (previously MHz)
fv=p0(1:2:end-1)*1e+6;
pv=p0(2:2:end);
%plot(fv,pv,'r-','LineWidth',2);
P_net=0;

for i=1:length(fv)-1

    if(fv(i)~=fv(i+1))
    
        b1=( pv(i+1)-pv(i) )./( fv(i+1)-fv(i) );
        b0=pv(i) - (b1*fv(i));
        
        if(b1~=0)
            P_net=P_net + ( 10*( (10^((b0 + (b1*fv(i+1)))/10)) - (10^((b0+(b1*fv(i)))/10)) )./(RBW*log(10)*b1) );
        else
            P_net= P_net + ( (10^(b0/10)).*((fv(i+1)-fv(i))./RBW) );
        end
    else
    end
    P_net_dB=10*log10(P_net);
end
%disp("************* Exiting calculate_power function **************");
end
