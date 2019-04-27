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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UnderlayMask complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UnderlayMask">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rating" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}Rating" minOccurs="0"/>
 *         &lt;element name="resolutionBW" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="scmMask" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}SCMMask"/>
 *         &lt;element name="confidence" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}Confidence" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="maskPowerMarginMethod" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="TotalPower"/>
 *             &lt;enumeration value="MaximumPowerDensity"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnderlayMask", propOrder = {
    "rating",
    "resolutionBW",
    "scmMask",
    "confidence"
})
public class UnderlayMask {

    protected Rating rating;
    protected double resolutionBW;
    @XmlElement(required = true)
    protected SCMMask scmMask;
    protected Confidence confidence;
    @XmlAttribute(name = "maskPowerMarginMethod", required = true)
    protected String maskPowerMarginMethod;

    /**
     * Gets the value of the rating property.
     * 
     * @return
     *     possible object is
     *     {@link Rating }
     *     
     */
    public Rating getRating() {
        return rating;
    }

    /**
     * Sets the value of the rating property.
     * 
     * @param value
     *     allowed object is
     *     {@link Rating }
     *     
     */
    public void setRating(Rating value) {
        this.rating = value;
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
     *     {@link SCMMask }
     *     
     */
    public SCMMask getScmMask() {
        return scmMask;
    }

    /**
     * Sets the value of the scmMask property.
     * 
     * @param value
     *     allowed object is
     *     {@link SCMMask }
     *     
     */
    public void setScmMask(SCMMask value) {
        this.scmMask = value;
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

    /**
     * Gets the value of the maskPowerMarginMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaskPowerMarginMethod() {
        return maskPowerMarginMethod;
    }

    /**
     * Sets the value of the maskPowerMarginMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaskPowerMarginMethod(String value) {
        this.maskPowerMarginMethod = value;
    }

}
