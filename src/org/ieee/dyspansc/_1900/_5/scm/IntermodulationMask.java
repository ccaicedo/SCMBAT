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
 * <p>Java class for IntermodulationMask complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IntermodulationMask">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="order">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;minExclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="intermediateFrequency" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
 *               &lt;minExclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="highSideInjection" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="imCombiningMask" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}SCMMask"/>
 *         &lt;element name="imAmplificationMask" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}SCMMask" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IntermodulationMask", propOrder = {
    "order",
    "intermediateFrequency",
    "highSideInjection",
    "imCombiningMask",
    "imAmplificationMask"
})
public class IntermodulationMask {

    protected int order;
    protected Double intermediateFrequency;
    protected Boolean highSideInjection;
    @XmlElement(required = true)
    protected SCMMask imCombiningMask;
    protected SCMMask imAmplificationMask;

    /**
     * Gets the value of the order property.
     * 
     */
    public int getOrder() {
        return order;
    }

    /**
     * Sets the value of the order property.
     * 
     */
    public void setOrder(int value) {
        this.order = value;
    }

    /**
     * Gets the value of the intermediateFrequency property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getIntermediateFrequency() {
        return intermediateFrequency;
    }

    /**
     * Sets the value of the intermediateFrequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setIntermediateFrequency(Double value) {
        this.intermediateFrequency = value;
    }

    /**
     * Gets the value of the highSideInjection property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHighSideInjection() {
        return highSideInjection;
    }

    /**
     * Sets the value of the highSideInjection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHighSideInjection(Boolean value) {
        this.highSideInjection = value;
    }

    /**
     * Gets the value of the imCombiningMask property.
     * 
     * @return
     *     possible object is
     *     {@link SCMMask }
     *     
     */
    public SCMMask getImCombiningMask() {
        return imCombiningMask;
    }

    /**
     * Sets the value of the imCombiningMask property.
     * 
     * @param value
     *     allowed object is
     *     {@link SCMMask }
     *     
     */
    public void setImCombiningMask(SCMMask value) {
        this.imCombiningMask = value;
    }

    /**
     * Gets the value of the imAmplificationMask property.
     * 
     * @return
     *     possible object is
     *     {@link SCMMask }
     *     
     */
    public SCMMask getImAmplificationMask() {
        return imAmplificationMask;
    }

    /**
     * Sets the value of the imAmplificationMask property.
     * 
     * @param value
     *     allowed object is
     *     {@link SCMMask }
     *     
     */
    public void setImAmplificationMask(SCMMask value) {
        this.imAmplificationMask = value;
    }

}
