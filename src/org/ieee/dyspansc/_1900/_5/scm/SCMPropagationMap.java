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
@XmlType(name = "SCMPropagationMap", propOrder = {
    "propMap",
    "antennaHeight",
    "locationIndex",
    "confidence"
})
public class SCMPropagationMap {

    @XmlElement(required = true)
    protected PropMap propMap;
    protected AntennaHeight antennaHeight;
    protected Integer locationIndex;
    protected Confidence confidence;

    /**
     * Gets the value of the propMap property.
     * 
     * @return
     *     possible object is
     *     {@link PropMap }
     *     
     */
    public PropMap getPropMap() {
        return propMap;
    }

    /**
     * Sets the value of the propMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropMap }
     *     
     */
    public void setPropMap(PropMap value) {
        this.propMap = value;
    }

    /**
     * Gets the value of the antennaHeight property.
     * 
     * @return
     *     possible object is
     *     {@link AntennaHeight }
     *     
     */
    public AntennaHeight getAntennaHeight() {
        return antennaHeight;
    }

    /**
     * Sets the value of the antennaHeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link AntennaHeight }
     *     
     */
    public void setAntennaHeight(AntennaHeight value) {
        this.antennaHeight = value;
    }

    /**
     * Gets the value of the locationIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLocationIndex() {
        return locationIndex;
    }

    /**
     * Sets the value of the locationIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLocationIndex(Integer value) {
        this.locationIndex = value;
    }

    /**
     * Gets the value of the confidence property.
     * 
     * @return
     *     possible object is
     *     {@link Confidence }
     *     
     */
    public Confidence getConfidence() {
        return confidence;
    }

    /**
     * Sets the value of the confidence property.
     * 
     * @param value
     *     allowed object is
     *     {@link Confidence }
     *     
     */
    public void setConfidence(Confidence value) {
        this.confidence = value;
    }

}
