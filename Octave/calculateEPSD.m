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
  calculateEPSD.m
  Function to calculate Effective power spectral density of narrow 
  band signals
  ef_bw: Effective Bandwidth,
  epsd: Effective power spectral density,
  bw: vector of Bandwidth of narrowband signals [bw_0,bw_1,....bw_n],
  maxPSD: vector of Maximum power density of narrow band signals [MPSD0,MPSD1,... MPSDn].
%}

function [ef_bw,epsd] = calculateEPSD (bw, maxPSD)

ef_bw=sum(bw);
epsd= 10*log10( sum(bw.*(10.^(maxPSD./10)))/sum(bw) );

endfunction
