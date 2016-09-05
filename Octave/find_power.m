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
  find_power.m
  Function to find power at a given frequency in a spectrum/underlay mask. 
  p: power at the provided frequency. 
  f: frequency where power is to be found.
  p0: spectrum/underlay mask representation : [f0,p0,f1,p1,...fn,pn]
%}

function [p] = find_power(fo,p0)

fv=p0(1:2:end-1);
pv=p0(2:2:end);

%plot(fv,pv,'r-','LineWidth',2);

 ind=find(fo==fv);
 
 if(isempty(ind)==1)
     
     
     ind2=find(fv>fo);
     if(isempty(ind2)==0)
         i=min(ind2);
         if(i==1)
             p=pv(1);
         else
             if(pv(i)==pv(i-1))
                 p=pv(i);
             else
                 b1=(pv(i)-pv(i-1))./( fv(i)-fv(i-1) );
                 b0=pv(i-1)-(b1*fv(i-1));
                 
                 p=b0+(b1*fo);
                 
             end
         end
     else
         p=pv(end);
     end
 else
     
     p=pv(ind);     
 end
                 
             

end