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
  freq_sort.m
  Function to sort two lists of frequencies f1 and f2 and return
  the sorted list in f_new. 
%}

function [f_new] = freq_sort(f1,f2)

f=sort([f1,f2]);
u=unique(f);

for i=1:length(u)
   
    ind=find(f==u(i));
    
    switch length(ind)
        
        case 2 
            ind2=find(f1==u(i));
            if(length(ind2)==1)
                  f(ind(1))=[]; 
            else
            end
        case 3
            f(ind(1))=[];
        case 4
            f(ind(1))=[];
            f(ind(2))=[];
        otherwise
        
    end
    
    
end

f_new=f;

end