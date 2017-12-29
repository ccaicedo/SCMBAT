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
 * <p>Java class for SCMPolicyOrProtocolType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SCMPolicyOrProtocolType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pOrPName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pOrPParameters" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}pOrPParametersType" minOccurs="0"/>
 *         &lt;element name="pOrPIndex" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SCMPolicyOrProtocolType", propOrder = {
    "pOrPName",
    "pOrPParameters",
    "pOrPIndex"
})
public class SCMPolicyOrProtocolType {

    @XmlElement(required = true)
    protected String pOrPName;
    protected POrPParametersType pOrPParameters;
    protected Integer pOrPIndex;

    /**
     * Gets the value of the pOrPName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOrPName() {
        return pOrPName;
    }

    /**
     * Sets the value of the pOrPName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOrPName(String value) {
        this.pOrPName = value;
    }

    /**
     * Gets the value of the pOrPParameters property.
     * 
     * @return
     *     possible object is
     *     {@link POrPParametersType }
     *     
     */
    public POrPParametersType getPOrPParameters() {
        return pOrPParameters;
    }

    /**
     * Sets the value of the pOrPParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link POrPParametersType }
     *     
     */
    public void setPOrPParameters(POrPParametersType value) {
        this.pOrPParameters = value;
    }

    /**
     * Gets the value of the pOrPIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPOrPIndex() {
        return pOrPIndex;
    }

    /**
     * Sets the value of the pOrPIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPOrPIndex(Integer value) {
        this.pOrPIndex = value;
    }

}
