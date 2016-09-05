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

function [n1,d_break,n2] = PropMap_find_piece(theta_0,phi_0,PropMap,Min_Dist)

P=PropMap;
c=length(P);

ind_phi_s=0;
ind_phi_e=c;

i=1;

while(i<c)

     if(P(i)==360)
         if(P(i+1)<=phi_0)
             ind_phi_s=i+1;
             i=i+1;
         else
             ind_phi_e=i+1;
             break;
         end
     else
         i=i+4;
     end
     
end

ind_theta_s=ind_phi_s+4;

while(ind_theta_s<ind_phi_e)
    if(P(ind_theta_s)>theta_0)
    
        n1=P(ind_theta_s-3);
        d_break=P(ind_theta_s-2);
        n2=P(ind_theta_s-1);        
        break;
    else
        ind_theta_s=ind_theta_s+4;
    end    
end

n1=P(ind_theta_s-3);
d_break=P(ind_theta_s-2);
n2=P(ind_theta_s-1);

end