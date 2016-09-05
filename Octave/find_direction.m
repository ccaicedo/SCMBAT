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
  find_direction.m
  Function to find distance, azimuth angle and elevation based on 
  Transmitter and Receiver location (geographical coordinates) 
  e: elevation angle
  az: azimuth angle
  d: distance
  T: Geographical coordinates for the transmitter
  R: Geographical coordinates for the receiver. 
%}

function [e,az,d] = find_direction(T,R)

% T(1)-Lat  T(2)-Long  T(3)-Height

T(1)=T(1)*pi/180;
T(2)=T(2)*pi/180;
R(1)=R(1)*pi/180;
R(2)=R(2)*pi/180;

a=6378137;
E=0.0818191908426;
er=6367495;

v_t=a/( (1-((E*sin(T(1))).^2)).^0.5);

x_t=(v_t+T(3))*cos(T(1))*cos(T(2));
y_t=(v_t+T(3))*cos(T(1))*sin(T(2));
z_t=( (v_t*(1-(E.^2))) + T(3) )*sin(T(1));

v_r=a/( (1-((E*sin(R(1))).^2)).^0.5);

x_r=(v_r+R(3))*cos(R(1))*cos(R(2));
y_r=(v_r+R(3))*cos(R(1))*sin(R(2));
z_r=( (v_r*(1-(E.^2))) + R(3) )*sin(R(1));

%Finding Coordinates

%C_r=[x_r,y_r,z_r]
%C_t=[x_t,y_t,z_t]

%Plotting Coordinates
%figure
%plot3(x_r-x_t,y_r-y_t,z_r-z_t,'rx','Linewidth',2)
%hold all
%plot3(0,0,0,'bo','Linewidth',2)

d=(  ((x_t-x_r).^2) + ((y_t-y_r).^2) + ((z_t-z_r).^2) ).^0.5;

% Finding the angle and curvature

d_t=( (x_t^2) + (y_t^2) + (z_t^2) ).^0.5;
d_r=( (x_r^2) + (y_r^2) + (z_r^2) ).^0.5;

w= acos( ( (x_t*x_r)+(y_t*y_r)+(z_t*z_r) )/(d_t*d_r) );
%d= er*w;

tan_theta=(y_r-y_t)./(x_r-x_t);
sin_phi=(z_r-z_t)./d;

if(y_r>y_t && x_r>x_t)
  theta=atan(tan_theta)*180/pi;
elseif(y_r>y_t && x_r<x_t)
  theta=180 + (atan(tan_theta)*180/pi) ;
elseif(y_r<y_t && x_r<x_t)
  theta=180 + (atan(tan_theta)*180/pi);
elseif(y_r<y_t && x_r>x_t)
  theta=360 + (atan(tan_theta)*180/pi);
end

phi=asin(sin_phi)*180/pi;

e=phi;
az=theta;

end