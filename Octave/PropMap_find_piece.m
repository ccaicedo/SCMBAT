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
  PropMap_find_piece.m
  Function to find a power given at an azimuth, elevation angle and minimum distance 
  for a piecewise propagation map.

  p: power at given orientation. 
  theta_0: azimuth angle
  phi_0: elevation angle
  Min_Dist: breakpoint distance. 
  PropMap: propagation map for the given transmitter/reciever. 
%}

function [n0,d_break,n1] = PropMap_find_piece(theta_0,phi_0,PropMap,Min_Dist)

P=PropMap;
c=length(P);

ind_phi_s=0;
ind_phi_e=0;
ind_theta_s=0;
ind_theta_e=0;

%Finding all the indexes of 360 in PropogationMap, the vector index360 shall contain index of all theh 360s present in PowerMap

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

n0=0;
d_break=0;
n1=0;

%disp("the phi at start is : "), disp(P(ind_phi_s));
%disp("the phi at end is : "), disp(P(ind_phi_e));
%disp("diff is : "), disp(ind_phi_e - ind_phi_s);
if(ind_phi_e - ind_phi_s == 5 || ind_phi_e - ind_phi_s == 7)
    ind_theta_curr=ind_phi_s+1;
    %disp("the theta current is : "), disp(P(ind_theta_curr));
    %disp("the theta current + 1 is : "), disp(P(ind_theta_curr+1));
    if(P(ind_theta_curr+1)==0)          % 0 refers to linear type
        %disp("it's linear type");
        n0=P(ind_theta_curr+2);
        d_break=0;
        n1=0;
    end
    if(P(ind_theta_curr+1)==1)          % 1 refers to piecewise linear
        %disp("it's piecewise linear");
        n0=P(ind_theta_curr+2);
        d_break=P(ind_theta_curr+3);
        n1=P(ind_theta_curr+4);
    end
    %disp("the values are: " ), disp(n0), disp(d_break), disp(n1);
else
    ind_theta_next=ind_phi_s+1;

    while index <= ind_phi_e-2
        ind_theta_curr=ind_theta_next;

        % Finding next theta depends on whether we have linear or piecewise linear
        if(P(ind_theta_curr + 1) == 0)          % 0 refers to linear type
            %disp("setting next for linear type");
            ind_theta_next=ind_theta_curr+3;
        end
        if(P(ind_theta_curr + 1) == 1)          % 1 refers to piecewise linear
            %disp("setting next for piecewise linear");
            ind_theta_next=ind_theta_curr+5;
        end

        if(theta_0 >= P(ind_theta_curr) && theta_0 < P(ind_theta_next))
            if(P(ind_theta_curr + 1) == 0)          % 0 refers to linear type
                %disp("it's linear type");
                n0 = P(ind_theta_curr + 2);
                d_break = 0;
                n1 = 0;
            end
            if(P(ind_theta_curr + 1) == 1)          % 1 refers to piecewise linear
                %disp("it's piecewise linear");
                n0 = P(ind_theta_curr + 2);
                d_break = P(ind_theta_curr + 3);
                n1 = P(ind_theta_curr + 4);
            end
            %disp("the values are: " ), disp(n0), disp(d_break), disp(n1);
            break;
        end
    end
end
