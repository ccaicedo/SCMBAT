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
  MaxPow_Diff.m
  Function to find the power difference between a spectrum mask and 
  and underlay mask using the Max Power Density method. 
  P_diff: Power difference between the spectrum and underlay mask
  pv_s: spectrum mask representation: [f0,p0,f1,p1....fn,pn];
  pv_r: underlay mask representation: [f0,p0,f1,p1....fn,pn];
%}

function P_diff = MaxPow_Diff(pv_r,pv_s)

fr=pv_r(1:2:end-1);
fs=pv_s(1:2:end-1);

f_new = freq_sort(fr,fs);
f_new(end+1) = 0;
i=1;

  while(i<length(f_new))

    if(f_new(i)==f_new(i+1))
    
      P_r=find_power(f_new(i),pv_r);
      P_s=find_power(f_new(i),pv_s);
      
      P_diff(i:i+1)=P_r-P_s;
      i=i+2;
    else
      
      P_r=find_power(f_new(i),pv_r);
      P_s=find_power(f_new(i),pv_s);
      
      P_diff(i)=P_r-P_s;
      i=i+1;
    end

  end

  f_new(end) =[];
end