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
import javax.xml.datatype.Duration;


/**
 * <p>Java class for Cycle complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Cycle">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="waitUntilOn" type="{http://www.w3.org/2001/XMLSchema}duration"/>
 *         &lt;element name="durationOn" type="{http://www.w3.org/2001/XMLSchema}duration"/>
 *         &lt;element name="durationOff" type="{http://www.w3.org/2001/XMLSchema}duration"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Cycle", propOrder = {
    "waitUntilOn",
    "durationOn",
    "durationOff"
})
public class Cycle {

    @XmlElement(required = true)
    protected Duration waitUntilOn;
    @XmlElement(required = true)
    protected Duration durationOn;
    @XmlElement(required = true)
    protected Duration durationOff;

    /**
     * Gets the value of the waitUntilOn property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getWaitUntilOn() {
        return waitUntilOn;
    }

    /**
     * Sets the value of the waitUntilOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setWaitUntilOn(Duration value) {
        this.waitUntilOn = value;
    }

    /**
     * Gets the value of the durationOn property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getDurationOn() {
        return durationOn;
    }

    /**
     * Sets the value of the durationOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setDurationOn(Duration value) {
        this.durationOn = value;
    }

    /**
     * Gets the value of the durationOff property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getDurationOff() {
        return durationOff;
    }

    /**
     * Sets the value of the durationOff property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setDurationOff(Duration value) {
        this.durationOff = value;
    }

}
