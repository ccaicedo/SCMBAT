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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Confidence", propOrder = {
    "measure",
    "maxDwellTime"
})
public class Confidence {

    protected double measure;
    protected Double maxDwellTime;
    @XmlAttribute(name = "approach", required = true)
    protected String approach;
    @XmlAttribute(name = "nature", required = true)
    protected String nature;
    @XmlAttribute(name = "derivation")
    protected String derivation;

    /**
     * Gets the value of the measure property.
     * 
     */
    public double getMeasure() {
        return measure;
    }

    /**
     * Sets the value of the measure property.
     * 
     */
    public void setMeasure(double value) {
        this.measure = value;
    }

    /**
     * Gets the value of the maxDwellTime property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMaxDwellTime() {
        return maxDwellTime;
    }

    /**
     * Sets the value of the maxDwellTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMaxDwellTime(Double value) {
        this.maxDwellTime = value;
    }

    /**
     * Gets the value of the approach property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApproach() {
        return approach;
    }

    /**
     * Sets the value of the approach property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApproach(String value) {
        this.approach = value;
    }

    /**
     * Gets the value of the nature property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNature() {
        return nature;
    }

    /**
     * Sets the value of the nature property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNature(String value) {
        this.nature = value;
    }

    /**
     * Gets the value of the derivation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDerivation() {
        return derivation;
    }

    /**
     * Sets the value of the derivation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDerivation(String value) {
        this.derivation = value;
    }

}
