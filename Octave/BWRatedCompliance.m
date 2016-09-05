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
	BWRatedCompliance
	Function to perform Bandwidth Rated Compatibility analysis
%}

function [epsd,compatBWMask_List] = BWRatedCompliance (bw, maxPSD)

  compatBWMask_List=0;

  load SCM_receiver_java.txt;

  BWRated_BW_List=Rx_BWRatedList(1:2:end-1);
  BWRated_Power_List=Rx_BWRatedList(2:2:end);

  [ef_bw,epsd] = calculateEPSD (bw, maxPSD);

  for i=1:length(BWRated_BW_List)

    MaskPowerCriterion = min(Rx_UnderlayMask(2:2:end)+BWRated_Power_List(i));
    MaskPowerCriterion=MaskPowerCriterion(1);

    [baepsd] = calculateBAEPSD (bw, BWRated_BW_List(i), epsd);

    if(baepsd<MaskPowerCriterion)
      compatBWMask_List(i) = BWRated_BW_List(i);
    else
    end

  end

endfunction
