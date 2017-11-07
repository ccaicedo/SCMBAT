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
 * <p>Java class for SCMPowerMapType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SCMPowerMapType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orientation" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}OrientationType"/>
 *         &lt;element name="scanningRegion" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}ScanningRegionType" minOccurs="0"/>
 *         &lt;element name="gainMap" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}GainMapType"/>
 *         &lt;element name="locationIndex" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
@XmlType(name = "SCMPowerMapType", propOrder = {
    "orientation",
    "scanningRegion",
    "gainMap",
    "locationIndex",
    "confidence"
})
public class SCMPowerMapType {

    @XmlElement(required = true)
    protected OrientationType orientation;
    protected ScanningRegionType scanningRegion;
    @XmlElement(required = true)
    protected GainMapType gainMap;
    protected Integer locationIndex;
    protected ConfidenceType confidence;

    /**
     * Gets the value of the orientation property.
     * 
     * @return
     *     possible object is
     *     {@link OrientationType }
     *     
     */
    public OrientationType getOrientation() {
        return orientation;
    }

    /**
     * Sets the value of the orientation property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrientationType }
     *     
     */
    public void setOrientation(OrientationType value) {
        this.orientation = value;
    }

    /**
     * Gets the value of the scanningRegion property.
     * 
     * @return
     *     possible object is
     *     {@link ScanningRegionType }
     *     
     */
    public ScanningRegionType getScanningRegion() {
        return scanningRegion;
    }

    /**
     * Sets the value of the scanningRegion property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScanningRegionType }
     *     
     */
    public void setScanningRegion(ScanningRegionType value) {
        this.scanningRegion = value;
    }

    /**
     * Gets the value of the gainMap property.
     * 
     * @return
     *     possible object is
     *     {@link GainMapType }
     *     
     */
    public GainMapType getGainMap() {
        return gainMap;
    }

    /**
     * Sets the value of the gainMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link GainMapType }
     *     
     */
    public void setGainMap(GainMapType value) {
        this.gainMap = value;
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
