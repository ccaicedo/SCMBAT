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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HoppingData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HoppingData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dwellTime">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
 *               &lt;minExclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="revisitPeriod">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
 *               &lt;minExclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="frequencyList" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}FrequencyList" minOccurs="0"/>
 *         &lt;element name="bandList" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}BandList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HoppingData", propOrder = {
    "dwellTime",
    "revisitPeriod",
    "frequencyList",
    "bandList"
})
public class HoppingData {

    protected double dwellTime;
    protected double revisitPeriod;
    protected FrequencyList frequencyList;
    protected BandList bandList;

    /**
     * Gets the value of the dwellTime property.
     * 
     */
    public double getDwellTime() {
        return dwellTime;
    }

    /**
     * Sets the value of the dwellTime property.
     * 
     */
    public void setDwellTime(double value) {
        this.dwellTime = value;
    }

    /**
     * Gets the value of the revisitPeriod property.
     * 
     */
    public double getRevisitPeriod() {
        return revisitPeriod;
    }

    /**
     * Sets the value of the revisitPeriod property.
     * 
     */
    public void setRevisitPeriod(double value) {
        this.revisitPeriod = value;
    }

    /**
     * Gets the value of the frequencyList property.
     * 
     * @return
     *     possible object is
     *     {@link FrequencyList }
     *     
     */
    public FrequencyList getFrequencyList() {
        return frequencyList;
    }

    /**
     * Sets the value of the frequencyList property.
     * 
     * @param value
     *     allowed object is
     *     {@link FrequencyList }
     *     
     */
    public void setFrequencyList(FrequencyList value) {
        this.frequencyList = value;
    }

    /**
     * Gets the value of the bandList property.
     * 
     * @return
     *     possible object is
     *     {@link BandList }
     *     
     */
    public BandList getBandList() {
        return bandList;
    }

    /**
     * Sets the value of the bandList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BandList }
     *     
     */
    public void setBandList(BandList value) {
        this.bandList = value;
    }

}
