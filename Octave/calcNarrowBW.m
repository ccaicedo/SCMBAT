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
  calcNarrowBW.m  
  Function to calculate the banwidth of narrow band signals
  BW: Bandwidth   p0: spectrum mask representation: [f0,p0,f1,p1....fn,pn];
%}

function [BW] = calcNarrowBW(p0)

fv=p0(1:2:end-1);
pv=p0(2:2:end);

f1 = ( fv(1) + fv(end) )/2;
f2 = ( fv(1) + fv(end) )/2;

maxP=max(pv);
p1=find_power(f1,p0);
delP1 = maxP-p1;

while(abs(delP1-20)>0.1)

  if(delP1>20)
    f1= f1+0.0001;
    p1=find_power(f1,p0);
  else
    f1= f1-0.0001;
    p1=find_power(f1,p0);
  end

  delP1=maxP-p1;

end

maxP=max(pv);
p2=find_power(f2,p0);
delP2 = maxP-p2;

while(abs(delP2-20)>0.1)

  if(delP2>20)
    f2= f2-0.0001;
    p2=find_power(f2,p0);
  else
    f2= f2+0.0001;
    p2=find_power(f2,p0);
  end

  delP2=maxP-p2;

end

BW=f2-f1;

%fig1 = figure;
%plot(p0(1:2:end-1),p0(2:2:end),'r.-','LineWidth',2)
%hold all;
%stem(f1,p1,'b');
%stem(f2,p2,'b');
%grid on
%xlabel('Frequency (MHz)');
%ylabel('Power (dB)');


end