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
  calculateBAEPSD.m
  Function to calculate the Bandwidth adjusted effective power
  spectral density (BAEPSD) for a combination of narrowband 
  spectrum masks.
  baepsd: BAEPSD value,
  bw: vector containing bandwidths of narrowband signals
  bwMask: bandwidth of the underlay mask. 
  epsd: Effective Power spectral density. 
%}

function [baepsd] = calculateBAEPSD (bw, bwMask, epsd)

baepsd=10*log10( (10.^(epsd/10))*sum(bw)/bwMask);

endfunction
