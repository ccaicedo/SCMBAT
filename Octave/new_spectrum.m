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
  new_spectrum.m
  Function to generate adjusted spectrum mask
  pv_new: new spectrum mask [f0,p0,f1,p1,f2,p2...fn,pn] 
  ps: spectrum mask representation: [f0,p0,f1,p1....fn,pn];
  pr: underlay mask representation: [f0,p0,f1,p1....fn,pn];
%}

function [pv_new] = new_spectrum(ps,pr)
%disp("Inside new_spectrum function");
fs=ps(1:2:end-1);
PS=ps(2:2:end);

fr=pr(1:2:end-1);
PR=pr(2:2:end);
%disp("fs is " ), disp(fs);
%disp("PS is "), disp(PS);
%disp("fr is "), disp(fr);
%disp("PR is "), disp(PR);

f_new=freq_sort(fs,fr);
%disp("f_new is :"), disp(f_new);

pl=min(PR);
%disp("pl is " ), disp(pl);

for i=1:length(f_new)
    %disp("the value of i is: "), disp(i);
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
	%disp("f0 is :"), disp(fo);
    	%disp("ps_v is :"), disp(ps_v);
	%disp("ps_i is :"), disp(ps_i);

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
    %disp("pr_v is : "), disp(pr_v);
    %disp("pr_i is : "), disp(pr_i);

    p_new(i)=ps_i+pl-pr_i;
    %disp("p_new is : "), disp(p_new);
    
end

%figure
%plot(f_new,p_new,'r.-','LineWidth',2);
%grid on
%xlabel('Frequency (MHz)');
%ylabel('Power (dB)');

pv_new=zeros(1,2*length(f_new));
pv_new(1:2:end-1)=f_new;
pv_new(2:2:end)=p_new;

%disp("Exiting new_spectrum function");
end
