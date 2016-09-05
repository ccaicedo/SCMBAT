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
  calculate_power_3dB.m
  Function to calculate underlay mask within the 3dB region
  p_new: new underlay mask,  
  p0: underlay mask representation: [f0,p0,f1,p1....fn,pn];
  RBW: resolution bandwidth.
%}

function [p_new] = calculate_power_3dB(p0,RBW)

fv=p0(1:2:end-1)*1e+6;
pv=p0(2:2:end);
%plot(fv,pv,'r-','LineWidth',2);
P_net=0;
pl=min(pv);
po=pl*(10^(.3));

ind=find(pv==pl);

i1=ind(1);
i2=ind(end);

if(fv(i1)~=fv(i1-1))
    
        b1=( pv(i1)-pv(i1-1) )./( fv(i1)-fv(i1-1) );
        b0=pv(i1-1) - (b1*fv(i1-1));        
        f1=((pl+3)-b0)/b1;
        p1=pl+3;

else
    f1=fv(i1-1);
    p1=pv(i1-1);
end


if(fv(i2)~=fv(i2+1))
    
        b1=( pv(i2+1)-pv(i2) )./( fv(i2+1)-fv(i2) );
        b0=pv(i2) - (b1*fv(i2));        
        f2=((pl+3)-b0)/b1;
        p2=pl+3;

else
    f2=fv(i2+1); 
    p2=pv(i2+1);
end

p_new=zeros(1,8);
p_new(1:2:end-1)=[f1,fv(i1),fv(i2),f2].*1e-6;
p_new(2:2:end)=[p1,pl,pl,p2];

%figure
%plot(p_new(1:2:end-1),p_new(2:2:end),'b-','LineWidth',2);

end