/*
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

*/


package org.ieee.dyspansc._1900._5.scm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HopRated", propOrder = {
    "hoppingData",
    "toleranceThreshold"
})
public class HopRated {

    @XmlElement(required = true)
    protected HoppingData hoppingData;
    protected double toleranceThreshold;

    /**
     * Gets the value of the hoppingData property.
     * 
     * @return
     *     possible object is
     *     {@link HoppingData }
     *     
     */
    public HoppingData getHoppingData() {
        return hoppingData;
    }

    /**
     * Sets the value of the hoppingData property.
     * 
     * @param value
     *     allowed object is
     *     {@link HoppingData }
     *     
     */
    public void setHoppingData(HoppingData value) {
        this.hoppingData = value;
    }

    /**
     * Gets the value of the toleranceThreshold property.
     * 
     */
    public double getToleranceThreshold() {
        return toleranceThreshold;
    }

    /**
     * Sets the value of the toleranceThreshold property.
     * 
     */
    public void setToleranceThreshold(double value) {
        this.toleranceThreshold = value;
    }

}
