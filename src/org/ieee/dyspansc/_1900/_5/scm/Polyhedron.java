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
@XmlType(name = "Polyhedron", propOrder = {
    "scmPolygon",
    "height",
    "transmitterDensity",
    "topAttenuation",
    "bottomAttenuation"
})
public class Polyhedron {

    @XmlElement(required = true)
    protected SCMPolygon scmPolygon;
    protected double height;
    protected Double transmitterDensity;
    @XmlElement(defaultValue = "0")
    protected Double topAttenuation;
    @XmlElement(defaultValue = "0")
    protected Double bottomAttenuation;

    /**
     * Gets the value of the scmPolygon property.
     * 
     * @return
     *     possible object is
     *     {@link SCMPolygon }
     *     
     */
    public SCMPolygon getScmPolygon() {
        return scmPolygon;
    }

    /**
     * Sets the value of the scmPolygon property.
     * 
     * @param value
     *     allowed object is
     *     {@link SCMPolygon }
     *     
     */
    public void setScmPolygon(SCMPolygon value) {
        this.scmPolygon = value;
    }

    /**
     * Gets the value of the height property.
     * 
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     * 
     */
    public void setHeight(double value) {
        this.height = value;
    }

    /**
     * Gets the value of the transmitterDensity property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTransmitterDensity() {
        return transmitterDensity;
    }

    /**
     * Sets the value of the transmitterDensity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTransmitterDensity(Double value) {
        this.transmitterDensity = value;
    }

    /**
     * Gets the value of the topAttenuation property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTopAttenuation() {
        return topAttenuation;
    }

    /**
     * Sets the value of the topAttenuation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTopAttenuation(Double value) {
        this.topAttenuation = value;
    }

    /**
     * Gets the value of the bottomAttenuation property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBottomAttenuation() {
        return bottomAttenuation;
    }

    /**
     * Sets the value of the bottomAttenuation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBottomAttenuation(Double value) {
        this.bottomAttenuation = value;
    }

}
