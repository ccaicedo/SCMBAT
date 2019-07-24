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
  PowerMap_find.m
  Function to compute a power level given an azimuth and elevation angle for 
  a power map.
  p: power at given orientation. 
  theta_0: azimuth angle
  phi_0: elevation angle
  PowerMap: power map for the given transmitter/reciever. 
%}

function [p] = PowerMap_find(theta_0,phi_0,PowerMap)

P=PowerMap;
c=length(P);
ind_phi_s=0;
ind_phi_e=0;
ind_theta_s=0;
ind_theta_e=0;


%Finding all the indexes of 360 in PowerMap, the vector index360 shall contain index of all theh 360s present in PowerMap

index360=find(P==360.0);

%This part identifies and marks the phi_s and phi_e, i.e. the block where required elevation resides

index=1;
ind_phi_curr=index;
ind_phi_nxt=index360(index)+1;
while true
    if(phi_0>=P(ind_phi_curr) && phi_0<P(ind_phi_nxt))
        ind_phi_s=ind_phi_curr;
        ind_phi_e=ind_phi_nxt;
        break;
    end
    ind_phi_curr=index360(index)+1;
    ind_phi_nxt=index360(index+1)+1;
    if(index==c)
        break;
    end
    index++;
end

%This part identifies and marks the theta_s and theta_e indexes i.e. the piece where the required azimuth resides

index=ind_phi_s+1;
if(ind_phi_e - ind_phi_s==3)
    p=P(ind_phi_s+2);
else
    while index <= ind_phi_e-2
        ind_theta_curr=index;
        ind_theta_next=index+2;
        if(theta_0 >= P(ind_theta_curr) && theta_0 < P(ind_theta_next))
            p=P(ind_theta_curr+1);
            break;
        end
        index=index+2;
    end
end
