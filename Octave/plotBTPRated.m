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
  plotBTPRated.m
  Function to plot BTP rated underlay masks
%}

function [retval] = plotBTPRated ()

load SCM_receiver_java.txt;

BTPRated_BW_List=Rx_BTPRatedList(1:2:end-1);
BTPRated_Power_List=Rx_BTPRatedList(2:2:end);

plot(Rx_UnderlayMask(1:2:end-1),Rx_UnderlayMask(2:2:end),'b-.','LineWidth',2)
hold all 
for i=1:length(BTPRated_BW_List)
%plot(Rx_UnderlayMask(1:2:end-1),Rx_UnderlayMask(2:2:end)+BTPRated_Power_List(i),'b.-','LineWidth',2)
  newPowerList = Rx_UnderlayMask(2:2:end)+BTPRated_Power_List(i);
  xpoint=(Rx_UnderlayMask((length(Rx_UnderlayMask)/2)-1)+Rx_UnderlayMask((length(Rx_UnderlayMask)/2)+1))/2;
  plot(Rx_UnderlayMask(1:2:end-1),newPowerList,'b.-','LineWidth',2)
  text(xpoint,min(newPowerList)+5,strcat(num2str(BTPRated_BW_List(i)), ' MHz.sec'))

  hold all
end

grid on
xlabel('Frequency (MHz)');
ylabel('Power (dB)');

retval=0;

endfunction