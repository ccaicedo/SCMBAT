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
  plotDutyRated.m
  Function to plot Duty cycle rated underlay masks.
%}

function [retval] = plotDutyRated ()

load SCM_receiver_java.txt;

DutyRated_List=Rx_DutyList(1:2:end-1);
DutyRated_PowerList=Rx_DutyList(2:2:end);

for i=1:length(DutyRated_List)
  plot(Rx_UnderlayMask(1:2:end-1),Rx_UnderlayMask(2:2:end)+DutyRated_PowerList(i),'b.-','LineWidth',2)
  hold all
end

grid on
xlabel('Frequency (MHz)');
ylabel('Power (dB)');

retval=0;

endfunction