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
  compat.m
  Function to generate adjusted spectrum mask. 
  p_new: power list for new spectrum mask: [p0,p1,p2...pn]
  f_new: frequency list for new spectrum mask: [f0,f1,f2...fn] 
  ps: spectrum mask representation: [f0,p0,f1,p1....fn,pn];
  pr: underlay mask representation: [f0,p0,f1,p1....fn,pn];
%}

function [p_new,f_new] = compat(ps,pr)

fs=ps(1:2:end-1);
PS=ps(2:2:end);

fr=pr(1:2:end-1);
PR=pr(2:2:end);

[f_new] = freq_sort(fs,fr);

pl=min(PR);

for i=1:length(f_new)
   
    fo=f_new(i);
    ps_v=find_power(fo,ps);
    if(length(ps_v)>1) %Given that there can't be more than two same frequencies in the frequency vector
       if(f_new(i)==f_new(i+1))
        ps_i=ps_v(1);
       else
        ps_i=ps_v(2);
       end
    else
        ps_i=ps_v;
    end
    
    pr_v=find_power(fo,pr);
    if(length(pr_v)>1) %Given that there can't be more than two same frequencies in the frequency vector
       if(f_new(i)==f_new(i+1))
        pr_i=pr_v(1);
       else
        pr_i=pr_v(2);
       end
    else
        pr_i=pr_v;
    end
    
    p_new(i)=ps_i+pl-pr_i;
    
end
figure
plot(f_new,p_new,'r-','LineWidth',2);

end