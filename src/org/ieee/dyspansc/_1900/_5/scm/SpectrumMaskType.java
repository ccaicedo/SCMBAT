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


/**
 * <p>Java class for SpectrumMaskType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SpectrumMaskType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="hoppingData" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}HoppingDataType" minOccurs="0"/>
 *         &lt;element name="resolutionBW" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="scmMask" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}SCMMaskType"/>
 *         &lt;element name="confidence" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}ConfidenceType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpectrumMaskType", propOrder = {
    "hoppingData",
    "resolutionBW",
    "scmMask",
    "confidence"
})
public class SpectrumMaskType {

    protected HoppingDataType hoppingData;
    protected double resolutionBW;
    @XmlElement(required = true)
    protected SCMMaskType scmMask;
    protected ConfidenceType confidence;

    /**
     * Gets the value of the hoppingData property.
     * 
     * @return
     *     possible object is
     *     {@link HoppingDataType }
     *     
     */
    public HoppingDataType getHoppingData() {
        return hoppingData;
    }

    /**
     * Sets the value of the hoppingData property.
     * 
     * @param value
     *     allowed object is
     *     {@link HoppingDataType }
     *     
     */
    public void setHoppingData(HoppingDataType value) {
        this.hoppingData = value;
    }

    /**
     * Gets the value of the resolutionBW property.
     * 
     */
    public double getResolutionBW() {
        return resolutionBW;
    }

    /**
     * Sets the value of the resolutionBW property.
     * 
     */
    public void setResolutionBW(double value) {
        this.resolutionBW = value;
    }

    /**
     * Gets the value of the scmMask property.
     * 
     * @return
     *     possible object is
     *     {@link SCMMaskType }
     *     
     */
    public SCMMaskType getScmMask() {
        return scmMask;
    }

    /**
     * Sets the value of the scmMask property.
     * 
     * @param value
     *     allowed object is
     *     {@link SCMMaskType }
     *     
     */
    public void setScmMask(SCMMaskType value) {
        this.scmMask = value;
    }

    /**
     * Gets the value of the confidence property.
     * 
     * @return
     *     possible object is
     *     {@link ConfidenceType }
     *     
     */
    public ConfidenceType getConfidence() {
        return confidence;
    }

    /**
     * Sets the value of the confidence property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfidenceType }
     *     
     */
    public void setConfidence(ConfidenceType value) {
        this.confidence = value;
    }

}
